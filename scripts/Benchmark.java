package scripts;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;

import colecao.IColecao;
import dominio.Aluno;
import dominio.ComparadorAlunoPorMatricula;
import dominio.ComparadorAlunoPorNome;
import dominio.Main;
import listaencadeada.ListaEncadeada;
import arvorebinaria.*;

/**
 * Executa, sem interação, a bateria de medições exigida pela seção 3 do
 * relatório: montagem da lista, pesquisa por matrícula, pesquisa por nome e
 * remoção — para cada tamanho de arquivo e para cada regime (não-ordenada e
 * ordenada).
 *
 * O alvo das buscas é sempre o ÚLTIMO NÓ DA LISTA, e não o último registro do
 * arquivo: na lista não-ordenada a inserção se dá no início, de modo que a
 * ordem da estrutura é a inversa da ordem de leitura. É o último nó que força a
 * varredura completa e, portanto, exibe o pior caso.
 *
 * Uso: java scripts.Benchmark [arquivo1.txt arquivo2.txt ...]
 * Sem argumentos, processa todos os .txt da pasta 'dados' em ordem de tamanho.
 */

// Classe Benchmark criada pelo Claude, para gerar relatório de tempo gasto ao longo dos processos de ler arquivo, buscar element e remover elemnt
// Utiliza métodos criado por nós
public class Benchmark {

    private static final Comparator<Aluno> ComparaMatricula = new ComparadorAlunoPorMatricula();
    private static final Comparator<Aluno> ComparaNome = new ComparadorAlunoPorNome();
    private static final int REPETICOES_BUSCA = 11;
    private static final int QUANT_TESTES = 10;

    public static void main(String[] args) throws IOException {
        File[] arquivos = (args.length > 0)
                ? Arrays.stream(args).map(File::new).toArray(File[]::new)
                : Main.listarArquivosTxt("dados");


        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo de entrada encontrado.");
            return;
        }
        Arrays.sort(arquivos, Comparator.comparingLong(File::length));

        //File saida = new File("relatorio/medicoes.csv");
        File saida = new File("relatorio/medicoes_consolidadas.csv");
        saida.getParentFile().mkdirs();

        /* Chamada de metodos para medicao Lista Encadeada
        try (PrintWriter csv = new PrintWriter(new FileWriter(saida))) {
            csv.println("arquivo;n;regime;montagem_ms;busca_matricula_ms;busca_nome_ms;remocao_ms");
            System.out.printf("%-28s %10s %-16s %12s %12s %12s %12s%n",
                    "arquivo", "n", "regime", "montagem", "busca_matr", "busca_nome", "remocao");

            for (File arquivo : arquivos) {
                medir(arquivo, false, csv);
                medir(arquivo, true, csv);
            }
        }*/

        try (PrintWriter csv = new PrintWriter(new FileWriter(saida))) {

            // Header written only once
            csv.println(
                    "teste;arquivo;n;regime;montagem_ms;" +
                            "busca_matricula_ms;busca_nome_ms;remocao_ms"
            );

            System.out.printf(
                    "%-8s %-28s %10s %-16s%n",
                    "teste", "arquivo", "n", "regime"
            );

            // Repeat the complete benchmark 10 times
            for (int teste = 1; teste <= QUANT_TESTES; teste++) {

                System.out.println(
                        "\n========== TESTE " + teste +
                                "/" + QUANT_TESTES + " =========="
                );

                for (File arquivo : arquivos) {
                    medirArvoreBinaria(arquivo, csv, teste);
                    medirArvoreBinariaAvl(arquivo, csv, teste);
                }
            }
        }

        System.out.println("\nMedições gravadas em " + saida.getPath());
    }

    //__________________________________Medicao tempo Lista Encadeada
    private static void medir(File arquivo, boolean ordenada, PrintWriter csv) {
        ListaEncadeada<Aluno> lista = new ListaEncadeada<>(ComparaMatricula, ordenada);
        IColecao<Aluno> colecao = lista;

        long inicio = System.nanoTime();
        int n = Main.carregarTxt(arquivo.getPath(), colecao);
        double montagem = ms(inicio, System.nanoTime());

        Aluno alvo = lista.ultimoValor(); // Aqui define qual elemento ele quer buscar
        if (alvo == null) {
            return;
        }

        double buscaMatricula = medianaDeBusca(
                lista,
                new Aluno(alvo.getMatricula(), "", 0),
                ComparaMatricula
        );

        double buscaNome = medianaDeBusca(
                lista,
                new Aluno(0, alvo.getNome(), 0),
                ComparaNome
        );

        inicio = System.nanoTime();
        boolean removido = lista.remover(new Aluno(alvo.getMatricula(), "", 0), ComparaMatricula);
        double remocao = ms(inicio, System.nanoTime());

        if (!removido) {
            System.out.println("AVISO: falha ao remover o alvo em " + arquivo.getName());
        }

        String regime = ordenada ? "ordenada" : "nao-ordenada";
        csv.printf("%s;%d;%s;%.4f;%.4f;%.4f;%.4f%n",
                arquivo.getName(), n, regime, montagem, buscaMatricula, buscaNome, remocao);
        System.out.printf("%-28s %10d %-16s %12.3f %12.4f %12.4f %12.4f%n",
                arquivo.getName(), n, regime, montagem, buscaMatricula, buscaNome, remocao);
    }

    private static double medianaDeBusca(
            ListaEncadeada<Aluno> lista,
            Aluno chave,
            Comparator<Aluno> criterio) {

        double[] tempos = new double[REPETICOES_BUSCA];

        for (int i = 0; i < REPETICOES_BUSCA; i++) {
            long inicio = System.nanoTime();

            Aluno resultado = lista.pesquisar(chave, criterio);

            tempos[i] = ms(inicio, System.nanoTime());

            if (resultado == null) {
                System.out.println("AVISO: aluno não encontrado durante a busca.");
            }// Check to see what it found was not null
        }

        System.out.println(Arrays.toString(tempos));

        Arrays.sort(tempos);

        return tempos[REPETICOES_BUSCA / 2];
    }

    //__________________________________Medicao tempo Arvore Binaria
    private static void medirArvoreBinaria(File arquivo, PrintWriter csv, int teste) {
        ArvoreBinaria<Aluno> arvoreBinaria = new ArvoreBinaria<Aluno>(new ComparadorAlunoPorMatricula());
        IColecao<Aluno> colecao = arvoreBinaria;

        long inicio = System.nanoTime();
        int n = Main.carregarTxt(arquivo.getPath(), colecao);
        double montagem = ms(inicio, System.nanoTime());

        Aluno alvo = arvoreBinaria.folhaMaisProfunda(); // Aqui define qual elemento ele quer buscar
        if (alvo == null) {
            return;
        }

        double buscaMatricula = medianaDeBuscaArvore(
                arvoreBinaria,
                new Aluno(alvo.getMatricula(), "", 0),
                ComparaMatricula
        );

        double buscaNome = medianaDeBuscaArvore(
                arvoreBinaria,
                new Aluno(0, alvo.getNome(), 0),
                ComparaNome
        );

        inicio = System.nanoTime();
        boolean removido = arvoreBinaria.remover(new Aluno(alvo.getMatricula(), "", 0));
        double remocao = ms(inicio, System.nanoTime());

        if (!removido) {
            System.out.println("AVISO: falha ao remover o alvo em " + arquivo.getName());
        }

        String regime = "Arvore Binaria";
        csv.printf("%d;%s;%d;%s;%.4f;%.4f;%.4f;%.4f%n",
                teste,arquivo.getName(), n, regime, montagem, buscaMatricula, buscaNome, remocao);
        System.out.printf("%-28s %10d %-16s %12.3f %12.4f %12.4f %12.4f%n",
                arquivo.getName(), n, regime, montagem, buscaMatricula, buscaNome, remocao);
    }

    private static double medianaDeBuscaArvore(
            ArvoreBinaria<Aluno> arvoreBinaria,
            Aluno chave,
            Comparator<Aluno> criterio) {

        double[] tempos = new double[REPETICOES_BUSCA];

        for (int i = 0; i < REPETICOES_BUSCA; i++) {
            long inicio = System.nanoTime();

            Aluno resultado = arvoreBinaria.pesquisar(chave, criterio);

            tempos[i] = ms(inicio, System.nanoTime());

            if (resultado == null) {
                System.out.println("AVISO: aluno não encontrado durante a busca.");
            }// Check to see what it found was not null
        }

        System.out.println(Arrays.toString(tempos));

        Arrays.sort(tempos);

        return tempos[REPETICOES_BUSCA / 2];
    }

    //__________________________________Medicao tempo Arvore Binaria AVL
    private static void medirArvoreBinariaAvl(File arquivo, PrintWriter csv, int teste) {
        ArvoreAVL<Aluno> arvoreAvl = new ArvoreAVL<Aluno>(new ComparadorAlunoPorMatricula());
        IColecao<Aluno> colecao = arvoreAvl;

        long inicio = System.nanoTime();
        int n = Main.carregarTxt(arquivo.getPath(), colecao);
        double montagem = ms(inicio, System.nanoTime());

        Aluno alvo = arvoreAvl.folhaMaisProfunda(); // Aqui define qual elemento ele quer buscar
        if (alvo == null) {
            return;
        }

        double buscaMatricula = medianaDeBuscaArvoreAvl(
                arvoreAvl,
                new Aluno(alvo.getMatricula(), "", 0),
                ComparaMatricula
        );

        double buscaNome = medianaDeBuscaArvoreAvl(
                arvoreAvl,
                new Aluno(0, alvo.getNome(), 0),
                ComparaNome
        );

        inicio = System.nanoTime();
        boolean removido = arvoreAvl.remover(new Aluno(alvo.getMatricula(), "", 0));
        double remocao = ms(inicio, System.nanoTime());

        if (!removido) {
            System.out.println("AVISO: falha ao remover o alvo em " + arquivo.getName());
        }

        String regime = "Arvore AVL";
        csv.printf("%d;%s;%d;%s;%.4f;%.4f;%.4f;%.4f%n",
                teste,arquivo.getName(), n, regime, montagem, buscaMatricula, buscaNome, remocao);
        System.out.printf("%-28s %10d %-16s %12.3f %12.4f %12.4f %12.4f%n",
                arquivo.getName(), n, regime, montagem, buscaMatricula, buscaNome, remocao);
    }

    private static double medianaDeBuscaArvoreAvl(
            ArvoreAVL<Aluno> arvoreAVL,
            Aluno chave,
            Comparator<Aluno> criterio) {

        double[] tempos = new double[REPETICOES_BUSCA];

        for (int i = 0; i < REPETICOES_BUSCA; i++) {
            long inicio = System.nanoTime();

            Aluno resultado = arvoreAVL.pesquisar(chave, criterio);

            tempos[i] = ms(inicio, System.nanoTime());

            if (resultado == null) {
                System.out.println("AVISO: aluno não encontrado durante a busca.");
            }// Check to see what it found was not null
        }

        System.out.println(Arrays.toString(tempos));

        Arrays.sort(tempos);

        return tempos[REPETICOES_BUSCA / 2];
    }

    private static double ms(long inicio, long fim) {
        return (fim - inicio) / 1_000_000.0;
    }
}