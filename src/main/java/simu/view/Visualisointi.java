package simu.view;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;

import simu.model.Asiakas;
import simu.model.Palvelupiste;

public class Visualisointi {

	private final GraphicsContext gc;
	private final Canvas canvas;
	private final Image taustakuva;
	private final int taustakuvaLeveys = 1585, taustakuvaKorkeus = 996;
	private final Kontrolleri kontrolleri;

	public Visualisointi(Kontrolleri kontrolleri, Canvas canvas) {
		this.kontrolleri = kontrolleri;
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		this.taustakuva = new Image("file:src/main/resources/ap_blueprint.png");
	}

	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}

	public void aloitaVisualisointi() {
		// Tehdään visualisointi Timerilla
		AnimationTimer timer = new AnimationTimer() {
			@Override
			// long now tarkoittaa nanosekunteja joka annetaan metodille parametrina koska
			// se on abstrakti metodi ja se pitää ylikirjoittaa
			public void handle(long now) {
				tyhjennaNaytto();
				// Piirretään taustakuva
				gc.drawImage(taustakuva, 0, 0, taustakuvaLeveys, taustakuvaKorkeus, 0, 0, canvas.getWidth(),
						canvas.getHeight());
				// Haetaan palvelupisteet
				Palvelupiste[] palvelupisteet = kontrolleri.getPalvelupisteet();

				for (Palvelupiste p : palvelupisteet) {
					// Piirretään tietoa palvelupisteistä
					p.piirra(gc);

					// Piirretään asiakkaat jonoihin ja riveihin
					int suunta = (p.getNimi().equals("LS") || p.getNimi().equals("T2") || p.getNimi().equals("PT")) ? -1
							: 1;
					int rivi = 0;
					int vaihdaRivia = 4;
					int jono = 0;
					for (Asiakas asiakas : p.getAsiakasJono()) {
						double destX = p.getX() + (jono * (asiakas.getWidth() + 5) * suunta);
						double destY = p.getY() + (rivi * (asiakas.getHeight() + 5));
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
}
