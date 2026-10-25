/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorebinaria;

public class NoArvore<T> {
    public T valor;
    public NoArvore<T> esquerdo;
    public NoArvore<T> direito;
    public int altura;

    public NoArvore(T valor) {
        this.valor = valor;
        this.esquerdo = null;
        this.direito = null;
        this.altura = 0;
    }
}
