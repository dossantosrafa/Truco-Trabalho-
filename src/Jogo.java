package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jogo {

    public List<Jogador> jogadores;

    public List<Rodada> rodadas;

    public Baralho baralho;

    public Carta cartaVirada;

    public byte pontuacaoA;
    public byte pontuacaoB;

    public Jogo(Jogador jogadorUm, Jogador jogadorDois) {
        this.jogadores = new ArrayList<>(List.of(jogadorUm, jogadorDois));
        this.baralho = new Baralho();
        this.rodadas = new ArrayList<>();
        pontuacaoA = 0;
        pontuacaoB = 0;
    }

    public boolean alguemGanhou(){
        return pontuacaoA >= 12 || pontuacaoB >= 12;
    }

    public void distribuirCartas(){
        cartaVirada = baralho.cartas.get(6);
        UserIteract.exibeCartaVirada(cartaVirada);
        transformaCartasEmManilhas();
        distribuiParaPrimeiroJogadorAJogar();
        distribuiParaSegundoJogadorAJogar();
    }

    private void distribuiParaSegundoJogadorAJogar() {
        jogadores.get(1).cartas.add(baralho.cartas.get(3));
        jogadores.get(1).cartas.add(baralho.cartas.get(4));
        jogadores.get(1).cartas.add(baralho.cartas.get(5));
    }

    private void distribuiParaPrimeiroJogadorAJogar() {
        jogadores.get(0).cartas.add(baralho.cartas.get(0));
        jogadores.get(0).cartas.add(baralho.cartas.get(1));
        jogadores.get(0).cartas.add(baralho.cartas.get(2));
    }

    private void transformaCartasEmManilhas() {
        for (Carta carta : baralho.cartas) {
            if(carta.numero == this.cartaVirada.numero + 1 || (this.cartaVirada.numero == 10 && carta.numero == 1)){
                switch (carta.naipe) {
                    case PAUS -> carta.valor*=10000;
                    case COPAS -> carta.valor*=1000;
                    case ESPADAS -> carta.valor*=100;
                    case OUROS -> carta.valor*=10;
                }
            }
        }
    }

    public void iniciarRodada() {
        rodadas.add(new Rodada());
        final Rodada rodada = rodadas.get(rodadas.size() - 1);
        rodada.cartaJogadorUm = jogada(0, null);
        rodada.cartaJogadorDois = jogada(1, rodada.cartaJogadorUm );
        defineVencedorETrocaOrdemParaProximaRodada(rodada);
    }

    private Carta jogada(int index, Carta cartaMesa) {
        Carta carta = jogadores.get(index).jogada(cartaMesa);
        removeCartaJogadaDaMaoDoJogador(index, carta);
        UserIteract.exibeCartaJogada(jogadores.get(index).nome, carta);
        return carta;
    }

    private void removeCartaJogadaDaMaoDoJogador(int index, Carta carta) {
        jogadores.get(index).cartas.remove(carta);
    }

    private void defineVencedorETrocaOrdemParaProximaRodada(Rodada rodada) {
        if(rodada.cartaJogadorUm.valor > rodada.cartaJogadorDois.valor) rodada.ganhador = 1;
        else if(rodada.cartaJogadorUm.valor < rodada.cartaJogadorDois.valor) rodada.ganhador = 2;
        else rodada.ganhador = 0;
        UserIteract.vencedorRodada(rodada.ganhador, jogadores.get(0), jogadores.get(1));
        if(rodada.cartaJogadorUm.valor < rodada.cartaJogadorDois.valor) Collections.swap(jogadores, 0 ,1);;
    }

    public boolean alguemPontuou() {
        int ganhador = 0;
        if(rodadas.isEmpty()) return false;
        if (rodadas.stream().filter(r -> r.ganhador == 1).count() >= 2) ganhador = 1;
        if (rodadas.stream().filter(r -> r.ganhador == 2).count() >= 2) ganhador = 2;
        if(rodadas.stream().anyMatch(r -> r.ganhador == 0) && rodadas.size() == 3)
            ganhador = rodadas.stream().filter(r -> r.ganhador != 0).findFirst().get().ganhador;
        if(ganhador == 1){
            pontuacaoA += 1;
            devolveCartasParaProximaRodada();
            return true;
        }
        if(ganhador == 2){
            pontuacaoB += 1;
            devolveCartasParaProximaRodada();
            return true;
        }
        return false;
    }

    private void devolveCartasParaProximaRodada() {
        this.baralho = new Baralho();
        rodadas.clear();
        jogadores.forEach(j -> j.cartas.clear());
    }
}
