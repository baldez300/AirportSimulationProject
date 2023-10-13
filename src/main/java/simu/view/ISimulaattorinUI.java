package simu.view;

/** Interface, joka maarittelee Simulaattorin kayttoliittyman
 *
 *  Kayttoliittyma tarjoaa kontrollerille syotteita, joita se valittaa Moottorille
 *  Kayttoliittyma tarjoaa Moottorin tuloksia, joita kontrolleri valittaa kayttoliittymalle
 *  Kayttoliittyma tarjoaa Moottorin visualisoinnin, jota kontrolleri valittaa kayttoliittymalle
 *
 *  Kayttoliittyma on toteutettu JavaFX:lla
 *

 */
public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syotteita, jotka se valittaa Moottorille
	/** Kontrolleri valittaa Moottorille kayttajan syottaman simulaatioajan
	 *
     */
	public double getAika();

	/** Kontrolleri valittaa Moottorille kayttajan syottaman viiveen
	 *
	 */
	public long getViive();
	
	//Kontrolleri antaa kayttoliittymalle tuloksia, joita Moottori tuottaa
	/** Moottori valittaa kayttoliittymalle tuloksia, joita kontrolleri valittaa Moottorilta
	 *
	 */
	public void setLoppuaika(double aika);
	
	// Moottori tarvitusee
	/** Moottori valittaa kayttoliittymalle visualisoinnin, jota kontrolleri valittaa Moottorilta
	 *
	 */
	public IVisualisointi getVisualisointi();

}
