package simu.model;

import java.time.LocalDate;

import simu.eduni.distributions.Normal;
import simu.framework.Kello;
import simu.framework.Moottori;
import simu.framework.Saapumisprosessi;
import simu.framework.Tapahtuma;
import simu.view.Kontrolleri;
import simu.entity.*;

/** Moottori-luokka, joka sisältää simulaation päälogiikan */
public class OmaMoottori extends Moottori {

	/** Tapahtumalista, joka sisältää kaikki tapahtumat */
	private Saapumisprosessi saapumisprosessi;
	/** Palvelupisteet, joissa tapahtuu palvelua */
	private Palvelupiste[] palvelupisteet;
	/** Tapahtumalista, joka sisältää kaikki tapahtumat */
	private boolean kaikkiAsiakkaatValmiit = false; // Lisätty lippu seuraamaan, ovatko kaikki asiakkaat valmiita
	/** Tulokset */
	private Tulokset tulokset;

	/** Konstruktori, joka luo uuden moottorin */
	public OmaMoottori(Kontrolleri kontrolleri) {
		super(kontrolleri);

		palvelupisteet = new Palvelupiste[5];

		// Lähtöselvitys
		palvelupisteet[0] = new Palvelupiste(700, 533, "LS", kontrolleri.getLahtoselvitysMaara(),
				new Normal(kontrolleri.getLSpalveluNopeus(), kontrolleri.getLahtoselvitysVar()), tapahtumalista,
				TapahtumanTyyppi.DEP1);
		// Turvatarkastus
		palvelupisteet[1] = new Palvelupiste(590, 360, "TT", kontrolleri.getTurvatarkastusMaara(),
				new Normal(kontrolleri.getTTpalveluNopeus(), kontrolleri.getTurvatarkastusVar()), tapahtumalista,
				TapahtumanTyyppi.DEP2);
		// Passintarkistus
		palvelupisteet[2] = new Palvelupiste(880, 195, "PT", kontrolleri.getPassintarkastusMaara(),
				new Normal(kontrolleri.getPTpalveluNopeus(), kontrolleri.getPassintarkastusVar()), tapahtumalista,
				TapahtumanTyyppi.DEP3);
		// Lähtöportti kotimaanlennot
		palvelupisteet[3] = new Palvelupiste(300, 45, "T1", 1,
				new Normal(kontrolleri.getKotimaaKA(), kontrolleri.getKotimaaVar()), tapahtumalista,
				TapahtumanTyyppi.DEP4);
		// Lähtöportti ulkomaanlennot
		palvelupisteet[4] = new Palvelupiste(830, 45, "T2", 1,
				new Normal(kontrolleri.getUlkomaaKA(), kontrolleri.getUlkomaaVar()), tapahtumalista,
				TapahtumanTyyppi.DEP5);
		// Saapumisprosessi
		saapumisprosessi = new Saapumisprosessi(tapahtumalista, TapahtumanTyyppi.ULKO, kontrolleri.getLentojenVali(),
				kontrolleri.getLentojenVali2());
	}

	/** Palauttaa kaikki palvelupisteet */
	public Palvelupiste[] getPalvelupisteet() {
		return palvelupisteet;
	}

	@Override
	/** Alustaa simulaation */
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava(); // Asetetaan ensimmäinen saapuminen järjestelmään
	}

	@Override
	/** Suorittaa tapahtuman */
	protected void suoritaTapahtuma(Tapahtuma t) {
		Asiakas a;
		switch ((TapahtumanTyyppi) t.getTyyppi()) {
			case ARR1:
				a = new Asiakas(TapahtumanTyyppi.ARR1);
				a.setUlkomaanlento();
				palvelupisteet[0].lisaaJonoon(a);
				break;
			case ARR2:
				a = new Asiakas(TapahtumanTyyppi.ARR2);
				palvelupisteet[0].lisaaJonoon(a);
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
					palvelupisteet[3].lisaaJonoon(a);

				}
				break;
			case DEP3:
				a = (Asiakas) palvelupisteet[2].otaJonosta();
				palvelupisteet[4].lisaaJonoon(a);
				break;
			case DEP4:
				a = (Asiakas) palvelupisteet[3].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				Asiakas.lennolleEhtineet++;
				Asiakas.i++;
				break;
			case DEP5:
				a = (Asiakas) palvelupisteet[4].otaJonosta();
				a.setPoistumisaika(Kello.getInstance().getAika());
				a.raportti();
				Asiakas.lennolleEhtineet++;
				Asiakas.i++;
				break;
			case ULKO:
				// Poistetaan jonoista kaikki ulkomaanlentojen asiakkaat
				for (Palvelupiste palvelupiste : palvelupisteet) {
					palvelupiste.removeAsiakasARR1();
				}
				// Poistetaan tapahtumalistan "Ulkomaalentojen"-tapahtuma
				tapahtumalista.removeUlkomaanTapahtumat();
				break;
			case SISA:
				// Poistetaan jonoista kaikki kotimaanlentojen asiakkaat
				for (Palvelupiste palvelupiste : palvelupisteet) {
					palvelupiste.removeAsiakasARR2();
				}
				// Poistetaan tapahtumalistan "Sisälentojen"-tapahtuma
				tapahtumalista.removeKotimaanTapahtumat();
				break;
		}
	}

	@Override
	/** Yrittää aloittaa palvelun */
	protected void yritaCTapahtumat() {
		for (Palvelupiste p : palvelupisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			}
		}
	}

	@Override
	/** Tarkistaa, onko kaikki asiakkaat valmiita */
	protected void tulokset() {

		System.out.println("\nSimulointi päättyi kello " + Kello.getInstance().getAika());

		// Laske ja näytä mittareita jokaiselle Palvelupisteelle
		for (int i = 0; i < palvelupisteet.length; i++) {
			Palvelupiste p = palvelupisteet[i];

			// Laske ja näytä nykyisen Palvelupisteen mittarit
			System.out.println("Tulokset Palvelupisteelle " + p.getNimi() + ":");

			// Laske ja näytä keskimääräinen odotusaika;
			System.out.println("Keskimääräinen odotusaika: " + p.getJonotusaika());

			// Laske ja näytä palvelun tehokkuus;
			System.out.println("Palvelutehokkuus: " + p.getSuoritusteho() + " %");

			// Palvelupisteiden käyttöasteen laskeminen ja näyttö;
			System.out
					.println("Palvelupisteen käyttöaste: " + p.getKayttoaste() + " %");

			// Lisämittareita voi tarvittaessa laskea täältä...

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

	// Asetetaan tulokset
	@Override
	/** Asettaa tulokset */
	public void asetaTulokset() {
		LSTulos lsTulos = null;
		TTTulos ttTulos = null;
		PTTulos ptTulos = null;
		T1Tulos t1Tulos = null;
		T2Tulos t2Tulos = null;
		Tulokset simunTulokset = null;

		for (Palvelupiste p : palvelupisteet) {

			if (p.getNimi().equals("LS")) {
				p.asetaPalvelupisteenTulokset(p, getSimulointiaika());
				lsTulos = new LSTulos(p.getKayttoaste(), p.getSuoritusteho(), p.getJonotusaika(), p.getJononPituus(),
						p.getMaara());
			} else if (p.getNimi().equals("PT")) {
				p.asetaPalvelupisteenTulokset(p, getSimulointiaika());
				ptTulos = new PTTulos(p.getKayttoaste(), p.getSuoritusteho(), p.getJonotusaika(), p.getJononPituus(),
						p.getMaara());
			} else if (p.getNimi().equals("TT")) {
				p.asetaPalvelupisteenTulokset(p, getSimulointiaika());
				ttTulos = new TTTulos(p.getKayttoaste(), p.getSuoritusteho(), p.getJonotusaika(), p.getJononPituus(),
						p.getMaara());
			} else if (p.getNimi().equals("T1")) {
				p.asetaPalvelupisteenTulokset(p, getSimulointiaika());
				t1Tulos = new T1Tulos(p.getKayttoaste(), p.getSuoritusteho(), p.getJonotusaika(), p.getJononPituus());
			} else if (p.getNimi().equals("T2")) {
				p.asetaPalvelupisteenTulokset(p, getSimulointiaika());
				t2Tulos = new T2Tulos(p.getKayttoaste(), p.getSuoritusteho(), p.getJonotusaika(), p.getJononPituus());
			}

			simunTulokset = new Tulokset(LocalDate.now(), getSimulointiaika(),
					Asiakas.i, Asiakas.lennolleEhtineet, Asiakas.T1myohastyneet + Asiakas.T2myohastyneet,
					Asiakas.T1myohastyneet,
					Asiakas.T2myohastyneet, lsTulos, ttTulos, ptTulos, t1Tulos, t2Tulos);

		}
		this.tulokset = simunTulokset;
	}

	// Tallennetaan tulokset tietokantaan
	/** Tallentaa tulokset tietokantaan */
	public void tallennaTulokset() {
		tuloksetDao.tallenna(this.tulokset);
	}

	// Tuloksien getteri käyttöliitymää varten
	/** Palauttaa tulokset */
	public Tulokset getTulokset() {
		return tulokset;
	}
}