package cartas.uno;

import cartas.framework.Baralho;

/* Implementação concreta do baralho para o jogo de Uno, contendo exatamente 108 cartas. */
public class BaralhoUno extends Baralho {

    @Override
    public void montarBaralho() {
        /* Iteramos pelas 4 cores principais para criar as cartas coloridas */
        Cor[] coresPrincipais = {Cor.VERMELHO, Cor.AZUL, Cor.VERDE, Cor.AMARELO};

        for (Cor cor : coresPrincipais) {
            /* um zero por cor somente */
            adicionarCarta(new CartaUno(cor, 0));

            /* dois de cada número de 1 a 9 por cor  */
            for (int i = 1; i <= 9; i++) {
                adicionarCarta(new CartaUno(cor, i));
                adicionarCarta(new CartaUno(cor, i));
            }

            /* cartas de ação (2 de cada por cor) */
            for (int i = 0; i < 2; i++) {
                adicionarCarta(new CartaUno(cor, TipoCarta.MAIS_DOIS)); /* +2  */
                adicionarCarta(new CartaUno(cor, TipoCarta.BLOQUEIO));  /* Bloqueio  */
                adicionarCarta(new CartaUno(cor, TipoCarta.INVERTER));  /* Inverter  */
            }
        }

        /* cartas coringa (4 de cada no total) */
        for (int i = 0; i < 4; i++) {
            adicionarCarta(new CartaUno(Cor.ESPECIAL, TipoCarta.CORINGA)); /* Escolhe a cor  */
            adicionarCarta(new CartaUno(Cor.ESPECIAL, TipoCarta.MAIS_QUATRO)); /* Escolhe a cor e compra 4  */
        }
    }
}