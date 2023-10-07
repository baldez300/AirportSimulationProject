package simu.view;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.shape.Circle;
import simu.dao.TuloksetDao;
import simu.datasource.SQLconnection;
import simu.entity.LSTulos;
import simu.entity.PTTulos;
import simu.entity.T1Tulos;
import simu.entity.T2Tulos;
import simu.entity.TTTulos;
import simu.entity.Tulokset;
import simu.framework.Moottori;
import simu.framework.IMoottori;
import simu.framework.Kello;
import simu.framework.Trace;
import simu.framework.Trace.Level;
import simu.model.Asiakas;
import simu.model.OmaMoottori;
import simu.model.Palvelupiste;

public class Kontrolleri {
    // Oletusarvot asetuksille jotta pysyvät muistissa
    private static double simulointiAika = 1000;
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
    private Text Tehtineet;

    @FXML
    private Text TkaikkiAsiakkaat;

    @FXML
    private Text TkokonaisAika;

    @FXML
    private Text Tmyohastyneet;

    @FXML
    private Text TmyohastyneetT1;

    @FXML
    private Text TmyohastyneetT2;

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
    private Spinner<Integer> lentojenVali2;

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
    private Button postuNappi;

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
    private VBox tallennetut;

    @FXML
    private Button tarkemmatTiedotNappi;

    @FXML
    private ListView<Tulokset> tuloksetLista;

    @FXML
    private Button tuloksetPoistu;

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

    @FXML
    private Circle tietokantaYhteysOnline;

    @FXML
    private Circle tietokantaYhteysOffline;

    @FXML
    private Text TT;
    @FXML
    private Text PT;
    @FXML
    private Text LS;

    private static IMoottori moottori;

    private TuloksetDao tuloksetDao = new TuloksetDao();

    private Visualisointi visualisointi;

    // Apumuuttujat
    private boolean tietokantaYhteys;
    private boolean tulosValittu;
    private boolean painettu;

    // Asetetaan oletusarvot spinnereille
    @FXML
    void initialize() {
        Trace.setTraceLevel(Level.INFO);
        // Spinnerien arvojen asettaminen
        SpinnerValueFactory<Integer> lahtoSelvitysSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                1);
        SpinnerValueFactory<Integer> lahtoselvitysKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,
                10);
        SpinnerValueFactory<Integer> lahtoselvitysVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                3);
        lahtoselvitysMaara.setValueFactory(lahtoSelvitysSpinner);
        lahtoselvitysKA.setValueFactory(lahtoselvitysKASpinner);
        lahtoselvitysVar.setValueFactory(lahtoselvitysVarSpinner);

        SpinnerValueFactory<Integer> passintarkastusSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                1);
        SpinnerValueFactory<Integer> passintarkastusKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                100, 15);
        SpinnerValueFactory<Integer> passintarkastusVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                20, 3);
        passintarkastusKA.setValueFactory(passintarkastusKASpinner);
        passintarkastusVar.setValueFactory(passintarkastusVarSpinner);
        passintarkastusMaara.setValueFactory(passintarkastusSpinner);

        SpinnerValueFactory<Integer> turvatarkastusSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                1);
        SpinnerValueFactory<Integer> turvatarkastusKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                100, 15);
        SpinnerValueFactory<Integer> turvatarkastusVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                15, 3);
        turvatarkastusMaara.setValueFactory(turvatarkastusSpinner);
        turvatarkastusVar.setValueFactory(turvatarkastusVarSpinner);
        turvatarkastusKA.setValueFactory(turvatarkastusKASpinner);

        SpinnerValueFactory<Integer> kotimaaKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10);
        SpinnerValueFactory<Integer> kotimaaVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        kotimaaVar.setValueFactory(kotimaaVarSpinner);
        kotimaaKA.setValueFactory(kotimaaKASpinner);

        SpinnerValueFactory<Integer> ulkomaaKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 10);
        SpinnerValueFactory<Integer> ulkomaaVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        ulkomaaVar.setValueFactory(ulkomaaVarSpinner);
        ulkomaaKA.setValueFactory(ulkomaaKASpinner);

        SpinnerValueFactory<Double> simulaationAikaSpinner = new SpinnerValueFactory.DoubleSpinnerValueFactory(1,
                Double.MAX_VALUE, simulointiAika);
        SpinnerValueFactory<Integer> simulaationViiveSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                2000, simulointiViive);
        simulaationAika.setValueFactory(simulaationAikaSpinner);
        simulaationViive.setValueFactory(simulaationViiveSpinner);

        SpinnerValueFactory<Integer> lentojenValiSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(300, 720,
                300);
        SpinnerValueFactory<Integer> lentojenVali2Spinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 240,
                10);
        lentojenVali.setValueFactory(lentojenValiSpinner);
        lentojenVali2.setValueFactory(lentojenVali2Spinner);

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

        /*
         * Asetataan Tuloksetlistalle kuuntelija joka päivittää tiedot kun valinta
         * muuttuu
         */
        tuloksetLista.getSelectionModel().selectedItemProperty().addListener(this::valintaMuuttui);

        visualisointi = new Visualisointi(this, contentCanvas);

        // Tarkistetaan tietokanta yhteys 3 sekunnin välein
        Timer tietokantaTimer = new Timer();
        tietokantaTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                testaaTietokantaYhteys();
                if (!painettu && tietokantaYhteys)
                    tallennaNappi.setDisable(false);
                else
                    tallennaNappi.setDisable(true);
                if (!tuloksetLista.getItems().isEmpty() && tietokantaYhteys && tulosValittu)
                    tarkemmatTiedotNappi.setDisable(false);
                else
                    tarkemmatTiedotNappi.setDisable(true);
                edellisetTulokset.setDisable(!tietokantaYhteys);
            }
        }, 0, 3000);
    }

    @FXML
    private void aloita(ActionEvent event) {
        // Resetoidaan kellon aika ja staattiset muuttujat
        Kello.getInstance().setAika(0);
        resetoiStaattisetMuuttujat();
        // Luodaan uusi moottori ja asetetaan sille asetukset
        moottori = new OmaMoottori(this);
        simulointiAika = simulaationAika.getValue();
        moottori.setSimulointiaika(simulointiAika);
        moottori.setViive(simulaationViive.getValue());
        Stage simulaationStage = (Stage) simulaatioSivu.getScene().getWindow();
        simulaationStage.setTitle("Simuloidaan...");
        AsetuksetSivu.setVisible(false);
        simulaatioSivu.setVisible(true);
        uusiNappi.setVisible(true);
        tuloksetPoistu.setVisible(false);
        ((Thread) moottori).start();
        visualisointi.aloitaVisualisointi();

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
                    asetaTallennetutTulokset(((OmaMoottori) moottori).getTulokset());
                    Platform.runLater(() -> {
                        Stage tuloksetStage = (Stage) TuloksetSivu.getScene().getWindow();
                        tuloksetStage.setTitle("Tulokset");
                    });
                    simulaatioSivu.setVisible(false);
                    TuloksetSivu.setVisible(true);
                    timer.cancel();
                }
            }
        }, 0, 1000); // Execute every 1000 milliseconds (1 second)
    }

    @FXML
    // Haetaan tulokset tietokannasta ja asetetaan ne listaan
    private void naytaTulokset(ActionEvent event) {
        List<Tulokset> tulokset = tuloksetDao.lataaKaikki();
        tuloksetLista.getItems().clear();
        for (Tulokset t : tulokset) {
            tuloksetLista.getItems().add(t);
        }
        Stage tallennetutStage = (Stage) tallennetut.getScene().getWindow();
        tallennetutStage.setTitle("Tallennetut tulokset");
        AsetuksetSivu.setVisible(false);
        tallennetut.setVisible(true);
    }

    @FXML
    // Haetaan tarkemmat tiedot tietokannasta valitun tuloksen id:n perusteella
    private void naytaTarkemmatTiedot(ActionEvent event) {
        try {
            Tulokset valittuTulos = tuloksetLista.getSelectionModel().getSelectedItem();
            HashMap<Object, Object> tarkemmatTiedot = tuloksetDao.lataaTarkemmatTiedot(valittuTulos.getId());
            asetaTallennetutTulokset(tarkemmatTiedot);
            Stage tuloksetStage = (Stage) TuloksetSivu.getScene().getWindow();
            tuloksetStage.setTitle("Tarkemmat tiedot");
            TuloksetSivu.setVisible(true);
            tallennetut.setVisible(false);
            tuloksetPoistu.setVisible(true);
            uusiNappi.setVisible(false);
            if (!painettu)
                tallennaNappi.setVisible(false);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void palaaAsetuksiin(ActionEvent event) {
        Stage asetuksetStage = (Stage) AsetuksetSivu.getScene().getWindow();
        tulosValittu = false;
        asetuksetStage.setTitle("Simulaation asetukset");
        tallennetut.setVisible(false);
        AsetuksetSivu.setVisible(true);
        tallennaNappi.setVisible(true);
    }

    @FXML
    private void palaaTallennettuihin(ActionEvent event) {
        Stage tallennetutStage = (Stage) tallennetut.getScene().getWindow();
        tallennetutStage.setTitle("Tallennetut tulokset");
        TuloksetSivu.setVisible(false);
        tallennetut.setVisible(true);
    }

    @FXML
    // Tallennetaan tulokset tietokantaan
    private void tallenna(ActionEvent event) {
        painettu = true;
        tallennaNappi.setDisable(true);
        ((OmaMoottori) moottori).tallennaTulokset();
    }

    @FXML
    private void uusiSimulointi(ActionEvent event) {
        ((Thread) moottori).interrupt();
        painettu = false;
        Stage asetuksetStage = (Stage) AsetuksetSivu.getScene().getWindow();
        asetuksetStage.setTitle("Simulaation asetukset");
        TuloksetSivu.setVisible(false);
        AsetuksetSivu.setVisible(true);
        tallennaNappi.setDisable(false);
    }

    public void lopetaSaie() {
        ((Thread) moottori).interrupt();
        Stage asetuksetStage = (Stage) AsetuksetSivu.getScene().getWindow();
        asetuksetStage.setTitle("Simulaation asetukset");
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

    public int getLentojenVali2() {
        return lentojenVali2.getValue();
    }

    public double getSimulointiAika() {
        return simulaationAika.getValue();
    }

    public int getSimulointiViive() {
        return simulaationViive.getValue();
    }

    protected Palvelupiste[] getPalvelupisteet() {
        return ((OmaMoottori) moottori).getPalvelupisteet();
    }

    protected boolean onHengissa() {
        return ((Moottori) moottori).isAlive();
    }

    protected long getMoottorinViive() {
        return ((OmaMoottori) moottori).getViive();
    }

    // Asetetaan tallennetut tulokset näkymään tulokset sivulle
    private void asetaTallennetutTulokset(HashMap<Object, Object> tuloksetMap) {
        pvm.setText(((Tulokset) tuloksetMap.get("SL")).getPaivamaara().toString());
        kokonaisAika.setText(String.format("%d h %02d min", (int) (((Tulokset) tuloksetMap.get("SL")).getAika() / 60),
                (int) (((Tulokset) tuloksetMap.get("SL")).getAika() % 60)));
        kaikkiAsiakkaat.setText(((Tulokset) tuloksetMap.get("SL")).getAsiakkaat() + " kpl");
        ehtineet.setText(((Tulokset) tuloksetMap.get("SL")).getLennolle_ehtineet() + " kpl");
        myohastyneet.setText(((Tulokset) tuloksetMap.get("SL")).getMyohastyneet_t1()
                + ((Tulokset) tuloksetMap.get("SL")).getMyohastyneet_t2() + " kpl");
        myohastyneetT1.setText(((Tulokset) tuloksetMap.get("SL")).getMyohastyneet_t1() + " kpl");
        myohastyneetT2.setText(((Tulokset) tuloksetMap.get("SL")).getMyohastyneet_t2() + " kpl");
        LSsuoritusteho.setText(String.format("%.2f", ((LSTulos) tuloksetMap.get("LS")).getSuoritusteho()) + " kpl/h");
        PTSuoritusteho.setText(String.format("%.2f", ((PTTulos) tuloksetMap.get("PT")).getSuoritusteho()) + " kpl/h");
        TTSuoritusteho.setText(String.format("%.2f", ((TTTulos) tuloksetMap.get("TT")).getSuoritusteho()) + " kpl/h");
        T1Suoritusteho.setText(String.format("%.2f", ((T1Tulos) tuloksetMap.get("T1")).getSuoritusteho()) + " kpl/h");
        T2Suoritusteho.setText(String.format("%.2f", ((T2Tulos) tuloksetMap.get("T2")).getSuoritusteho()) + " kpl/h");
        LSJononPituus.setText(String.format("%.2f", ((LSTulos) tuloksetMap.get("LS")).getJononpituus()) + " kpl");
        PTJononPituus.setText(String.format("%.2f", ((PTTulos) tuloksetMap.get("PT")).getJononpituus()) + " kpl");
        TTJononPituus.setText(String.format("%.2f", ((TTTulos) tuloksetMap.get("TT")).getJononpituus()) + " kpl");
        T1JononPituus.setText(String.format("%.2f", ((T1Tulos) tuloksetMap.get("T1")).getJononpituus()) + " kpl");
        T2JononPituus.setText(String.format("%.2f", ((T2Tulos) tuloksetMap.get("T2")).getJononpituus()) + " kpl");
        LSjonotusaika.setText(String.format("%.2f", ((LSTulos) tuloksetMap.get("LS")).getJonotusaika()) + " min");
        PTJonotusaika.setText(String.format("%.2f", ((PTTulos) tuloksetMap.get("PT")).getJonotusaika()) + " min");
        TTJonotusaika.setText(String.format("%.2f", ((TTTulos) tuloksetMap.get("TT")).getJonotusaika()) + " min");
        T1Jonotusaika.setText(String.format("%.2f", ((T1Tulos) tuloksetMap.get("T1")).getJonotusaika()) + " min");
        T2Jonotusaika.setText(String.format("%.2f", ((T2Tulos) tuloksetMap.get("T2")).getJonotusaika()) + " min");
        LSkayttoaste.setText(String.format("%.2f", ((LSTulos) tuloksetMap.get("LS")).getKayttoaste()) + " %");
        PTKayttoaste.setText(String.format("%.2f", ((PTTulos) tuloksetMap.get("PT")).getKayttoaste()) + " %");
        TTKayttoaste.setText(String.format("%.2f", ((TTTulos) tuloksetMap.get("TT")).getKayttoaste()) + " %");
        T1Kayttoaste.setText(String.format("%.2f", ((T1Tulos) tuloksetMap.get("T1")).getKayttoaste()) + " %");
        T2Kayttoaste.setText(String.format("%.2f", ((T2Tulos) tuloksetMap.get("T2")).getKayttoaste()) + " %");
        LS.setText(String.format("%d kpl", getLahtoselvitysMaara()));
        TT.setText(String.format("%d kpl", getTurvatarkastusMaara()));
        PT.setText(String.format("%d kpl", getPassintarkastusMaara()));
    }

    // Asetetaan tulokset näkymään viereseen Vboxiin kun valinta muuttuu
    private void valintaMuuttui(ObservableValue<? extends Tulokset> observable, Tulokset oldValue, Tulokset newValue) {
        // Asennetaan tulokset näkymään
        if (newValue != null) {
            tulosValittu = true;
            TkokonaisAika.setText(
                    String.format("%d h %02d min", (int) (newValue.getAika() / 60), (int) (newValue.getAika()) % 60));
            ;
            TkaikkiAsiakkaat.setText(newValue.getAsiakkaat() + " kpl");
            Tehtineet.setText(newValue.getLennolle_ehtineet() + " kpl");
            Tmyohastyneet.setText(newValue.getMyohastyneet_t1() + newValue.getMyohastyneet_t2() + " kpl");
            TmyohastyneetT1.setText(newValue.getMyohastyneet_t1() + " kpl");
            TmyohastyneetT2.setText(newValue.getMyohastyneet_t2() + " kpl");
        }
    }

    // Resetoidaan staattiset muuttujat
    private void resetoiStaattisetMuuttujat() {
        Asiakas.T1myohastyneet = 0;
        Asiakas.T2myohastyneet = 0;
        Asiakas.lennolleEhtineet = 0;
        Asiakas.i = 0;
        Palvelupiste.palvellutAsiakkaatTotal = 0;
    }

    private void testaaTietokantaYhteys() {
        try {
            SQLconnection.testaaTietokantaYhteys();
            tietokantaYhteysOnline.setVisible(true);
            tietokantaYhteysOffline.setVisible(false);
            tietokantaYhteys = true;
        } catch (Exception e) {
            tietokantaYhteys = false;
            tietokantaYhteysOffline.setVisible(true);
            tietokantaYhteysOnline.setVisible(false);
        }
    }
}
