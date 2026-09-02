package listaencadeada;

import colecao.IColecao;
import java.util.Comparator;

public class ListaEncadeada<T> implements IColecao<T>{
    private No<T> prim;
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
        // TODO Auto-generated method stub

        if(!ehOrdenada){ //Start of Unordered adding elements methods
            if(prim == null){
                prim = new No<>(novoValor);
                return true;
            }//Adds a new Node to an empty list

            No<T> novoNo = new No<>(novoValor);
            novoNo.setProx(prim);
            prim = novoNo;
            /*Adds a new Node to a non-empty List
            It adds at the first position because its faster than running to all the list
            And adding to the last spot*/

            return true;
        }

        //Start of Ordered adding elements methods
        return true;


    }

    @Override
    public T pesquisar(T valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pesquisar'");
    }

    @Override
    public boolean remover(T valor) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'remover'");
    }

    @Override
    public int quantidadeNos() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'quantidadeNos'");
    }

    
}
