package simu.test;

import simu.framework.Kello;
import simu.model.TapahtumanTyyppi;

/** Test luokka Asiakas-luokan testaamiseen */
public class AsiakasForTest {
	/** Tapahtuman tyyppi */
	private TapahtumanTyyppi tyyppi;
	/** Asiakkaan saapumisaika */
	private double saapumisaika;
	/** Asiakkaan poistumisaika */
	private double poistumisaika;
	/** Asiakkaan leveys */
	private double width;
	/** Asiakkaan korkeus */
	private double height;
	/** Asiakkaan id */
	private int id;
	/** Lippu, joka maarittaa onko asiakas ulkomaanlennon asiakas */
	private boolean ulkomaanlento; // Uusi kentta, maarittaa onko asiakas ulkomaanlennon asiakas
	/** Ehtineet lennolle */
	public static int lennolleEhtineet = 0;
	/** Kotimaan Myohastyneet */
	public static int T1myohastyneet = 0;
	/** Ulkomaan Myohastyneet */
	public static int T2myohastyneet = 0;
	/** Palvellut asiakkaat */
	public static int i = 0;

	/** Luokan konstruktori */
	public AsiakasForTest(TapahtumanTyyppi tyyppi) {
		++id;
		saapumisaika = Kello.getInstance().getAika();
		this.tyyppi = tyyppi;
		this.ulkomaanlento = false;
		this.width = 25;
		this.height = 25;
	}

	// Getterit
	/** Palauttaa asiakkaan poistumisajan */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/** Asettaa asiakkaan poistumisajan */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/** Palauttaa asiakkaan saapumisajan */
	public double getSaapumisaika() {
		return saapumisaika;
	}

	/** Asettaa asiakkaan saapumisajan */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	/** Palauttaa asiakkaan id:n */
	public int getId() {
		return this.id;
	}

	/** Palauttaa asiakkaan tyyppin */
	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}

	/** Asettaa asiakkaan tyyppin */
	public boolean isUlkomaanlento() {
		return ulkomaanlento;
	}

	/** Asettaa asiakkaan tyyppin */
	public void setUlkomaanlento() {
		this.ulkomaanlento = true;
	}

	/** Palauttaa asiakkaan leveyden */
	public double getWidth() {
		return width;
	}

	/** Palauttaa asiakkaan korkeuden */
	public double getHeight() {
		return height;
	}

}