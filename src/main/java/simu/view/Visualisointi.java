package simu.view;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import simu.model.Asiakas;
import simu.model.Palvelupiste;
import simu.framework.Kello;

/** Luokka joka visualisoi simulaatiota */
public class Visualisointi {

	/** Luokan attribuutit */
	private final GraphicsContext gc;
	private final Canvas canvas;
	private final Image taustakuva;
	private final int taustakuvaLeveys = 1585, taustakuvaKorkeus = 996;
	private final Kontrolleri kontrolleri;

	/** Luokan konstruktori */
	public Visualisointi(Kontrolleri kontrolleri, Canvas canvas) {
		this.kontrolleri = kontrolleri;
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		this.taustakuva = new Image("file:src/main/resources/ap_blueprint.png");
	}

	/** Metodi joka tyhjentää näytön */
	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}

	/** Metodi joka aloittaa visualisoinnin */
	public void aloitaVisualisointi() {
		// Tehdään visualisointi Timerilla
		AnimationTimer timer = new AnimationTimer() {
			@Override
			// long now tarkoittaa nanosekunteja joka annetaan metodille parametrina koska
			// se on abstrakti metodi ja se pitää ylikirjoittaa
			/**
			 * Metodi joka piirtää kuvan näytölle
			 * @param now
			 */
			public void handle(long now) {
				viive(kontrolleri.getMoottorinViive());
				tyhjennaNaytto();
				// Piirretään taustakuva
				gc.drawImage(taustakuva, 0, 0, taustakuvaLeveys, taustakuvaKorkeus, 0, 0, canvas.getWidth(),
						canvas.getHeight());

				// Piireteään kellonaika
				gc.setFont(Font.font("Arial", FontWeight.BOLD, 28)); // Fonttikoko 28
				gc.setFill(Color.BLACK); // Fontin väri musta
				String s = String.format("%05d", (int)Kello.getInstance().getAika()); // Aika muotoillaan kokonaisluvuksi
				gc.fillText("Aika: ",10,50);
				gc.fillText(s,30,80);
				
				// Haetaan palvelupisteet
				Palvelupiste[] palvelupisteet = kontrolleri.getPalvelupisteet();

				for (Palvelupiste p : palvelupisteet) {
					// Piirretään tietoa palvelupisteistä
					p.piirra(gc);

					// Piirretään asiakkaat jonoihin ja riveihin
					int suunta = (p.getNimi().equals("LS") || p.getNimi().equals("PT")) ? -1
							: 1;
					int rivi = 0;
					int vaihdaRivia = 2;
					int jono = 0;
					for (Asiakas asiakas : p.getAsiakasJono()) {
						int offsetSuunta = (Math.random() > 0.5) ? -1 : 1;
						double destX = p.getX() + (jono * (asiakas.getWidth() + 5) * suunta) + (offsetSuunta * Math.random() * 5);
						double destY = p.getY() + (rivi * (asiakas.getHeight() + 5) + (offsetSuunta * Math.random() * 5));
						asiakas.piirra(gc, destX, destY);
						rivi++;
						if (rivi >= vaihdaRivia) {
							rivi = 0;
							jono++;
						}
					}
				}
				// Jos moottori thread on pysäytetty, pysäytetään myös visualisointi
				if (!kontrolleri.onHengissa())
					this.stop();
			}
		};
		timer.start();
	}

	/** Metodi joka viivästää simulaatiota */
		private void viive(long aika) { // UUSI
		try {
			Thread.sleep(aika);
		} catch (InterruptedException e) {
			kontrolleri.lopetaSaie();
		}
	}
}
