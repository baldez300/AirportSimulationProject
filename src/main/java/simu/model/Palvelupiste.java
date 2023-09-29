package simu.model;

import javafx.scene.canvas.GraphicsContext;
import simu.framework.Kello;
import simu.framework.Trace;
import simu.framework.Tapahtuma;
import simu.framework.Tapahtumalista;
import java.util.LinkedList;
import simu.eduni.distributions.ContinuousGenerator;

// TODO:
// Palvelupistekohtaiset toiminnallisuudet, laskutoimitukset (+ tarvittavat muuttujat) ja raportointi koodattava
public class Palvelupiste {

	private final LinkedList<Asiakas> jono = new LinkedList<>(); // Tietorakennetoteutus
	private final ContinuousGenerator generator;
	private final Tapahtumalista tapahtumalista;
	private final TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;

	// Muuttujat metriikkojen tallentamiseen
	private double kokonaisOdottamisaika = 0;
	private double kokonaisPalveluaika = 0;
	private double kokonaisSimulaatioaika = 0;

	// JonoStartegia strategia; //optio: asiakkaiden järjestys

	// Laskutoimituksien tarvitsemat muuttujat
	private static double kokoJarjestelmanPalveluaika = 0;
	private double palvelupisteenPalveluAika, suoritusTeho;
	private static int palvellutAsiakkaatTotal = 0;
	private double palvelupisteessaPalvellutAsiakkaat;
	private final int x, y;
	private int pisteidenMaara;
	private final String nimi;

	private boolean varattu = false;

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

	public LinkedList<Asiakas> getAsiakasJono() {
		return jono;
	}

	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		// Toteutetaan asiakkaan lisäys jonoon
		a.setSaapumisaika(Kello.getInstance().getAika());
		jono.add(a);
	}

	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;
		// System.out.println("+1");
		palvelupisteessaPalvellutAsiakkaat += 1;
		// Toteutus asiakkaan poistamiseksi jonosta
		double odotusaika = Kello.getInstance().getAika() - jono.peek().getSaapumisaika();
		kokonaisOdottamisaika += odotusaika;
		palvellutAsiakkaatTotal += 1;
		return jono.poll();
	}

	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId());

		varattu = true;
		double palveluaika = generator.sample();
		palvelupisteenPalveluAika += palveluaika;
		kokoJarjestelmanPalveluaika += palveluaika;

		if (jono.peek().isUlkomaanlento()) {
			tapahtumalista.lisaa(
					new Tapahtuma(skeduloitavanTapahtumanTyyppi, (Kello.getInstance().getAika() + palveluaika), true));

		} else {
			tapahtumalista.lisaa(
					new Tapahtuma(skeduloitavanTapahtumanTyyppi, (Kello.getInstance().getAika() + palveluaika), false));

		}
	}

	public boolean onVarattu() {
		return varattu;
	}

	public boolean eiVarattu() {
		return !varattu;
	}

	// Esimerkki Baldelle jatka tästä...
	public void setSuoritusteho(double kokonaisAika) {
		this.suoritusTeho = (palvelupisteessaPalvellutAsiakkaat / kokonaisAika) * 100;
	}
	public double getSuoritusteho() {
		return suoritusTeho;
	}

	public void setJononPituus(double jononPituus) {
		this.palvelupisteessaPalvellutAsiakkaat = jononPituus;
	}

	public double getJononPituus() {
		return palvelupisteessaPalvellutAsiakkaat;
	}

	public void setKayttoaste(double simulointiAika) {
		this.palvelupisteenPalveluAika = (palvelupisteenPalveluAika / simulointiAika) * 100;
	}

	public double getKayttoaste() {
		return palvelupisteenPalveluAika;
	}

	public void setJonotusaika(double jonotusAika) {
		this.kokonaisOdottamisaika = jonotusAika;
	}

	public double getJonotusaika() {
		return kokonaisOdottamisaika;
	}



	public double getPalvelupisteessaPalvellutAsiakkaat() {
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

	public void removeAsiakasARR1(Asiakas a) {
		jono.removeIf(b -> a.isUlkomaanlento());
	}

	public void removeAsiakasARR2(Asiakas a) {
		jono.removeIf(b -> !a.isUlkomaanlento());
	}

	public String getNimi() {
		return this.nimi;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
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