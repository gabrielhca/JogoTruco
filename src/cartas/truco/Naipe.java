package cartas.truco;

/* Enumeração para garantir apenas os quatro naipes válidos do Truco. */
public enum Naipe {
    OUROS("♦"),
    ESPADAS("♠"),
    COPAS("♥"),
    PAUS("♣");

    private final String simbolo;

    Naipe(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getSimbolo() {
        return simbolo;
    }
}