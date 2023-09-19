package simu.model;

import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private Palvelupiste[] palvelupisteet;
	private boolean kaikkiAsiakkaatValmiit = false; // Lisätty lippu seuraamaan, ovatko kaikki asiakkaat valmiita

	public OmaMoottori() {

		// TODO:
		// Määritä palvelupisteille jotain järkeviä paveluaikoja ?

		palvelupisteet = new Palvelupiste[5];

		// Lähtöselvitys
		palvelupisteet[0] = new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.DEP1);
		// Turvatarkastus
		palvelupisteet[1] = new Palvelupiste(new Normal(10,6), tapahtumalista, TapahtumanTyyppi.DEP2);
		// Passintarkistus
		palvelupisteet[2] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP3);
		// Lähtöportti ulkomaat
		palvelupisteet[3] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP4);
		// Lähtöportti kotimaa
		palvelupisteet[4] = new Palvelupiste(new Normal(5,3), tapahtumalista, TapahtumanTyyppi.DEP5);
		// Saapumisprosessi
		saapumisprosessi = new Saapumisprosessi(new Negexp(15,5), tapahtumalista, TapahtumanTyyppi.ARR1);

	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Asetetaan ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {
		Asiakas a;
		switch ((TapahtumanTyyppi) t.getTyyppi()) {
			case ARR1:
				palvelupisteet[0].lisaaJonoon(new Asiakas());
				saapumisprosessi.generoiSeuraava();
				break;
			case DEP1:
				a = (Asiakas) palvelupisteet[0].otaJonosta();
				palvelupisteet[1].lisaaJonoon(a);
				break;
			case DEP2:
				a = (Asiakas) palvelupisteet[1].otaJonosta();
				if (a.isUlkomaanlento()) {
					palvelupisteet[2].lisaaJonoon(a);
				} else {
					palvelupisteet[4].lisaaJonoon(a);
				}
				break;
			case DEP3:
				a = (Asiakas) palvelupisteet[2].otaJonosta();
				palvelupisteet[3].lisaaJonoon(a);
				break;
			case DEP4:
				a = (Asiakas) palvelupisteet[3].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				tarkistaKaikkiAsiakkaatValmiit(); // Tarkista, ovatko kaikki asiakkaat valmiita
				break;
			case DEP5:
				a = (Asiakas) palvelupisteet[4].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				tarkistaKaikkiAsiakkaatValmiit(); // Tarkista, ovatko kaikki asiakkaat valmiita
				break;
		}
	}

	private void tarkistaKaikkiAsiakkaatValmiit() {
		// Tarkista, ovatko kaikki asiakkaat valmiita
		boolean kaikkiValmiit = true;
		for (Palvelupiste p : palvelupisteet) {
			if (p.onVarattu() || p.onJonossa()) {
				kaikkiValmiit = false;
				break;
			}
		}
		if (kaikkiValmiit) {
			kaikkiAsiakkaatValmiit = true;
		}
	}

	@Override
	protected void yritaCTapahtumat() {
		for (Palvelupiste p : palvelupisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}
	}

	@Override
	protected void tulokset() {

		System.out.println("\nSimulointi päättyi kello " + Kello.getInstance().getAika());
		//System.out.println("Tulokset ... puuttuvat vielä");
		System.out.println("Koko järjestelmässä palvellut asiakkaat: " + (palvelupisteet[3].getPalvelupisteessaPalvellutAsiakkaat()+palvelupisteet[4].getPalvelupisteessaPalvellutAsiakkaat()));
		System.out.println("Koko järjestelmän palveluaika: " + Palvelupiste.getKokoJärjstelmäPalveluAika());
		for (int i = 0; i < palvelupisteet.length; i++) {
			System.out.println("Palvelupiste " + (i+1) + " palvellut asiakkaat: " + palvelupisteet[i].getPalvelupisteessaPalvellutAsiakkaat());
			System.out.println("Palvelupiste " + (i+1)+ " palveluaika: " + palvelupisteet[i].getPalvelupisteenPalveluAika());
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		if (kaikkiAsiakkaatValmiit) {
			System.out.println("Kaikki asiakkaat ovat kulkeneet läpi.");
		} else {
			System.out.println("Kaikki asiakkaat eivät ole vielä kulkeneet läpi.");
		}
	}
}