package bankrupt;

public class Jogador {
    private String nome;
    private int saldo = 300;
    private int posicao = 0;
    private boolean ativo = true;
    private final Comportamento comportamento;

    public Jogador(String nome, Comportamento comportamento) {
        this.nome = nome;
        this.comportamento = comportamento;
    }

    public boolean deveComprar(Propriedade propriedade) {
        return comportamento.deveComprar(this, propriedade);
    }

    public void receber(int valor) {
        saldo += valor;
    }

    public void pagar(int valor) {
        saldo -= valor;
        if (saldo < 0) {
            ativo = false;
        }
    }

    public boolean estaAtivo() {
        return ativo;
    }

    public int getSaldo() {
        return saldo;
    }

    public int getPosicao() {
        return posicao;
    }

    public void mover(int casas, int tamanhoTabuleiro) {
        posicao = (posicao + casas) % tamanhoTabuleiro;
    }

    public String getNome() {
        return nome;
    }

    public Comportamento getComportamento() {
        return comportamento;
    }
}
