package scripts;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/** Gera um arquivo entrada.txt contendo contatos no formato:
 *  Nome Sobrenome;Telefone */
public class GeradorArquivos {

    private static final int QUANTIDADE = 10000;

    public static void main(String[] args) {

        File pasta = new File("dados");

        if (!pasta.exists()) {
            pasta.mkdirs();
        }

        File arquivo = new File(pasta, "entrada.txt");

        long inicio = System.nanoTime();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivo))) {

            for (int i = 1; i <= QUANTIDADE; i++) {

                String nome = gerarNome(i);
                String telefone = gerarTelefone(i);

                writer.write(nome + ";" + telefone);
                writer.newLine();
            }

            long fim = System.nanoTime();

            double tempoMs = (fim - inicio) / 1_000_000.0;

            System.out.println("Arquivo gerado com sucesso!");
            System.out.println("Local: " + arquivo.getAbsolutePath());
            System.out.println("Contatos gerados: " + QUANTIDADE);
            System.out.printf("Tempo de geração: %.2f ms%n", tempoMs);

        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo: " + e.getMessage());
        }
    }

    private static String gerarNome(int i) {
        String[] nomes = {
            "Ana", "Bruno", "Carla", "Daniel", "Eduarda",
            "Felipe", "Gabriela", "Henrique", "Isabela", "João"
        };

        String[] sobrenomes = {
            "Silva", "Souza", "Oliveira", "Santos", "Pereira",
            "Costa", "Rodrigues", "Almeida", "Nascimento", "Lima"
        };

        return nomes[i % nomes.length] + " "
             + sobrenomes[i % sobrenomes.length] + i;
    }

    private static String gerarTelefone(int i) {
        return String.format("279%08d", i);
    }
}