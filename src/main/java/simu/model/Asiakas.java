package simu.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simu.framework.Trace;
import simu.framework.Kello;

/** Luokka mallintaa asiakasta
 *
 * @see simu.model.Asiakas
 */
public class Asiakas {

	/** Asiakkaan tapahtumatyyppi
	 *
	 * @see simu.model.TapahtumanTyyppi
	 */
	/** Asiakkaan tapahtumatyyppi
	 *
	 * @see simu.model.TapahtumanTyyppi
	 */
	private TapahtumanTyyppi tyyppi;
	/** Asiakkaan saapumisaika
	 *
	 * @see simu.model.Asiakas
	 */
	private double saapumisaika,
			/** Asiakkaan poistumisaika
			 *
			 * @see simu.model.Asiakas
			 */
			poistumisaika,
			/** Asiakkaan  leveys
			 *
			 * @see simu.model.Asiakas
			 */
			width,
			/** Asiakkaan korkeus
			 *
			 * @see simu.model.Asiakas
			 */
			height;
	/** Asiakkaan id
	 * @see simu.model.Asiakas
	 * */
	private int id;

	/** Asiakkaiden läpimenoaikojen summa
	 * @see simu.model.Asiakas
	 * */
	private static long sum = 0;
	/** Asiakkaan ulkomaanlento
	 * @see simu.model.Asiakas
	 * */
	private boolean ulkomaanlento; // Uusi kenttä, määrittää onko asiakas ulkomaanlennon asiakas

	/** Asiakkaan lähtöselvityksen myöhästyneet ja ehtineet asiakkaat
	 * @see simu.model.Asiakas
	 * */
	public static int lennolleEhtineet = 0, T1myohastyneet = 0, T2myohastyneet = 0;
	/** Tiläpäiset muutujat
	 * @see simu.model.Asiakas
	 * */
	public static int i = 0, j = 0;

	/** Konstruktori
	 *
	 * @param tyyppi Asiakkaan tapahtumatyyppi
	 * @see simu.model.Asiakas
	 */
	public Asiakas(TapahtumanTyyppi tyyppi) {
		this.id = ++j;
		saapumisaika = Kello.getInstance().getAika();
		this.tyyppi = tyyppi;
		this.ulkomaanlento = false;
		this.width = 25;
		this.height = 25;
		Trace.out(Trace.Level.INFO, "Uusi asiakas nro " + id + " saapui klo " + saapumisaika);
	}

	// Getterit ja setterit

	/** Palauttaa asiakkaan poistumisajan
	 *
	 * @return Asiakkaan poistumisaika
	 * @see simu.model.Asiakas
	 */
	public double getPoistumisaika() {
		return poistumisaika;
	}

	/** Asettaa asiakkaan poistumisajan
	 *
	 * @param poistumisaika Asiakkaan poistumisaika
	 * @see simu.model.Asiakas
	 */
	public void setPoistumisaika(double poistumisaika) {
		this.poistumisaika = poistumisaika;
	}

	/** Palauttaa asiakkaan saapumisajan
	 *
	 * @return Asiakkaan saapumisaika
	 * @see simu.model.Asiakas
	 */
	public double getSaapumisaika() {
		return saapumisaika;
	}

	/** Asettaa asiakkaan saapumisajan
	 *
	 * @param saapumisaika Asiakkaan saapumisaika
	 * @see simu.model.Asiakas
	 */
	public void setSaapumisaika(double saapumisaika) {
		this.saapumisaika = saapumisaika;
	}

	/** Palauttaa asiakkaan id:n
	 *
	 * @return Asiakkaan id
	 * @see simu.model.Asiakas
	 */
	public int getId() {
		return this.id;
	}

	/** Palauttaa asiakkaan tapahtumatyypin
	 *
	 * @return Asiakkaan tapahtumatyyppi
	 * @see simu.model.Asiakas
	 */
	public TapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}

	/** Asettaa asiakkaan tapahtumatyypin
	 *
	 * @see simu.model.Asiakas
	 */
	public boolean isUlkomaanlento() {
		return ulkomaanlento;
	}

	/** Asettaa asiakkaan tapahtumatyypin
	 *
	 * @see simu.model.Asiakas
	 */
	public void setUlkomaanlento() {
		this.ulkomaanlento = true;
	}

	/** Palauttaa asiakkaan leveyden
	 *
	 * @return Asiakkaan leveys
	 * @see simu.model.Asiakas
	 */
	public double getWidth() {
		return width;
	}

	/** Palauttaa asiakkaan korkeuden
	 *
	 * @return Asiakkaan korkeus
	 * @see simu.model.Asiakas
	 */
	public double getHeight() {
		return height;
	}

	// Raportti kun asiakas poistuu
	/** Raportti kun asiakas poistuu
	 *
	 * @see simu.model.Asiakas
	 */
	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
		sum += (long) (poistumisaika - saapumisaika);
		double keskiarvo = (double) sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}

	/** Piirtää asiakkaan
	 *
	 * @param gc GraphicsContext
	 * @param destX Asiakkaan x-koordinaatti
	 * @param destY Asiakkaan y-koordinaatti
	 * @see simu.model.Asiakas
	 */
	public void piirra(GraphicsContext gc, double destX, double destY) {
		if(this.ulkomaanlento) gc.setFill(Color.BLUE);
		else gc.setFill(Color.RED);
		gc.fillOval(destX, destY, this.width, this.height);
	}
}