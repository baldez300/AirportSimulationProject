package simu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "t2")
/**
 * T2Tulos-luokka sisaltaa T2-tulokset
 */
public class T2Tulos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "tulokset_id")
    @Column(name = "id")
    /**
     * T2Tulos-luokan id
     */
    private int id;
    @Column(name = "kayttoaste")
    /**
     * T2Tulos-luokan kayttoaste
     */
    private double kayttoaste;
    @Column(name = "suoritusteho")
    /**
     * T2Tulos-luokan suoritusteho
     */
    private double suoritusteho;
    @Column(name = "jonotusaika")
    /**
     * T2Tulos-luokan jonotusaika
     */
    private double jonotusaika;
    @Column(name = "jononpituus")
    /**
     * T2Tulos-luokan jononpituus
     */
    private double jononpituus;


    /**
     * T2Tulos-luokan konstruktori
     * @param kayttoaste T2Tulos-luokan kayttoaste
     * @param suoritusteho T2Tulos-luokan suoritusteho
     * @param jonotusaika T2Tulos-luokan jonotusaika
     * @param jononpituus T2Tulos-luokan jononpituus
     */
    public T2Tulos(double kayttoaste, double suoritusteho, double jonotusaika, double jononpituus) {
        this.kayttoaste = kayttoaste;
        this.suoritusteho = suoritusteho;
        this.jonotusaika = jonotusaika;
        this.jononpituus = jononpituus;
    }

    /**
     * T2Tulos-luokan konstruktori
     */
    public T2Tulos() {
    }

    // Setterit
    /**
     * Asettaa T2Tulos-luokan id:n
     * @param id T2Tulos-luokan id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Asettaa T2Tulos-luokan kayttoasteen
     * @param kayttoaste T2Tulos-luokan kayttoaste
     */
    public void setKayttoaste(double kayttoaste) {
        this.kayttoaste = kayttoaste;
    }

    /**
     * Asettaa T2Tulos-luokan suoritustehon
     * @param suoritusteho T2Tulos-luokan suoritusteho
     */
    public void setSuoritusteho(double suoritusteho) {
        this.suoritusteho = suoritusteho;
    }

    /**
     * Asettaa T2Tulos-luokan jonotusajan
     * @param jonotusaika T2Tulos-luokan jonotusaika
     */
    public void setJonotusaika(double jonotusaika) {
        this.jonotusaika = jonotusaika;
    }

    /**
     * Asettaa T2Tulos-luokan jononpituuden
     * @param jononpituus T2Tulos-luokan jononpituus
     */
    public void setJononpituus(double jononpituus) {
        this.jononpituus = jononpituus;
    }

    // Getterit
    /**
     * Palauttaa T2Tulos-luokan id:n
     * @return T2Tulos-luokan id
     */
    public int getId() {
        return id;
    }

    /**
     * Palauttaa T2Tulos-luokan kayttoasteen
     * @return T2Tulos-luokan kayttoaste
     */
    public double getKayttoaste() {
        return kayttoaste;
    }

    /**
     * Palauttaa T2Tulos-luokan suoritustehon
     * @return T2Tulos-luokan suoritusteho
     */
    public double getSuoritusteho() {
        return suoritusteho;
    }

    /**
     * Palauttaa T2Tulos-luokan jonotusajan
     * @return T2Tulos-luokan jonotusaika
     */
    public double getJonotusaika() {
        return jonotusaika;
    }

    /**
     * Palauttaa T2Tulos-luokan jononpituuden
     * @return T2Tulos-luokan jononpituus
     */
    public double getJononpituus() {
        return jononpituus;
    }
}
