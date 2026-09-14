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

    //______________________________________Carregar TXT
    public static int carregarTxt(String nomeArquivo, IColecao<Aluno> l){
        int total = 0;// Numero total de alunos

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {// Enquanto linha nao for null continua
                if (linha.isBlank()) {
                    continue;
                }

                String[] dados = linha.split(";");// Identifica como dados sao separados
                if (dados.length < 3) {
                    continue;
                }
                Aluno aluno = new Aluno(
                        Integer.parseInt(dados[0].trim()),
                        dados[1].trim(),
                        Integer.parseInt(dados[2].trim()));// Registra matricula, nome e nota em um novo aluno

                if (l.adicionar(aluno)) {
                    total++;
                }// Adiciona aluno em lista
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Arquivo com formato inválido: " + e.getMessage());
        }

        return total;// retorna total de Alunos registrados
    }

    public static File[] listarArquivosTxt(String pasta){

        File diretorio = new File(pasta);

        File[] arquivos = diretorio.listFiles((dir, nome) -> nome.endsWith(".txt")); // Lista todos arquivos txt em uma pasta

        return arquivos;
    }

    //______________________________________Menu Principal
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

        //______________________________________Escolha de ordenar ou nao a lista
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

        } else if (ordenado == 2) {
            l = new ListaEncadeada<Aluno>(new ComparadorAlunoPorMatricula(), false);//Object l has an empty list as value (It doesnt matter if by name or matricula)


        }else{
            System.out.println("Erro em decidir se lista é ordenada ou não.\n");
        }

        //______________________________________Inicia Menu
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
                    //______________________________________Carregar dados de arquivo
                    case 1:
                        try{
                            File[] arquivos = listarArquivosTxt("dados"); //Todos arquivos dentro da pasta dados

                            System.out.println("Escolha um dos arquivos a seguir (estão todos dentro da pasta dados):");

                            for (int i = 0; i < arquivos.length; i++){
                                System.out.println((i+1) + " - " + arquivos[i].getName());
                            }// Imprime todos os arquivos encontrados

                            resp = scanner.nextInt();
                            scanner.nextLine();// Resgata numero do arquivo escolhido

                            if(resp < 1 || resp > arquivos.length){
                                System.out.println("Arquivo não encontrado");
                                break;
                            }// Se resposta for menor que 1 ou resposta maior que quantidade de arquivos, devolve erro

                            File arquivoEscolhido = arquivos[resp - 1];// Registra arquivo escolhido

                            long inicio = System.nanoTime();// Inicio da contagem de tempo

                            carregarTxt(arquivoEscolhido.getPath(), l);// Carrega elementos do TXT na lista

                            long fim = System.nanoTime(); // Final contagem de tempo
                            double tempoMs = (fim - inicio) / 1_000_000.0; // Calculo contagem de tempo

                            System.out.printf("Tempo de leitura: %.2f ms%n", tempoMs);// Imprime tempo total



                        } catch (Exception e) {
                            System.out.println("ERRO! " + e.getMessage());
                            scanner.nextLine();
                        }// Qualquer outra coisa, devolve erro
                        break;

                   //______________________________________Adicionar um Aluno
                    case 2:
                        try{

                            System.out.println("Digite a matricula do aluno");
                            mat = scanner.nextInt();
                            scanner.nextLine();// Receives Student number

                            if (l.pesquisar(new Aluno(mat, "", 0)) != null) {
                                System.out.println("Já existe um aluno com a matrícula " + mat + ". Operação cancelada.");
                                break;
                            }// Verifica se matricula esta sendo usada

                            System.out.println("Digite o nome do aluno");
                            nome = scanner.nextLine();// Receives Student name

                            System.out.println("Digite a nota do aluno");
                            nota = scanner.nextInt(); // Receives Student grade

                            a = new Aluno(mat, nome, nota);
                            l.adicionar(a);// Adiciona aluno na lista

                            todosAlunos = l.toString();
                            System.out.println(todosAlunos);// Prints all students in array

                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("Se atente ao preencher os dados do aluno." + e.getMessage());
                        }

                        break;
                    //______________________________________Pesquisar Aluno por nome
                    case 3:
                        try {
                            System.out.println("Digite o nome do aluno a pesquisar");
                            scanner.nextLine();
                            nome = scanner.nextLine();// Resgata nome inserido por usuario

                            long inicioPesqNome = System.nanoTime();// Inicio da contagem de tempo

                            ListaEncadeada<Aluno> encontrados =
                                    ((ListaEncadeada<Aluno>) l).pesquisarTodosporNome(nome); // Pesquisa todos Alunos com mesmo nome

                            if (encontrados.quantidadeNos() == 0) {
                                System.out.println("Nenhum aluno encontrado com o nome: " + nome);
                                break;
                            }// Se nao encontrar ninguem retorna que nao existe

                            System.out.println("\nAlunos encontrados:");
                            System.out.println(encontrados);// Se encontrar, mostra todos que encontrou

                            System.out.println("\nDigite a matrícula do aluno que deseja selecionar:");
                            int matricula = scanner.nextInt();
                            scanner.nextLine();// Pega a matricula de quais dele o usuario deseja

                            Aluno encontradoPorMatricula = encontrados.pesquisar(
                                    new Aluno(matricula, "", 0),
                                    new ComparadorAlunoPorMatricula()
                            );// Procura o Aluno com matricula que ele digitou

                            if (encontradoPorMatricula == null) {
                                System.out.println("Nenhum aluno encontrado com a matrícula " + matricula);
                                break;
                            }// Se for null, retorna que nao encontrou

                            System.out.println("Aluno selecionado: " + encontradoPorMatricula);// Se for qualquer outra coisa, retorna o que encontrou

                            long fimPesqNome = System.nanoTime();// Para de contar o tempo

                            double tempoPesqNomeMs = (fimPesqNome - inicioPesqNome) / 1_000_000.0;// Faz o calculo de tempo total
                            System.out.printf("Tempo de pesquisa: %.4f ms%n", tempoPesqNomeMs);// Registra o tempo total

                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("ERRO! " + e.getMessage());
                        }
                        break;

                    //______________________________________Pesquisa aluno por matricula
                    case 4:
                        try {
                            System.out.println("Digite a matrícula do aluno a pesquisar");
                            mat = scanner.nextInt();
                            scanner.nextLine();// Resgata matricula digitada por usuario

                            Aluno chaveMatricula = new Aluno(mat, "", 0);// Salva matricula em novo aluno

                            long inicioPesqMat = System.nanoTime();// Inicia tempo

                            // Metodo da interface -> usa o comparador definido na construção da lista (matrícula)
                            Aluno encontradoPorMatricula = l.pesquisar(chaveMatricula);// Acha aluno e salva ele

                            long fimPesqMat = System.nanoTime();// Termina o tempo
                            double tempoPesqMatMs = (fimPesqMat - inicioPesqMat) / 1_000_000.0;// Faz calculo de tempo

                            if (encontradoPorMatricula != null) {// Se aluno que foi utilizado para salvar nao estiver vazio
                                System.out.println("Aluno encontrado: " + encontradoPorMatricula);

                            } else {//Qualquer outra coisa, nao encontrou aluno
                                System.out.println("Aluno com matrícula " + mat + " não encontrado.");
                            }
                            System.out.printf("Tempo de pesquisa: %.4f ms%n", tempoPesqMatMs);// Mostra tempo total gasto

                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("ERRO! " + e.getMessage());
                        }
                        break;

                    //______________________________________Remover aluno
                    case 5:
                        try {
                            System.out.println("Digite a matrícula do aluno a remover:");
                            mat = scanner.nextInt();
                            scanner.nextLine();// Resgata matricula dada por usuario


                            Aluno chaveRemover = new Aluno(mat, "", 0);// Cria-se a chave de busca

                            Aluno alunoEncontrado = l.pesquisar(chaveRemover);// Busca prévia para resgatar o nome (assumindo que pesquisar retorna o objeto T)

                            long inicioRem = System.nanoTime();// Inicia tempo

                            boolean removido = l.remover(chaveRemover);// Executa o seu metodo, retorna boolean

                            long fimRem = System.nanoTime();// Termina tempo
                            double tempoRemMs = (fimRem - inicioRem) / 1_000_000.0;// Calcula tempo

                            // Valida se a exclusão foi true e se a busca prévia encontrou o aluno
                            if (removido && alunoEncontrado != null) {
                                System.out.println("Aluno(a) " + alunoEncontrado.getNome() + " (Matrícula " + mat + ") removido com sucesso!");
                            } else {
                                System.out.println("Aluno com matrícula " + mat + " não encontrado.");
                            }
                            System.out.printf("Tempo de remoção: %.4f ms%n", tempoRemMs);// Mostra tempo total gasto

                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("ERRO! " + e.getMessage());
                        }
                        break;

                    //______________________________________Alterar dados de usuario
                    case 6:
                        try{
                            System.out.println("Digite a matrícula do que deseja alterar");
                            mat = scanner.nextInt();
                            scanner.nextLine();// Recebe matricula do Aluno que deseja alterar

                            Aluno chaveMatricula = new Aluno(mat, "", 0);
                            Aluno encontradoPorMatricula = l.pesquisar(chaveMatricula); // Procura por Aluno que deseja alterar

                            if (encontradoPorMatricula != null) {
                                System.out.println("Aluno encontrado: " + encontradoPorMatricula);// Se for diferente de null, retorna aluno
                            } else {
                                System.out.println("Aluno com matrícula " + mat + " não encontrado.");// Qualquer outra coisa, retorna que nao encontrou
                                break;
                            }

                            System.out.println("O que deseja alterar?");
                            System.out.println("1: Matricula");
                            System.out.println("2: Nome");
                            System.out.println("3: Nota");
                            resp = scanner.nextInt();
                            scanner.nextLine();// Resgata o que usuario deseja alterar

                            try{
                                if(resp == 1){
                                    System.out.println("Digite a nova matricula:");
                                    mat = scanner.nextInt();
                                    scanner.nextLine();// Recebe nova matricula


                                    if (l.pesquisar(new Aluno(mat, "", 0)) != null) {
                                        System.out.println("Já existe um aluno com a matrícula " + mat + ". Operação cancelada.");
                                        break;
                                    }// Se ja existir um aluno com a matricula nova que usuario digitou, cancela operacao

                                    l.remover(new Aluno(encontradoPorMatricula.getMatricula(), "", 0));// Remove antigo aluno
                                    l.adicionar(new Aluno(mat, encontradoPorMatricula.getNome(), encontradoPorMatricula.getNota()));// Adiciona ele com matricula nova, e refaz ordenacao

                                }else if(resp == 2){
                                    System.out.println("Digite a novo nome:");
                                    nome = scanner.nextLine();// Recebe novo nome

                                    encontradoPorMatricula.setNome(nome);// Atualiza o nome do aluno

                                }else if(resp == 3){
                                    System.out.println("Digite a nova nota:");
                                    nota = scanner.nextInt();// Recebe nova nota
                                    scanner.nextLine();

                                    encontradoPorMatricula.setNota(nota);// Atualiza nota nova do aluno
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
        System.out.println("A quantidade total de Alunos é " + l.quantidadeNos());// Retorna quantidade total de alunos registrados
        System.out.println("Programa encerrado.");
        scanner.close();

    }
}
