package simu.framework;
import simu.eduni.distributions.Normal;

import static simu.model.TapahtumanTyyppi.ARR1;
import static simu.model.TapahtumanTyyppi.*;

public class Saapumisprosessi {
	private Tapahtumalista tapahtumalista;
	private ITapahtumanTyyppi tyyppi;

	private double ulkoLahtoAika, lentojenVali;

	public Saapumisprosessi(Tapahtumalista tl, ITapahtumanTyyppi tyyppi, double ulkoLahtoAika, double lentojenVali) {
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
		this.ulkoLahtoAika = ulkoLahtoAika;
		this.lentojenVali = lentojenVali;
	}

	public void generoiSeuraava() {
		
		// Lähtöaika ulkomaan lennoille
		double ulkoLahtoAika = Kello.getInstance().getAika() + new Normal(this.ulkoLahtoAika, 5).sample();

		// Lähtöaika kotimaan lennoille
		double lahtoAika = ulkoLahtoAika + this.lentojenVali;

		// Luodaan tapahtuma ulkomaan lento
		Tapahtuma tUlko = new Tapahtuma(tyyppi, ulkoLahtoAika, true);
		tapahtumalista.lisaa(tUlko);

		// Luodaan tapahtuma kotimaan lento
		Tapahtuma tSisa = new Tapahtuma(SISA, lahtoAika, false);
		tapahtumalista.lisaa(tSisa);

		// Luodaan 10 tapahtumaa (saapuvaa asiakasta) ulkomaan lennolle
		for (int i=0; i<20; i++) {
			Tapahtuma t1 = new Tapahtuma(ARR1, ulkoLahtoAika - (new Normal(240, 15).sample()), true);
			tapahtumalista.lisaa(t1);
		}

		// Luodaan 10 tapahtumaa (saapuvaa asiakasta) kotimaan lennolle
		for (int i=0; i<20; i++) {
			Tapahtuma t2 = new Tapahtuma(ARR2, lahtoAika - (new Normal(240, 15).sample()), false);
			tapahtumalista.lisaa(t2);
		}
	}
}