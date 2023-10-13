package simu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "lahtoselvitys")

/**
 * Luokka sisaltaa lahtoselvityksen tulokset
 *
 * @see simu.model.Simulaatio
 */
public class LSTulos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "tulokset_id")
    @Column(name = "id")
    /**
     * Tulos id
     */
    private int id;
    @Column(name = "kayttoaste")
    /**
     * Kayttoaste
     */
    private double kayttoaste;
    @Column(name = "suoritusteho")
    /**
     * Suoritusteho
     */
    private double suoritusteho;
    @Column(name = "jonotusaika")
    /**
     * Jonotusaika
     */
    private double jonotusaika;
    @Column(name = "jononpituus")
    /**
     * Jononpituus
     */
    private double jononpituus;
    @Column(name = "maara")
    /**
     * Lahtoselvityksessa kasiteltyjen asiakkaiden maara
     */
    private int maara;

    /**
     * Konstruktori
     *
     * @param kayttoaste Kayttoaste
     * @param suoritusteho Suoritusteho
     * @param jonotusaika Jonotusaika
     * @param jononpituus Jononpituus
     * @param maara Lahtoselvityksessa kasiteltyjen asiakkaiden maara
     */
    public LSTulos(double kayttoaste, double suoritusteho, double jonotusaika, double jononpituus, int maara) {
        this.kayttoaste = kayttoaste;
        this.suoritusteho = suoritusteho;
        this.jonotusaika = jonotusaika;
        this.jononpituus = jononpituus;
        this.maara = maara;
    }

    /**
     * Konstruktori
     */
    public LSTulos() {
    }

    // Setterit
    /**
     * Asettaa tuloksen id:n
     *
     * @param id Tulos id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Asettaa kayttoasteen
     *
     * @param kayttoaste Kayttoaste
     */
    public void setKayttoaste(double kayttoaste) {
        this.kayttoaste = kayttoaste;
    }

    /**
     * Asettaa suoritustehon
     *
     * @param suoritusteho Suoritusteho
     */
    public void setSuoritusteho(double suoritusteho) {
        this.suoritusteho = suoritusteho;
    }

    /**
     * Asettaa jonotusajan
     *
     * @param jonotusaika Jonotusaika
     */
    public void setJonotusaika(double jonotusaika) {
        this.jonotusaika = jonotusaika;
    }

    /**
     * Asettaa jononpituuden
     *
     * @param jononpituus Jononpituus
     */
    public void setJononpituus(double jononpituus) {
        this.jononpituus = jononpituus;
    }

    /**
     * Asettaa lahtoselvityksessa kasiteltyjen asiakkaiden maaran
     *
     * @param maara Lahtoselvityksessa kasiteltyjen asiakkaiden maara
     */
    public void setMaara(int maara) {
        this.maara = maara;
    }

    // Getterit
    /**
     * Palauttaa tuloksen id:n
     *
     * @return Tulos id
     */
    public int getId() {
        return id;
    }

    /**
     * Palauttaa kayttoasteen
     *
     * @return Kayttoaste
     */
    public double getKayttoaste() {
        return kayttoaste;
    }

    /**
     * Palauttaa suoritustehon
     *
     * @return Suoritusteho
     */
    public double getSuoritusteho() {
        return suoritusteho;
    }

    /**
     * Palauttaa jonotusajan
     *
     * @return Jonotusaika
     */
    public double getJonotusaika() {
        return jonotusaika;
    }

    /**
     * Palauttaa jononpituuden
     *
     * @return Jononpituus
     */
    public double getJononpituus() {
        return jononpituus;
    }

    /**
     * Palauttaa lahtoselvityksessa kasiteltyjen asiakkaiden maaran
     *
     * @return Lahtoselvityksessa kasiteltyjen asiakkaiden maara
     */
    public int getMaara() {
        return maara;
    }
}
