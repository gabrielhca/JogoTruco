package cartas.uno;

import cartas.framework.Carta;
import cartas.framework.Jogador;
import cartas.framework.JogoCartas;

import java.util.ArrayList;
import java.util.List;

/* Orquestra as regras específicas do jogo de Uno, implementando a lógica de turnos e efeitos das cartas. */
public class JogoUno extends JogoCartas {

    private List<CartaUno> monteDescarte;
    private Cor corAtual;
    private boolean sentidoHorario;

    public JogoUno() {
        super();
        setBaralho(new BaralhoUno());
        this.monteDescarte = new ArrayList<>();
        this.sentidoHorario = true; /* O jogo começa no sentido horário */
    }

    @Override
    public void iniciarPartida() {
        getBaralho().embaralhar();
        monteDescarte.clear();
        this.sentidoHorario = true;

        /* Regra 5.1.2: Cada jogador recebe 7 cartas  */
        for (Jogador jogador : getJogadores()) {
            jogador.getMao().descartarMao();
            List<Carta> cartasIniciais = getBaralho().distribuir(7);
            jogador.getMao().adicionarCartas(cartasIniciais);
        }

        /* Regra 5.1.2: A primeira carta do descarte não pode ser coringa  */
        CartaUno primeiraCarta;
        do {
            primeiraCarta = (CartaUno) getBaralho().comprarCarta();
            monteDescarte.add(primeiraCarta);
        } while (primeiraCarta.getCor() == Cor.ESPECIAL);

        this.corAtual = primeiraCarta.getCor();
    }

    @Override
    public boolean isFimDeJogo() {
        /* Regra 5.1.5: Vitória ao ficar com zero cartas na mão  */
        for (Jogador jogador : getJogadores()) {
            if (jogador.getMao().getQuantidadeCartas() == 0) {
                return true;
            }
        }
        return false;
    }

    /* Retorna o jogador que bateu (ficou com 0 cartas) */
    public Jogador getVencedor() {
        for (Jogador jogador : getJogadores()) {
            if (jogador.getMao().getQuantidadeCartas() == 0) {
                return jogador;
            }
        }
        return null; /* Retorna null se ninguém tiver ganhado ainda */
    }

    /* Retorna a carta no topo do monte de descarte */
    public CartaUno getCartaTopo() {
        if (monteDescarte.isEmpty()) return null;
        return monteDescarte.get(monteDescarte.size() - 1);
    }

    /* Regra 5.1.3: Valida se a carta pode ser jogada  */
    public boolean podeJogarCarta(CartaUno cartaJogada) {
        CartaUno cartaTopo = getCartaTopo();
        if (cartaTopo == null) return true;

        /* Mesma cor OU mesmo número/símbolo OU é um coringa  */
        boolean mesmaCor = cartaJogada.getCor() == this.corAtual;
        boolean mesmoNumero = (cartaJogada.getTipo() == TipoCarta.NUMERO) &&
                (cartaJogada.getNumero() == cartaTopo.getNumero());
        boolean mesmoSimbolo = (cartaJogada.getTipo() != TipoCarta.NUMERO) &&
                (cartaJogada.getTipo() == cartaTopo.getTipo());
        boolean ehCoringa = cartaJogada.getCor() == Cor.ESPECIAL;

        return mesmaCor || mesmoNumero || mesmoSimbolo || ehCoringa;
    }

    /* Executa a jogada de uma carta, aplica seus efeitos e passa o turno */
    public boolean jogarCarta(Jogador jogador, CartaUno carta, Cor novaCorEscolhida) {
        if (!podeJogarCarta(carta)) {
            return false; /* Jogada inválida */
        }

        jogador.getMao().jogarCarta(carta);
        monteDescarte.add(carta);

        /* Atualiza a cor da mesa. Se for especial, usa a cor escolhida pelo jogador  */
        if (carta.getCor() == Cor.ESPECIAL) {
            this.corAtual = novaCorEscolhida;
        } else {
            this.corAtual = carta.getCor();
        }

        aplicarEfeitosEspeciais(carta);
        passarAVez(); /* Passa para o próximo após os efeitos */
        return true;
    }

    /* Regra 5.1.3: Se não puder jogar, deve comprar 1 carta  */
    public CartaUno comprarCarta(Jogador jogador) {
        Carta comprada = getBaralho().comprarCarta();
        if (comprada != null) {
            jogador.getMao().adicionarCarta(comprada);
        }
        return (CartaUno) comprada;
    }

    /* Regra 5.1.4: Aplica os efeitos especiais das cartas de ação  */
    private void aplicarEfeitosEspeciais(CartaUno carta) {
        switch (carta.getTipo()) {
            case INVERTER:
                /* Muda o sentido do jogo  */
                sentidoHorario = !sentidoHorario;
                break;
            case BLOQUEIO:
                passarAVez(); // Pula o turno do próximo jogador
                break;
            case MAIS_DOIS:
                /* Próximo compra 2 cartas e perde a vez  */
                passarAVez();
                Jogador proximo = getJogadorAtual();
                comprarCartasParaJogador(proximo, 2);
                break;
            case MAIS_QUATRO:
                /* Próximo compra 4 cartas e perde a vez  */
                passarAVez();
                Jogador proximoM4 = getJogadorAtual();
                comprarCartasParaJogador(proximoM4, 4);
                break;
            default:
                break; /* Cartas numéricas ou coringa simples não têm efeitos punitivos no próximo */
        }
    }

    /* Método auxiliar para comprar múltiplas cartas como punição (+2, +4) */
    private void comprarCartasParaJogador(Jogador jogador, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            Carta c = getBaralho().comprarCarta();
            if (c != null) {
                jogador.getMao().adicionarCarta(c);
            }
        }
    }

    /* Utiliza o método criado no framework para alterar o sentido do Uno */
    private void passarAVez() {
        if (sentidoHorario) {
            avancarTurno();  /* Chama o método do seu framework (vai para frente) */
        } else {
            inverterTurno(); /* Chama o método do seu framework (vai para trás) */
        }
    }

    public Cor getCorAtual() { return corAtual; }
    public boolean isSentidoHorario() { return sentidoHorario; }
}