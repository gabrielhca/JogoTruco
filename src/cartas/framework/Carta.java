package cartas.framework;

/* Interface base para todas as cartas do framework.Garante que qualquer jogo possa ter seu proprio tipo de de carta */
public interface Carta {
    /*retorna o tipo da carta */
    String getDescricao();
}