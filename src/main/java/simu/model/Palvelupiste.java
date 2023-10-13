package simu.model;

import javafx.scene.canvas.GraphicsContext;
import simu.framework.Kello;
import simu.framework.Trace;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

import java.util.Iterator;
import java.util.LinkedList;
import simu.eduni.distributions.ContinuousGenerator;

/** Plavelupiste luokka */
public class Palvelupiste {

	/** Palvelupiste luokan muuttujat */
	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus

	/** Random generaattori */
	private final ContinuousGenerator generator;
	/** Tapahtumalista */
	private final Tapahtumalista tapahtumalista;
	/** Tapahtuman tyyppi */
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	// Laskutoimituksien tarvitsemat muuttujat
	/** Palvelupisteessä palvellut asiakkaat */
	public static int palvellutAsiakkaatTotal = 0;
	/** Palvelupisteen koordinatit */
	private final int x, y;
	/** Palvelupisteen palveluaika */
	private double palvelupisteenPalveluAika = 0,
			/** Palvelupisteen suoritusteho */
			suoritusTeho = 0,
			/** Palvelupisteen kokonaisjonotusaika */
			kokonaisJonotusaika = 0,
			/** Palvelupisteen kokonaisläpimenoaika */
			kokonaisLapimenoaika = 0,
			/** Palvelupisteen jonon pituus */
			jononPituus = 0,
			/** Palvelupisteen käyttöaste */
			kayttoaste = 0,
			/** Palvelupisteen jonotusaika */
			jonotusAika = 0;
	/** Palvelupisteen pisteiden määrä */
	private int pisteidenMaara,
			/** Palvelupisteessä palvellut asiakkaat */
			palvelupisteessaPalvellutAsiakkaat;

	/** Palvelupisteen nimi */
	private final String nimi;
	/** Palvelupisteen varattu- tai ei varattu -tilanne */
	private boolean varattu;

	/** Palvelupisteen konstruktori */
	public Palvelupiste(int x, int y, String nimi, int pisteidenMaara, ContinuousGenerator generator,
			Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi) {
		this.x = x;
		this.y = y;
		this.nimi = nimi;
		this.pisteidenMaara = pisteidenMaara;
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.varattu = false;
	}

	// Jonon getteri
	/** Palauttaa jonon */
	public LinkedList<Asiakas> getAsiakasJono() {
		return jono;
	}

	/** Lisätään asiakas jonoon */
	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		// Toteutetaan asiakkaan lisäys jonoon
		a.setSaapumisaika(Kello.getInstance().getAika());
		jono.add(a);
	}

	/** Poistetaan asiakas jonosta */
	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		this.varattu = false;
		// lasketaan kokonaisLapimenoaika kun asiakas poistuu jonosta
		kokonaisLapimenoaika += Kello.getInstance().getAika() - jono.peek().getSaapumisaika();
		palvelupisteessaPalvellutAsiakkaat += 1; // pisteessä palvellut asiakkaat
		palvellutAsiakkaatTotal += 1; // järjestelmässä palvellut asiakkaat
		jono.peek().setPoistumisaika(Kello.getInstance().getAika());
		return jono.poll();
	}

	/** Aloitetaan palvelu */
	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());

		this.varattu = true;
		double palveluaika = generator.sample();

		// Lasketaan palvelupisteen kokonaisJonotusaikaa kun palvelu alkaa
		kokonaisJonotusaika += Kello.getInstance().getAika() - jono.peek().getSaapumisaika();
		// Lasketaan palvelupisteen palveluaikaa kun palvelu alkaa
		palvelupisteenPalveluAika += palveluaika;

		if (jono.peek().isUlkomaanlento()) {
			tapahtumalista.lisaa(
					new Tapahtuma(skeduloitavanTapahtumanTyyppi, (Kello.getInstance().getAika() + palveluaika), true));

		} else {
			tapahtumalista.lisaa(
					new Tapahtuma(skeduloitavanTapahtumanTyyppi, (Kello.getInstance().getAika() + palveluaika), false));
		}
	}

	// Getterit
	/** Palauttaa palvelupisteen nimen */
	public String getNimi() {
		return this.nimi;
	}

	/** Palauttaa palvelupisteen x-koordinaatin */
	public double getX() {
		return x;
	}
	/** Palauttaa palvelupisteen y-koordinaatin */

	public double getY() {
		return y;
	}
	/** Palauttaa palvelupisteen palveluajan */

	public double getSuoritusteho() {
		return suoritusTeho;
	}
	/** Palauttaa palvelupisteen jonon pituuden */

	public double getJononPituus() {
		return this.jononPituus;
	}

	/** Palauttaa palvelupisteen käyttöasteen */
	public double getKayttoaste() {
		return this.kayttoaste;
	}

	/** Palauttaa palvelupisteen jonotusajan */
	public double getJonotusaika() {
		return this.jonotusAika;
	}

	/** Palauttaa palvelupisteen palveluajan */
	public double getPalvelupisteessaPalvellutAsiakkaat() {
		return palvelupisteessaPalvellutAsiakkaat;
	}

	/** Palauttaa palvelupisteen kokonaisläpimenoajan */
	public double getPalvelupisteenPalveluAika() {
		return palvelupisteenPalveluAika;
	}

	/** Palauttaa palvelupisteen kokonaisjonotusajan */
	public int getMaara() {
		return this.pisteidenMaara;
	}

	// Setterit
	/** Asettaa palvelupisteen palveluajan */
	public void setSuoritusteho(double simulointiAika) {
		// Tästä tulee asiakasta / minuutissa. Muutetaan asiakasta / tunti kertomalla
		// 60:llä
		this.suoritusTeho = (palvelupisteessaPalvellutAsiakkaat / simulointiAika) * 60;
	}

	/** Asettaa palvelupisteen jonon pituuden */
	public void setJononPituus(double simulointiAika) {
		if (kokonaisLapimenoaika > 0)
			this.jononPituus = kokonaisLapimenoaika / simulointiAika;
		else
			this.jononPituus = 0;
	}

	/** Asettaa palvelupisteen käyttöasteen */
	public void setKayttoaste(double simulointiAika) {
		this.kayttoaste = (palvelupisteenPalveluAika / simulointiAika) * 100;
	}

	/** Asettaa palvelupisteen jonotusajan */
	public void setJonotusaika() {
		if (kokonaisJonotusaika > 0)
			this.jonotusAika = kokonaisJonotusaika / palvelupisteessaPalvellutAsiakkaat;
		else
			this.jonotusAika = 0;
	}

	// Booleanit
	/** Palauttaa palvelupisteen varattu- tai ei varattu -tilanteen */
	public boolean onVarattu() {
		return this.varattu;
	}

	/** Palauttaa palvelupisteen jonon tilanteen */
	public boolean onJonossa() {
		return jono.size() != 0;
	}

	// Lennolta myöhästyneiden poisto jonosta
	/** Poistaa lennolta myöhästyneet asiakkaat jonosta */
	public void removeAsiakasARR1() {
		if (!jono.isEmpty()) {
			if (jono.peek().isUlkomaanlento())
				varattu = false;
			Iterator<Asiakas> iterator = jono.iterator();
			while (iterator.hasNext()) {
				Asiakas a = iterator.next();
				if (a.isUlkomaanlento()) {
					a.setPoistumisaika(Kello.getInstance().getAika());
					kokonaisJonotusaika += Kello.getInstance().getAika() - a.getSaapumisaika();
					iterator.remove();
					Asiakas.T2myohastyneet++;
					Asiakas.i++;

				}
			}
		}
	}

	/** Poistaa kotimaan lennoilta myöhästyneet asiakkaat jonosta */
	public void removeAsiakasARR2() {
		if (!jono.isEmpty()) {
			if (!jono.peek().isUlkomaanlento())
				varattu = false;
			Iterator<Asiakas> iterator = jono.iterator();
			while (iterator.hasNext()) {
				Asiakas a = iterator.next();
				if (!a.isUlkomaanlento()) {
					a.setPoistumisaika(Kello.getInstance().getAika());
					kokonaisJonotusaika += Kello.getInstance().getAika() - a.getSaapumisaika();
					iterator.remove();
					Asiakas.T1myohastyneet++;
					Asiakas.i++;
				}
			}
		}
	}

	// Asetetaan palvelupisteen tulokset palvelupisteen olion muuttujiin
	/** Asettaa palvelupisteen tulokset palvelupisteen olion muuttujiin */
	public void asetaPalvelupisteenTulokset(Palvelupiste p, double simulointiAika) {
		p.setJononPituus(simulointiAika);
		p.setKayttoaste(simulointiAika);
		p.setJonotusaika();
		p.setSuoritusteho(simulointiAika);
	}

	// Piirretään infoa palvelupisteiden päälle
	/** Piirtää palvelupisteiden päälle infoa */
	public void piirra(GraphicsContext gc) {
		gc.setFont(javafx.scene.text.Font.font("Verdana", 15));
		if (this.nimi.equals("LS"))
			gc.strokeText("Pisteiden määrä: " + this.pisteidenMaara, this.x + 315, this.y + 60);
		else if (this.nimi.equals("PT"))
			gc.strokeText("Pisteiden määrä: " + this.pisteidenMaara, this.x + 200, this.y + 45);
		else if (this.nimi.equals("TT"))
			gc.strokeText("Pisteiden määrä: " + this.pisteidenMaara, this.x - 385, this.y + 60);
	}
}