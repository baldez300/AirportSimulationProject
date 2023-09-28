package simu.framework;

public class Tapahtuma implements Comparable<Tapahtuma> {

	private ITapahtumanTyyppi tyyppi;
	private boolean ulkomaanlento; // Uusi kenttä, määrittää onko tapahtuma ulkomaanlennon tapahtuma
	private double aika;

	public Tapahtuma(ITapahtumanTyyppi tyyppi, double aika, boolean ulkomaanlento){
		this.tyyppi = tyyppi;
		this.aika = aika;
		this.ulkomaanlento = ulkomaanlento;
	}

	public void setTyyppi(ITapahtumanTyyppi tyyppi) {
		this.tyyppi = tyyppi;
	}
	public ITapahtumanTyyppi getTyyppi() {
		return tyyppi;
	}
	public void setAika(double aika) {
		this.aika = aika;
	}
	public double getAika() {
		return aika;
	}

	public boolean isUlkomaanTyyppi() {
		return ulkomaanlento;
	}

	@Override
	public int compareTo(Tapahtuma arg) {
		if (this.aika < arg.aika) return -1;
		else if (this.aika > arg.aika) return 1;
		return 0;
	}
	public boolean isUlkomaanlento() {
		return ulkomaanlento;
	}

}