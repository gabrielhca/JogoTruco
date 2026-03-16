package cartas.framework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Classe base para os baralhos. Contém a lógica reutilizável de gerenciamento das cartas. */
public abstract class Baralho {
    private List<Carta> cartas;

    public Baralho() {
        this.cartas = new ArrayList<>();
        montarBaralho();
        embaralhar();
    }

    /* Método abstrato que cada jogo específico deve implement para instanciar e adicionar suas próprias cartas à lista. */
    public abstract void montarBaralho();

    protected void adicionarCarta(Carta carta) {
        if (carta != null) {
            this.cartas.add(carta);
        }
    }

    /* Embaralha as cartas atuais no baralho.*/
    public void embaralhar() {
        Collections.shuffle(this.cartas);
    }

    /* Remove e retorna a carta do topo do baralho. Retorna a carta comprada, ou null se o baralho estiver vazio */
    public Carta comprarCarta() {
        if (cartas.isEmpty()) {
            return null;
        }
        return cartas.remove(cartas.size() - 1);
    }

    /* Distribui um número específico de cartas */
    public List<Carta> distribuir(int quantidade) {
        List<Carta> mao = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            Carta c = comprarCarta();
            if (c != null) {
                mao.add(c);
            }
        }
        return mao;
    }

    public int getQuantidadeCartas() {
        return cartas.size();
    }
}