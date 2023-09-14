package simu.model;

import simu.framework.*;

public class Asiakas {
	private double saapumisaika;
	private double poistumisaika;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private boolean ulkomaanlento; // Uusi kenttä, määrittää onko asiakas ulkomaanlennon asiakas

	public Asiakas() {
		id = i++;
		saapumisaika = Kello.getInstance().getAika();

		// Arvotaan ulkomaanlennon asiakkuus
		ulkomaanlento = arvoUlkomaanlento();

		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika);
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
		return id;
	}

	public boolean isUlkomaanlento() {
		return ulkomaanlento;
	}

	// Arpoa ulkomaanlennon asiakkuuden
	// Jos satunnaisluku on pienempi kuin 0.5 väliltä 0.0 ja 1.0, Math.random() < 0.5 palauttaa true.
	// Jos satunnaisluku on suurempi tai yhtä suuri kuin 0.5, se palauttaa false.

	private boolean arvoUlkomaanlento() {
		return Math.random() < 0.5; // Voit muuttaa arvontakriteeriä tarpeidesi mukaan
	}

	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
		sum += (poistumisaika - saapumisaika);
		double keskiarvo = sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}
}
