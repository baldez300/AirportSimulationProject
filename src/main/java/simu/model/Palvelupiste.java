package simu.model;

import simu.framework.*;
import java.util.LinkedList;

import javafx.scene.canvas.GraphicsContext;
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
	private int pisteidenMaara;
	private final String nimi;

	private boolean varattu = false;

	// Muuttujat metriikkojen tallentamiseen
	private double kokonaisOdottamisaika = 0;
	private double kokonaisPalveluaika = 0;
	private double kokonaisSimulaatioaika = 0;

	public Palvelupiste(int x, int y, String nimi, ContinuousGenerator generator, Tapahtumalista tapahtumalista,
						TapahtumanTyyppi tyyppi) {
		this.x = x;
		this.y = y;
		this.nimi = nimi;
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
	}

	public Palvelupiste(int x, int y, String nimi, int pisteidenMaara, ContinuousGenerator generator,
						Tapahtumalista tapahtumalista,
						TapahtumanTyyppi tyyppi) {
		this.x = x;
		this.y = y;
		this.nimi = nimi;
		this.pisteidenMaara = pisteidenMaara;
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
	}

	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		// Toteuta asiakkaan lisäys jonoon tässä
		a.setSaapumisaika(Kello.getInstance().getAika());
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
		// Toteuta asiakkaan poisto jonosta tässä
		double odotusaika = Kello.getInstance().getAika() - jono.peek().getSaapumisaika();
		kokonaisOdottamisaika += odotusaika;
		palvellutAsiakkaatTotal++;
		return jono.poll();
	}

	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana
		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());
		varattu = true;
		double palveluaika = generator.sample();
		// Toteuta palvelun aloitus tässä
		kokonaisPalveluaika += palveluaika;
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

	// Laske keskimääräinen odotusaika
	public double getKeskimaarainenOdotusaika() {
		return kokonaisOdottamisaika / palvellutAsiakkaatTotal;
	}

	// Laske palvelutehokkuus
	public double getSuoritusTeho() {
		return (palvellutAsiakkaatTotal / kokonaisSimulaatioaika) * 100;
	}

	// Laske palvelupisteen käyttöaste
	public double getPalvelupisteenKayttoaste(double simulointAika) {
		return (palvelupisteenPalveluAika / simulointAika) * 100;
	}

	// Loput luokasta...

	// Piirretään infoa palvelupisteiden päälle
	public void piirra(GraphicsContext gc) {
		gc.setFont(javafx.scene.text.Font.font("Verdana", 15));
		if (this.pisteidenMaara != 0 && !this.nimi.equals("TT"))
			gc.strokeText("Pisteiden maara: " + this.pisteidenMaara, this.x + 43, this.y + 20);
		else if (this.pisteidenMaara != 0 && this.nimi.equals("TT"))
			gc.strokeText("Pisteiden maara: " + this.pisteidenMaara, this.x - 160, this.y + 15);
	}
}
