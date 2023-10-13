package simu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "turvatarkastus")
/**
 * TTTulos-luokka sisaltaa turvatarkastuksen tulokset
 *
 * @param id           turvatarkastuksen id
 * @param kayttoaste   turvatarkastuksen kayttoaste
 * @param suoritusteho turvatarkastuksen suoritusteho
 * @param jonotusaika  turvatarkastuksen jonotusaika
 * @param jononpituus  turvatarkastuksen jononpituus
 * @param maara        turvatarkastuksen maara
 */
public class TTTulos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "tulokset_id")
    @Column(name = "id")
    /**
     * turvatarkastuksen id
     */
    private int id;
    @Column(name = "kayttoaste")
    /**
     * turvatarkastuksen kayttoaste
     */
    private double kayttoaste;
    @Column(name = "suoritusteho")
    /**
     * turvatarkastuksen suoritusteho
     */
    private double suoritusteho;
    @Column(name = "jonotusaika")
    /**
     * turvatarkastuksen jonotusaika
     */
    private double jonotusaika;
    @Column(name = "jononpituus")
    /**
     * turvatarkastuksen jononpituus
     */
    private double jononpituus;
    @Column(name = "maara")
    /**
     * turvatarkastuksen maara
     */
    private int maara;

    /**
     * TTTulos-luokan konstruktori
     *
     * @param kayttoaste   turvatarkastuksen kayttoaste
     * @param suoritusteho turvatarkastuksen suoritusteho
     * @param jonotusaika  turvatarkastuksen jonotusaika
     * @param jononpituus  turvatarkastuksen jononpituus
     * @param maara        turvatarkastuksen maara
     */
    public TTTulos(double kayttoaste, double suoritusteho, double jonotusaika, double jononpituus, int maara) {
        this.kayttoaste = kayttoaste;
        this.suoritusteho = suoritusteho;
        this.jonotusaika = jonotusaika;
        this.jononpituus = jononpituus;
        this.maara = maara;
    }

    /**
     * TTTulos-luokan konstruktori
     */
    public TTTulos() {
    }

    // Setterit
    /**
     * Asettaa turvatarkastuksen id:n
     *
     * @param id turvatarkastuksen id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Asettaa turvatarkastuksen kayttoasteen
     *
     * @param kayttoaste turvatarkastuksen kayttoaste
     */
    public void setKayttoaste(double kayttoaste) {
        this.kayttoaste = kayttoaste;
    }

    /**
     * Asettaa turvatarkastuksen suoritustehon
     *
     * @param suoritusteho turvatarkastuksen suoritusteho
     */
    public void setSuoritusteho(double suoritusteho) {
        this.suoritusteho = suoritusteho;
    }

    /**
     * Asettaa turvatarkastuksen jonotusajan
     *
     * @param jonotusaika turvatarkastuksen jonotusaika
     */
    public void setJonotusaika(double jonotusaika) {
        this.jonotusaika = jonotusaika;
    }

    /**
     * Asettaa turvatarkastuksen jononpituuden
     *
     * @param jononpituus turvatarkastuksen jononpituus
     */
    public void setJononpituus(double jononpituus) {
        this.jononpituus = jononpituus;
    }

    /**
     * Asettaa turvatarkastuksen maaran
     *
     * @param maara turvatarkastuksen maara
     */
    public void setMaara(int maara) {
        this.maara = maara;
    }

    // Getterit
    /**
     * Palauttaa turvatarkastuksen id:n
     *
     * @return turvatarkastuksen id
     */
    public int getId() {
        return id;
    }

    /**
     * Palauttaa turvatarkastuksen kayttoasteen
     *
     * @return turvatarkastuksen kayttoaste
     */
    public double getKayttoaste() {
        return kayttoaste;
    }

    /**
     * Palauttaa turvatarkastuksen suoritustehon
     *
     * @return turvatarkastuksen suoritusteho
     */
    public double getSuoritusteho() {
        return suoritusteho;
    }

    /**
     * Palauttaa turvatarkastuksen jonotusajan
     *
     * @return turvatarkastuksen jonotusaika
     */
    public double getJonotusaika() {
        return jonotusaika;
    }

    /**
     * Palauttaa turvatarkastuksen jononpituuden
     *
     * @return turvatarkastuksen jononpituus
     */
    public double getJononpituus() {
        return jononpituus;
    }

    /**
     * Palauttaa turvatarkastuksen maaran
     *
     * @return turvatarkastuksen maara
     */
    public int getMaara() {
        return maara;
    }
}
