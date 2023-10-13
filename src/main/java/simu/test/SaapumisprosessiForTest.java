package simu.test;
import simu.eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.ITapahtumanTyyppi;
import simu.framework.Tapahtuma;

import static simu.model.TapahtumanTyyppi.ARR1;
import static simu.model.TapahtumanTyyppi.*;

/** Saapumisprosessin test -luokka */
public class SaapumisprosessiForTest {
	/** Saamupisprosessin test -luokan muutujat */
	private TapahtumalistaForTest tapahtumalista;
	private ITapahtumanTyyppi tyyppi;

	private double ulkoLahtoAika, lentojenVali;

	/** Saapumisprosessin test -luokan konstruktori */
	public SaapumisprosessiForTest(TapahtumalistaForTest tl, ITapahtumanTyyppi tyyppi, double ulkoLahtoAika, double lentojenVali) {
		//this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
		this.ulkoLahtoAika = ulkoLahtoAika;
		this.lentojenVali = lentojenVali;
	}

	/** Metodi generoi seuraavat tapahtumat */
	public void generoiSeuraava() {
		
		// Lahtoaika ulkomaalennoille
		double ulkoLahtoAika = Kello.getInstance().getAika() + new Normal(this.ulkoLahtoAika, 5).sample();

		// Lahtoaika sisalennoille
		double lahtoAika = ulkoLahtoAika + this.lentojenVali; //new Normal(this.lentojenVali, 5).sample() ;

		// Luodaan tapahtuma "Ulkomaan lento"
		Tapahtuma tUlko = new Tapahtuma(tyyppi, ulkoLahtoAika, true);
		tapahtumalista.lisaa(tUlko);

		// Luodaan tapahtuma "Sisamaan lento"
		Tapahtuma tSisa = new Tapahtuma(SISA, lahtoAika, false);
		tapahtumalista.lisaa(tSisa);

		// Luodaan 10 tapahtumaa "Saapuva asiakas ulkomaalle"
		for (int i=0; i<10; i++) {
			Tapahtuma t1 = new Tapahtuma(ARR1, ulkoLahtoAika - (new Normal(240, 15).sample()), true);
			tapahtumalista.lisaa(t1);
		}

		// Luodaan 10 tapahtumaa "Saapuva asiakas sisalennolle"
		for (int i=0; i<10; i++) {
			Tapahtuma t2 = new Tapahtuma(ARR2, lahtoAika - (new Normal(240, 15).sample()), false);
			tapahtumalista.lisaa(t2);
		}
	}
}