package cartas.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Representa as cartas que um jogador tem em mãos durante o jogo. */
public class Mao {

    /* Mantendo o bom encapsulamento com private */
    private List<Carta> cartas;

    public Mao() {
        this.cartas = new ArrayList<>();
    }

    public void adicionarCarta(Carta carta) {
        if (carta != null) {
            this.cartas.add(carta);
        }
    }

    public void adicionarCartas(List<Carta> novasCartas) {
        if (novasCartas != null) {
            this.cartas.addAll(novasCartas);
        }
    }

    /* Joga uma carta baseada na sua posição na mão. */
    public Carta jogarCarta(int indice) {
        if (indice >= 0 && indice < cartas.size()) {
            return cartas.remove(indice);
        }
        return null;
    }

    /* Joga uma carta específica, passando o próprio objeto. */
    public Carta jogarCarta(Carta carta) {
        if (cartas.remove(carta)) {
            return carta;
        }
        return null;
    }

    public int getQuantidadeCartas() {
        return cartas.size();
    }

    /* Retorna a lista de cartas, mas de forma "somente leitura" para não quebrar o encapsulamento. */
    public List<Carta> getCartas() {
        return Collections.unmodifiableList(cartas);
    }

    public void descartarMao() {
        cartas.clear();
    }
}