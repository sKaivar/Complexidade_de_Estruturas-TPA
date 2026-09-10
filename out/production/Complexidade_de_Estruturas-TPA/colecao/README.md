# 📚 Disciplina: Técnicas de Programação Avançadas (2026)

Este repositório contém os exemplos de código utilizados em aula e nos trabalhos.
---

## 📂 Organização do Repositório

Cada pasta representa um tópico ou projeto específico abordado durante o semestre:

* **[listas-encadeadas](./listas-encadeadas):** Interface IColecao a ser implementada pela lista encadeada implementada no trabalho e exemplos de implemetação da lista e de um programa principal

---

## 🛠️ Como rodar os exemplos no VS Code

Para garantir que a IDE reconheça o projeto corretamente, siga estes passos:

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/seu-usuario/nome-do-repo.git](https://github.com/seu-usuario/nome-do-repo.git)
    ```
2.  **Abra o VS Code.**
3.  Vá em `File > Open Folder...` e selecione **a pasta específica do exercício** (ex: `listas-encadeadas`). 
    > **Importante:** Não abra a pasta raiz da disciplina se quiser que o suporte ao Java (IntelliSense) funcione perfeitamente para cada projeto individual.
4.  Certifique-se de ter o **Extension Pack for Java** da Microsoft instalado.
5.  Abra o arquivo `Main.java` e clique em **Run** acima do método `main`.

---

## 💻 Comandos Úteis (Terminal)

Caso prefira rodar via terminal sem usar o botão "Run" da IDE:

**Para compilar:**
* No Linux: 
javac src/*.java -d bin

* No Windows (Powershell): 
javac (Get-ChildItem -Recurse src/*.java) -d bin

**Para executar:**
java -cp bin dominio.Main
