package cartas.uno;

import cartas.framework.Carta;
import cartas.framework.Jogador;
import java.util.Scanner;

/* Esta classe atua como a VIEW (Interface de Usuário) e CONTROLLER do console */
public class PartidaUno {

    private JogoUno jogo;
    private Scanner scanner;

    public PartidaUno() {
        this.jogo = new JogoUno();
        this.scanner = new Scanner(System.in);
    }

    /* Método principal que controla o fluxo visual da partida */
    public void iniciarJogo() {
        System.out.println("=== BEM-VINDO AO UNO NO CONSOLE ===");
        System.out.print("Quantos jogadores vão jogar? (Ex: 2, 3, 4): ");
        int numJogadores = Integer.parseInt(scanner.nextLine());

        for (int i = 1; i <= numJogadores; i++) {
            System.out.print("Digite o nome do " + i + "º jogador: ");
            String nome = scanner.nextLine();
            jogo.adicionarJogador(new Jogador(nome));
        }

        jogo.iniciarPartida();
        System.out.println("\nPartida Iniciada! Distribuindo 7 cartas para cada...\n");

        loopDaPartida();
        exibirVencedor();
    }

    /* Isola o laço de repetição enquanto o jogo acontece */
    private void loopDaPartida() {
        while (!jogo.isFimDeJogo()) {
            Jogador atual = jogo.getJogadorAtual();
            imprimirMesa(atual);

            boolean jogadaValida = false;
            while (!jogadaValida) {
                System.out.print("Escolha a opção (número da carta ou 'C' para comprar): ");
                String escolha = scanner.nextLine().toUpperCase();

                if (escolha.equals("C")) {
                    processarCompraDeCarta(atual);
                    jogadaValida = true;
                } else {
                    jogadaValida = tentarJogarCartaDaMao(atual, escolha);
                }
            }
        }
    }

    /* Imprime o status atual da mesa na tela */
    private void imprimirMesa(Jogador atual) {
        System.out.println("--------------------------------------------------");
        System.out.println("MESA -> Carta no Topo: [" + jogo.getCartaTopo().getDescricao() + "] | Cor Atual: " + jogo.getCorAtual());
        System.out.println("Sentido do jogo: " + (jogo.isSentidoHorario() ? "Horário (->)" : "Anti-horário (<-)"));
        System.out.println("\nVez de: " + atual.getNome());

        System.out.println("Suas cartas:");
        for (int i = 0; i < atual.getMao().getQuantidadeCartas(); i++) {
            System.out.println("  [" + i + "] " + atual.getMao().getCartas().get(i).getDescricao());
        }
        System.out.println("  [C] Comprar uma carta");
    }

    /* Processa a tentativa de jogar uma carta da mão baseada no número digitado */
    private boolean tentarJogarCartaDaMao(Jogador atual, String escolha) {
        try {
            int indice = Integer.parseInt(escolha);
            CartaUno cartaEscolhida = (CartaUno) atual.getMao().getCartas().get(indice);

            if (jogo.podeJogarCarta(cartaEscolhida)) {
                Cor novaCor = cartaEscolhida.getCor();

                if (novaCor == Cor.ESPECIAL) {
                    novaCor = escolherCorCoringa();
                }

                jogo.jogarCarta(atual, cartaEscolhida, novaCor);
                System.out.println("-> Você jogou: " + cartaEscolhida.getDescricao());
                return true;
            } else {
                System.out.println("❌ JOGADA INVÁLIDA! A carta não combina com a mesa. Tente de novo.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Opção inválida! Digite o número da carta ou 'C'.");
            return false;
        }
    }

    /* Isola toda a lógica de interação quando o jogador compra uma carta */
    private void processarCompraDeCarta(Jogador atual) {
        CartaUno comprada = jogo.comprarCarta(atual);

        if (comprada == null) {
            System.out.println("-> O baralho acabou! Passando a vez...");
            jogo.avancarTurno();
            return;
        }

        System.out.println("-> Você comprou: " + comprada.getDescricao());

        if (jogo.podeJogarCarta(comprada)) {
            System.out.print("A carta serve na mesa! Deseja jogá-la agora? (S/N): ");
            String querJogar = scanner.nextLine().toUpperCase();

            if (querJogar.equals("S")) {
                Cor novaCor = comprada.getCor();
                if (novaCor == Cor.ESPECIAL) {
                    novaCor = escolherCorCoringa();
                }
                jogo.jogarCarta(atual, comprada, novaCor);
                System.out.println("-> Você jogou a carta que acabou de comprar!");
            } else {
                System.out.println("Você guardou a carta. Passando a vez...");
                jogo.avancarTurno();
            }
        } else {
            System.out.println("A carta não serve para jogar. Passando a vez...");
            jogo.avancarTurno();
        }
    }

    /* Rotina para escolher a cor */
    private Cor escolherCorCoringa() {
        System.out.println("Você jogou um Coringa! Escolha a nova cor:");
        System.out.println("[0] VERMELHO  [1] AZUL  [2] VERDE  [3] AMARELO");
        int escolhaCor = Integer.parseInt(scanner.nextLine());
        return Cor.values()[escolhaCor];
    }

    /* Exibe os resultados ao final da partida */
    private void exibirVencedor() {
        Jogador vencedor = jogo.getVencedor();
        if (vencedor != null) {
            vencedor.registrarVitoria();
            System.out.println("\n🎉🎉🎉 FIM DE JOGO! 🎉🎉🎉");
            System.out.println("🏆 O campeão foi: " + vencedor.getNome().toUpperCase() + "!");
        } else {
            System.out.println("\nFim de jogo inesperado.");
        }
        scanner.close();
    }
}