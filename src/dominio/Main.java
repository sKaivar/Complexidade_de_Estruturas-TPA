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
import java.util.Scanner;

import colecao.IColecao;
import listaencadeada.*;

/**
 *
 * @author victoriocarvalho
 */
public class Main {

    public static void carregarTxt(String nomeArquivo, IColecao<Aluno> l){
        int totalAlunos = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))){

            String linha;


            while((linha = br.readLine()) != null){

                String[] dados = linha.split(";");

                int matricula = Integer.parseInt(dados[0]);
                String nome = dados[1];
                int nota = Integer.parseInt(dados[2]);


                Aluno aluno = new Aluno(matricula, nome, nota);

                l.adicionar(aluno);
                totalAlunos +=1;
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo:" + e.getMessage());
        }

        System.out.println("Arquivo lido com sucesso!");
        System.out.println("Contatos lidos: " + totalAlunos);
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
                System.out.println("2: Adicionar contato");
                System.out.println("3: Pesquisar contato por nome");
                System.out.println("4: Pesquisar contato por telefone");
                System.out.println("5: Remover contato por telefone");
                System.out.println("6: Alterar dados de um contato");
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
                        System.out.println("3: Pesquisar aluno por nome - FALTA IMPLEMENTAR");
                        break;
                    case 4:
                        System.out.println("4: Pesquisar aluno por matricula - FALTA IMPLEMENTAR");
                        break;
                    case 5:
                        System.out.println("5: Remover contato por matricula - FALTA IMPLEMENTAR");
                        break;
                    case 6:
                        System.out.println("6: Alterar dados de um aluno - FALTA IMPLEMENTAR");
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
}
