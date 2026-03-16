package cartas.truco;

import cartas.framework.Baralho;

/* Implementação concreta do baralho genérico para o jogo de Truco. */
public class BaralhoTruco extends Baralho {

    @Override
    public void montarBaralho() {
        String[] valores = {"4", "5", "6", "7", "Q", "J", "K", "A", "2", "3"};

        for (Naipe naipe : Naipe.values()) {
            for (String valor : valores) {
                /* O método adicionarCarta() foi herdado da nossa classe Baralho do framework */
                adicionarCarta(new CartaTruco(valor, naipe));
            }
        }
    }
}