package simu.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/** Luokka, joka kaynnistaa simulaattorin graafisen kayttoliittyman.
 *  Luokka on simulaattorin kaynnistava luokka.
 */
public class SimulaattorinGUI extends Application {

    /** Metodi, joka kaynnistaa simulaattorin graafisen kayttoliittyman.
     *  @param stage Stage-olio, joka on simulaattorin graafisen kayttoliittyman ikkuna.
     */
    @Override
    public void start(Stage stage) {
        try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulaattori.fxml"));
        Parent root = fxmlLoader.load();
        root.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setTitle("Simulaation asetukset");
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
