package simu.model;

import simu.framework.ITapahtumanTyyppi;

/*  ARR1, = Saapuva asiakas DEP1 = Lähtöselvitys poistuminen, DEP2 = Turvatarkastus poistuminen 
	DEP3 = Passintarkistus poistuminen, DEP4 = Lähtöportti ulkomaat, DEP5 = Lähtöportti kotimaa
*/

public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	ARR1, DEP1, DEP2, DEP3, DEP4, DEP5;
}
