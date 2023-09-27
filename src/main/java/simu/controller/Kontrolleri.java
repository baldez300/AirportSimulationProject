package simu.controller;

import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalDate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.OmaMoottori;

public class Kontrolleri {
    // Oletusarvot asetuksille jotta pysyvät muistissa
    private static double simulointiAika = 1440;
    private static int simulointiViive = 100;

    @FXML
    private VBox AsetuksetSivu;

    @FXML
    private Text LSJononPituus;

    @FXML
    private Text LSjonotusaika;

    @FXML
    private Text LSkayttoaste;

    @FXML
    private Text LSsuoritusteho;

    @FXML
    private Text PTJononPituus;

    @FXML
    private Text PTJonotusaika;

    @FXML
    private Text PTKayttoaste;

    @FXML
    private Text PTSuoritusteho;

    @FXML
    private Text T1JononPituus;

    @FXML
    private Text T1Jonotusaika;

    @FXML
    private Text T1Kayttoaste;

    @FXML
    private Text T1Suoritusteho;

    @FXML
    private Text T2JononPituus;

    @FXML
    private Text T2Jonotusaika;

    @FXML
    private Text T2Kayttoaste;

    @FXML
    private Text T2Suoritusteho;

    @FXML
    private Text TTJononPituus;

    @FXML
    private Text TTJonotusaika;

    @FXML
    private Text TTKayttoaste;

    @FXML
    private Text TTSuoritusteho;

    @FXML
    private VBox TuloksetSivu;

    @FXML
    private Button aloitaNappi;

    @FXML
    private Canvas contentCanvas;

    @FXML
    private Button edellisetTulokset;

    @FXML
    private Text ehtineet;

    @FXML
    private Button hidastaNappi;

    @FXML
    private Text kaikkiAsiakkaat;

    @FXML
    private Text kokonaisAika;

    @FXML
    private Spinner<Integer> kotimaaKA;

    @FXML
    private Spinner<Integer> kotimaaVar;

    @FXML
    private Spinner<Integer> lahtoselvitysKA;

    @FXML
    private Spinner<Integer> lahtoselvitysMaara;

    @FXML
    private Spinner<Integer> lahtoselvitysVar;

    @FXML
    private Spinner<Integer> lentojenVali;

    @FXML
    private Button lopetaNappi;

    @FXML
    private Text myohastyneet;

    @FXML
    private Text myohastyneetT1;

    @FXML
    private Text myohastyneetT2;

    @FXML
    private Button nopeutaNappi;

    @FXML
    private Spinner<Integer> passintarkastusKA;

    @FXML
    private Spinner<Integer> passintarkastusMaara;

    @FXML
    private Spinner<Integer> passintarkastusVar;

    @FXML
    private Text pvm;

    @FXML
    private VBox simulaatioSivu;

    @FXML
    private Spinner<Double> simulaationAika;

    @FXML
    private Spinner<Integer> simulaationViive;

    @FXML
    private Button tallennaNappi;

    @FXML
    private Spinner<Integer> turvatarkastusKA;

    @FXML
    private Spinner<Integer> turvatarkastusMaara;

    @FXML
    private Spinner<Integer> turvatarkastusVar;

    @FXML
    private Spinner<Integer> ulkomaaKA;

    @FXML
    private Spinner<Integer> ulkomaaVar;

    @FXML
    private Button uusiNappi;

    private static IMoottori moottori;

    // Asetetaan oletusarvot spinnereille
    @FXML
    void initialize() {
        Trace.setTraceLevel(Level.INFO);
        // Spinnrien arvojen asettaminen
        SpinnerValueFactory<Integer> lahtoSelvitysSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                1);
        lahtoselvitysMaara.setValueFactory(lahtoSelvitysSpinner);
        SpinnerValueFactory<Integer> passintarkastusSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                1);
        passintarkastusMaara.setValueFactory(passintarkastusSpinner);
        SpinnerValueFactory<Integer> turvatarkastusSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                1);
        turvatarkastusMaara.setValueFactory(turvatarkastusSpinner);
        SpinnerValueFactory<Integer> LahtoselvitysSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                3);
        lahtoselvitysVar.setValueFactory(LahtoselvitysSpinner);
        SpinnerValueFactory<Integer> passintarkastusVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                20, 3);
        passintarkastusVar.setValueFactory(passintarkastusVarSpinner);
        SpinnerValueFactory<Integer> turvatarkastusVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                20, 3);
        turvatarkastusVar.setValueFactory(turvatarkastusVarSpinner);
        SpinnerValueFactory<Integer> kotimaaVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 3);
        kotimaaVar.setValueFactory(kotimaaVarSpinner);
        SpinnerValueFactory<Integer> ulkomaaVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 3);
        ulkomaaVar.setValueFactory(ulkomaaVarSpinner);
        SpinnerValueFactory<Integer> kotimaaKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
        kotimaaKA.setValueFactory(kotimaaKASpinner);
        SpinnerValueFactory<Integer> ulkomaaKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 5);
        ulkomaaKA.setValueFactory(ulkomaaKASpinner);
        SpinnerValueFactory<Integer> passintarkastusKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                100, 15);
        passintarkastusKA.setValueFactory(passintarkastusKASpinner);
        SpinnerValueFactory<Integer> turvatarkastusKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                100, 20);
        turvatarkastusKA.setValueFactory(turvatarkastusKASpinner);
        SpinnerValueFactory<Integer> lahtoselvitysKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,
                15);
        lahtoselvitysKA.setValueFactory(lahtoselvitysKASpinner);
        SpinnerValueFactory<Double> simulaationAikaSpinner = new SpinnerValueFactory.DoubleSpinnerValueFactory(1,
                Double.MAX_VALUE, simulointiAika);
        simulaationAika.setValueFactory(simulaationAikaSpinner);
        SpinnerValueFactory<Integer> simulaationViiveSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                2000, simulointiViive);
        simulaationViive.setValueFactory(simulaationViiveSpinner);
        SpinnerValueFactory<Integer> lentojenValiSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(120, 240,
                120);
        lentojenVali.setValueFactory(lentojenValiSpinner);
        // Asetetaan päivämäärä
        pvm.setText(LocalDate.now().toString());

        hidastaNappi.setOnAction(e -> {
            hidasta();
        });

        nopeutaNappi.setOnAction(e -> {
            nopeuta();
        });

        lopetaNappi.setOnAction(e -> {
            lopetaSaie();
        });
    }

    @FXML
    void aloita(ActionEvent event) {
        Kello.getInstance().setAika(0);
        moottori = new OmaMoottori(this);
        simulointiAika = simulaationAika.getValue();
        moottori.setSimulointiaika(simulointiAika);
        moottori.setViive(simulaationViive.getValue());
        AsetuksetSivu.setVisible(false);
        simulaatioSivu.setVisible(true);
        ((Thread) moottori).start();

        // Suljetaan ohjelma jos ikkuna suljetaan
        Stage stage = (Stage) simulaatioSivu.getScene().getWindow();
        stage.setOnCloseRequest((WindowEvent e) -> {
            Platform.exit();
            System.exit(0);
        });

        // Timer tarkistamaan onko simulointi loppunut
        // Jos on niin siirtyy tulokset näkymään
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // Check the condition
                if (Kello.getInstance().getAika() >= simulointiAika) {
                    // Update the UI on the JavaFX application thread
                    simulaatioSivu.setVisible(false);
                    TuloksetSivu.setVisible(true);
                    timer.cancel();
                }
            }
        }, 0, 1000); // Execute every 1000 milliseconds (1 second)
    }

    @FXML
    void naytaTulokset(ActionEvent event) {
        // TODO: Toteuta tulosten haku databasesta ja näyttäminen
    }

    @FXML
    void tallenna(ActionEvent event) {
        // TODO: Tallenna tulokset databaseen
    }

    @FXML
    void uusiSimulointi(ActionEvent event) {
        ((Thread) moottori).interrupt();
        TuloksetSivu.setVisible(false);
        AsetuksetSivu.setVisible(true);
    }

    public void lopetaSaie() {
        ((Thread) moottori).interrupt();
        simulaatioSivu.setVisible(false);
        AsetuksetSivu.setVisible(true);
    }

    protected void hidasta() {
        moottori.setViive((long) (moottori.getViive() * 1.10));
    }

    protected void nopeuta() {
        moottori.setViive((long) (moottori.getViive() * 0.9));
    }

    public Canvas getCanvas() {
        return contentCanvas;
    }

    // Getters for settings

    /*
     * Tämä palauttaa lähtöselvityksen keskiarvon jaettuna pisteiden määrällä
     * joka on keskimääräinen palvelunopeus
     */
    public int getLSpalveluNopeus() {
        return lahtoselvitysKA.getValue() / lahtoselvitysMaara.getValue();
    }

    public int getLahtoselvitysMaara() {
        return lahtoselvitysMaara.getValue();
    }

    public int getLahtoselvitysVar() {
        return lahtoselvitysVar.getValue();
    }

    public int getPTpalveluNopeus() {
        return passintarkastusKA.getValue() / passintarkastusMaara.getValue();
    }

    public int getPassintarkastusMaara() {
        return passintarkastusMaara.getValue();
    }

    public int getPassintarkastusVar() {
        return passintarkastusVar.getValue();
    }

    public int getTTpalveluNopeus() {
        return turvatarkastusKA.getValue() / turvatarkastusMaara.getValue();
    }

    public int getTurvatarkastusMaara() {
        return turvatarkastusMaara.getValue();
    }

    public int getTurvatarkastusVar() {
        return turvatarkastusVar.getValue();
    }

    public int getKotimaaVar() {
        return kotimaaVar.getValue();
    }

    public int getKotimaaKA() {
        return kotimaaKA.getValue();
    }

    public int getUlkomaaVar() {
        return ulkomaaVar.getValue();
    }

    public int getUlkomaaKA() {
        return ulkomaaKA.getValue();
    }

    public int getLentojenVali() {
        return lentojenVali.getValue();
    }

    public double getSimulointiAika() {
        return simulaationAika.getValue();
    }

    public int getSimulointiViive() {
        return simulaationViive.getValue();
    }
}
