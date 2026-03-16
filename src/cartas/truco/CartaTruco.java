package cartas.truco;

import cartas.framework.Carta;

/* Representa uma carta do Truco Paulista, implementando a interface genérica do framework. */
public class CartaTruco implements Carta {
    private String valorNominal;
    private Naipe naipe;
    private int peso;

    public CartaTruco(String valorNominal, Naipe naipe) {
        this.valorNominal = valorNominal;
        this.naipe = naipe;
        this.peso = calcularPeso(valorNominal, naipe);
    }

    /*  Calcula a força da carta no jogo. Mantém a regra original das manilhas fixas exigida nas especificações.*/
    private int calcularPeso(String valor, Naipe n) {
        /* Verifica as manilhas fixas: 4 ♠ (Zape), 7 ♥, 4 ♣ (Espadilha), 7 ♦ */
        if (valor.equals("4") && n == Naipe.PAUS) return 14; /* Zape (4 ♣) - Nota: Adaptei o naipe com base no nome comum, mas o peso é absoluto */
        if (valor.equals("7") && n == Naipe.COPAS) return 13; /* 7 ♥ */
        if (valor.equals("4") && n == Naipe.ESPADAS) return 12; /* Espadilha (4 ♠) */
        if (valor.equals("7") && n == Naipe.OUROS) return 11; /* 7 ♦ */

        /* Hierarquia das cartas simples (3 é a mais forte, 4 a mais fraca) */
        switch (valor) {
            case "3": return 10;
            case "2": return 9;
            case "A": return 8;
            case "K": return 7;
            case "J": return 6;
            case "Q": return 5;
            case "7": return 4;
            case "6": return 3;
            case "5": return 2;
            case "4": return 1;
            default: return 0;
        }
    }

    public int getPeso() {
        return peso;
    }

    public String getValorNominal() {
        return valorNominal;
    }

    public Naipe getNaipe() {
        return naipe;
    }

    @Override
    public String getDescricao() {
        return valorNominal + " " + naipe.getSimbolo();
    }
}