package simu.framework;

import simu.controller.Kontrolleri;

public abstract class Moottori extends Thread implements IMoottori {

	private double simulointiaika = 0;
	private long viive = 0;

	private Kello kello;

	protected Tapahtumalista tapahtumalista;

	private Kontrolleri kontrolleri;

	public Moottori(Kontrolleri kontrolleri) {

		this.kontrolleri = kontrolleri;

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

		tapahtumalista = new Tapahtumalista();

		// Palvelupisteet luodaan simu.model-pakkauksessa Moottorin aliluokassa

	}

	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	public double getSimulointiaika() {
		return simulointiaika;
	}

	@Override // UUSI
	public void setViive(long viive) {
		this.viive = viive;
	}

	@Override // UUSI
	public long getViive() {
		return viive;
	}

	public void run() {
		alustukset(); // luodaan mm. ensimmäinen tapahtuma
		while (!Thread.interrupted() && simuloidaan()) {
			viive();

			Trace.out(Trace.Level.INFO, "\nA-vaihe: kello on " + nykyaika());
			kello.setAika(nykyaika());

			Trace.out(Trace.Level.INFO, "\nB-vaihe:");
			suoritaBTapahtumat();

			Trace.out(Trace.Level.INFO, "\nC-vaihe:");
			yritaCTapahtumat();

		}
		asetaTulokset();
		tulokset();
	}

	private void viive() { // UUSI
		Trace.out(Trace.Level.INFO, "Viive " + viive);
		try {
			sleep(viive);
		} catch (InterruptedException e) {
			kontrolleri.lopetaSaie();
		}
	}

	private void suoritaBTapahtumat() {
		try {
			while (tapahtumalista.getSeuraavanAika() == kello.getAika()) {
				suoritaTapahtuma(tapahtumalista.poista());
			}
		} catch (NullPointerException e) {
			System.out.println("Ei seuraavia tapahtumia.. ");

			System.out.println("Ulkomaille ja sisälle lähtevät lennot ovat lähteneet..");

			System.out.println("Genetoidaan seuraavia lentoja.. jatketaan simulointia..");

			// Generoidaan uudet lennot ja niiden yhteydessä myös uudet tapahtumat
			alustukset();
		}
	}

	private double nykyaika() {
		return tapahtumalista.getSeuraavanAika();
	}

	private boolean simuloidaan() {
		return kello.getAika() < simulointiaika;

	}

	protected abstract void suoritaTapahtuma(Tapahtuma t); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void yritaCTapahtumat(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void tulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract void asetaTulokset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

}