package arvorebinaria;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    public ArvoreAVL(Comparator<T> comparador) {
        super(comparador);
    }

    //_____________________________Adicionar com balanceamento AVL
    @Override
    public boolean adicionar(T novoValor) {
        // TODO: chamar o metodo recursivo adicionarAVL e atualizar a raiz
        // Lembrar de incrementar o quant se conseguir adicionar e retornar true
        return false;
    }

    private NoArvore<T> adicionarAVL(NoArvore<T> atual, T valor) {
        // TODO: inserir o no igual na arvore binaria normal e chamar balancear(atual) no final antes do retorno
        return null;
    }

    //_____________________________Remover com balanceamento AVL
    @Override
    public boolean remover(T valor) {
        // TODO: chamar o metodo recursivo removerAVL e atualizar a raiz
        // Lembrar de decrementar o quant se remover com sucesso
        return false;
    }

    private NoArvore<T> removerAVL(NoArvore<T> atual, T valor) {
        // TODO: remover no igual na arvore binaria comum e chamar balancear(atual) no final
        return null;
    }

    //_____________________________Metodos de auxilio para o balanceamento

    // Calcula a diferenca de altura dos filhos: altura da esquerda menos altura da direita
    private int balanceamento(NoArvore<T> no) {
        if (no == null) {
            return 0;
        }
        // TODO: pegar calcularAltura do filho da esquerda e da direita e subtrair
        return 0;
    }

    // Metodo principal que avalia o fator de balanceamento e chama a rotacao certa
    private NoArvore<T> balancear(NoArvore<T> no) {
        // TODO: conferir os 4 casos de desbalanceamento e fazer as rotacoes necessarias
        return no;
    }

    // Rotacao simples para a direita
    private NoArvore<T> rotDir(NoArvore<T> a) {
        // TODO: fazer a rotacao simples a direita
        return null;
    }

    // Rotacao simples para a esquerda
    private NoArvore<T> rotEsq(NoArvore<T> a) {
        // TODO: fazer a rotacao simples a esquerda
        return null;
    }

}
