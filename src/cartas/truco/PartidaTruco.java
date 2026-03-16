package cartas.truco;

import cartas.framework.Carta;
import cartas.framework.Jogador;

import java.util.Scanner;

/* Esta classe atua como a VIEW (Interface de Usuário) e CONTROLLER do console para o Truco */
public class PartidaTruco {

    private JogoTruco jogo;
    private Scanner scanner;
    private Dupla dupla1;
    private Dupla dupla2;

    public PartidaTruco() {
        this.scanner = new Scanner(System.in);
    }

    /* Ponto de entrada visual do jogo */
    public void iniciarJogo() {
        System.out.println("=== BEM-VINDO AO TRUCO NO CONSOLE ===");
        configurarDuplas();

        this.jogo = new JogoTruco(dupla1, dupla2);
        this.jogo.iniciarPartida();

        loopDaPartida();
        exibirVencedor();
    }

    /* Isola a rotina chata de ler nomes e instanciar jogadores */
    private void configurarDuplas() {
        System.out.println("DUPLA 1:");
        System.out.print("Nome do Jogador 1: ");
        String n1 = scanner.nextLine();
        System.out.print("Nome do Jogador 2: ");
        String n2 = scanner.nextLine();
        this.dupla1 = new Dupla("Dupla 1", new Jogador(n1), new Jogador(n2));

        System.out.println("\nDUPLA 2:");
        System.out.print("Nome do Jogador 3: ");
        String n3 = scanner.nextLine();
        System.out.print("Nome do Jogador 4: ");
        String n4 = scanner.nextLine();
        this.dupla2 = new Dupla("Dupla 2", new Jogador(n3), new Jogador(n4));
    }

    /* O laço principal que mantém o jogo rodando */
    private void loopDaPartida() {
        while (!jogo.isFimDeJogo()) {
            Jogador atual = jogo.getJogadorAtual();
            imprimirMesa(atual);

            boolean jogadaValida = false;
            while (!jogadaValida) {
                System.out.print("Escolha uma carta [Número] ou ação [T = Truco, F = Fugir]: ");
                String escolha = scanner.nextLine().toUpperCase();

                if (escolha.equals("T")) {
                    processarGritoDeTruco();
                    jogadaValida = true; // Após gritar, ainda precisaria jogar, mas simplificamos aqui passando a vez ou pedindo carta novamente.
                    // Para ficar preciso: ao gritar truco, a pessoa ainda tem que jogar a carta,
                    // então vamos só avisar e deixar o laço rodar de novo pedindo a carta:
                    jogadaValida = false;
                }
                else if (escolha.equals("F")) {
                    processarFuga(atual);
                    jogadaValida = true;
                }
                else {
                    jogadaValida = tentarJogarCarta(atual, escolha);
                }
            }
        }
    }

    /* Imprime o status da mesa e as cartas da mão do jogador atual */
    private void imprimirMesa(Jogador atual) {
        System.out.println("\n==================================================");
        System.out.println("PLACAR: " + dupla1.getNome() + " [" + dupla1.getPontos() + "] x [" + dupla2.getPontos() + "] " + dupla2.getNome());
        System.out.println("VALOR DA MÃO ATUAL: " + jogo.getValorMaoAtual() + " ponto(s)");
        System.out.println("--------------------------------------------------");

        System.out.print("CARTAS NA MESA: ");
        for (CartaTruco c : jogo.getMesaAtual()) {
            System.out.print("[" + c.getDescricao() + "] ");
        }
        System.out.println("\n");

        System.out.println("Vez de: " + atual.getNome());
        System.out.println("Suas cartas:");
        for (int i = 0; i < atual.getMao().getQuantidadeCartas(); i++) {
            System.out.println("  [" + i + "] " + atual.getMao().getCartas().get(i).getDescricao());
        }
    }

    /* Ação de aumentar a aposta */
    private void processarGritoDeTruco() {
        jogo.aumentarAposta();
        System.out.println("🔥 TRUCOOOOO! A mão agora vale " + jogo.getValorMaoAtual() + " pontos!");
        System.out.println("Agora jogue uma carta ou fuja:");
    }

    /* Ação de correr */
    private void processarFuga(Jogador atual) {
        Dupla duplaQueCorreu = pertenceADupla(atual, dupla1) ? dupla1 : dupla2;
        jogo.processarCorrer(duplaQueCorreu);
        System.out.println("🏃‍♂️ ALGUÉM CORREU! Os pontos foram contabilizados e uma nova mão vai começar.");
    }

    /* Tenta converter o texto digitado num índice de carta e mandá-la para a mesa */
    private boolean tentarJogarCarta(Jogador atual, String escolha) {
        try {
            int indice = Integer.parseInt(escolha);
            CartaTruco cartaEscolhida = (CartaTruco) atual.getMao().getCartas().get(indice);

            // O próprio método jogarCarta do JogoTruco já remove da mão, põe na mesa e avança o turno
            jogo.jogarCarta(atual, cartaEscolhida);
            System.out.println("-> " + atual.getNome() + " jogou: " + cartaEscolhida.getDescricao());

            return true;
        } catch (Exception e) {
            System.out.println("❌ Entrada inválida. Digite o número da carta válido, 'T' ou 'F'.");
            return false;
        }
    }

    /* Método auxiliar para verificar a qual dupla o jogador pertence */
    private boolean pertenceADupla(Jogador jogador, Dupla dupla) {
        return jogador == dupla.getJogador1() || jogador == dupla.getJogador2();
    }

    /* Exibe quem chegou a 12 pontos primeiro */
    private void exibirVencedor() {
        System.out.println("\n🎉🎉🎉 FIM DE JOGO! 🎉🎉🎉");
        if (dupla1.venceuPartida()) {
            System.out.println("🏆 A " + dupla1.getNome() + " é a grande CAMPEÃ com " + dupla1.getPontos() + " pontos!");
        } else {
            System.out.println("🏆 A " + dupla2.getNome() + " é a grande CAMPEÃ com " + dupla2.getPontos() + " pontos!");
        }
        scanner.close();
    }
}