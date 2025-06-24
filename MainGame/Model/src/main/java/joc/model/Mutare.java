package joc.model;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "mutare")
public class Mutare extends Entity<Integer>{

    @ManyToOne
    @JoinColumn(name = "jucator_id")
    private Jucator jucator;

    @ManyToOne
    @JoinColumn(name = "joc_id")
    private Joc joc;

    @Column(name = "linie")
    private int linie;
    @Column(name = "coloana")
    private int coloana;
    @Column(name = "ghicit")
    private boolean ghicit;

    public Mutare() {
    }

    public Mutare(int id, Jucator jucator, Joc joc, int linie, int coloana, boolean ghicit) {
        this.setId(id);
        this.jucator = jucator;
        this.joc = joc;
        this.linie = linie;
        this.coloana = coloana;
        this.ghicit = ghicit;
    }

    public Mutare(Jucator jucator, Joc joc, int linie, int coloana, boolean ghicit) {
        this.jucator = jucator;
        this.joc = joc;
        this.linie = linie;
        this.coloana = coloana;
        this.ghicit = ghicit;
    }

    public Jucator getJucator() {
        return jucator;
    }
    public void setJucator(Jucator jucator) {
        this.jucator = jucator;
    }

    public Joc getJoc() {
        return joc;
    }
    public void setJoc(Joc joc) {
        this.joc = joc;
    }

    public int getLinie() {
        return linie;
    }
    public void setLinie(int linie) {
        this.linie = linie;
    }

    public int getColoana() {
        return coloana;
    }
    public void setColoana(int coloana) {
        this.coloana = coloana;
    }

    public boolean isGhicit() {
        return ghicit;
    }
    public void setGhicit(boolean ghicit) {
        this.ghicit = ghicit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mutare mutare = (Mutare) o;
        return linie == mutare.linie && coloana == mutare.coloana && ghicit == mutare.ghicit && jucator.equals(mutare.jucator);
    }
    @Override
    public int hashCode() {
        return java.util.Objects.hash(jucator, linie, coloana, ghicit);
    }
}
