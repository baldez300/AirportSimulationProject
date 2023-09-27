package simu.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tulokset")

public class Tulokset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OneToMany
    @Column(name = "id")
    private int id;
    @Column(name = "paivamaara")
    private String paivamaara;
    @Column(name = "aika")
    private double aika;
    @Column(name = "asiakkaat")
    private double asiakkaat;
    @Column(name = "lennolle_ehtineet")
    private double lennolle_ehtineet;
    @Column(name = "myohastyneet")
    private double lennolta_myohastyneet;
    @Column(name = "myohastyneet_t1")
    private double myohastyneet_t1;
    @Column(name = "myohastyneet_t2")
    private double myohastyneet_t2;

    public Tulokset(String paivamaara, double aika, double asiakkaat, double lennolle_ehtineet, double lennolta_myohastyneet, double myohastyneet_t1, double myohastyneet_t2) {
        this.paivamaara = paivamaara;
        this.aika = aika;
        this.asiakkaat = asiakkaat;
        this.lennolle_ehtineet = lennolle_ehtineet;
        this.lennolta_myohastyneet = lennolta_myohastyneet;
        this.myohastyneet_t1 = myohastyneet_t1;
        this.myohastyneet_t2 = myohastyneet_t2;
    }

    public Tulokset() {
    }

    //Getterit
    public String getPaivamaara() {
        return paivamaara;
    }
    public double getAika() {
        return aika;
    }
    public double getAsiakkaat() {
        return asiakkaat;
    }
    public double getLennolle_ehtineet() {
        return lennolle_ehtineet;
    }
    public double getLennolta_myohastyneet() {
        return lennolta_myohastyneet;
    }
    public double getMyohastyneet_t1() {
        return myohastyneet_t1;
    }
    public double getMyohastyneet_t2() {
        return myohastyneet_t2;
    }
}
