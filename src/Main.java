package src;

public class Main {

    public static void main(String[] args) {

        Jogador j1 = new Jogador("Valdivia");
        Jogador j2 = new Jogador("Roberto Carlos");
        Jogo jogo = new Jogo(j1, j2);

        while (!jogo.alguemGanhou()) {
            UserIteract.mensagemEmbaralhando();
            jogo.baralho.embaralhar();
            jogo.distribuirCartas();
            while (!jogo.alguemPontuou()) {
                jogo.iniciarRodada();
            }
            UserIteract.exibirPlacar(j1.nome, jogo.pontuacaoA, j2.nome, jogo.pontuacaoB);
        }
    }
}
