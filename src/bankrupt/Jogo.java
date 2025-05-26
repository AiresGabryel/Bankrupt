package bankrupt;

import java.util.*;

public class Jogo {
    private List<Jogador> jogadores;
    private List<Propriedade> propriedades;
    private final int NUM_PROPRIEDADES = 20;
    private final int MAX_RODADAS = 1000;
    private int rodada;

    public void iniciar() {
        criarJogadores();
        criarPropriedades();
        rodada = 0;

        while (jogoContinua() && rodada < MAX_RODADAS) {
            rodada++;
            for (Jogador jogador : jogadores) {
                if (jogador.estaAtivo()) {
                    turno(jogador);
                }
            }
        }

        exibirResultado();
    }

    private void criarJogadores() {
        jogadores = new ArrayList<>();
        jogadores.add(new Jogador("Impulsivo", Comportamento.IMPULSIVO));
        jogadores.add(new Jogador("Exigente", Comportamento.EXIGENTE));
        jogadores.add(new Jogador("Cauteloso", Comportamento.CAUTELOSO));
        jogadores.add(new Jogador("Aleatório", Comportamento.ALEATORIO));

        Collections.shuffle(jogadores);
    }

    private void criarPropriedades() {
        propriedades = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < NUM_PROPRIEDADES; i++) {
            int preco = 100 + random.nextInt(101);
            int aluguel = 20 + random.nextInt(31);
            propriedades.add(new Propriedade(preco, aluguel));
        }
    }

    private void turno(Jogador jogador) {
        int dado = new Random().nextInt(6) + 1;
        jogador.mover(dado, propriedades.size());
        Propriedade atual = propriedades.get(jogador.getPosicao());

        if (atual.estaDisponivel()) {
            if (jogador.deveComprar(atual)) {
                if (jogador.getSaldo() >= atual.getPreco()) {
                    jogador.pagar(atual.getPreco());
                    atual.setDono(jogador);
                }
            }
        } else if (!atual.getDono().equals(jogador)) {
            jogador.pagar(atual.getAluguel());
            atual.getDono().receber(atual.getAluguel());
        }

        if (!jogador.estaAtivo()) {
            liberarPropriedades(jogador);
        }
    }

    private void liberarPropriedades(Jogador jogador) {
        for (Propriedade p : propriedades) {
            if (jogador.equals(p.getDono())) {
                p.setDono(null);
            }
        }
    }

    private boolean jogoContinua() {
        int ativos = 0;
        for (Jogador jogador : jogadores) {
            if (jogador.estaAtivo()) ativos++;
        }
        return ativos > 1;
    }

    private void exibirResultado() {
        System.out.println("\n=== FIM DO JOGO ===");
        System.out.println("Rodadas: " + rodada);
        Jogador vencedor = null;
        for (Jogador j : jogadores) {
            System.out.printf("%s - %s - saldo: %d\n",
                j.getNome(),
                j.estaAtivo() ? "Ativo" : "Falido",
                j.getSaldo());
            if (j.estaAtivo()) {
                if (vencedor == null || j.getSaldo() > vencedor.getSaldo()) {
                    vencedor = j;
                }
            }
        }
        if (vencedor != null) {
            System.out.println("Vencedor: " + vencedor.getNome());
        } else {
            System.out.println("Ninguém venceu (empate).");
        }
    }
}
