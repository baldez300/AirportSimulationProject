package simu.test;

import simu.dao.TuloksetDao;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.framework.Tapahtuma;

// Luodaan MoottoriForTest jotta ei tarvita GUI:ta testaukseen
/** Moottorin test luokka*/
public abstract class MoottoriForTest  extends Thread  implements IMoottori   {

	/** MoottorinTest luokan muuttujat*/
	private double simulointiaika = 0;
	private long viive = 0;
	public boolean running = false;

	private Kello kello;

	protected TapahtumalistaForTest tapahtumalista;

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

	@Override // UUSI
	/** Metodi asettaa viiveen*/
	public void setViive(long viive) {
		this.viive = viive;
	}

	@Override // UUSI
	/** Metodi palauttaa viiveen*/
	public long getViive() {
		return viive;
	}

	/** Metodi palauttaa tapahtumalistan*/
	public void run() {

		running = true;

		alustukset(); // luodaan mm. ensimmäinen tapahtuma

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

			System.out.println("Ulkomaille ja sisälle lähtevät lennot ovat lähteneet..");

			System.out.println("Genetoidaan seuraavia lentoja.. jatketaan simulointia..");

			// Generoidaan uudet lennot ja niiden yhteydessä myös uudet tapahtumat
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
	protected abstract void suoritaTapahtuma(Tapahtuma t); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	/** Metodi yrittää suorittaa C tapahtumat*/
	protected abstract void yritaCTapahtumat(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa

	/** Metodi alustaa tapahtumat*/
	protected abstract void alustukset(); // Määritellään simu.model-pakkauksessa Moottorin aliluokassa
}