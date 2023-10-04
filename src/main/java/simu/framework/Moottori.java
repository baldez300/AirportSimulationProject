package simu.framework;

import simu.dao.TuloksetDao;
import simu.model.Palvelupiste;
import simu.view.Kontrolleri;

public abstract class Moottori extends Thread implements IMoottori {

	private double simulointiaika = 0;
	private long viive = 0;

	private Kello kello;

	protected Tapahtumalista tapahtumalista;

	private Kontrolleri kontrolleri;

	public TuloksetDao tuloksetDao;

	public Moottori(Kontrolleri kontrolleri) {

		this.kontrolleri = kontrolleri;

		this.tuloksetDao = new TuloksetDao();

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

		tapahtumalista = new Tapahtumalista();

		Palvelupiste[] palvelupisteet;

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
			if(lento1lahtenyt) {
				tapahtumalista.removeUlkoTapahtumia();
				lento1lahtenyt = false;
			}
			else if (lento2lahtenyt){
				tapahtumalista.removeTapahtumia();
				lento2lahtenyt = false;
				alustukset();
			}

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
		while (tapahtumalista.getSeuraavanAika() == kello.getAika()) {
			suoritaTapahtuma(tapahtumalista.poista());
			if (tapahtumalista.getKoko() == 0) {
				alustukset();
			}
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

	protected abstract void tulostPalvelupisteidenkoko(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	protected abstract boolean jarjestelmaOnTyhja();

}