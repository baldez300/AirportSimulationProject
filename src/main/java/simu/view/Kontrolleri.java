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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simu.dao.TuloksetDao;
import simu.entity.LSTulos;
import simu.entity.PTTulos;
import simu.entity.T1Tulos;
import simu.entity.T2Tulos;
import simu.entity.TTTulos;
import simu.entity.Tulokset;
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

    private static IMoottori moottori;

    private TuloksetDao tuloksetDao = new TuloksetDao();

    // Asetetaan oletusarvot spinnereille
    @FXML
    void initialize() {
        Trace.setTraceLevel(Level.INFO);
        // Spinnrien arvojen asettaminen
        SpinnerValueFactory<Integer> lahtoSelvitysSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                1);
        SpinnerValueFactory<Integer> lahtoselvitysKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100,
                5);
        SpinnerValueFactory<Integer> lahtoselvitysVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                3);
        lahtoselvitysMaara.setValueFactory(lahtoSelvitysSpinner);
        lahtoselvitysKA.setValueFactory(lahtoselvitysKASpinner);
        lahtoselvitysVar.setValueFactory(lahtoselvitysVarSpinner);

        SpinnerValueFactory<Integer> passintarkastusSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                1);
        SpinnerValueFactory<Integer> passintarkastusKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                100, 10);
        SpinnerValueFactory<Integer> passintarkastusVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                20, 3);
        passintarkastusKA.setValueFactory(passintarkastusKASpinner);
        passintarkastusVar.setValueFactory(passintarkastusVarSpinner);
        passintarkastusMaara.setValueFactory(passintarkastusSpinner);

        SpinnerValueFactory<Integer> turvatarkastusSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20,
                1);
        SpinnerValueFactory<Integer> turvatarkastusKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                100, 9);
        SpinnerValueFactory<Integer> turvatarkastusVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                20, 3);
        turvatarkastusMaara.setValueFactory(turvatarkastusSpinner);
        turvatarkastusVar.setValueFactory(turvatarkastusVarSpinner);
        turvatarkastusKA.setValueFactory(turvatarkastusKASpinner);

        SpinnerValueFactory<Integer> kotimaaKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 5);
        SpinnerValueFactory<Integer> kotimaaVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        kotimaaVar.setValueFactory(kotimaaVarSpinner);
        kotimaaKA.setValueFactory(kotimaaKASpinner);

        SpinnerValueFactory<Integer> ulkomaaKASpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 5);
        SpinnerValueFactory<Integer> ulkomaaVarSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 1);
        ulkomaaVar.setValueFactory(ulkomaaVarSpinner);
        ulkomaaKA.setValueFactory(ulkomaaKASpinner);

        SpinnerValueFactory<Double> simulaationAikaSpinner = new SpinnerValueFactory.DoubleSpinnerValueFactory(1,
                Double.MAX_VALUE, simulointiAika);
        SpinnerValueFactory<Integer> simulaationViiveSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
                2000, simulointiViive);
        simulaationAika.setValueFactory(simulaationAikaSpinner);
        simulaationViive.setValueFactory(simulaationViiveSpinner);

        SpinnerValueFactory<Integer> lentojenValiSpinner = new SpinnerValueFactory.IntegerSpinnerValueFactory(60, 240,
                60);
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
    }

    @FXML
    void aloita(ActionEvent event) {
        // Resetoidaan kellon aika ja staattiset muuttujat
        Kello.getInstance().setAika(0);
        resetoiStaattisetMuuttujat();
        // Luodaan uusi moottori ja asetetaan sille asetukset
        moottori = new OmaMoottori(this);
        simulointiAika = simulaationAika.getValue();
        moottori.setSimulointiaika(simulointiAika);
        moottori.setViive(simulaationViive.getValue());
        AsetuksetSivu.setVisible(false);
        simulaatioSivu.setVisible(true);
        uusiNappi.setVisible(true);
        tallennaNappi.setVisible(true);
        tuloksetPoistu.setVisible(false);
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
                    asetaTallennetutTulokset(((OmaMoottori) moottori).getTulokset());
                    simulaatioSivu.setVisible(false);
                    TuloksetSivu.setVisible(true);
                    timer.cancel();
                }
            }
        }, 0, 1000); // Execute every 1000 milliseconds (1 second)
    }

    @FXML
    // Haetaan tulokset tietokannasta ja asetetaan ne listaan
    void naytaTulokset(ActionEvent event) {
        List<Tulokset> tulokset = tuloksetDao.lataaKaikki();
        tuloksetLista.getItems().clear();
        for (Tulokset t : tulokset) {
            tuloksetLista.getItems().add(t);
        }
        AsetuksetSivu.setVisible(false);
        tallennetut.setVisible(true);
    }

    @FXML
    // Haetaan tarkemmat tiedot tietokannasta valitun tuloksen id:n perusteella
    void naytaTarkemmatTiedot(ActionEvent event) {
        try {
        Tulokset valittuTulos = tuloksetLista.getSelectionModel().getSelectedItem();
        HashMap<Object, Object> tarkemmatTiedot = tuloksetDao.lataaTarkemmatTiedot(valittuTulos.getId());
        asetaTallennetutTulokset(tarkemmatTiedot);
        TuloksetSivu.setVisible(true);
        tallennetut.setVisible(false);
        tuloksetPoistu.setVisible(true);
        uusiNappi.setVisible(false);
        tallennaNappi.setVisible(false);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void palaaAsetuksiin(ActionEvent event) {
        tallennetut.setVisible(false);
        AsetuksetSivu.setVisible(true);
    }

    @FXML
    void palaaTallennettuihin(ActionEvent event) {
        TuloksetSivu.setVisible(false);
        tallennetut.setVisible(true);
    }

    @FXML
    // Tallennetaan tulokset tietokantaan
    void tallenna(ActionEvent event) {
        tallennaNappi.setDisable(true);
        ((OmaMoottori) moottori).tallennaTulokset();
    }

    @FXML
    void uusiSimulointi(ActionEvent event) {
        ((Thread) moottori).interrupt();
        TuloksetSivu.setVisible(false);
        AsetuksetSivu.setVisible(true);
        tallennaNappi.setDisable(false);
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

    public int getLentojenVali2() {
        return lentojenVali2.getValue();
    }

    public double getSimulointiAika() {
        return simulaationAika.getValue();
    }

    public int getSimulointiViive() {
        return simulaationViive.getValue();
    }

    // Esimerkki Baldelle jatka tästä...
    public void asetaTulokset(Palvelupiste[] palvelupisteet) {
        for (Palvelupiste p : palvelupisteet) {
            pvm.setText(LocalDate.now().toString());
            kokonaisAika.setText(simulointiAika + " min");
            kaikkiAsiakkaat.setText(Asiakas.i + " kpl");
            ehtineet.setText(Asiakas.lennolleEhtineet + " kpl");
            myohastyneet.setText(Asiakas.T1myohastyneet + Asiakas.T2myohastyneet + " kpl");
            myohastyneetT1.setText(Asiakas.T1myohastyneet + " kpl");
            myohastyneetT2.setText(Asiakas.T2myohastyneet + " kpl");
            if (p.getNimi().equals("LS")) {
                LSsuoritusteho.setText(String.format("%.2f", p.getSuoritusteho()));
            } else if (p.getNimi().equals("PT")) {
                PTSuoritusteho.setText(String.format("%.2f", p.getSuoritusteho()));
            } else if (p.getNimi().equals("TT")) {
                TTSuoritusteho.setText(String.format("%.2f", p.getSuoritusteho()));
            } else if (p.getNimi().equals("T1")) {
                T1Suoritusteho.setText(String.format("%.2f", p.getSuoritusteho()));
            } else if (p.getNimi().equals("T2")) {
                T2Suoritusteho.setText(String.format("%.2f", p.getSuoritusteho()));
            }
        }
    }

    // Asetetaan tallennetut tulokset näkymään tulokset sivulle
    public void asetaTallennetutTulokset(HashMap<Object, Object> tuloksetMap) {
        pvm.setText(((Tulokset) tuloksetMap.get("SL")).getPaivamaara().toString());
        kokonaisAika.setText(((Tulokset) tuloksetMap.get("SL")).getAika() + " min");
        kaikkiAsiakkaat.setText(((Tulokset) tuloksetMap.get("SL")).getAsiakkaat() + " kpl");
        ehtineet.setText(((Tulokset) tuloksetMap.get("SL")).getLennolle_ehtineet() + " kpl");
        myohastyneet.setText(((Tulokset) tuloksetMap.get("SL")).getMyohastyneet_t1()
                + ((Tulokset) tuloksetMap.get("SL")).getMyohastyneet_t2() + " kpl");
        myohastyneetT1.setText(((Tulokset) tuloksetMap.get("SL")).getMyohastyneet_t1() + " kpl");
        myohastyneetT2.setText(((Tulokset) tuloksetMap.get("SL")).getMyohastyneet_t2() + " kpl");
        LSsuoritusteho.setText(String.format("%.2f", ((LSTulos) tuloksetMap.get("LS")).getSuoritusteho()));
        PTSuoritusteho.setText(String.format("%.2f", ((PTTulos) tuloksetMap.get("PT")).getSuoritusteho()));
        TTSuoritusteho.setText(String.format("%.2f", ((TTTulos) tuloksetMap.get("TT")).getSuoritusteho()));
        T1Suoritusteho.setText(String.format("%.2f", ((T1Tulos) tuloksetMap.get("T1")).getSuoritusteho()));
        T2Suoritusteho.setText(String.format("%.2f", ((T2Tulos) tuloksetMap.get("T2")).getSuoritusteho()));
        LSJononPituus.setText(String.format("%.2f", ((LSTulos) tuloksetMap.get("LS")).getJononpituus()));
        PTJononPituus.setText(String.format("%.2f", ((PTTulos) tuloksetMap.get("PT")).getJononpituus()));
        TTJononPituus.setText(String.format("%.2f", ((TTTulos) tuloksetMap.get("TT")).getJononpituus()));
        T1JononPituus.setText(String.format("%.2f", ((T1Tulos) tuloksetMap.get("T1")).getJononpituus()));
        T2JononPituus.setText(String.format("%.2f", ((T2Tulos) tuloksetMap.get("T2")).getJononpituus()));
        LSjonotusaika.setText(String.format("%.2f", ((LSTulos) tuloksetMap.get("LS")).getJonotusaika()));
        PTJonotusaika.setText(String.format("%.2f", ((PTTulos) tuloksetMap.get("PT")).getJonotusaika()));
        TTJonotusaika.setText(String.format("%.2f", ((TTTulos) tuloksetMap.get("TT")).getJonotusaika()));
        T1Jonotusaika.setText(String.format("%.2f", ((T1Tulos) tuloksetMap.get("T1")).getJonotusaika()));
        T2Jonotusaika.setText(String.format("%.2f", ((T2Tulos) tuloksetMap.get("T2")).getJonotusaika()));
        LSkayttoaste.setText(String.format("%.2f", ((LSTulos) tuloksetMap.get("LS")).getKayttoaste()));
        PTKayttoaste.setText(String.format("%.2f", ((PTTulos) tuloksetMap.get("PT")).getKayttoaste()));
        TTKayttoaste.setText(String.format("%.2f", ((TTTulos) tuloksetMap.get("TT")).getKayttoaste()));
        T1Kayttoaste.setText(String.format("%.2f", ((T1Tulos) tuloksetMap.get("T1")).getKayttoaste()));
        T2Kayttoaste.setText(String.format("%.2f", ((T2Tulos) tuloksetMap.get("T2")).getKayttoaste()));
    }

    // Asetetaan tulokset näkymään kun valinta muuttuu viereseen Vboxiin
    public void valintaMuuttui(ObservableValue<? extends Tulokset> observable, Tulokset oldValue, Tulokset newValue) {
        // Asennetaan tulokset näkymään
        if (newValue != null) {
            TkokonaisAika.setText(newValue.getAika() + " min");
            TkaikkiAsiakkaat.setText(newValue.getAsiakkaat() + " kpl");
            Tehtineet.setText(newValue.getLennolle_ehtineet() + " kpl");
            Tmyohastyneet.setText(newValue.getMyohastyneet_t1() + newValue.getMyohastyneet_t2() + " kpl");
            TmyohastyneetT1.setText(newValue.getMyohastyneet_t1() + " kpl");
            TmyohastyneetT2.setText(newValue.getMyohastyneet_t2() + " kpl");
        }
    }

    // Resetoidaan staattiset muuttujat
    public void resetoiStaattisetMuuttujat() {
        Asiakas.T1myohastyneet = 0;
        Asiakas.T2myohastyneet = 0;
        Asiakas.lennolleEhtineet = 0;
        Asiakas.i = 0;
    }
}