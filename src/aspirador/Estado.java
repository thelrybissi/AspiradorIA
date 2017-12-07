package aspirador;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class Estado {
    static enum Posicao {A, B};

    private final static Posicao[] POSICOES = Posicao.values();
    private final static Random GENERATOR = new Random();

    private final boolean[] ambiente = new boolean[POSICOES.length];
    private Posicao posicao;

    public Estado() {
        int r = Math.abs( GENERATOR.nextInt() ) % POSICOES.length;

        this.posicao = POSICOES[r];

        for (int i = 0; i < this.ambiente.length; i++)
            this.ambiente[i] = GENERATOR.nextBoolean();
    }

    private Estado(Estado outro) {
        this.posicao = outro.posicao;

        for (int i = 0; i < this.ambiente.length; i++)
            this.ambiente[i] = outro.ambiente[i];
    }

    public Posicao getPosicao() {
        return this.posicao;
    }

    public void aspirar() {
        this.ambiente[ this.posicao.ordinal() ] = true;
    }

    public void irDireita() {
        this.posicao = Posicao.B;
    }

    public void irEsquerda() {
        this.posicao = Posicao.A;
    }

    public boolean verificarFinal() {
        boolean limpo = this.ambiente[0];

        for (int i = 1; limpo && (i < this.ambiente.length); i++)
            limpo = this.ambiente[i];

        return limpo;
    }

    public boolean verificarAtualLimpo() {
        return this.ambiente[ this.posicao.ordinal() ];
    }

    public Collection<Estado> expandir() {
        ArrayList<Estado> sucessores = new ArrayList<>();
        Estado aux;

        if ( !this.verificarAtualLimpo() ) {
            aux = new Estado(this);
            aux.aspirar();
            sucessores.add(aux);
        }

        if (this.posicao != Posicao.B) {
            aux = new Estado(this);
            aux.irDireita();
            sucessores.add(aux);
        }

        if (this.posicao != Posicao.A) {
            aux = new Estado(this);
            aux.irEsquerda();
            sucessores.add(aux);
        }

        return sucessores;
    }

    @Override
    public boolean equals(Object outro) {
        return (outro != null) && (outro instanceof Estado) && this.equals((Estado) outro);
    }

    public boolean equals(Estado outro) {
        boolean iguais = this.posicao == outro.posicao;

        for (int i = 0; iguais && i < this.ambiente.length; i++)
            iguais = this.ambiente[i] == outro.ambiente[i];

        return iguais;
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();

        b.append("Mapa = ");

        for (boolean condicao : this.ambiente) {
            if (condicao)
                b.append("| ");

            else
                b.append("|*");
        }

        b.append("|   Posição = " + this.posicao.toString());

        return b.toString();
    }
}
