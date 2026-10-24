package arvorebinaria;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    // Variavel auxiliar para saber se o no foi inserido ou se era duplicado
    private boolean inseriu;

    public ArvoreAVL(Comparator<T> comparador) {
        super(comparador);
    }

    //_____________________________Adicionar com balanceamento AVL
    @Override
    public boolean adicionar(T novoValor) {
        // Se o valor passado for nulo, nao adiciona nada
        if (novoValor == null) {
            return false;
        }

        inseriu = false; // Comeca como falso antes da tentativa de insercao
        raiz = adicionarAVL(raiz, novoValor); // Chama a recursao atualizando a raiz

        // Se conseguiu inserir com sucesso, incrementa o contador e retorna true
        if (inseriu) {
            quant++;
            return true;
        }

        return false; // Se nao inseriu (ex: valor duplicado), retorna false
    }

    private NoArvore<T> adicionarAVL(NoArvore<T> atual, T valor) {
        // Se chegou em uma posicao vazia, cria e insere o novo no
        if (atual == null) {
            inseriu = true;
            return new NoArvore<>(valor);
        }

        int resultado = comparador.compare(valor, atual.valor);

        // Se o valor ja existe na arvore, nao insere duplicatas
        if (resultado == 0) {
            inseriu = false;
            return atual;
        }

        // Se o valor for menor, insere na subarvore esquerda
        if (resultado < 0) {
            atual.esquerdo = adicionarAVL(atual.esquerdo, valor);
        } else {
            // Se o valor for maior, insere na subarvore direita
            atual.direito = adicionarAVL(atual.direito, valor);
        }

        // No retorno da recursao, chama o balanceamento para garantir as regras da AVL
        return balancear(atual);
    }

    //_____________________________Remover com balanceamento AVL
    @Override
    public boolean remover(T valor) {
        return false;
    }

    private NoArvore<T> removerAVL(NoArvore<T> atual, T valor) {
        return null;
    }

    //_____________________________Metodos de auxilio para o balanceamento

    // Calcula a diferenca de altura dos filhos: altura da esquerda menos altura da direita
    private int balanceamento(NoArvore<T> no) {
        if (no == null) {
            return 0;
        }
        return 0;
    }

    // Metodo principal que avalia o fator de balanceamento e chama a rotacao certa
    private NoArvore<T> balancear(NoArvore<T> no) {
        return no;
    }

    // Rotacao simples para a direita
    private NoArvore<T> rotDir(NoArvore<T> a) {
        return null;
    }

    // Rotacao simples para a esquerda
    private NoArvore<T> rotEsq(NoArvore<T> a) {
        return null;
    }

}
