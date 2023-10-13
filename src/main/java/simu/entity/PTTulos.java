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
     * Passintarkastuksen kayttoaste.
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
     * Passintarkastuksen lapaisseiden matkustajien maara.
     */
    private int maara;

    /**
     * Konstruktori.
     *
     * @param kayttoaste Passintarkastuksen kayttoaste.
     * @param suoritusteho Passintarkastuksen suoritusteho.
     * @param jonotusaika Passintarkastuksen jonotusaika.
     * @param jononpituus Passintarkastuksen jononpituus.
     * @param maara Passintarkastuksen lapaisseiden matkustajien maara.
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
     * Asettaa passintarkastuksen kayttoasteen.
     *
     * @param kayttoaste Passintarkastuksen kayttoaste.
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
     * Asettaa passintarkastuksen lapaisseiden matkustajien maaran.
     *
     * @param maara Passintarkastuksen lapaisseiden matkustajien maara.
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
     * Palauttaa passintarkastuksen kayttoasteen.
     *
     * @return Passintarkastuksen kayttoaste.
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
     * Palauttaa passintarkastuksen lapaisseiden matkustajien maaran.
     *
     * @return Passintarkastuksen lapaisseiden matkustajien maara.
     */
    public int getMaara() {
        return maara;
    }
}
