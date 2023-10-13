package simu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "passintarkastus")
/**
 * Luokka, joka mallintaa passintarkastuksen tuloksia.
 *
 * @see simu.entity.Tulos
 */
public class PTTulos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "tulokset_id")
    @Column(name = "id")
    /**
     * Tulos-taulun id.
     */
    private int id;
    @Column(name = "kayttoaste")
    /**
     * Passintarkastuksen käyttöaste.
     */
    private double kayttoaste;
    @Column(name = "suoritusteho")
    /**
     * Passintarkastuksen suoritusteho.
     */
    private double suoritusteho;
    @Column(name = "jonotusaika")
    /**
     * Passintarkastuksen jonotusaika.
     */
    private double jonotusaika;
    @Column(name = "jononpituus")
    /**
     * Passintarkastuksen jononpituus.
     */
    private double jononpituus;
    @Column(name = "maara")
    /**
     * Passintarkastuksen läpäisseiden matkustajien määrä.
     */
    private int maara;

    /**
     * Konstruktori.
     *
     * @param kayttoaste Passintarkastuksen käyttöaste.
     * @param suoritusteho Passintarkastuksen suoritusteho.
     * @param jonotusaika Passintarkastuksen jonotusaika.
     * @param jononpituus Passintarkastuksen jononpituus.
     * @param maara Passintarkastuksen läpäisseiden matkustajien määrä.
     */
    public PTTulos(double kayttoaste, double suoritusteho, double jonotusaika, double jononpituus, int maara) {
        this.kayttoaste = kayttoaste;
        this.suoritusteho = suoritusteho;
        this.jonotusaika = jonotusaika;
        this.jononpituus = jononpituus;
        this.maara = maara;
    }

    /**
     * Konstruktori.
     */
    public PTTulos() {
    }

    // Setterit
    /**
     * Asettaa tuloksen id:n.
     *
     * @param id Tulos-taulun id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Asettaa passintarkastuksen käyttöasteen.
     *
     * @param kayttoaste Passintarkastuksen käyttöaste.
     */
    public void setKayttoaste(double kayttoaste) {
        this.kayttoaste = kayttoaste;
    }

    /**
     * Asettaa passintarkastuksen suoritustehon.
     *
     * @param suoritusteho Passintarkastuksen suoritusteho.
     */
    public void setSuoritusteho(double suoritusteho) {
        this.suoritusteho = suoritusteho;
    }

    /**
     * Asettaa passintarkastuksen jonotusajan.
     *
     * @param jonotusaika Passintarkastuksen jonotusaika.
     */
    public void setJonotusaika(double jonotusaika) {
        this.jonotusaika = jonotusaika;
    }

    /**
     * Asettaa passintarkastuksen jononpituuden.
     *
     * @param jononpituus Passintarkastuksen jononpituus.
     */
    public void setJononpituus(double jononpituus) {
        this.jononpituus = jononpituus;
    }

    /**
     * Asettaa passintarkastuksen läpäisseiden matkustajien määrän.
     *
     * @param maara Passintarkastuksen läpäisseiden matkustajien määrä.
     */
    public void setMaara(int maara) {
        this.maara = maara;
    }

    // Getterit
    /**
     * Palauttaa tuloksen id:n.
     *
     * @return Tulos-taulun id.
     */
    public int getId() {
        return id;
    }

    /**
     * Palauttaa passintarkastuksen käyttöasteen.
     *
     * @return Passintarkastuksen käyttöaste.
     */
    public double getKayttoaste() {
        return kayttoaste;
    }

    /**
     * Palauttaa passintarkastuksen suoritustehon.
     *
     * @return Passintarkastuksen suoritusteho.
     */
    public double getSuoritusteho() {
        return suoritusteho;
    }

    /**
     * Palauttaa passintarkastuksen jonotusajan.
     *
     * @return Passintarkastuksen jonotusaika.
     */
    public double getJonotusaika() {
        return jonotusaika;
    }

    /**
     * Palauttaa passintarkastuksen jononpituuden.
     *
     * @return Passintarkastuksen jononpituus.
     */
    public double getJononpituus() {
        return jononpituus;
    }

    /**
     * Palauttaa passintarkastuksen läpäisseiden matkustajien määrän.
     *
     * @return Passintarkastuksen läpäisseiden matkustajien määrä.
     */
    public int getMaara() {
        return maara;
    }
}
