package listaencadeada;

import colecao.IColecao;
import java.util.Comparator;

public class ListaEncadeada<T> implements IColecao<T>{
    private No<T> prim, ult;
    private int quant;
    private final Comparator<T> comparador;
    private final boolean ehOrdenada;

    public ListaEncadeada() {
        this(null, false);
    }

    public ListaEncadeada(Comparator<T> comparador, boolean ehOrdenada) {
        this.comparador = comparador;
        this.ehOrdenada = ehOrdenada;
    }

    @Override
    public String toString() {
        No<T> aux = this.prim;
        StringBuilder s = new StringBuilder("[");

        while(aux != null){
            s.append(aux.getValor());

            if(aux.getProx() != null){
                s.append(",");
            }
            aux = aux.getProx();
        }
        return (s + "]");
    } // Creates a toString so it possible to print all elements in a pretty way

    @Override
    public boolean adicionar(T novoValor) {

        if (!ehOrdenada) {
            return inserirElementoNaoOrd(novoValor);
        } else {
            return inserirElementoOrd(novoValor);
        }
    }

    public boolean inserirElementoNaoOrd(T novoValor) {

        if (prim == null) {
            prim = new No<>(novoValor);
            return true;
        }

        No<T> novoNo = new No<>(novoValor);
        novoNo.setProx(prim);
        prim = novoNo;

        return true;
    }

    public boolean inserirElementoOrd(T novoValor){

        No<T> novoNo = new No<>(novoValor);
        No<T> atual, ant;

        atual = this.prim;
        ant = null;

        if(this.prim == null){//Se lista estiver vazia, cria novo no e adiciona no prim (primeiro na lista)
            this.prim = this.ult=novoNo;

        }else{// Enquanto não é o ultimo da lista e é maior que o atual, vai para o próximo

            while(atual != null){// Enquanto novoValor for maior que atual.getValor

                if(comparador.compare(novoValor, atual.getValor()) == 0){ // O novo no possui o mesmo valor de um no da lista, logo não é adicionado
                    System.out.println("Aluno não adicionado pois já existe.");
                    return false;

                } else if (comparador.compare(novoValor, atual.getValor()) < 0) {// O novo no é menor que o No atual, então quebra do loop e realiza os checks
                    break;
                }

                ant = atual; // No atual vira o anterior
                atual = atual.getProx(); // Atual passa apontar para o próximo No
            }
            if(ant == null){// No for menor que o primeiro No da lista, logo tem que ser o novo primeiro No
                novoNo.setProx(this.prim);
                this.prim = novoNo;

            } else if (atual == null ) {// Chegou ao final da lista, No vai ser inserido na última posição
                this.ult.setProx(novoNo);
                this.ult = novoNo;


            } else{
                ant.setProx(novoNo);
                novoNo.setProx(atual);
            }

        }
        this.quant++;
        return true;
    }

    @Override
    public T pesquisar(T valor) {
        return pesquisar(valor, this.comparador);
    }

    // Permite buscar por outro critério (nesse caso, nome), mais facil de implementar no menu
    public T pesquisar(T valor, Comparator<T> criterio) {
        if (criterio == null) {
            throw new IllegalArgumentException("Um comparador válido é necessário para a pesquisa.");
        }

        No<T> atual = this.prim;

        while (atual != null) {
            int cmp = criterio.compare(valor, atual.getValor());

            if (cmp == 0) {
                return atual.getValor();
            }

            // Se a lista tiver ordenada pelo critério passado, para de procurar caso passe da posição onde deveria estar.
            // Adicionado "this.comparador != null" para evitar NPE no .getClass() caso a lista não tenha comparador base.
            if (this.ehOrdenada && this.comparador != null && criterio.getClass() == this.comparador.getClass() && cmp < 0) {
                break;
            }

            atual = atual.getProx();
        }

        return null;
    }

    @Override
    public boolean remover(T valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }

    @Override
    public int quantidadeNos() {
        return quant;
    }

    
}
