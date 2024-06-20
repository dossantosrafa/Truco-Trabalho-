package src;

import java.util.ArrayList;
import java.util.List;

public class Jogador {

    public List<Carta> cartas;
    public String nome;

    public Jogador(String nome) {
        this.cartas = new ArrayList<>();
        this.nome = nome;
    }

    public Carta jogada(Carta cartaNaMesa) {
        // Se não há carta na mesa, o jogador é o primeiro a jogar
        if (cartaNaMesa == null) {
            // Joga a carta de menor valor na primeira rodada
            return cartaDeMenorValor();
        } else {
            // Se há uma carta na mesa, tenta jogar uma carta que ganhe da carta na mesa
            Carta cartaParaGanhar = cartaParaGanhar(cartaNaMesa);
            if (cartaParaGanhar != null) {
                return cartaParaGanhar;
            } else {
                // Se não pode ganhar, joga a carta de menor valor
                return cartaDeMenorValor();
            }
        }
    }

    private Carta cartaDeMenorValor() {
        Carta menorCarta = cartas.get(0);
        for (Carta carta : cartas) {
            if (carta.valor < menorCarta.valor) {
                menorCarta = carta;
            }
        }
        cartas.remove(menorCarta);
        return menorCarta;
    }

    private Carta cartaParaGanhar(Carta cartaNaMesa) {
        Carta melhorCarta = null;
        for (Carta carta : cartas) {
            if (carta.valor > cartaNaMesa.valor) {
                if (melhorCarta == null || carta.valor < melhorCarta.valor) {
                    melhorCarta = carta;
                }
            }
        }
        if (melhorCarta != null) {
            cartas.remove(melhorCarta);
        }
        return melhorCarta;
    }

    public void exibeCartas() {
        cartas.forEach(c -> {
            System.out.print(c + " ");
        });
        System.out.println();
    }
}
