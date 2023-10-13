package simu.view;

/** Interface, joka määrittelee Simulaattorin käyttöliittymän
 *
 *  Käyttöliittymä tarjoaa kontrollerille syötteitä, joita se välittää Moottorille
 *  Käyttöliittymä tarjoaa Moottorin tuloksia, joita kontrolleri välittää käyttöliittymälle
 *  Käyttöliittymä tarjoaa Moottorin visualisoinnin, jota kontrolleri välittää käyttöliittymälle
 *
 *  Käyttöliittymä on toteutettu JavaFX:llä
 *

 */
public interface ISimulaattorinUI {
	
	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	/** Kontrolleri välittää Moottorille käyttäjän syöttämän simulaatioajan
	 *
     */
	public double getAika();

	/** Kontrolleri välittää Moottorille käyttäjän syöttämän viiveen
	 *
	 */
	public long getViive();
	
	//Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa
	/** Moottori välittää käyttöliittymälle tuloksia, joita kontrolleri välittää Moottorilta
	 *
	 */
	public void setLoppuaika(double aika);
	
	// Moottori tarvitusee
	/** Moottori välittää käyttöliittymälle visualisoinnin, jota kontrolleri välittää Moottorilta
	 *
	 */
	public IVisualisointi getVisualisointi();

}
