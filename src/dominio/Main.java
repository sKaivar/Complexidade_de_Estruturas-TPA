/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Scanner;

import colecao.IColecao;
import listaencadeada.*;

/**
 *
 * @author victoriocarvalho
 */
public class Main {

    private static final Comparator<Aluno> ComparaMatricula = new ComparadorAlunoPorMatricula();

    public static int carregarTxt(String nomeArquivo, IColecao<Aluno> l){
        int total = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.isBlank()) {
                    continue;
                }
                String[] dados = linha.split(";");
                if (dados.length < 3) {
                    continue;
                }
                Aluno aluno = new Aluno(
                        Integer.parseInt(dados[0].trim()),
                        dados[1].trim(),
                        Integer.parseInt(dados[2].trim()));

                if (l.adicionar(aluno)) {
                    total++;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Arquivo com formato inválido: " + e.getMessage());
        }

        return total;
    }

    public static File[] listarArquivosTxt(String pasta){

        File diretorio = new File(pasta);

        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt")); // Lista todos arquivos txt em uma pasta

        return arquivos;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        IColecao<Aluno> l; //Object type IColecao
        l = new ListaEncadeada<Aluno>(new ComparadorAlunoPorMatricula(), false);//Initiates List
        Aluno a;//Empty object class Aluno
        int ordenado = 0, mat, nota, resp = 10; //Ordenado (tells if user wants an ordered list or not), mat (matricula), nota, resp(any integer choice of user)
        String nome, todosAlunos; //nome (Aluno`s name), todoAlunos (print array with all students)

        Scanner scanner = new Scanner(System.in);

        do {
            try {
                System.out.println("Digite 1 para lista ordenada e 2 para lista não ordenada: ");
                ordenado = scanner.nextInt();
                scanner.nextLine();

                if(ordenado != 1 && ordenado != 2){
                    System.out.println("Digite apenas números.\n");
                }
            }catch (Exception e) {
                System.out.println("ERRO! " + e.getMessage());
                scanner.nextLine();
                ordenado = 0;

            }

        }while(ordenado != 1 && ordenado != 2);

        if(ordenado == 1){
            l = new ListaEncadeada<Aluno>(new ComparadorAlunoPorMatricula(), true);//Object l has an empty list as value (by matricula)
            //l = new ListaEncadeada<Aluno>(new ComparadorAlunoPorNome(), true); //Object l has an empty list as value (by name)

        } else if (ordenado == 2) {
            l = new ListaEncadeada<Aluno>(new ComparadorAlunoPorMatricula(), false);//Object l has an empty list as value (It doesnt matter if by name or matricula)


        }else{
            System.out.println("Erro em decidir se lista é ordenada ou não.\n");
        }

        //Start of Menu
        do{
            try{
                System.out.println("___________");
                System.out.println("Menu: ");
                System.out.println("1: Carregar alunos de arquivo");
                System.out.println("2: Adicionar aluno");
                System.out.println("3: Pesquisar aluno por nome");
                System.out.println("4: Pesquisar aluno por matrícula");
                System.out.println("5: Remover aluno por matrícula");
                System.out.println("6: Alterar dados de um aluno");
                System.out.println("0: Sair");

                resp = scanner.nextInt();

                switch (resp){
                    case 1:
                        try{
                            File[] arquivos = listarArquivosTxt("dados"); //Todos arquivos dentro da pasta dados

                            System.out.println("Escolha um dos arquivos a seguir (estão todos dentro da pasta dados):");

                            for (int i = 0; i < arquivos.length; i++){
                                System.out.println((i+1) + " - " + arquivos[i].getName());
                            }

                            resp = scanner.nextInt();
                            scanner.nextLine();// Receives Choosen File

                            if(resp < 1 || resp > arquivos.length){
                                System.out.println("Arquivo não encontrado");
                                break;
                            }// Se resposta for menor que 1 ou resposta maior que quantidade de arquivos, devolve erro

                            File arquivoEscolhido = arquivos[resp - 1];

                            long inicio = System.nanoTime();// Inicio da contagem de tempo
                            carregarTxt(arquivoEscolhido.getPath(), l);
                            long fim = System.nanoTime(); // Final contagem de tempo

                            double tempoMs = (fim - inicio) / 1_000_000.0; // Calculo contagem de tempo

                            System.out.printf("Tempo de leitura: %.2f ms%n", tempoMs);



                        } catch (Exception e) {
                            System.out.println("ERRO! " + e.getMessage());
                            scanner.nextLine();
                        }// Qualquer outra coisa, devolve erro



                        //loads choosen file with carregarTxt();
                        break;
                    case 2:
                        try{

                            System.out.println("Digite a matricula do aluno");
                            mat = scanner.nextInt();
                            scanner.nextLine();// Receives Student number

                            if (buscar(l, new Aluno(mat, "", 0), ComparaMatricula) != null) {
                                System.out.println("Já existe um aluno com a matrícula " + mat + ". Operação cancelada.");
                                break;
                            }


                            System.out.println("Digite o nome do aluno");
                            nome = scanner.nextLine();// Receives Student name

                            System.out.println("Digite a nota do aluno");
                            nota = scanner.nextInt(); // Receives Student grade

                            a = new Aluno(mat, nome, nota);
                            l.adicionar(a);// Adds new Student to array

                            todosAlunos = l.toString();
                            System.out.println(todosAlunos);// Prints all students in array
                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("Se atente ao preencher os dados do aluno." + e.getMessage());
                        }

                        break;

                    case 3:
                        try {
                            System.out.println("Digite o nome do aluno a pesquisar");
                            scanner.nextLine();
                            nome = scanner.nextLine();

                            Aluno chaveNome = new Aluno(0, nome, 0);

                            long inicioPesqNome = System.nanoTime();
                            Aluno encontradoPorNome = ((ListaEncadeada<Aluno>) l)
                                    .pesquisar(chaveNome, new ComparadorAlunoPorNome());
                            long fimPesqNome = System.nanoTime();

                            double tempoPesqNomeMs = (fimPesqNome - inicioPesqNome) / 1_000_000.0;

                            if (encontradoPorNome != null) {
                                System.out.println("Aluno encontrado: " + encontradoPorNome);
                            } else {
                                System.out.println("Aluno com nome \"" + nome + "\" não encontrado.");
                            }
                            System.out.printf("Tempo de pesquisa: %.4f ms%n", tempoPesqNomeMs);

                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("ERRO! " + e.getMessage());
                        }
                        break;
                    case 4:
                        try {
                            System.out.println("Digite a matrícula do aluno a pesquisar");
                            mat = scanner.nextInt();
                            scanner.nextLine();

                            Aluno chaveMatricula = new Aluno(mat, "", 0);

                            long inicioPesqMat = System.nanoTime();
                            // Método da interface -> usa o comparador definido na construção da lista (matrícula)
                            Aluno encontradoPorMatricula = l.pesquisar(chaveMatricula);
                            long fimPesqMat = System.nanoTime();

                            double tempoPesqMatMs = (fimPesqMat - inicioPesqMat) / 1_000_000.0;

                            if (encontradoPorMatricula != null) {
                                System.out.println("Aluno encontrado: " + encontradoPorMatricula);
                            } else {
                                System.out.println("Aluno com matrícula " + mat + " não encontrado.");
                            }
                            System.out.printf("Tempo de pesquisa: %.4f ms%n", tempoPesqMatMs);

                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("ERRO! " + e.getMessage());
                        }
                        break;
                    case 5:
                        try {
                            System.out.println("Digite a matrícula do aluno a remover:");
                            mat = scanner.nextInt();
                            scanner.nextLine();

                            // Cria-se a chave de busca
                            Aluno chaveRemover = new Aluno(mat, "", 0);

                            long inicioRem = System.nanoTime();

                            // Busca prévia para resgatar o nome (assumindo que pesquisar retorna o objeto T)
                            Aluno alunoEncontrado = l.pesquisar(chaveRemover);

                            // Executa o seu método original que retorna boolean
                            boolean removido = l.remover(chaveRemover);

                            long fimRem = System.nanoTime();

                            double tempoRemMs = (fimRem - inicioRem) / 1_000_000.0;

                            // Valida se a exclusão foi true e se a busca prévia encontrou o aluno
                            if (removido && alunoEncontrado != null) {
                                System.out.println("Aluno(a) " + alunoEncontrado.getNome() + " (Matrícula " + mat + ") removido com sucesso!");
                            } else {
                                System.out.println("Aluno com matrícula " + mat + " não encontrado.");
                            }
                            System.out.printf("Tempo de remoção: %.4f ms%n", tempoRemMs);

                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("ERRO! " + e.getMessage());
                        }
                        break;
                    case 6:
                        try{
                            System.out.println("Digite a matrícula do que deseja alterar");
                            mat = scanner.nextInt();
                            scanner.nextLine();

                            Aluno chaveMatricula = new Aluno(mat, "", 0);
                            Aluno encontradoPorMatricula = l.pesquisar(chaveMatricula); // Procura por Aluno que deseja alterar

                            if (encontradoPorMatricula != null) {
                                System.out.println("Aluno encontrado: " + encontradoPorMatricula);
                            } else {
                                System.out.println("Aluno com matrícula " + mat + " não encontrado.");
                                break;
                            }

                            System.out.println("O que deseja alterar?");
                            System.out.println("1: Matricula");
                            System.out.println("2: Nome");
                            System.out.println("3: Nota");
                            resp = scanner.nextInt();
                            scanner.nextLine();

                            try{
                                if(resp == 1){
                                    System.out.println("Digite a nova matricula:");
                                    mat = scanner.nextInt();
                                    scanner.nextLine();// Receives Student number

                                    if (buscar(l, new Aluno(mat, "", 0), ComparaMatricula) != null) {
                                        System.out.println("Já existe um aluno com a matrícula " + mat + ". Operação cancelada.");
                                        break;
                                    }

                                    encontradoPorMatricula.setMatricula(mat);

                                }else if(resp == 2){
                                    System.out.println("Digite a novo nome:");
                                    nome = scanner.nextLine();// Receives Student name

                                    encontradoPorMatricula.setNome(nome);

                                }else if(resp == 3){
                                    System.out.println("Digite a nova nota:");
                                    nota = scanner.nextInt();// Receives Student nota
                                    scanner.nextLine();

                                    encontradoPorMatricula.setNota(nota);
                                }

                            } catch (Exception e) {
                                scanner.nextLine();
                                System.out.println("ERRO! " + e.getMessage());
                            }

                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("ERRO! " + e.getMessage());
                        }
                        break;
                }

            }catch (Exception e) {
                scanner.nextLine();// Cleans if input is wrong type
                System.out.println("ERRO! " + e.getMessage());// Tells the error message
            }

        }while(resp != 0);
        System.out.println("A quantidade total de Alunos é " + l.quantidadeNos());
        System.out.println("Programa encerrado.");
        scanner.close();

    }

    private static Aluno buscar(IColecao<Aluno> lista, Aluno chave, Comparator<Aluno> criterio) {
        if (lista instanceof ListaEncadeada) {
            return ((ListaEncadeada<Aluno>) lista).pesquisar(chave, criterio);
        }
        return lista.pesquisar(chave);
    }
}
