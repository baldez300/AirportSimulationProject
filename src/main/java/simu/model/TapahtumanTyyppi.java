package simu.model;

import simu.framework.ITapahtumanTyyppi;

/*  ARR1 = Saapuva asiakas ulkomaalle, ARR2 = Saapuva asiakas sisälennolle, DEP1 = Lähtöselvitys poistuminen, DEP2 = Turvatarkastus poistuminen
	DEP3 = Passintarkistus poistuminen, DEP4 = Lähtöportti kotimaa, DEP5 = Lähtöportti ulkomaa,
	ULKO = Ulkomaanlento, SISA = Kotimaanlento
*/

/**
 * TapahtumanTyyppi enum määrittelee tapahtumien tyypit
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
	 * Lähtöselvitys poistuminen
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
	 * Lähtöportti kotimaa
	 */
	DEP4,
	/**
	 * Lähtöportti ulkomaa
	 */
	DEP5;
}