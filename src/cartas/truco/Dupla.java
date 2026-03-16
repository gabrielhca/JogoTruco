package cartas.truco;

import cartas.framework.Jogador;

/* Representa uma equipe de dois jogadores no jogo de Truco. */
public class Dupla {
    private String nome;
    private Jogador jogador1;
    private Jogador jogador2;
    private int pontos; /* Pontos na partida atual (vai até 12) */
    private int quedasGanhas; /* Estatística de vitórias totais da dupla */

    public Dupla(String nome, Jogador jogador1, Jogador jogador2) {
        this.nome = nome;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
        this.pontos = 0;
        this.quedasGanhas = 0;
    }

    public void adicionarPontos(int valor) {
        this.pontos += valor;
    }

    public void zerarPontos() {
        this.pontos = 0;
    }

    public int getPontos() {
        return pontos;
    }

    public void registrarQuedaGanha() {
        this.quedasGanhas++;
    }

    public String getNome() {
        return nome;
    }
    public Jogador getJogador1() {
        return jogador1;
    }
    public Jogador getJogador2() {
        return jogador2;
    }

    public boolean venceuPartida() {
        return this.pontos >= 12;
    }

}