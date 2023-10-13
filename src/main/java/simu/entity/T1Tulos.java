package simu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "t1")
/**
 * T1Tulos-luokka sisältää tietokantataulun t1 tulokset
 */
public class T1Tulos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "tulokset_id")
    @Column(name = "id")
    /**
     * Tietokannan id
     */
    private int id;
    @Column(name = "kayttoaste")
    /**
     * Tietokannan kayttoaste
     */
    private double kayttoaste;
    @Column(name = "suoritusteho")
    /**
     * Tietokannan suoritusteho
     */
    private double suoritusteho;
    @Column(name = "jonotusaika")
    /**
     * Tietokannan jonotusaika
     */
    private double jonotusaika;
    @Column(name = "jononpituus")
    /**
     * Tietokannan jononpituus
     */
    private double jononpituus;


    /**
     * T1Tulos-luokan konstruktori
     * @param kayttoaste
     * @param suoritusteho
     * @param jonotusaika
     * @param jononpituus
     */
    public T1Tulos(double kayttoaste, double suoritusteho, double jonotusaika, double jononpituus) {
        this.kayttoaste = kayttoaste;
        this.suoritusteho = suoritusteho;
        this.jonotusaika = jonotusaika;
        this.jononpituus = jononpituus;
    }

    /**
     * T1Tulos-luokan konstruktori
     */
    public T1Tulos() {
    }

    // Setterit
    /**
     * Setteri
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Setteri
     * @param kayttoaste
     */
    public void setKayttoaste(double kayttoaste) {
        this.kayttoaste = kayttoaste;
    }

    /**
     * Setteri
     * @param suoritusteho
     */
    public void setSuoritusteho(double suoritusteho) {
        this.suoritusteho = suoritusteho;
    }

    /**
     * Setteri
     * @param jonotusaika
     */
    public void setJonotusaika(double jonotusaika) {
        this.jonotusaika = jonotusaika;
    }

    /**
     * Setteri
     * @param jononpituus
     */
    public void setJononpituus(double jononpituus) {
        this.jononpituus = jononpituus;
    }

    // Getterit
    /**
     * Getteri
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getteri
     * @return kayttoaste
     */
    public double getKayttoaste() {
        return kayttoaste;
    }

    /**
     * Getteri
     * @return suoritusteho
     */
    public double getSuoritusteho() {
        return suoritusteho;
    }

    /**
     * Getteri
     * @return jonotusaika
     */
    public double getJonotusaika() {
        return jonotusaika;
    }

    /**
     * Getteri
     * @return jononpituus
     */
    public double getJononpituus() {
        return jononpituus;
    }
}
