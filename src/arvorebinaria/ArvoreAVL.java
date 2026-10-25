package arvorebinaria;

import java.util.Comparator;

public class ArvoreAVL<T> extends ArvoreBinaria<T> {

    // Variavel auxiliar para saber se o no foi inserido ou se era duplicado
    private boolean inseriu;

    public ArvoreAVL(Comparator<T> comparador) {
        super(comparador);
    }

    //_____________________________Metodos de auxilio para o balanceamento

    private int altura(NoArvore<T> no) {
        if (no == null) {
            return -1;
        }
        return no.altura;
    }

    private void atualizarAltura(NoArvore<T> no) {
        int altEsq = altura(no.esquerdo);
        int altDir = altura(no.direito);

        if (altEsq > altDir) {
            no.altura = 1 + altEsq;
        } else {
            no.altura = 1 + altDir;
        }
    }

    // Calcula a diferenca de altura dos filhos: altura da esquerda menos altura da direita
    private int fatorBalanceamento(NoArvore<T> no) {
        if (no == null) {
            return 0;
        }
        return altura(no.esquerdo) - altura(no.direito);
    }

    // Rotacao simples para a direita
    private NoArvore<T> rotDir(NoArvore<T> a) {
        NoArvore<T> b = a.esquerdo;
        a.esquerdo = b.direito;
        b.direito = a;

        atualizarAltura(a);
        atualizarAltura(b);

        return b;
    }

    // Rotacao simples para a esquerda
    private NoArvore<T> rotEsq(NoArvore<T> a) {
        NoArvore<T> b = a.direito;
        a.direito = b.esquerdo;
        b.esquerdo = a;

        atualizarAltura(a);
        atualizarAltura(b);

        return b;
    }

    // Metodo principal que avalia o fator de balanceamento e chama a rotacao certa
    private NoArvore<T> balancear(NoArvore<T> no) {
        if (no == null) {
            return null;
        }

        atualizarAltura(no);
        int fb = fatorBalanceamento(no);

        // Desbalanceamento para a esquerda
        if (fb > 1) {
            // Se o filho da esquerda tem peso para a direita: rotacao dupla LR
            if (fatorBalanceamento(no.esquerdo) < 0) {
                no.esquerdo = rotEsq(no.esquerdo);
            }
            return rotDir(no); // Rotacao simples LL
        }

        // Desbalanceamento para a direita
        if (fb < -1) {
            // Se o filho da direita tem peso para a esquerda: rotacao dupla RL
            if (fatorBalanceamento(no.direito) > 0) {
                no.direito = rotDir(no.direito);
            }
            return rotEsq(no); // Rotacao simples RR
        }

        return no;
    }

    //_____________________________Altura em O(1)
    @Override
    public int altura() {
        return altura(this.raiz);
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
        if (valor == null || raiz == null || pesquisar(valor) == null) {
            return false;
        }

        this.raiz = removerRecursivo(this.raiz, valor);
        this.quant--;
        return true;
    }

    private NoArvore<T> removerRecursivo(NoArvore<T> atual, T valor) {
        if (atual == null) {
            return null;
        }

        int comp = this.comparador.compare(valor, atual.valor);

        if (comp < 0) {
            atual.esquerdo = removerRecursivo(atual.esquerdo, valor);
        } else if (comp > 0) {
            atual.direito = removerRecursivo(atual.direito, valor);
        } else {
            // Caso 1 e 2: zero ou um filho
            if (atual.esquerdo == null) {
                return atual.direito;
            }
            if (atual.direito == null) {
                return atual.esquerdo;
            }

            // Caso 3: dois filhos (busca sucessor na subarvore direita)
            NoArvore<T> sucessor = atual.direito;
            while (sucessor.esquerdo != null) {
                sucessor = sucessor.esquerdo;
            }

            // Copia o valor do sucessor
            atual.valor = sucessor.valor;
            // Remove o sucessor na subarvore direita
            atual.direito = removerRecursivo(atual.direito, sucessor.valor);
        }

        // No retorno da recursao, rebalanceia todos os ancestrais
        return balancear(atual);
    }

}
