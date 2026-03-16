package cartas.framework;

/* Entidade base para um jogador em qualquer jogo de cartas. */
public class Jogador {
    private String nome;
    private Mao mao;

    /* estatisticas tiradas do jogo de truco fornecido, que guardam a quantidade de partidas e vitorias de cada jogador */
    private int vitoriasTotais;
    private int partidasTotais;

    public Jogador(String nome) {
        this.nome = nome;
        this.mao = new Mao();
        this.vitoriasTotais = 0;
        this.partidasTotais = 0;
    }

    public String getNome() { return nome; }
    public Mao getMao() { return mao; }
    public void registrarVitoria() { this.vitoriasTotais++; }
    public void registrarPartida() { this.partidasTotais++; }
    public int getVitoriasTotais() { return vitoriasTotais; }
    public int getPartidasTotais() { return partidasTotais; }
}