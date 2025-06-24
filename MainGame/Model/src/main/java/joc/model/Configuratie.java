package joc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "configuratie")
public class Configuratie extends Entity<Integer> {
    @Column(name = "linie")
    private int linie;
    @Column(name = "coloana")
    private int coloana;
    @Column(name = "animal")
    private String animal;
    @Column(name = "imagine_url")
    private String imagineUrl;

    public Configuratie() {
    }

    public Configuratie(int id, int linie, int coloana, String animal, String imagineUrl) {
        this.setId(id);
        this.linie = linie;
        this.coloana = coloana;
        this.animal = animal;
        this.imagineUrl = imagineUrl;
    }

    public Configuratie(int linie, int coloana, String animal, String imagineUrl) {
        this.linie = linie;
        this.coloana = coloana;
        this.animal = animal;
        this.imagineUrl = imagineUrl;
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
    public String getAnimal() {
        return animal;
    }
    public void setAnimal(String animal) {
        this.animal = animal;
    }
    public String getImagineUrl() {
        return imagineUrl;
    }
    public void setImagineUrl(String imagineUrl) {
        this.imagineUrl = imagineUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuratie that = (Configuratie) o;
        return linie == that.linie && coloana == that.coloana && Objects.equals(animal, that.animal) && Objects.equals(imagineUrl, that.imagineUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(linie, coloana, animal, imagineUrl);
    }
}
