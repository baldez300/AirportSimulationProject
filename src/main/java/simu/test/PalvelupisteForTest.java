package simu.test;

import simu.framework.Kello;
import simu.framework.Tapahtuma;
import simu.model.Asiakas;
import simu.model.TapahtumanTyyppi;
import simu.eduni.distributions.ContinuousGenerator;

import java.util.Iterator;
import java.util.LinkedList;

/** Palvelupisteen test -luokka */
public class PalvelupisteForTest {

	/** Palvelupisteide jono*/
	private final LinkedList<AsiakasForTest> jono = new LinkedList<>(); // Tietorakennetoteutus
	/** Palvelupisteen generoitu palveluaika */
	private final ContinuousGenerator generator;
	/** Tapahtumalista */
	private final TapahtumalistaForTest tapahtumalista;
	/** Tapahtuman skeduloitavan tapahtuman tyyppi */
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	// Laskutoimituksien tarvitsemat muuttujat
	/** Koko järjectelmän palvellut asiakkaat */
	public static int palvellutAsiakkaatTotal = 0;
	/** Palvelupisteen palveluaika */
	private double palvelupisteenPalveluAika = 0;
	/** Palvelupisteen suoritusteho */
	private double suoritusTeho = 0;
	/** Palvelupisteen kokonaisjonotusaika */
	private double kokonaisJonotusaika = 0;
	/** Palvelupisteen kokonaislapimenoaika */
	private double kokonaisLapimenoaika = 0;
	/** Palvelupisteen jonon pituus */
	private double jononPituus = 0;
	/** Palvelupisteen kayttoaste */
	private double kayttoaste = 0;
	/** Palvelupisteen jonotusaika */
	private double jonotusAika = 0;
	/** Palvelupisteen palvellut asiakkaat */
	private int palvelupisteessaPalvellutAsiakkaat;
	/** Lippu joka kertoo onko palvelupiste varattu */
	private boolean varattu = false;

	/** Palvelupisteen konstruktori */
	public PalvelupisteForTest(ContinuousGenerator generator, TapahtumalistaForTest tapahtumalista, TapahtumanTyyppi tyyppi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
	}

	/** Metodi palauttaa asiakaiden jonon */
	public LinkedList<AsiakasForTest> getAsiakasJono() {
		return jono;
	}

	/** Metodi lisaa asiakkaan jonoon */
	public void lisaaJonoon(AsiakasForTest a) { // Jonon 1. asiakas aina palvelussa
		// Toteutetaan asiakkaan lisays jonoon
		a.setSaapumisaika(Kello.getInstance().getAika());
		jono.add(a);
	}

	/** Metodi poistaa asiakkaan jonosta */
	public AsiakasForTest otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;
		// lasketaan kokonaisLapimenoaika kun asiakas poistuu jonosta
		kokonaisLapimenoaika += Kello.getInstance().getAika() - jono.peek().getSaapumisaika();
		palvelupisteessaPalvellutAsiakkaat += 1; // pisteessa palvellut asiakkaat
		palvellutAsiakkaatTotal += 1; // jarjestelmassa palvellut asiakkaat
		return jono.poll();
	}

	/** Metodi aloittaa palvelun */
	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		varattu = true;
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

	/** Metodi poistaa jonosta myohastyneet asiakkaat */
	public void removeAsiakasARR1() {
		if (!jono.isEmpty()) {
			if (jono.peek().isUlkomaanlento())
				varattu = false;
			Iterator<AsiakasForTest> iterator = jono.iterator();
			while (iterator.hasNext()) {
				AsiakasForTest a = iterator.next();
				if (a.isUlkomaanlento()) {
					iterator.remove();
					Asiakas.T2myohastyneet++;
					Asiakas.i++;
				}
			}
		}
	}

	/** Metodi poistaa jonosta myohastyneet asiakkaat */
	public void removeAsiakasARR2() {
		if (!jono.isEmpty()) {
			if (!jono.peek().isUlkomaanlento())
				varattu = false;
			Iterator<AsiakasForTest> iterator = jono.iterator();
			while (iterator.hasNext()) {
				AsiakasForTest a = iterator.next();
				if (!a.isUlkomaanlento()) {
					iterator.remove();
					Asiakas.T1myohastyneet++;
					Asiakas.i++;
				}
			}
		}
	}

	// Getterit
	/** Metodi palauttaa palvelupisteen suoritustehon */
	public double getSuoritusteho() {
		return suoritusTeho;
	}

	/** Metodi palauttaa palvelupisteen jonon pituuden */
	public double getJononPituus() {
		return this.jononPituus;
	}

	/** Metodi palauttaa palvelupisteen kayttoasteen */
	public double getKayttoaste() {
		return this.kayttoaste;
	}

	/** Metodi palauttaa palvelupisteen jonotusajan */
	public double getJonotusaika() {
		return this.jonotusAika;
	}

	/** Metodi palauttaa palvelupisteen palvellut asiakkaat */
	public double getPalvelupisteessaPalvellutAsiakkaat() {
		return palvelupisteessaPalvellutAsiakkaat;
	}

	/** Metodi palauttaa palvelupisteen palveluaika */
	public double getPalvelupisteenPalveluAika() {
		return palvelupisteenPalveluAika;
	}

	// Setterit
	/** Metodi asettaa palvelupisteen suoritustehon */
	public void setSuoritusteho(double simulointiAika) {
		// Tasta tulee asiakasta / minuutissa. Muutetaan asiakasta / tunti kertomalla
		// 60:lla
		this.suoritusTeho = (palvelupisteessaPalvellutAsiakkaat / simulointiAika) * 60;
	}

	/** Metodi asettaa palvelupisteen jonon pituuden */
	public void setJononPituus(double simulointiAika) {
		if (kokonaisLapimenoaika > 0)
			this.jononPituus = kokonaisLapimenoaika / simulointiAika;
		else
			this.jononPituus = 0;
	}

	/** Metodi asettaa palvelupisteen kayttoasteen */
	public void setKayttoaste(double simulointiAika) {
		this.kayttoaste = (palvelupisteenPalveluAika / simulointiAika) * 100;
	}

	/** Metodi asettaa palvelupisteen jonotusajan */
	public void setJonotusaika() {
		if (kokonaisJonotusaika > 0)
			this.jonotusAika = kokonaisJonotusaika / palvelupisteessaPalvellutAsiakkaat;
		else
			this.jonotusAika = 0;
	}

	// Booleanit
	/** Metodi palauttaa onko palvelupiste varattu */
	public boolean onVarattu() {
		return varattu;
	}

	/** Metodi asettaa palvelupisteen varatuksi */
	public void eiVarattu() {
		varattu = false;
	}

	/** Metodi palauttaa onko palvelupisteessa asiakkaita */
	public boolean onJonossa() {
		return jono.size() != 0;
	}

	// Asetetaan palvelupisteen tulokset palvelupisteen olion muuttujiin
	/** Metodi asettaa palvelupisteen tulokset */
	public void asetaPalvelupisteenTulokset(PalvelupisteForTest p, double simulointiAika) {
		p.setJononPituus(simulointiAika);
		p.setKayttoaste(simulointiAika);
		p.setJonotusaika();
		p.setSuoritusteho(simulointiAika);
	}
}