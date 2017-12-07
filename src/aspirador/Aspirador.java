package aspirador;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;

public final class Aspirador {
    private Aspirador() {}

    public static void resolverCondAcao() {
        Estado e = new Estado();

        System.out.println(e);

        while ( !e.verificarFinal() ) {
            if ( !e.verificarAtualLimpo() )
                e.aspirar();

            else if (e.getPosicao() == Estado.Posicao.A)
                e.irDireita();

            else if (e.getPosicao() == Estado.Posicao.B)
                e.irEsquerda();

            System.out.println(e);
        }
    }

    private static void buscar(Queue<Estado> borda) {
        Estado atual = new Estado();
        boolean pronto;

        borda.clear();
        borda.add(atual);

        do {
            atual = borda.remove();
            System.out.println(atual);
            pronto = atual.verificarFinal();

            if (!pronto)
                borda.addAll( atual.expandir() );
        } while (!pronto && !borda.isEmpty());
    }

    private static void buscarSemRepeticao(Queue<Estado> borda) {
        final ArrayList<Estado> listaFechada = new ArrayList<>();
        Estado atual = new Estado();
        boolean pronto;

        borda.clear();
        borda.add(atual);
        listaFechada.add(atual);

        do {
            atual = borda.remove();
            System.out.println(atual);
            pronto = atual.verificarFinal();

            if (!pronto)
                for (Estado sucessor : atual.expandir())
                    if ( !listaFechada.contains(sucessor) ) {
                        borda.add(sucessor);
                        listaFechada.add(sucessor);
                    }
        } while (!pronto && !borda.isEmpty());
    }

    public static void main(String[] args) {
        final Queue<Estado> fila = new ArrayDeque<>();
        final Queue<Estado> pilha = Collections.asLifoQueue(new ArrayDeque<>());

        System.out.println("Reativo simples");
        resolverCondAcao();
        System.out.println();

        System.out.println("Busca em largura");
        buscar(fila);
        System.out.println();

        /*System.out.println("Busca em profundidade");
        buscar(pilha);
        System.out.println();*/

        System.out.println("Busca em largura sem repetição");
        buscarSemRepeticao(fila);
        System.out.println();

        System.out.println("Busca em profundidade sem repetição");
        buscarSemRepeticao(pilha);
        System.out.println();
    }
}
