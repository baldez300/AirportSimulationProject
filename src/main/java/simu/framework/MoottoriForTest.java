package simu.framework;
import simu.dao.TuloksetDao;

// Luodaan MoottoriForTest jotta ei tarvita GUI:ta testaukseen
public abstract class MoottoriForTest extends Thread implements IMoottori {

	private double simulointiaika = 0;
	private long viive = 0;

	private Kello kello;

	protected Tapahtumalista tapahtumalista;

	public TuloksetDao tuloksetDao;

	public boolean generoidaanUusiaLentoja = false;

	public MoottoriForTest() {

		this.tuloksetDao = new TuloksetDao();

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

		tapahtumalista = new Tapahtumalista();

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
		generoidaanUusiaLentoja = true;
		while (simuloidaan()) {
			generoidaanUusiaLentoja = true;
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

	public void suoritaBTapahtumat() {
		try {
			while (tapahtumalista.getSeuraavanAika() == kello.getAika()) {
				suoritaTapahtuma(tapahtumalista.poista());
			}
		} catch (NullPointerException e) {
			generoidaanUusiaLentoja = true;
			System.out.println("Ei seuraavia tapahtumia.. ");

			System.out.println("Ulkomaille ja sisälle lähtevät lennot ovat lähteneet..");

			System.out.println("Genetoidaan seuraavia lentoja.. jatketaan simulointia..");

			// Generoidaan uudet lennot ja niiden yhteydessä myös uudet tapahtumat
			alustukset();
			generoidaanUusiaLentoja = false;
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