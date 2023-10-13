package simu.test;

import simu.dao.TuloksetDao;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.framework.Tapahtuma;

// Luodaan MoottoriForTest jotta ei tarvita GUI:ta testaukseen
/** Moottorin test luokka*/
public abstract class MoottoriForTest  extends Thread  implements IMoottori   {

	/** Simulointiaika*/
	private double simulointiaika = 0;
	/** Viive*/
	private long viive = 0;
	/** Lippu joka nayttaa onko simulointi kaynnissa*/
	public boolean running = false;

	/** Kello*/
	private Kello kello;

	/** Tapahtumalista*/
	protected TapahtumalistaForTest tapahtumalista;

	/** TuloksetDao*/
	public TuloksetDao tuloksetDao;

	/** MoottorinTest luokan konstruktori*/
	public MoottoriForTest() {

		this.tuloksetDao = new TuloksetDao();

		kello = Kello.getInstance(); // Otetaan kello muuttujaan yksinkertaistamaan koodia

		tapahtumalista = new TapahtumalistaForTest();

	}

	// Setterit ja getterit
	/** Metodi asettaa simulointiajan*/
	public void setSimulointiaika(double aika) {
		simulointiaika = aika;
	}

	/** Metodi palauttaa simulointiajan*/
	public double getSimulointiaika() {
		return simulointiaika;
	}

	/** Metodi asettaa viiveen*/
	@Override // UUSI
	public void setViive(long viive) {
		this.viive = viive;
	}

	/** Metodi palauttaa viiveen*/
	@Override // UUSI

	public long getViive() {
		return viive;
	}

	/** Metodi palauttaa tapahtumalistan*/
	public void run() {

		running = true;

		alustukset(); // luodaan mm. ensimmainen tapahtuma

		while (simuloidaan() && !Thread.interrupted()) {

			kello.setAika(nykyaika());


			suoritaBTapahtumat();


			yritaCTapahtumat();
		}
	}

	/** Metodi suorittaa B tapahtumat*/
	public void suoritaBTapahtumat() {
		try {
			while (tapahtumalista.getSeuraavanAika() == kello.getAika()) {
				suoritaTapahtuma(tapahtumalista.poista());
			}
		} catch (NullPointerException e) {

			System.out.println("Ei seuraavia tapahtumia.. ");

			System.out.println("Ulkomaille ja sisalle lahtevat lennot ovat lahteneet..");

			System.out.println("Genetoidaan seuraavia lentoja.. jatketaan simulointia..");

			// Generoidaan uudet lennot ja niiden yhteydessa myos uudet tapahtumat
			alustukset();

		}
	}

	/** Metodi palauttaa nykyisen ajan*/
	private double nykyaika() {
		return tapahtumalista.getSeuraavanAika();
	}

	/** Metodi palauttaa true jos simuloidaan*/
	private boolean simuloidaan() {
		return kello.getAika() < simulointiaika;
	}

	/** Metodi palauttaa true jos running on true*/
	public boolean isRunning() {
		return running;
	}

	/** Metodi asettaa runningin*/
	public void setRunning(boolean value) {
		running = value;
	}

	/** Metodi suorittaa tapahtumat*/
	protected abstract void suoritaTapahtuma(Tapahtuma t); // Maaritellaan simu.model-pakkauksessa Moottorin aliluokassa

	/** Metodi yrittaa suorittaa C tapahtumat*/
	protected abstract void yritaCTapahtumat(); // Maaritellaan simu.model-pakkauksessa Moottorin aliluokassa

	/** Metodi alustaa tapahtumat*/
	protected abstract void alustukset(); // Maaritellaan simu.model-pakkauksessa Moottorin aliluokassa
}