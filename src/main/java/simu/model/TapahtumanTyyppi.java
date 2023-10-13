package simu.model;

import simu.framework.ITapahtumanTyyppi;

/*  ARR1 = Saapuva asiakas ulkomaalle, ARR2 = Saapuva asiakas sisalennolle, DEP1 = Lahtoselvitys poistuminen, DEP2 = Turvatarkastus poistuminen
	DEP3 = Passintarkistus poistuminen, DEP4 = Lahtoportti kotimaa, DEP5 = Lahtoportti ulkomaa,
	ULKO = Ulkomaanlento, SISA = Kotimaanlento
*/

/**
 * TapahtumanTyyppi enum maarittelee tapahtumien tyypit
 *
 * @see simu.framework.ITapahtumanTyyppi
 */
public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	/**
	 *  ulkomaan lennolle tapahtuma
	 */
	ULKO,
	/**
	 * kotimaan lennolle tapahtuma
	 */
	SISA,
	/**
	 * Saapuva asiakas ulkomaan lennolle
	 */
	ARR1,
	/**
	 * Saapuva asiakas kotiomaan lennolle
	 */
	ARR2,
	/**
	 * Lahtoselvitys poistuminen
	 */
	DEP1,
	/**
	 * Turvatarkastus poistuminen
	 */
	DEP2,
	/**
	 * Passintarkistus poistuminen
	 */
	DEP3,
	/**
	 * Lahtoportti kotimaa
	 */
	DEP4,
	/**
	 * Lahtoportti ulkomaa
	 */
	DEP5;
}