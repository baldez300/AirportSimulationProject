package simu.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SimulaattorinGUI extends Application {

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
