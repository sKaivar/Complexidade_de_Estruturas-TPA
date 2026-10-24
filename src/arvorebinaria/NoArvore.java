/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorebinaria;

/**
 *
 * @author victoriocarvalho
 */
public class NoArvore<T> {
    private T valor;
    private NoArvore<T> dir;
    private NoArvore<T> esq;

    
    public NoArvore(T valor){
        this.valor = valor;
        this.dir = null;
        this.esq = null;
    }
    /**
     * @return the valor
     */
    public T getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(T valor) {
        this.valor = valor;
    }

    /**
     * @return the prox
     */
    public NoArvore<T> getDireita() {
        return dir;
    }

    public NoArvore<T> getEsquerda() {
        return esq;
    }

    /**
     * @param prox the prox to set
     */
    public void setDireita(NoArvore<T> novoNo) {
        this.dir = novoNo;
    }
    
    public void setEsquerda(NoArvore<T> novoNo) {
        this.esq = novoNo;
    }
    
    
}
