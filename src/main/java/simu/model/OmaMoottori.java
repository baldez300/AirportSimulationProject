package simu.model;

import simu.controller.Kontrolleri;
import simu.eduni.distributions.Negexp;
import simu.eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.view.Visualisointi;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;
	private Palvelupiste[] palvelupisteet;
	private boolean kaikkiAsiakkaatValmiit = false; // Lisätty lippu seuraamaan, ovatko kaikki asiakkaat valmiita
	Visualisointi visualisointi;

	public OmaMoottori(Kontrolleri kontrolleri) {
		super(kontrolleri);
		visualisointi = new Visualisointi(kontrolleri.getCanvas());
		// TODO:
		// Määritä palvelupisteille jotain järkeviä paveluaikoja ?

		palvelupisteet = new Palvelupiste[5];

		// Lähtöselvitys
		palvelupisteet[0] = new Palvelupiste(1187, 500, "LS", kontrolleri.getLahtoselvitysMaara(), new Normal(kontrolleri.getLSpalveluNopeus(), kontrolleri.getLahtoselvitysVar()), tapahtumalista, TapahtumanTyyppi.DEP1);
		// Turvatarkastus
		palvelupisteet[1] = new Palvelupiste(288, 338, "TT", kontrolleri.getTurvatarkastusMaara(), new Normal(kontrolleri.getTTpalveluNopeus(), kontrolleri.getTurvatarkastusVar()), tapahtumalista, TapahtumanTyyppi.DEP2);
		// Passintarkistus
		palvelupisteet[2] = new Palvelupiste(1187, 165, "PT", kontrolleri.getPassintarkastusMaara(), new Normal(kontrolleri.getPTpalveluNopeus(), kontrolleri.getPassintarkastusVar()), tapahtumalista, TapahtumanTyyppi.DEP3);
		// Lähtöportti ulkomaat
		palvelupisteet[3] = new Palvelupiste(1360, 12, "T1", new Normal(kontrolleri.getUlkomaaKA(), kontrolleri.getUlkomaaVar()), tapahtumalista, TapahtumanTyyppi.DEP4);
		// Lähtöportti kotimaa
		palvelupisteet[4] = new Palvelupiste(127, 12, "T2", new Normal(kontrolleri.getKotimaaKA(), kontrolleri.getKotimaaVar()), tapahtumalista, TapahtumanTyyppi.DEP5);
		// Saapumisprosessi
		saapumisprosessi = new Saapumisprosessi(new Negexp(5, 15), tapahtumalista, TapahtumanTyyppi.ARR1);
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
				a = new Asiakas();
				palvelupisteet[0].lisaaJonoon(a);
				saapumisprosessi.generoiSeuraava();
				visualisointi.piirra(palvelupisteet);
				break;
			case DEP1:
				a = (Asiakas) palvelupisteet[0].otaJonosta();
				palvelupisteet[1].lisaaJonoon(a);
				visualisointi.piirra(palvelupisteet);
				break;
			case DEP2:
				a = (Asiakas) palvelupisteet[1].otaJonosta();
				if (a.isUlkomaanlento()) {
					palvelupisteet[2].lisaaJonoon(a);
					visualisointi.piirra(palvelupisteet);
				} else {
					palvelupisteet[4].lisaaJonoon(a);
					visualisointi.piirra(palvelupisteet);
				}
				break;
			case DEP3:
				a = (Asiakas) palvelupisteet[2].otaJonosta();
				palvelupisteet[3].lisaaJonoon(a);
				visualisointi.piirra(palvelupisteet);
				break;
			case DEP4:
				a = (Asiakas) palvelupisteet[3].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				visualisointi.piirra(palvelupisteet);
				break;
			case DEP5:
				a = (Asiakas) palvelupisteet[4].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				visualisointi.piirra(palvelupisteet);
				break;
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
		// System.out.println("Tulokset ... puuttuvat vielä");
		System.out.println(
				"Koko järjestelmässä palvellut asiakkaat: " + (palvelupisteet[3].getPalvelupisteessaPalvellutAsiakkaat()
						+ palvelupisteet[4].getPalvelupisteessaPalvellutAsiakkaat()));
		System.out.println("Koko järjestelmän palveluaika: " + Palvelupiste.getKokoJarjestelmanPalveluAika());
		for (int i = 0; i < palvelupisteet.length; i++) {
			System.out.println("Palvelupiste " + (i + 1) + " palvellut asiakkaat: "
					+ palvelupisteet[i].getPalvelupisteessaPalvellutAsiakkaat());
			System.out.println(
					"Palvelupiste " + (i + 1) + " palveluaika: " + palvelupisteet[i].getPalvelupisteenPalveluAika());
			System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
			if (kaikkiAsiakkaatValmiit) {
				System.out.println("Kaikki asiakkaat ovat kulkeneet läpi.");
			} else {
				System.out.println("Kaikki asiakkaat eivät ole vielä kulkeneet läpi.");
			}
		}
	}
}