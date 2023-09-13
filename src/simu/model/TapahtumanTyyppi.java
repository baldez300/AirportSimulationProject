package simu.model;

import simu.framework.ITapahtumanTyyppi;

/*  ARR1, = Lähtöselvitys (kotimaanlennot), ARR2 = Lähtöselvitys (ulkomaanlennot) 
	DEP1 = Lähtöselvitys postuminen, DEP2 = Turvatarkastus poistuminen 
	DEP3 = Passintarkistus poistuminen, DEP4 = Boarding kotimaa, DEP5 = Boarding ulkomaat
*/

public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	ARR1, ARR2, DEP1, DEP2, DEP3, DEP4, DEP5;
}
