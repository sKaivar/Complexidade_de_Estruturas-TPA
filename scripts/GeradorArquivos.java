package scripts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/** Gera um arquivo entrada.txt contendo contatos no formato:
 *  Nome Sobrenome;Telefone */
/**
public class GeradorArquivos {

    private static final int QUANTIDADE = 10000;

    public static void main(String[] args) {

        File pasta = new File("dados");

        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File arquivo = new File(pasta, "entrada.txt");

        long inicio = System.nanoTime();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {

            for (int i = 1; i <= QUANTIDADE; i++) {

                String nome = gerarNome(i);
                String telefone = gerarTelefone(i);

                writer.write(nome + ";" + telefone);
                writer.newLine();
            }

            long fim = System.nanoTime();

            double tempoMs = (fim - inicio) / 1_000_000.0;

            System.out.println("Arquivo gerado com sucesso!");
            System.out.println("Local: " + arquivo.getAbsolutePath());
            System.out.println("Contatos gerados: " + QUANTIDADE);
            System.out.printf("Tempo de geração: %.2f ms%n", tempoMs);

        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo: " + e.getMessage());
        }
    }

    private static String gerarNome(int i) {
        String[] nomes = {
            "Ana", "Bruno", "Carla", "Daniel", "Eduarda",
            "Felipe", "Gabriela", "Henrique", "Isabela", "João"
        };

        String[] sobrenomes = {
            "Silva", "Souza", "Oliveira", "Santos", "Pereira",
            "Costa", "Rodrigues", "Almeida", "Nascimento", "Lima"
        };

        return nomes[i % nomes.length] + " "
             + sobrenomes[i % sobrenomes.length] + i;
    }

    private static String gerarTelefone(int i) {
        return String.format("279%08d", i);
    }
}**/
/** Gera um arquivo entrada.txt contendo alunos no formato:
 *  matricula;nome;nota */
public class GeradorArquivos {

    private static final int[] TAMANHOS = {
            100, 25000, 50000, 100000, 200000
    };

    private static final Random RANDOM = new Random();

    // Representa um registro de aluno
    private static class Registro {
        int matricula;
        String nome;
        int nota;

        Registro(int matricula, String nome, int nota) {
            this.matricula = matricula;
            this.nome = nome;
            this.nota = nota;
        }

        String paraLinha() {
            return String.format(
                    "%08d;%s;%d",
                    matricula, nome, nota
            );
        }
    }

    public static void main(String[] args) throws IOException {

        File pasta = new File("dados");

        if (!pasta.exists() && !pasta.mkdirs()) {
            throw new IOException("Não foi possível criar a pasta dados.");
        }

        //File arquivo = new File(pasta, "Matricula_Aleatoria_entrada"+ QUANTIDADE +".txt");
        //File arquivo = new File(pasta, "entrada"+ QUANTIDADE +".txt");

        long inicio = System.nanoTime();

        for (int quantidade : TAMANHOS) {

            System.out.println(
                    "\nGerando arquivos com " + quantidade + " alunos..."
            );

            // Gera os registros com matrículas únicas
            List<Registro> registros = gerarRegistros(quantidade);

            // Ordena os registros por matrícula crescente
            registros.sort(
                    Comparator.comparingInt(
                            (Registro r) -> r.matricula
                    )
            );

            // 1. Arquivo que produz uma árvore degenerada
            File arquivoOrdenado = new File(
                    pasta, "entrada" + quantidade + ".txt"
            );

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(arquivoOrdenado))) {

                for (Registro registro : registros) {
                    writer.write(registro.paraLinha());
                    writer.newLine();
                }
            }

            // 2. Arquivo que produz uma árvore balanceada
            File arquivoBalanceado = new File(
                    pasta, "entrada" + quantidade + "_bal.txt"
            );

            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter(arquivoBalanceado))) {

                escreverBalanceado(
                        writer, registros, 0, registros.size() - 1
                );
            }

            System.out.println(
                    "Gerado: " + arquivoOrdenado.getName()
            );

            System.out.println(
                    "Gerado: " + arquivoBalanceado.getName()
            );
        }

        long fim = System.nanoTime();

        System.out.printf(
                "\nTodos os arquivos foram gerados em %.2f ms.%n",
                (fim - inicio) / 1_000_000.0
        );
    }

    // Gera alunos com matrículas aleatórias e únicas
    private static List<Registro> gerarRegistros(int quantidade) {

        List<Registro> registros = new ArrayList<>(quantidade);
        Set<Integer> matriculasUtilizadas = new HashSet<>();

        for (int i = 1; i <= quantidade; i++) {

            int matricula;

            do {
                matricula = RANDOM.nextInt(99_999_999) + 1;
            } while (!matriculasUtilizadas.add(matricula));

            String nome = gerarNome(i);
            int nota = RANDOM.nextInt(11);

            registros.add(
                    new Registro(matricula, nome, nota)
            );
        }

        return registros;
    }

    // Escreve a mediana primeiro e, em seguida,
    // as metades esquerda e direita recursivamente.
    private static void escreverBalanceado(
            BufferedWriter writer,
            List<Registro> registros,
            int inicio,
            int fim) throws IOException {

        if (inicio > fim) {
            return;
        }

        int meio = inicio + (fim - inicio) / 2;

        // Primeiro, insere o elemento central
        writer.write(registros.get(meio).paraLinha());
        writer.newLine();

        // Depois, constrói a subárvore esquerda
        escreverBalanceado(
                writer, registros, inicio, meio - 1
        );

        // Por último, constrói a subárvore direita
        escreverBalanceado(
                writer, registros, meio + 1, fim
        );
    }

    private static String gerarNome(int i) {

        String[] nomes = {
                "Ana", "Bruno", "Carla", "Daniel", "Eduarda",
                "Felipe", "Gabriela", "Henrique", "Isabela", "João"
        };

        String[] sobrenomes = {
                "Silva", "Souza", "Oliveira", "Santos", "Pereira",
                "Costa", "Rodrigues", "Almeida", "Nascimento", "Lima"
        };

        return nomes[i % nomes.length] + " "
                + sobrenomes[i % sobrenomes.length];
    }
}

    /*
    private static String gerarMatricula(int i) {
        return String.format("%08d", i);
        //%d pede para colocar um inteiro naquele lugar, o ~i~
        //O 08, no meio do %d fala para colocar 8 zeros naquele lugar
        //Juntos eles ficariam assim: 00000000i
    }

    private static String gerarMatriculaAletoria(Set<Integer> utilizadas) {

        int matricula;

        do {
            // Random number from 1 to 99,999,999
            matricula = RANDOM.nextInt(99_999_999) + 1;

        } while (!utilizadas.add(matricula));

        // Keep the matrícula at 8 digits, including leading zeros
        return String.format("%08d", matricula);
    }

    private static String gerarMatricula(int i) {
        return String.format("%08d", i);
        //%d pede para colocar um inteiro naquele lugar, o ~i~
        //O 08, no meio do %d fala para colocar 8 zeros naquele lugar
        //Juntos eles ficariam assim: 00000000i
    }

    private static Integer gerarNota() {
        //Numero aleatorio entre 0 e 10 (11 numeros: 0,1,2,3...10)
        return RANDOM.nextInt(11);

    }
}*/
