# 1. Estrutura Inicial do Repositório

```text
trabalho-lista-encadeada/
│
├── README.md
├── .gitignore
├── relatorio/
│   ├── relatorio.tex
│   ├── relatorio.pdf
│   ├── imagens/
│   └── graficos/
│
├── dados/
│   ├── entrada.txt
│   ├── entrada_100k.txt
│   ├── entrada_200k.txt
│   ├── entrada_400k.txt
│   └── entrada_800k.txt
│
├── scripts/
│   └── GeradorArquivos.java
│
└── src/
    ├── app/
    │   ├── Main.java
    │   ├── Menu.java
    │   └── GerenciadorContatos.java
    │
    ├── dominio/
    │   ├── Contato.java
    │   ├── ComparadorContatoNome.java
    │   └── ComparadorContatoTelefone.java
    │
    ├── estruturas/
    │   ├── IColecao.java
    │   ├── No.java
    │   └── ListaEncadeada.java
    │
    ├── io/
    │   └── LeitorArquivo.java
    │
    ├── util/
    │   ├── Cronometro.java
    │   └── ValidadorTelefone.java
    │
    └── testes/
        ├── TesteLista.java
        └── TesteEmpirico.java
```

---

# 2. O que deve ficar em cada pasta

## `/src/estruturas`

Contém **toda a biblioteca da lista encadeada**.

### `IColecao.java`

Interface exigida pelo trabalho.

Deve declarar operações como:

```java
public interface IColecao<T> {
    boolean adicionar(T valor);
    T pesquisar(T valor);
    boolean remover(T valor);
    int quantidadeNos();
}
```

---

### `No.java`

Representa um nó da lista.

```java
class No<T> {
    T valor;
    No<T> proximo;
}
```

---

### `ListaEncadeada.java`

Classe principal da biblioteca.

Deve conter:

- atributo `Comparator<T> comparator`;
- atributo `boolean ordenada`;
- ponteiro para o primeiro nó;
- implementação dos métodos da interface;
- sobrescrita de `toString()`.

**Métodos obrigatórios:**

```java
public class ListaEncadeada<T> implements IColecao<T> {

    public ListaEncadeada(Comparator<T> comparator, boolean ordenada)

    public boolean adicionar(T valor)

    public T pesquisar(T valor)

    public boolean remover(T valor)

    public int quantidadeNos()

    @Override
    public String toString()
}
```

---

# 3. Pasta `/src/dominio`

Contém as **classes de negócio**.

## `Contato.java`

Classe exigida pelo enunciado.

```java
public class Contato {
    private String nome;
    private String telefone;

    // construtores
    // getters e setters

    @Override
    public String toString() {
        return nome + "-" + telefone;
    }
}
```

---

## `ComparadorContatoNome.java`

Responsável por comparar contatos pelo nome.

```java
public class ComparadorContatoNome
        implements Comparator<Contato> {

    @Override
    public int compare(Contato c1, Contato c2) {
        return c1.getNome().compareToIgnoreCase(c2.getNome());
    }
}
```

---

## `ComparadorContatoTelefone.java`

Usado para comparação por telefone.

---

# 4. Pasta `/src/app`

Responsável pela **interface de interação com o usuário**.

## `Main.java`

Ponto de entrada do programa.

Fluxo esperado:

```text
Perguntar se a lista será ordenada
        ↓
Instanciar IColecao<Contato>
        ↓
Exibir menu principal
```

---

## `Menu.java`

Contém apenas a lógica do menu textual:

```text
1 - Carregar dados de arquivo
2 - Adicionar contato
3 - Pesquisar por nome
4 - Pesquisar por telefone
5 - Remover contato
6 - Alterar contato
7 - Sair
```

---

## `GerenciadorContatos.java`

Coordena as operações entre o menu e a biblioteca.

Aqui deve ficar a regra de negócio:

- impedir telefones duplicados;
- atualizar dados;
- controlar duas listas, se o grupo optar por essa estratégia.

**Importante:** nenhuma mensagem de `System.out.println` deve existir dentro de `ListaEncadeada.java`.

---

# 5. Pasta `/src/io`

## `LeitorArquivo.java`

Responsável por ler `entrada.txt`.

Formato sugerido do arquivo:

```text
Ana;27999990001
Bruno;27999990002
Carla;27999990003
```

Método principal:

```java
public void carregar(String caminho, IColecao<Contato> lista)
```

Esse método deve medir o tempo de leitura e inserção.

---

# 6. Pasta `/src/util`

## `Cronometro.java`

Classe utilitária para medir tempo.

```java
public class Cronometro {
    private long inicio;

    public void iniciar() {
        inicio = System.nanoTime();
    }

    public long parar() {
        return System.nanoTime() - inicio;
    }
}
```

---

## `ValidadorTelefone.java`

Centraliza validações de telefone.

---

# 7. Pasta `/src/testes`

## `TesteLista.java`

Testes simples de funcionamento da biblioteca:

- adicionar;
- remover;
- pesquisar;
- verificar `toString()`.

---

## `TesteEmpirico.java`

Executa automaticamente os experimentos do item 5 do trabalho:

- carregar arquivos grandes;
- medir tempo de montagem;
- medir busca do último elemento;
- medir remoção do último elemento;
- salvar resultados em CSV.

---

# 8. Pasta `/dados`

Contém os arquivos de entrada usados nos testes.

## Arquivos mínimos

| Arquivo | Quantidade aproximada |
|---|---|
| `entrada_100k.txt` | 100.000 contatos |
| `entrada_200k.txt` | 200.000 contatos |
| `entrada_400k.txt` | 400.000 contatos |
| `entrada_800k.txt` | 800.000 contatos |

Esses arquivos serão utilizados na **análise empírica de complexidade**.

---

# 9. Pasta `/scripts`

## `GeradorArquivos.java`

Gera automaticamente os arquivos grandes.

Exemplo de saída:

```text
Contato000001;27990000001
Contato000002;27990000002
```

Esse código **não faz parte da biblioteca**, apenas auxilia os experimentos.

---

# 10. Primeiros Passos de Execução

## 1. Clonar o repositório

```bash
git clone https://github.com/SEU-USUARIO/trabalho-lista-encadeada.git
cd trabalho-lista-encadeada
```

---

## 2. Criar a estrutura de pastas

```bash
mkdir -p src/app
mkdir -p src/dominio
mkdir -p src/estruturas
mkdir -p src/io
mkdir -p src/util
mkdir -p src/testes
mkdir -p dados
mkdir -p scripts
mkdir -p relatorio/imagens
mkdir -p relatorio/graficos
```

---

## 3. Implementar primeiro a biblioteca

Ordem recomendada:

### Etapa 1

```text
IColecao.java
No.java
```

### Etapa 2

```text
ListaEncadeada.java
```

Implementar apenas:

- construtor;
- adicionar;
- quantidadeNos;
- toString.

### Etapa 3

Adicionar:

- pesquisar;
- remover.

---

## 4. Criar a classe `Contato`

Depois que a lista estiver funcional, implementar:

```text
Contato.java
ComparadorContatoNome.java
ComparadorContatoTelefone.java
```

---

## 5. Criar o programa de menu

Implementar inicialmente apenas:

- adicionar contato;
- listar contatos;
- sair.

Quando isso funcionar, adicionar as operações de busca e remoção.

---

# 11. Compilação Manual

## Linux / macOS

```bash
javac -d out $(find src -name "*.java")
java -cp out app.Main
```

---

## Windows (PowerShell)

```powershell
javac -d out (Get-ChildItem -Recurse src\\*.java)
java -cp out app.Main
```

---

# 12. Organização do Relatório

## `/relatorio`

### `relatorio.tex`

Estrutura sugerida:

```text
1. Desenvolvimento da biblioteca e do programa
2. Análise matemática de complexidade
3. Análise empírica de complexidade
4. Conclusão
```

---

## `/relatorio/imagens`

Salvar capturas dos códigos com **linhas numeradas**, pois o trabalho exige referência linha a linha na análise matemática.

---

## `/relatorio/graficos`

Salvar gráficos como:

```text
tempo_montagem.png
tempo_busca_nome.png
tempo_busca_telefone.png
tempo_remocao.png
```

---

# 13. Distribuição Recomendada do Grupo

| Integrante | Responsabilidade |
|---|---|
| Pessoa 1 | `ListaEncadeada`, `No`, `IColecao` |
| Pessoa 2 | `Contato`, comparadores, menu e interação |
| Pessoa 3 | leitura de arquivos, testes empíricos, gráficos e relatório |

Todos devem revisar conjuntamente a análise de complexidade.

---

# 14. Checklist Inicial

## Antes de começar a codificar

- [ ] Repositório criado no GitHub
- [ ] `README.md` adicionado
- [ ] Estrutura de pastas criada
- [ ] `.gitignore` configurado para Java
- [ ] Todos os integrantes com acesso ao repositório

## Primeira entrega funcional

- [ ] `IColecao.java`
- [ ] `No.java`
- [ ] `ListaEncadeada.java` com inserção funcionando
- [ ] `Contato.java`
- [ ] `Main.java` com menu mínimo
- [ ] `entrada.txt` de teste
- [ ] Compilação sem erros

---

# 15. Estrutura Mínima para o Primeiro Commit

O primeiro commit ideal deve conter apenas a base do projeto:

```text
README.md
.gitignore
src/estruturas/IColecao.java
src/estruturas/No.java
src/estruturas/ListaEncadeada.java
src/dominio/Contato.java
src/app/Main.java
dados/entrada.txt
```

Mensagem sugerida:

```bash
git add .
git commit -m "Estrutura inicial do trabalho de lista encadeada"
git push origin main
```

---

# 16. Ordem Recomendada para os Próximos Dias

## Dia 1

- Estrutura do repositório
- `IColecao`
- `No`
- `ListaEncadeada` (adição)

## Dia 2

- Busca e remoção
- Classe `Contato`
- Comparadores

## Dia 3

- Menu completo
- Leitura de arquivo
- Validação de telefone

## Dia 4

- Testes com arquivos grandes
- Coleta de tempos
- Geração de gráficos

## Dia 5

- Análise matemática
- Interpretação dos resultados
- Finalização do PDF do relatório

---

## Observação Final

O enunciado exige explicitamente que:

1. a classe principal da biblioteca se chame **`ListaEncadeada`**;
2. ela implemente **`IColecao`**;
3. receba **`Comparator<T>`** e **`boolean ordenada`** no construtor;
4. o repositório possua um **README explicando a organização do código e como executá-lo**;
5. nenhuma mensagem de interação com o usuário seja impressa pelos métodos da biblioteca.

Esta estrutura já deixa o projeto preparado tanto para a **implementação** quanto para a **análise de complexidade** e a **geração do relatório final** exigidos no trabalho.