package simu.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simu.framework.Trace;
import simu.framework.Kello;

public class Asiakas {
	private TapahtumanTyyppi tyyppi;
	private double saapumisaika, poistumisaika, width, height;
	private int id;
	private static long sum = 0;
	private boolean ulkomaanlento; // Uusi kenttä, määrittää onko asiakas ulkomaanlennon asiakas
	public static int lennolleEhtineet = 0, T1myohastyneet = 0, T2myohastyneet = 0;
	public static int i = 0;

	public Asiakas(TapahtumanTyyppi tyyppi) {
		++id;
		saapumisaika = Kello.getInstance().getAika();
		this.tyyppi = tyyppi;
		this.ulkomaanlento = false;
		this.width = 25;
		this.height = 25;
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

	public void raportti() {
		Trace.out(Trace.Level.INFO, "\nAsiakas " + id + " valmis! ");
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " saapui: " + saapumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " poistui: " + poistumisaika);
		Trace.out(Trace.Level.INFO, "Asiakas " + id + " viipyi: " + (poistumisaika - saapumisaika));
		sum += (long) (poistumisaika - saapumisaika);
		double keskiarvo = (double) sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}

	public void piirra(GraphicsContext gc, double destX, double destY) {
		gc.setFill(Color.RED);
		gc.fillOval(destX, destY, this.width, this.height);
	}
}