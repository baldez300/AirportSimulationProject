package simu.model;

import simu.framework.*;
import java.util.LinkedList;
import simu.eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	// JonoStartegia strategia; //optio: asiakkaiden järjestys

	// Laskutoimituksien tarvitsemat muuttujat
	private static double kokoJarjestelmanPalveluaika = 0;
	private double palvelupisteenPalveluAika;
	private static int palvellutAsiakkaatTotal = 0;
	private int palvelupisteessaPalvellutAsiakkaat;
	private final int x, y;
	private final String nimi;

	private boolean varattu = false;

	public Palvelupiste(int x, int y, String nimi, ContinuousGenerator generator, Tapahtumalista tapahtumalista,
			TapahtumanTyyppi tyyppi) {
		this.x = x;
		this.y = y;
		this.nimi = nimi;
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
	}

	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		jono.add(a);
	}

	public LinkedList<Asiakas> getAsiakasJono() {
		return jono;
	}

	public int getKoko() {
		return jono.size();
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public String getNimi() {
		return nimi;
	}

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;
		palvelupisteessaPalvellutAsiakkaat++;
		return jono.poll();
	}

	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		varattu = true;
		double palveluaika = generator.sample();
		palvelupisteenPalveluAika += palveluaika;
		kokoJarjestelmanPalveluaika += palveluaika;
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
	}

	public boolean onVarattu() {
		return varattu;
	}

	public int getPalvelupisteessaPalvellutAsiakkaat() {
		return palvelupisteessaPalvellutAsiakkaat;
	}

	public static int getPalvellutAsiakkaatTotal() {
		return palvellutAsiakkaatTotal;
	}

	public double getPalvelupisteenPalveluAika() {
		return palvelupisteenPalveluAika;
	}

	public static double getKokoJarjestelmanPalveluAika() {
		return kokoJarjestelmanPalveluaika;
	}

	public boolean onJonossa() {
		return jono.size() != 0;
	}

}
