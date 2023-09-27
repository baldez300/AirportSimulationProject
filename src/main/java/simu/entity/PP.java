package simu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lahtoselvitys")

public class PP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "kayttoaste")
    private double kayttoaste;
    @Column(name = "suoritusteho")
    private double suoritusteho;
    @Column(name = "jonotusaika")
    private double jonotusaika;
    @Column(name = "jononpituus")
    private double jononpituus;

    public PP(double kayttoaste, double suoritusteho, double jonotusaika, double jononpituus) {
        this.kayttoaste = kayttoaste;
        this.suoritusteho = suoritusteho;
        this.jonotusaika = jonotusaika;
        this.jononpituus = jononpituus;
    }

    public PP() {
    }

    //Getterit
    public double getKayttoaste() {
        return kayttoaste;
    }
    public double getSuoritusteho() {
        return suoritusteho;
    }
    public double getJonotusaika() {
        return jonotusaika;
    }
    public double getJononpituus() {
        return jononpituus;
    }
}
