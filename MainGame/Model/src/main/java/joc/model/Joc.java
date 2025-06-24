package joc.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "joc")
public class Joc extends Entity<Integer> {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jucator_id")
    private Jucator jucator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "configuratie_id")
    private Configuratie configuratie;

    @Column(name = "finalizat")
    private boolean finalizat;

    @Column(name = "mutari_ramase")
    private int mutariRamase;

    @Column(name = "start_time")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime startTime;

    @Column(name = "end_time")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime endTime;

    public Joc() {
    }

    public Joc(int id, Jucator jucator, Configuratie configuratie, boolean finalizat, int mutariRamase, LocalDateTime startTime, LocalDateTime endTime) {
        this.setId(id);
        this.jucator = jucator;
        this.configuratie = configuratie;
        this.finalizat = finalizat;
        this.mutariRamase = mutariRamase;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Joc(Jucator jucator, Configuratie configuratie) {
        this.jucator = jucator;
        this.configuratie = configuratie;
        this.finalizat = false;
        this.mutariRamase = 3;
        this.startTime = LocalDateTime.now();
        this.endTime = null;
    }

    public Jucator getJucator() {
        return jucator;
    }

    public void setJucator(Jucator jucator) {
        this.jucator = jucator;
    }

    public Configuratie getConfiguratie() {
        return configuratie;
    }

    public void setConfiguratie(Configuratie configuratie) {
        this.configuratie = configuratie;
    }

    public boolean isFinalizat() {
        return finalizat;
    }

    public void setFinalizat(boolean finalizat) {
        this.finalizat = finalizat;
    }

    public int getMutariRamase() {
        return mutariRamase;
    }

    public void setMutariRamase(int mutariRamase) {
        this.mutariRamase = mutariRamase;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Joc joc)) return false;
        return finalizat == joc.finalizat &&
               mutariRamase == joc.mutariRamase &&
               Objects.equals(jucator, joc.jucator) &&
               Objects.equals(configuratie, joc.configuratie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jucator, configuratie, finalizat, mutariRamase);
    }
}
