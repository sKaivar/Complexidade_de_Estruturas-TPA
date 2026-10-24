package arvorebinaria;

import java.util.Comparator;
import arvorebinaria.NoArvore;

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
        if (pesquisar(valor) == null){
            return false;
        }

        this.raiz = removerRecursivo(this.raiz, valor);
        this.tamNo--;
        return true;
    }

    private NoArvore<T> removerRecursivo(NoArvore<T> atual, T valor){
        if (atual == null){
            return null;
        }
        int comp = this.comparador.compare(valor, atual.getValor());

        if (comp < 0){
            atual.setEsquerda(removerRecursivo(atual.getEsquerda(), valor));
        }else if (comp > 0){
            atual.setDireita(removerRecursivo(atual.getDireita(), valor));
        }else{
            if(atual.getEsquerda() == null) return atual.getDireita();
            if(atual.getDireita() == null) return atual.getEsquerda();

            NoArvore<T> sucessor = atual.getDireita();
            while(sucessor.getEsquerda() != null){
                sucessor = sucessor.getEsquerda();
            }

            T menor = sucessor.getValor();
            atual.setValor(menor);
            atual.setDireita(removerRecursivo(atual.getDireita(), menor));
        }
        return atual;
    }

    private NoArvore<T> removerAVL(NoArvore<T> atual, T valor) {
        return null;
    }

    //_____________________________Metodos de auxilio para o balanceamento

    // Calcula a diferenca de altura dos filhos: altura da esquerda menos altura da direita
    private int balanceamento(NoArvore<T> no) {
        if(no == null) return 0;
		int alturaEsq = calcularAltura(no.getEsquerda());
		int alturaDir = calcularAltura(no.getDireita());
		
		return alturaEsq - alturaDir;
    }

    // Metodo principalque  avalia o fator de balanceamento e chama a rotacao certa
    private NoArvore<T> balancear(NoArvore<T> no) {
        
        int fator = balanceamento(no);

		if(fator > 1){
			// se o filho da esquerda tem peso para a direita
			if(balanceamento(no.getEsquerda()) < 0){
				no.setEsquerda(rotEsq(no.getEsquerda()));
				}
			return rotDir(no);
		}

		if(fator <-1){
			// se o filho da direita tem peso para a esquerda
			if(balanceamento(no.getDireita()) > 0){
				no.setDireita(rotDir(no.getDireita()));
			}
			return rotEsq(no);
		}
	
		return no;

    }

    // Rotacao simples para a direita
    private NoArvore<T> rotDir(NoArvore<T> a) {
        NoArvore<T> b = a.getEsquerda();
		NoArvore<T> aux = b.direito;

		b.setDireita(a);
		a.setEsquerda(aux);

		return b;
    }

    // Rotacao simples para a esquerda
    private NoArvore<T> rotEsq(NoArvore<T> a) {
        NoArvore<T> b = a.getDireita();
		NoArvore<T> aux = b.getEsquerda();

		b.setEsquerda(a);
		a.setDireita(aux);

		return b;
    }

}
