package simu.model;

import simu.framework.ITapahtumanTyyppi;

/*  ARR1 = Saapuva asiakas ulkomaalle, ARR2 = Saapuva asiakas sisälennolle, DEP1 = Lähtöselvitys poistuminen, DEP2 = Turvatarkastus poistuminen
	DEP3 = Passintarkistus poistuminen, DEP4 = Lähtöportti kotimaa, DEP5 = Lähtöportti ulkomaa,
	ULKO = Ulkomaanlento, SISA = Kotimaanlento
*/

public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	ULKO, SISA, ARR1, ARR2, DEP1, DEP2, DEP3, DEP4, DEP5;
}