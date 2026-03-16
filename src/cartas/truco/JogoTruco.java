package cartas.truco;

import cartas.framework.JogoCartas;
import cartas.framework.Jogador;
import cartas.framework.Carta;
import java.util.List;

/* Orquestra as regras específicas do Truco Paulista. */
public class JogoTruco extends JogoCartas {
    private Dupla dupla1;
    private Dupla dupla2;
    private int valorMaoAtual; /* Controla o sistema de truco: 1, 3, 6, 9, 12 */
    private List<CartaTruco> mesaAtual; //
    private int[] vazasGanhadas;

    public JogoTruco(Dupla dupla1, Dupla dupla2) {
        super();
        this.dupla1 = dupla1;
        this.dupla2 = dupla2;
        this.valorMaoAtual = 1; /* A mão sempre começa valendo 1 ponto */
        this.valorMaoAtual = 1;
        this.mesaAtual = new java.util.ArrayList<>();
        this.vazasGanhadas = new int[2];


        setBaralho(new BaralhoTruco());

        /* Adiciona os jogadores na sequência exigida (0 -> 1 -> 2 -> 3) alternando os membros das duplas na mesa. */
        adicionarJogador(dupla1.getJogador1());
        adicionarJogador(dupla2.getJogador1());
        adicionarJogador(dupla1.getJogador2());
        adicionarJogador(dupla2.getJogador2());
    }

    @Override
    public void iniciarPartida() {
        dupla1.zerarPontos();
        dupla2.zerarPontos();
        prepararNovaMao();
    }

    /* Prepara uma nova rodada (mão), distribuindo 3 cartas para cada jogador */
    public void prepararNovaMao() {
        this.valorMaoAtual = 1; // A mão volta a valer 1 ponto
        // Limpa as cartas que estavam na mesa da rodada anterior
        if (this.mesaAtual != null) {
            this.mesaAtual.clear();
        }
        // Zera o placar de vazas ("melhor de 3") da mão atual
        if (this.vazasGanhadas != null) {
            this.vazasGanhadas[0] = 0; // Vazas ganhas pela Dupla 1
            this.vazasGanhadas[1] = 0; // Vazas ganhas pela Dupla 2
        }
        getBaralho().embaralhar();
        // Distribui 3 cartas novas para cada jogador
        for (Jogador jogador : getJogadores()) {
            jogador.getMao().descartarMao(); // Joga fora as cartas velhas da mão
            List<Carta> cartasDistribuidas = getBaralho().distribuir(3);
            jogador.getMao().adicionarCartas(cartasDistribuidas);
        }
    }

    @Override
    public boolean isFimDeJogo() {
        /* O jogo termina quando uma das duplas atinge ou ultrapassa 12 pontos */
        return dupla1.getPontos() >= 12 || dupla2.getPontos() >= 12;
    }

    /* Método para gerenciar o sistema de apostas (Truco, Seis, Nove, Doze) */
    public void aumentarAposta() {
        if (valorMaoAtual == 1) {
            valorMaoAtual = 3;
        } else if (valorMaoAtual == 3) {
            valorMaoAtual = 6;
        } else if (valorMaoAtual == 6) {
            valorMaoAtual = 9;
        } else if (valorMaoAtual == 9) {
            valorMaoAtual = 12;
        }
    }

    /* Processa a pontuação correta quando uma equipe corre (desiste) do truco */
    public void processarCorrer(Dupla duplaQueCorreu) {
        Dupla duplaVencedora = (duplaQueCorreu == dupla1) ? dupla2 : dupla1;

        /* Se a equipe correu, a outra ganha o valor ANTERIOR da aposta. Ex: Se pediu truco (foi para 3) e o outro correu, ganha 1.*/
        int pontosGanhos = (valorMaoAtual == 3) ? 1 :
                (valorMaoAtual == 6) ? 3 :
                        (valorMaoAtual == 9) ? 6 : 9;

        duplaVencedora.adicionarPontos(pontosGanhos);
        prepararNovaMao();
    }

    /* Processa uma carta jogada na mesa */
    public void jogarCarta(Jogador jogador, CartaTruco carta) {
        jogador.getMao().jogarCarta(carta); // Remove da mão do jogador
        mesaAtual.add(carta);               // Coloca na mesa
        avancarTurno();                     // Passa a vez

        // Se a mesa tem 4 cartas, a vaza acabou (todos os 4 jogaram)
        if (mesaAtual.size() == 4) {
            avaliarVaza();
        }
    }

    /* Descobre quem jogou a carta mais forte da rodada e dá a vaza para a dupla */
    /* Descobre quem jogou a carta mais forte da rodada e dá a vaza para a dupla */
    private void avaliarVaza() {
        int maiorPeso = -1;
        int indiceVencedor = -1;

        for (int i = 0; i < mesaAtual.size(); i++) {
            if (mesaAtual.get(i).getPeso() > maiorPeso) {
                maiorPeso = mesaAtual.get(i).getPeso();
                indiceVencedor = i;
            }
        }

        // Descobre quem foi o dono da carta vencedora
        Jogador vencedorRodada = getJogadores().get(indiceVencedor);

        if (pertenceDupla1(vencedorRodada)) {
            vazasGanhadas[0]++;
        } else {
            vazasGanhadas[1]++;
        }

        mesaAtual.clear(); // Limpa a mesa para a próxima vaza

        // O vencedor da vaza é quem começa jogando a próxima carta!
        setIndiceJogadorAtual(indiceVencedor);
        // ==============================================

        // Verifica se alguém já ganhou 2 vazas (melhor de 3)
        if (vazasGanhadas[0] == 2) {
            dupla1.adicionarPontos(valorMaoAtual);
            prepararNovaMao();
        } else if (vazasGanhadas[1] == 2) {
            dupla2.adicionarPontos(valorMaoAtual);
            prepararNovaMao();
        }
    }

    private boolean pertenceDupla1(Jogador j) {
        return j == dupla1.getJogador1() || j == dupla1.getJogador2();
    }

    public int getValorMaoAtual() {
        return valorMaoAtual;
    }

    public Dupla getDupla1() {
        return dupla1;
    }

    public Dupla getDupla2() {
        return dupla2;
    }

    public List<CartaTruco> getMesaAtual() {
        return mesaAtual;
    }
}