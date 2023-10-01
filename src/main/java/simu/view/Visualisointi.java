package simu.view;

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

	public Visualisointi(Canvas canvas) {
		this.canvas = canvas;
		gc = canvas.getGraphicsContext2D();
		this.taustakuva = new Image("file:src/main/resources/ap_blueprint.png");
	}

	public void tyhjennaNaytto() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
	}

	public void piirra(Palvelupiste[] palvelupisteet) {
		// Piirretään taustakuva
		gc.drawImage(taustakuva, 0, 0, taustakuvaLeveys, taustakuvaKorkeus, 0, 0, canvas.getWidth(),
				canvas.getHeight());

		// Piirretään asiakkaat palvelupisteiden jonoihin
		for (Palvelupiste p : palvelupisteet) {
			int suunta = (p.getNimi().equals("LS") || p.getNimi().equals("T2") || p.getNimi().equals("PT")) ? -1 : 1;
			int row = 0;
			int rowBreak = 4;
			int line = 0;

			p.piirra(gc);

			for (Asiakas asiakas : p.getAsiakasJono()) {
				double destX = p.getX() + (line * (asiakas.getWidth() + 5) * suunta);
				double destY = p.getY() + (row * (asiakas.getHeight() + 5));
				asiakas.piirra(gc, destX, destY);

				row++;
				if (row >= rowBreak) {
					row = 0;
					line++;
				}
			}
		}
	}
}
