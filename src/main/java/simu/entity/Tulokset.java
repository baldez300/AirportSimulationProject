package simu.entity;

import java.time.LocalDate;
import java.sql.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "tulokset")

public class Tulokset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tulokset_id")
    private int id;
    @Column(name = "paivamaara")
    private Date paivamaara;
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

    public Tulokset(LocalDate paivamaara, double aika, double asiakkaat, double lennolle_ehtineet, double lennolta_myohastyneet, double myohastyneet_t1, double myohastyneet_t2) {
        this.paivamaara = Date.valueOf(paivamaara);
        this.aika = aika;
        this.asiakkaat = asiakkaat;
        this.lennolle_ehtineet = lennolle_ehtineet;
        this.lennolta_myohastyneet = lennolta_myohastyneet;
        this.myohastyneet_t1 = myohastyneet_t1;
        this.myohastyneet_t2 = myohastyneet_t2;
    }

    public Tulokset() {
    }

    //Setterit
    public void setId(int id) {
        this.id = id;
    }
    public void setPaivamaara(LocalDate paivamaara) {
        this.paivamaara =  Date.valueOf(paivamaara);
    }
    public void setAika(double aika) {
        this.aika = aika;
    }
    public void setAsiakkaat(double asiakkaat) {
        this.asiakkaat = asiakkaat;
    }
    public void setLennolle_ehtineet(double lennolle_ehtineet) {
        this.lennolle_ehtineet = lennolle_ehtineet;
    }
    public void setLennolta_myohastyneet(double lennolta_myohastyneet) {
        this.lennolta_myohastyneet = lennolta_myohastyneet;
    }
    public void setMyohastyneet_t1(double myohastyneet_t1) {
        this.myohastyneet_t1 = myohastyneet_t1;
    }
    public void setMyohastyneet_t2(double myohastyneet_t2) {
        this.myohastyneet_t2 = myohastyneet_t2;
    }

    //Getterit
    public int getId() {
        return id;
    }
    public LocalDate getPaivamaara() {
        return paivamaara.toLocalDate();
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
