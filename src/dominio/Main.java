/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.Scanner;

import colecao.IColecao;
import listaencadeada.*;

/**
 *
 * @author victoriocarvalho
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        IColecao<Aluno> l; //Object type IColecao
        l = new ListaEncadeada<Aluno>(new ComparadorAlunoPorMatricula(), false); //Object l has an empty list as value
        Aluno a;//Empty object class Aluno
        int ordenado = 0, mat, nota, resp = 10; //Ordenado (tells if user wants a ordered list or not), mat (matricula), nota, resp(any integer choice of user)
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
            l = new ListaEncadeada<Aluno>(new ComparadorAlunoPorMatricula(), true);

        } else if (ordenado == 2) {
            l = new ListaEncadeada<Aluno>(new ComparadorAlunoPorMatricula(), false);

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
                        System.out.println("1: Carregar alunos de arquivo - FALTA IMPLEMENTAR");
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
                        System.out.println("3: Pesquisar contato por nome - FALTA IMPLEMENTAR");
                        break;
                    case 4:
                        System.out.println("4: Pesquisar contato por telefone - FALTA IMPLEMENTAR");
                        break;
                    case 5:
                        System.out.println("5: Remover contato por telefone - FALTA IMPLEMENTAR");
                        break;
                    case 6:
                        System.out.println("6: Alterar dados de um contato - FALTA IMPLEMENTAR");
                        break;
                }
            }catch (Exception e) {
                scanner.nextLine();// Cleans if input is wrong type
                System.out.println("ERRO! " + e.getMessage());// Tells the error message
            }

        }while(resp != 0);
        System.out.println("Programa encerrado.");
        scanner.close();

    }
}
