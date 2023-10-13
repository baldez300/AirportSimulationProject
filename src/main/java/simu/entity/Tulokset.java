package simu.entity;

import java.time.LocalDate;
import java.sql.Date;

import jakarta.persistence.*;

// Tietokantataulu
@Entity
@Table(name = "tulokset")
/**
 * Tulokset-luokka sisältää tulokset tietokantataulun tiedot
 * @param id
 * @param paivamaara
 * @param aika
 * @param asiakkaat
 * @param lennolle_ehtineet
 * @param lennolta_myohastyneet
 * @param myohastyneet_t1
 * @param myohastyneet_t2
 * @param lsTulos
 * @param ttTulos
 * @param ptTulos
 * @param t1Tulos
 * @param t2Tulos
 */
public class Tulokset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tulokset_id")
    /**
     * Tulokset-luokan id
     */
    private int id;
    @Column(name = "paivamaara")
    /**
     * Tulokset-luokan paivamaara
     */
    private Date paivamaara;
    @Column(name = "aika")
    /**
     * Tulokset-luokan aika
     */
    private double aika;
    @Column(name = "asiakkaat")
    /**
     * Tulokset-luokan asiakkaat
     */
    private double asiakkaat;
    @Column(name = "lennolle_ehtineet")
    /**
     * Tulokset-luokan lennolle_ehtineet
     */
    private double lennolle_ehtineet;
    @Column(name = "myohastyneet")
    /**
     * Tulokset-luokan lennolta_myohastyneet
     */
    private double lennolta_myohastyneet;
    @Column(name = "myohastyneet_t1")
    /**
     * Tulokset-luokan myohastyneet_t1
     */
    private double myohastyneet_t1;
    @Column(name = "myohastyneet_t2")
    /**
     * Tulokset-luokan myohastyneet_t2
     */
    private double myohastyneet_t2;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan lsTulos
     */
    private LSTulos lsTulos;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan ttTulos
     */
    private TTTulos ttTulos;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan ptTulos
     */
    private PTTulos ptTulos;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan t1Tulos
     */
    private T1Tulos t1Tulos;
    @ManyToOne(cascade = CascadeType.PERSIST)
    /**
     * Tulokset-luokan t2Tulos
     */
    private T2Tulos t2Tulos;

    /**
     * Tulokset-luokan konstruktori
     * @param paivamaara
     * @param aika
     * @param asiakkaat
     * @param lennolle_ehtineet
     * @param lennolta_myohastyneet
     * @param myohastyneet_t1
     * @param myohastyneet_t2
     * @param lsTulos
     * @param ttTulos
     * @param ptTulos
     * @param t1Tulos
     * @param t2Tulos
     */
    public Tulokset(LocalDate paivamaara, double aika, double asiakkaat, double lennolle_ehtineet, double lennolta_myohastyneet, double myohastyneet_t1, double myohastyneet_t2, LSTulos lsTulos, TTTulos ttTulos, PTTulos ptTulos, T1Tulos t1Tulos, T2Tulos t2Tulos) {
        this.paivamaara = Date.valueOf(paivamaara);
        this.aika = aika;
        this.asiakkaat = asiakkaat;
        this.lennolle_ehtineet = lennolle_ehtineet;
        this.lennolta_myohastyneet = lennolta_myohastyneet;
        this.myohastyneet_t1 = myohastyneet_t1;
        this.myohastyneet_t2 = myohastyneet_t2;
        this.lsTulos = lsTulos;
        this.ttTulos = ttTulos;
        this.ptTulos = ptTulos;
        this.t1Tulos = t1Tulos;
        this.t2Tulos = t2Tulos;
    }

    /**
     * Tulokset-luokan tyhjä konstruktori
     */
    public Tulokset() {
    }


    //Setterit

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param paivamaara
     */
    public void setPaivamaara(LocalDate paivamaara) {
        this.paivamaara =  Date.valueOf(paivamaara);
    }

    /**
     * @param aika
     */
    public void setAika(double aika) {
        this.aika = aika;
    }
    /**
     * @param asiakkaat
     */
    public void setAsiakkaat(double asiakkaat) {
        this.asiakkaat = asiakkaat;
    }
    /**
     * @param lennolle_ehtineet
     */
    public void setLennolle_ehtineet(double lennolle_ehtineet) {
        this.lennolle_ehtineet = lennolle_ehtineet;
    }
    /**
     * @param lennolta_myohastyneet
     */
    public void setLennolta_myohastyneet(double lennolta_myohastyneet) {
        this.lennolta_myohastyneet = lennolta_myohastyneet;
    }
    /**
     * @param myohastyneet_t1
     */
    public void setMyohastyneet_t1(double myohastyneet_t1) {
        this.myohastyneet_t1 = myohastyneet_t1;
    }
    /**
     * @param myohastyneet_t2
     */
    public void setMyohastyneet_t2(double myohastyneet_t2) {
        this.myohastyneet_t2 = myohastyneet_t2;
    }
    /**
     * @param lsTulos
     */
    public void setLSTulos(LSTulos lsTulos) {
        this.lsTulos = lsTulos;
    }
    /**
     * @param ttTulos
     */
    public void setTTTulos(TTTulos ttTulos) {
        this.ttTulos = ttTulos;
    }
    /**
     * @param ptTulos
     */
    public void setPTTulos(PTTulos ptTulos) {
        this.ptTulos = ptTulos;
    }
    /**
     * @param t1Tulos
     */
    public void setT1Tulos(T1Tulos t1Tulos) {
        this.t1Tulos = t1Tulos;
    }
    /**
     * @param t2Tulos
     */
    public void setT2Tulos(T2Tulos t2Tulos) {
        this.t2Tulos = t2Tulos;
    }

    //Getterit

    /**
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * @return LocalDate
     */
    public LocalDate getPaivamaara() {
        return paivamaara.toLocalDate();
    }
    /**
     * @return double
     */
    public double getAika() {
        return aika;
    }
    /**
     * @return double
     */
    public double getAsiakkaat() {
        return asiakkaat;
    }
    /**
     * @return double
     */
    public double getLennolle_ehtineet() {
        return lennolle_ehtineet;
    }
    /**
     * @return double
     */
    public double getLennolta_myohastyneet() {
        return lennolta_myohastyneet;
    }
    /**
     * @return double
     */
    public double getMyohastyneet_t1() {
        return myohastyneet_t1;
    }
    /**
     * @return double
     */
    public double getMyohastyneet_t2() {
        return myohastyneet_t2;
    }
    /**
     * @return LSTulos
     */
    public LSTulos getLSTulos() {
        return lsTulos;
    }
    /**
     * @return TTTulos
     */
    public TTTulos getTTTulos() {
        return ttTulos;
    }
    /**
     * @return PTTulos
     */
    public PTTulos getPTTulos() {
        return ptTulos;
    }
    /**
     * @return T1Tulos
     */
    public T1Tulos getT1Tulos() {
        return t1Tulos;
    }
    /**
     * @return T2Tulos
     */
    public T2Tulos getT2Tulos() {
        return t2Tulos;
    }

    // Override toString jotta saadaan ListViewiin paivamaara näkyviin Objectin sijaan
    @Override
    /**
     * @return String
     */
    public String toString() {
        return this.paivamaara.toString();
    }
}
