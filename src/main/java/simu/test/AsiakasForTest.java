package simu.test;

import simu.framework.Kello;
import simu.model.TapahtumanTyyppi;

/** Test luokka Asiakas-luokan testaamiseen */
public class AsiakasForTest {
	/** Luokkan attribuutit */
	private TapahtumanTyyppi tyyppi;
	private double saapumisaika, poistumisaika, width, height;
	private int id;
	private boolean ulkomaanlento; // Uusi kenttä, määrittää onko asiakas ulkomaanlennon asiakas
	public static int lennolleEhtineet = 0, T1myohastyneet = 0, T2myohastyneet = 0;
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