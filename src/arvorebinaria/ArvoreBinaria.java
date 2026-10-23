package arvorebinaria;

import listaencadeada.ListaEncadeada;
import listaencadeada.No;

import java.util.Comparator;

public class ArvoreBinaria<T> extends ArvoreBinariaBase<T> {

    private static class NoArvore<E>{
        E valor;
        NoArvore<E> esquerdo;
        NoArvore<E> direito;

        NoArvore(E valor){
            this.valor = valor;
            this.esquerdo = null;
            this.direito = null;
        }
    }

    private NoArvore<T> raiz;

    public ArvoreBinaria(Comparator<T> comparador) {
        super(comparador);
        this.raiz = null;
    }
    //_____________________________Adicionar

    @Override
    public boolean adicionar(T novoValor) {

        //If valor added is null dont add anything
        if(novoValor == null){
            return false;
        }

        //If raiz is empty, then valor added is the first node of the tree
        if(raiz == null){
            raiz = new NoArvore<>(novoValor);

            return true;
        }

        NoArvore<T> atual = raiz;

        while(true){
            int resultado = comparador.compare(novoValor, atual.valor);

            //If value is duplicated do not add to the tree
            if(resultado == 0){
                return false;
            }

            if(resultado < 0){
                //Adds node to the left
                if(atual.esquerdo == null){
                    atual.esquerdo = new NoArvore<>(novoValor);
                    return true;
                }

                atual = atual.esquerdo; //Points to the newest added node
            }else {
                //Adds node to the right
                if(atual.direito == null){
                    atual.direito = new NoArvore<>(novoValor);
                    return true;
                }
                atual = atual.direito; //Points to the newest added node
            }
        }
    }

    //_____________________________Pesquisar
    @Override
    public T pesquisar(T valor) {
        if(valor == null){
            return null;
        }

        NoArvore<T> atual = raiz;

        while(atual != null){
            int resultado = comparador.compare(valor, atual.valor); //Result when comparing two values

            if(resultado == 0){
                return atual.valor;
            }// If resultado is the value looking for return the atual.valor

            if(resultado < 0){
                atual = atual.esquerdo; // If valor is lower than atual.valor, go to the node on the left
            }else{
                atual = atual.direito; // If valor is higher than atual.valor, go to the node on the right
            }
        }
        return null; //If its not found, return null
    }

    //_____________________________Tamanho da árvore
    @Override
    public int altura() {
        return calcularAltura(raiz);
    }

    private int calcularAltura(NoArvore<T> no){
        if(no == null){
            return -1;
        }
        int alturaEsquerda = calcularAltura(no.esquerdo);
        int alturaDireita = calcularAltura(no.direito);

        if(alturaEsquerda > alturaDireita){
            return 1 + alturaEsquerda;
        }else{
            return 1 + alturaDireita;
        }
    }

    @Override
    public int quantidadeNos() { // Method recursive
        return contarNos(raiz);// It will grab the first root
    }

    private int contarNos(NoArvore<T> no){
        if(no == null){
            return 0;
        }// If the node is empty, return 0

        // If its not, it will call the same function to the nodes on the left and right
        // at the end will add the return at the end of the search
        return 1 + contarNos(no.esquerdo) + contarNos(no.direito);
    }

    //_____________________________Em ordem

    @Override
    public String caminharEmOrdem() {
        StringBuilder resultado = new StringBuilder("[");

        adicionarEmOrdem(raiz, resultado);

        resultado.append("]");

        return resultado.toString();
    }

    private void adicionarEmOrdem(NoArvore<T> no, StringBuilder resultado){

        if(no == null){
            return;
        }

        //Recursive method to get all left nodes
        adicionarEmOrdem(no.esquerdo, resultado);

        //Adds current node to string
        if(resultado.length() > 1){
            resultado.append(", ");
        }

        resultado.append(no.valor);

        //Recursive method to get all the right nodes
        adicionarEmOrdem(no.direito, resultado);
    }

    //_____________________________Em nível

    @Override
    public String caminharEmNivel() {

        if (raiz == null) {
            return "[]";
        }// Se arvore estiver vazia, retorna vazio

        ListaEncadeada<NoArvore<T>> fila = new ListaEncadeada<>(); // Cria uma lista de Nos de arvores
        StringBuilder resultado = new StringBuilder("["); // Chamada para realizar os prints

        fila.enfileirar(raiz);// Adiciona raiz na ultima posiçao da lista

        boolean primeiroNivel = true;

        while (fila.quantidadeNos() > 0) {

            int quantidadeNivel = fila.quantidadeNos();// Resgata quantos nos na lista, e indica quantidade de niveis

            if (!primeiroNivel) {
                resultado.append("\n");
            }// Quando entrar aqui, já realizou o processo de imprimir todos elementos de um nivel

            primeiroNivel = false;

            for (int i = 0; i < quantidadeNivel; i++) { // Enquanto i for menor que a quantidade de niveis

                NoArvore<T> n = fila.desenfileirar();// Vai retirar o primeiro elemento da lista e pegar seu valor

                if (i > 0) {
                    resultado.append(", ");
                }

                resultado.append(n.valor);// Vai imprimir o valor resgatado

                if (n.esquerdo != null) {
                    fila.enfileirar(n.esquerdo);
                }// Se tiver filho a esquerda, vai adicionar ele na lista

                if (n.direito != null) {
                    fila.enfileirar(n.direito);
                }// Se tiver filho a direita, vai adicionar ele na lista
            }

        }// Enquanto existem nos na lista, existem niveis e o loop repete

        resultado.append("]");// No final adiciona isso ao print
        return resultado.toString(); // Retorna o print que realizou
    }

    @Override
    public boolean remover(T valor) {
        // Se a arvore esta vazia ou o valor passado for nulo, nao tem como remover
        if (valor == null || raiz == null) {
            return false;
        }

        NoArvore<T> atual = raiz;
        NoArvore<T> pai = null;

        // Procura o elemento na arvore guardando a referencia do pai
        while (atual != null) {
            int comp = comparador.compare(valor, atual.valor);

            if (comp == 0) {
                break; // Encontrou o elemento que deve ser removido
            }

            pai = atual;
            if (comp < 0) {
                atual = atual.esquerdo; // Vai procurar na esquerda
            } else {
                atual = atual.direito; // Vai procurar na direita
            }
        }

        // Se chegou ao fim do caminho e nao achou ninguem
        if (atual == null) {
            return false;
        }

        // Caso 3: o no a ser removido tem dois filhos
        // Buscamos o sucessor (menor elemento da subarvore da direita)
        if (atual.esquerdo != null && atual.direito != null) {
            NoArvore<T> paiSucessor = atual;
            NoArvore<T> sucessor = atual.direito;

            while (sucessor.esquerdo != null) {
                paiSucessor = sucessor;
                sucessor = sucessor.esquerdo;
            }

            // Copia o valor do sucessor pro no que queriamos deletar
            atual.valor = sucessor.valor;

            // O no que realmente vamos desligar da arvore é o sucessor
            atual = sucessor;
            pai = paiSucessor;
        }

        // Caso 1 e 2: o no tem no maximo um filho (ou e folha)
        NoArvore<T> filho = (atual.esquerdo != null) ? atual.esquerdo : atual.direito;

        // Se o no a remover for a propria raiz
        if (pai == null) {
            raiz = filho;
        } else if (atual == pai.esquerdo) {
            pai.esquerdo = filho; // Liga o filho do no removido no lado esquerdo do pai
        } else {
            pai.direito = filho; // Liga o filho do no removido no lado direito do pai
        }

        quant--; // Atualiza a quantidade total de nos
        return true;
    }


}
