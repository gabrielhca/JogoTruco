package cartas.uno;

import cartas.framework.Carta;

/* Representa uma carta específica do jogo de Uno[cite: 35, 36]. */
public class CartaUno implements Carta {
    private Cor cor;
    private TipoCarta tipo;
    private int numero;

    /* Construtor para cartas numeradas [cite: 35] */
    public CartaUno(Cor cor, int numero) {
        this.cor = cor;
        this.tipo = TipoCarta.NUMERO;
        this.numero = numero;
    }

    /* Construtor para cartas de ação e coringas [cite: 36, 40] */
    public CartaUno(Cor cor, TipoCarta tipo) {
        this.cor = cor;
        this.tipo = tipo;
        this.numero = -1; /* Usamos -1 para indicar que não é uma carta numérica */
    }

    public Cor getCor() {
        return cor;
    }

    /* Permite mudar a cor da carta (usado quando alguém joga um curinga e escolhe a cor da mesa) [cite: 41, 57] */
    public void setCor(Cor cor) {
        this.cor = cor;
    }

    public TipoCarta getTipo() {
        return tipo;
    }

    public int getNumero() {
        return numero;
    }

    @Override
    public String getDescricao() {
        if (tipo == TipoCarta.NUMERO) {
            return numero + " " + cor;
        } else if (tipo == TipoCarta.CORINGA || tipo == TipoCarta.MAIS_QUATRO) {
            return tipo.toString() + (cor != Cor.ESPECIAL ? " (" + cor + ")" : "");
        } else {
            return tipo.toString() + " " + cor;
        }
    }
}