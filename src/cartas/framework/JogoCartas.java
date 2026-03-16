package cartas.framework;

import java.util.ArrayList;
import java.util.List;

/* Classe base abstrata que orquestra qualquer jogo de cartas, controlando jogadores e turnos. */
public abstract class JogoCartas {

    /* Mantendo o encapsulamento com private */
    private List<Jogador> jogadores;
    private Baralho baralho;
    private int indiceJogadorAtual;

    public JogoCartas() {
        this.jogadores = new ArrayList<>();
        this.indiceJogadorAtual = 0;
    }

    public void adicionarJogador(Jogador jogador) {
        if (jogador != null) {
            this.jogadores.add(jogador);
        }
    }

    public List<Jogador> getJogadores() {
        return this.jogadores;
    }

    /* Permite que as subclasses (JogoTruco, JogoUno) injetem seus baralhos específicos */
    public void setBaralho(Baralho baralho) {
        this.baralho = baralho;
    }

    public Baralho getBaralho() {
        return this.baralho;
    }

    /* Controle de turnos: avança para o próximo jogador em sentido horário */
    public void avancarTurno() {
        if (!jogadores.isEmpty()) {
            indiceJogadorAtual = (indiceJogadorAtual + 1) % jogadores.size();
        }
    }

    /* Útil para a regra do Uno de "Inverter" o sentido do jogo e outros jogos que utilizem alguma mecanica semelhante */
    public void inverterTurno() {
        if (!jogadores.isEmpty()) {
            indiceJogadorAtual = (indiceJogadorAtual - 1 + jogadores.size()) % jogadores.size();
        }
    }

    /* Define explicitamente de quem é a vez (útil para jogos com regras de pular vez) */
    public void setIndiceJogadorAtual(int indice) {
        this.indiceJogadorAtual = indice;
    }

    public Jogador getJogadorAtual() {
        if (jogadores.isEmpty()) {
            return null;
        }
        return jogadores.get(indiceJogadorAtual);
    }

    /* Padrão Template Method: Métodos abstratos que os jogos específicos devem implementar com suas próprias regras */
    public abstract void iniciarPartida();

    public abstract boolean isFimDeJogo();
}