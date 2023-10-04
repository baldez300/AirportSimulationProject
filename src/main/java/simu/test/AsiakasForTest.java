package simu.test;

import simu.framework.Kello;
import simu.model.TapahtumanTyyppi;

public class AsiakasForTest {
	private TapahtumanTyyppi tyyppi;
	private double saapumisaika, poistumisaika, width, height;
	private int id;
	private boolean ulkomaanlento; // Uusi kenttä, määrittää onko asiakas ulkomaanlennon asiakas
	public static int lennolleEhtineet = 0, T1myohastyneet = 0, T2myohastyneet = 0;
	public static int i = 0;

	public AsiakasForTest(TapahtumanTyyppi tyyppi) {
		++id;
		saapumisaika = Kello.getInstance().getAika();
		this.tyyppi = tyyppi;
		this.ulkomaanlento = false;
		this.width = 25;
		this.height = 25;
	}

	public double getPoistumisaika() {
		return poistumisaika;
	}

	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	public double getSaapumisaika() {
		return saapumisaika;
	}

	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	public int getId() {
		return this.id;
	}

	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}

	public boolean isUlkomaanlento() {
		return ulkomaanlento;
	}

	public void setUlkomaanlento() {
		this.ulkomaanlento = true;
	}

	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}

}