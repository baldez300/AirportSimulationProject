package simu.model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simu.framework.Trace;
import simu.framework.Kello;
import static simu.model.TapahtumanTyyppi.*;

public class Asiakas {
	private TapahtumanTyyppi tyypi;
	private double saapumisaika, poistumisaika, width, height;
	private int id;
	private static int i = 1;
	private static long sum = 0;
	private boolean ulkomaanlento; // Uusi kenttä, määrittää onko asiakas ulkomaanlennon asiakas
	private static int myohastuneet = 0;

	public Asiakas(TapahtumanTyyppi tyyppi) {
		id = i++;
		saapumisaika = Kello.getInstance().getAika();
		this.tyypi = tyyppi;
		this.ulkomaanlento = this.tyypi.equals(ARR1);
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

	public void setMyohastuneet() {
		myohastuneet += 1;
	}

	public int getMyohastuneet() {
		return myohastuneet;
	}

	public TapahtumanTyyppi getTyyppi() {
		return tyypi;
	}

	public boolean isUlkomaanlento() {
		return ulkomaanlento;
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
		sum += (poistumisaika - saapumisaika);
		double keskiarvo = sum / id;
		System.out.println("Asiakkaiden läpimenoaikojen keskiarvo tähän asti " + keskiarvo);
	}

	public void piirra(GraphicsContext gc, double destX, double destY) {
		gc.setFill(Color.RED);
		gc.fillOval(destX, destY, this.width, this.height);
	}
}