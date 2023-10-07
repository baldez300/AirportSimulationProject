package simu.model;

import javafx.scene.canvas.GraphicsContext;
import simu.framework.Kello;
import simu.framework.Trace;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;

import java.util.Iterator;
import java.util.LinkedList;
import simu.eduni.distributions.ContinuousGenerator;

public class Palvelupiste {

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	// Laskutoimituksien tarvitsemat muuttujat
	public static int palvellutAsiakkaatTotal = 0;
	private final int x, y;
	private double palvelupisteenPalveluAika = 0, suoritusTeho = 0, kokonaisJonotusaika = 0, kokonaisLapimenoaika = 0,
			jononPituus = 0, kayttoaste = 0, jonotusAika = 0;
	private int pisteidenMaara, palvelupisteessaPalvellutAsiakkaat;
	private final String nimi;
	private boolean varattu;

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

	public LinkedList<Asiakas> getAsiakasJono() {
		return jono;
	}

	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		// Toteutetaan asiakkaan lisäys jonoon
		a.setSaapumisaika(Kello.getInstance().getAika());
		jono.add(a);
	}

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		this.varattu = false;
		// lasketaan kokonaisLapimenoaika kun asiakas poistuu jonosta
		kokonaisLapimenoaika += Kello.getInstance().getAika() - jono.peek().getSaapumisaika();
		palvelupisteessaPalvellutAsiakkaat += 1; // pisteessä palvellut asiakkaat
		palvellutAsiakkaatTotal += 1; // järjestelmässä palvellut asiakkaat
		jono.peek().setPoistumisaika(Kello.getInstance().getAika());
		return jono.poll();
	}

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
	public String getNimi() {
		return this.nimi;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getSuoritusteho() {
		return suoritusTeho;
	}

	public double getJononPituus() {
		return this.jononPituus;
	}

	public double getKayttoaste() {
		return this.kayttoaste;
	}

	public double getJonotusaika() {
		return this.jonotusAika;
	}

	public double getPalvelupisteessaPalvellutAsiakkaat() {
		return palvelupisteessaPalvellutAsiakkaat;
	}

	public double getPalvelupisteenPalveluAika() {
		return palvelupisteenPalveluAika;
	}

	public int getMaara() {
		return this.pisteidenMaara;
	}

	// Setterit
	public void setSuoritusteho(double simulointiAika) {
		// Tästä tulee asiakasta / minuutissa. Muutetaan asiakasta / tunti kertomalla
		// 60:llä
		this.suoritusTeho = (palvelupisteessaPalvellutAsiakkaat / simulointiAika) * 60;
	}

	public void setJononPituus(double simulointiAika) {
		if (kokonaisLapimenoaika > 0)
			this.jononPituus = kokonaisLapimenoaika / simulointiAika;
		else
			this.jononPituus = 0;
	}

	public void setKayttoaste(double simulointiAika) {
		this.kayttoaste = (palvelupisteenPalveluAika / simulointiAika) * 100;
	}

	public void setJonotusaika() {
		if (kokonaisJonotusaika > 0)
			this.jonotusAika = kokonaisJonotusaika / palvelupisteessaPalvellutAsiakkaat;
		else
			this.jonotusAika = 0;
	}

	// Booleanit
	public boolean onVarattu() {
		return this.varattu;
	}

	public boolean onJonossa() {
		return jono.size() != 0;
	}

	// Lennolta myöhästyneiden poisto jonosta
	public void removeAsiakasARR1() {
		if (!jono.isEmpty()) {
			if (jono.peek().isUlkomaanlento())
				varattu = false;
			Iterator<Asiakas> iterator = jono.iterator();
			while (iterator.hasNext()) {
				Asiakas a = iterator.next();
				if (a.isUlkomaanlento()) {
					a.setPoistumisaika(Kello.getInstance().getAika());
					iterator.remove();
					Asiakas.T2myohastyneet++;
					Asiakas.i++;

				}
			}
		}
	}

	public void removeAsiakasARR2() {
		if (!jono.isEmpty()) {
			if (!jono.peek().isUlkomaanlento())
				varattu = false;
			Iterator<Asiakas> iterator = jono.iterator();
			while (iterator.hasNext()) {
				Asiakas a = iterator.next();
				if (!a.isUlkomaanlento()) {
					a.setPoistumisaika(Kello.getInstance().getAika());
					iterator.remove();
					Asiakas.T1myohastyneet++;
					Asiakas.i++;
				}
			}
		}
	}

	// Asetetaan palvelupisteen tulokset palvelupisteen olion muuttujiin
	public void asetaPalvelupisteenTulokset(Palvelupiste p, double simulointiAika) {
		p.setJononPituus(simulointiAika);
		p.setKayttoaste(simulointiAika);
		p.setJonotusaika();
		p.setSuoritusteho(simulointiAika);
	}

	// Piirretään infoa palvelupisteiden päälle
	public void piirra(GraphicsContext gc) {
		gc.setFont(javafx.scene.text.Font.font("Verdana", 15));
		if (this.nimi.equals("LS") || this.nimi.equals("PT"))
			gc.strokeText("Pisteiden maara: " + this.pisteidenMaara, this.x + 43, this.y + 20);
		else if (this.nimi.equals("TT"))
			gc.strokeText("Pisteiden maara: " + this.pisteidenMaara, this.x - 160, this.y + 15);
	}
}