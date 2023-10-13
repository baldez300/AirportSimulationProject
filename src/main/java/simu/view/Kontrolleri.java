package simu.view;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.shape.Circle;
import javafx.scene.chart.XYChart;
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
import javafx.util.converter.DoubleStringConverter;

/** Kontrolleri-luokka, joka toimii käyttöliittymän ja sovelluslogiikan välissä */
public class Kontrolleri {
    // Oletusarvot asetuksille jotta pysyvät muistissa
    /** Luokan attribuutit */
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

    @FXML
    private BarChart<String, Double> jononpituusChart;

    @FXML
    private BarChart<String, Double> jonotusaikaChart;

    @FXML
    private BarChart<String, Double> kayttoasteChart;

    @FXML
    private BarChart<String, Double> suoritustehoChart;

    @FXML
    private PieChart myohastyneetPie;

    @FXML
    private Text T1_myohastyneet_pros;

    @FXML
    private Text T2_myohastyneet_pros;

    private static IMoottori moottori;

    private TuloksetDao tuloksetDao = new TuloksetDao();

    private Visualisointi visualisointi;

    // Timer tarkistamaan onko simulointi loppunut ja avaamaan tulokset näkymän
    private Timer timer;

    // Apumuuttujat
    private boolean tietokantaYhteys;
    private boolean tulosValittu;
    private boolean painettu;

    // Asetetaan oletusarvot spinnereille
    @FXML
    /**
     * Metodi, joka suoritetaan kun käyttöliittymä on ladattu
     */
    void initialize() {
        // Alusta TextFormatter rajoittaaksesi syötteen numeerisiin arvoihin
        TextFormatter<Double> textFormatter = new TextFormatter<>(
                new DoubleStringConverter(),
                0.0, // Oletusarvo (voidään muuttaa tämän halutuksi oletusarvoksi)
                change -> {
                    String newText = change.getControlNewText();
                    if (isValidDouble(newText)) {
                        return change;
                    } else {
                        return null; // Hylkää muutos, jos se ei ole kelvollinen tupla
                    }
                });

        simulaationAika.getEditor().setTextFormatter(textFormatter);

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

        // Asetetaan graafien x-akselin arvot näkymättömiksi
        jononpituusChart.getXAxis().setTickLabelsVisible(false);
        jonotusaikaChart.getXAxis().setTickLabelsVisible(false);
        kayttoasteChart.getXAxis().setTickLabelsVisible(false);
        suoritustehoChart.getXAxis().setTickLabelsVisible(false);

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
            /**
             * Metodi, joka suoritetaan kun tietokanta yhteys on muuttunut
             */
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

        // Asetetaan kaikki muut sivut näkymättömiksi paitsi asetukset
        simulaatioSivu.setVisible(false);
        TuloksetSivu.setVisible(false);
        tallennetut.setVisible(false);
        AsetuksetSivu.setVisible(true);
    }

    @FXML
    /**
     * Metodi, joka suoritetaan kun käyttäjä painaa aloita nappia
     */
    private void aloita(ActionEvent event) {
        // Resetoidaan kellon aika ja staattiset muuttujat
        Kello.getInstance().setAika(0);
        resetoiStaattisetMuuttujat();
        tyhjennaChartit();
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
        if (timer != null) timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            /**
             * Metodi, joka suoritetaan kun timer on suoritettu
             */
            public void run() {
                // Jos simulointi on loppunut, asetetaan tulokset
                if (Kello.getInstance().getAika() >= simulointiAika) {
                    asetaTulokset(((OmaMoottori) moottori).getTulokset());
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

    // Tarkistetaan onko merkkijono numero
    /**
     * Metodi, joka tarkistaa onko merkkijono numero
     * @param str merkkijono
     * @return palauttaa true jos on numero, muuten false
     */
    private boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @FXML
    // Haetaan tulokset tietokannasta ja asetetaan ne listaan
    /**
     * Metodi, joka suoritetaan kun käyttäjä painaa edelliset tulokset nappia
     */
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
    /** Metodo, joka suoritetaan kun käyttäjä painaa tarkemmat tiedot nappia */
    private void naytaTarkemmatTiedot(ActionEvent event) {
        try {
            Tulokset valittuTulos = tuloksetLista.getSelectionModel().getSelectedItem();
            asetaTulokset(valittuTulos);
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

    /**
     * Metodi, joka suoritetaan kun käyttäjä painaa poistu nappia
     */
    private void palaaAsetuksiin(ActionEvent event) {
        Stage asetuksetStage = (Stage) AsetuksetSivu.getScene().getWindow();
        tulosValittu = false;
        asetuksetStage.setTitle("Simulaation asetukset");
        tallennetut.setVisible(false);
        AsetuksetSivu.setVisible(true);
        tallennaNappi.setVisible(true);
    }

    @FXML
    /**
     * Metodi, joka suoritetaan kun käyttäjä painaa poistu nappia
     */
    private void palaaTallennettuihin(ActionEvent event) {
        // Tyhjennetään kaikki graafit
        tyhjennaChartit();
        Stage tallennetutStage = (Stage) tallennetut.getScene().getWindow();
        tallennetutStage.setTitle("Tallennetut tulokset");
        TuloksetSivu.setVisible(false);
        tallennetut.setVisible(true);
    }

    @FXML
    // Tallennetaan tulokset tietokantaan
    /**
     * Metodi, joka suoritetaan kun käyttäjä painaa tallenna nappia
     */
    private void tallenna(ActionEvent event) {
        painettu = true;
        tallennaNappi.setDisable(true);
        ((OmaMoottori) moottori).tallennaTulokset();
    }

    @FXML
    /**
     * Metodi, joka suoritetaan kun käyttäjä painaa uusi nappia
     */
    private void uusiSimulointi(ActionEvent event) {
        ((Thread) moottori).interrupt();
        painettu = false;
        // Tyhjennetään kaikki chartit
        tyhjennaChartit();
        Stage asetuksetStage = (Stage) AsetuksetSivu.getScene().getWindow();
        asetuksetStage.setTitle("Simulaation asetukset");
        TuloksetSivu.setVisible(false);
        AsetuksetSivu.setVisible(true);
        tallennaNappi.setDisable(false);
    }

    /** Metodi, joka suoritetaan kun käyttäjä painaa poistu nappia */
    public void lopetaSaie() {
        ((Thread) moottori).interrupt();
        timer.cancel();
        Stage asetuksetStage = (Stage) AsetuksetSivu.getScene().getWindow();
        asetuksetStage.setTitle("Simulaation asetukset");
        simulaatioSivu.setVisible(false);
        AsetuksetSivu.setVisible(true);
    }

    /**
     * Metodi, joka hidasaa simulaatiota

     */
    protected void hidasta() {
        moottori.setViive((long) (moottori.getViive() * 1.10));
    }

    /**
     * Metodi, joka nopeuttaa simulaatiota

     */
    protected void nopeuta() {
        moottori.setViive((long) (moottori.getViive() * 0.9));
    }

    /**
     * Metodi, palauttaa Canvasin

     */
    public Canvas getCanvas() {
        return contentCanvas;
    }

    // Getters for settings

    /*
     * Tämä palauttaa lähtöselvityksen keskiarvon jaettuna pisteiden määrällä
     * joka on keskimääräinen palvelunopeus
     */
    /**
     * Metodi, joka palauttaa lähtöselvityksen keskiarvon jaettuna pisteiden määrällä joka on keskimääräinen palvelunopeus
     * @return palauttaa lähtöselvityksen keskiarvon jaettuna pisteiden määrällä joka on keskimääräinen palvelunopeus
     */
    public int getLSpalveluNopeus() {
        return lahtoselvitysKA.getValue() / lahtoselvitysMaara.getValue();
    }

    /**
     * Metodi, joka palauttaa lähtöselvityksen pisteiden määrän
     * @return palauttaa lähtöselvityksen pisteiden määrän
     */
    public int getLahtoselvitysMaara() {
        return lahtoselvitysMaara.getValue();
    }

    /**
     * Metodi, joka palauttaa lähtöselvityksen vaihtelun
     * @return palauttaa lähtöselvityksen vaihtelun
     */
    public int getLahtoselvitysVar() {
        return lahtoselvitysVar.getValue();
    }

    /** Metodi palauttaa passintarkastuksen keskiarvon jaettuna pisteiden määrällä joka on keskimääräinen palvelunopeus
     * @return palauttaa passintarkastuksen keskiarvon jaettuna pisteiden määrällä joka on keskimääräinen palvelunopeus
     */
    public int getPTpalveluNopeus() {
        return passintarkastusKA.getValue() / passintarkastusMaara.getValue();
    }

    /**
     * Metodi, joka palauttaa passintarkastuksen pisteiden määrän
     * @return palauttaa passintarkastuksen pisteiden määrän
     */
    public int getPassintarkastusMaara() {
        return passintarkastusMaara.getValue();
    }

    /**
     * Metodi, joka palauttaa passintarkastuksen vaihtelun
     * @return palauttaa passintarkastuksen vaihtelun
     */
    public int getPassintarkastusVar() {
        return passintarkastusVar.getValue();
    }

    /**
     * Metodi, joka palauttaa turvatarkastuksen keskiarvon jaettuna pisteiden määrällä joka on keskimääräinen palvelunopeus
     * @return palauttaa turvatarkastuksen keskiarvon jaettuna pisteiden määrällä joka on keskimääräinen palvelunopeus
     */
    public int getTTpalveluNopeus() {
        return turvatarkastusKA.getValue() / turvatarkastusMaara.getValue();
    }

    /** Metodi palauttaa turvatarkastuksen pisteiden määrän
     * @return palauttaa turvatarkastuksen pisteiden määrän
     */
    public int getTurvatarkastusMaara() {
        return turvatarkastusMaara.getValue();
    }

    /**
     * Metodi, joka palauttaa turvatarkastuksen vaihtelun
     * @return palauttaa turvatarkastuksen vaihtelun
     */
    public int getTurvatarkastusVar() {
        return turvatarkastusVar.getValue();
    }

    /** Metodi palauttaa kotimaan lähtöportin vaihtelun
     * @return palauttaa kotimaan lähtöportin vaihtelun
     */
    public int getKotimaaVar() {
        return kotimaaVar.getValue();
    }

    /** Metodi palauttaa kotimaan lähtoportin keskiarvon palveluaika
     *  @return palauttaa kotimaan lähtoportin keskiarvon palveluaika
     *  */
    public int getKotimaaKA() {
        return kotimaaKA.getValue();
    }
    /** Metodi palauttaa ulkomaan lähtöportin vaihtelun
     * @return palauttaa ulkomaan lähtöportin vaihtelun
     */
    public int getUlkomaaVar() {
        return ulkomaaVar.getValue();
    }

    /** Metodi palauttaa ulkomaan lähtoportin keskiarvon palveluaika
     *  @return palauttaa ulkomaan lähtoportin keskiarvon palveluaika
     *  */
    public int getUlkomaaKA() {
        return ulkomaaKA.getValue();
    }

    /**
     * Metodi, joka palauttaa lentojen välisen ajan
     * @return palauttaa lentojen välisen ajan
     */
    public int getLentojenVali() {
        return lentojenVali.getValue();
    }

    /**
     * Metodi, joka palauttaa lentojen välisen ajan
     * @return palauttaa lentojen välisen ajan
     */
    public int getLentojenVali2() {
        return lentojenVali2.getValue();
    }

    /**
     * Metodi, joka palauttaa simuloinnin ajan
     * @return palauttaa simuloinnin ajan
     */
    public double getSimulointiAika() {
        return simulaationAika.getValue();
    }

    /**
     * Metodi, joka palauttaa simuloinnin viiveen
     * @return palauttaa simuloinnin viiveen
     */
    public int getSimulointiViive() {
        return simulaationViive.getValue();
    }

    /**
     * Metodi, joka palauttaa kaikki palvelupisteet
     * @return palauttaa kaikki palvelupisteet
     */
    protected Palvelupiste[] getPalvelupisteet() {
        return ((OmaMoottori) moottori).getPalvelupisteet();
    }

    /** Metodi tarkistaa onko simulaatio käynnissä
     * @return palauttaa true jos simulaatio on käynnissä, muuten false
     */
    protected boolean onHengissa() {
        return ((Moottori) moottori).isAlive();
    }

    /** Metodi palauttaa moottorin viiveen
     * @return palauttaa moottorin viiveen
     */
    protected long getMoottorinViive() {
        return ((OmaMoottori) moottori).getViive();
    }

    // Asetetaan tallennetut tulokset näkymään tulokset sivulle
    /**
     * Metodi, joka asettaa tallennetut tulokset näkymään tulokset sivulle
     * @param tulokset tulokset
     */
    private void asetaTulokset(Tulokset tulokset) {
        LSTulos lsTulos = tulokset.getLSTulos();
        PTTulos ptTulos = tulokset.getPTTulos();
        TTTulos ttTulos = tulokset.getTTTulos();
        T1Tulos t1Tulos = tulokset.getT1Tulos();
        T2Tulos t2Tulos = tulokset.getT2Tulos();
    
        pvm.setText(tulokset.getPaivamaara().toString());
        kokonaisAika.setText(String.format("%d h %02d min", (int) (tulokset.getAika() / 60), (int) (tulokset.getAika() % 60)));
        kaikkiAsiakkaat.setText(tulokset.getAsiakkaat() + " kpl");
        ehtineet.setText(tulokset.getLennolle_ehtineet() + " kpl");
        myohastyneet.setText(tulokset.getMyohastyneet_t1() + tulokset.getMyohastyneet_t2() + " kpl");
        myohastyneetT1.setText(tulokset.getMyohastyneet_t1() + " kpl");
        myohastyneetT2.setText(tulokset.getMyohastyneet_t2() + " kpl");
        LSsuoritusteho.setText(String.format("%.2f", lsTulos.getSuoritusteho()) + " kpl/h");
        PTSuoritusteho.setText(String.format("%.2f", ptTulos.getSuoritusteho()) + " kpl/h");
        TTSuoritusteho.setText(String.format("%.2f", ttTulos.getSuoritusteho()) + " kpl/h");
        T1Suoritusteho.setText(String.format("%.2f", t1Tulos.getSuoritusteho()) + " kpl/h");
        T2Suoritusteho.setText(String.format("%.2f", t2Tulos.getSuoritusteho()) + " kpl/h");
        LSJononPituus.setText(String.format("%.2f", lsTulos.getJononpituus()) + " kpl");
        PTJononPituus.setText(String.format("%.2f", ptTulos.getJononpituus()) + " kpl");
        TTJononPituus.setText(String.format("%.2f", ttTulos.getJononpituus()) + " kpl");
        T1JononPituus.setText(String.format("%.2f", t1Tulos.getJononpituus()) + " kpl");
        T2JononPituus.setText(String.format("%.2f", t2Tulos.getJononpituus()) + " kpl");
        LSjonotusaika.setText(String.format("%.2f", lsTulos.getJonotusaika()) + " min");
        PTJonotusaika.setText(String.format("%.2f", ptTulos.getJonotusaika()) + " min");
        TTJonotusaika.setText(String.format("%.2f", ttTulos.getJonotusaika()) + " min");
        T1Jonotusaika.setText(String.format("%.2f", t1Tulos.getJonotusaika()) + " min");
        T2Jonotusaika.setText(String.format("%.2f", t2Tulos.getJonotusaika()) + " min");
        LSkayttoaste.setText(String.format("%.2f", lsTulos.getKayttoaste()) + " %");
        PTKayttoaste.setText(String.format("%.2f", ptTulos.getKayttoaste()) + " %");
        TTKayttoaste.setText(String.format("%.2f", ttTulos.getKayttoaste()) + " %");
        T1Kayttoaste.setText(String.format("%.2f", t1Tulos.getKayttoaste()) + " %");
        T2Kayttoaste.setText(String.format("%.2f", t2Tulos.getKayttoaste()) + " %");
        LS.setText(String.format("%d kpl", lsTulos.getMaara()));
        TT.setText(String.format("%d kpl", ttTulos.getMaara()));
        PT.setText(String.format("%d kpl", ptTulos.getMaara()));

        // Myöhästyneet prosentit
        double myohastyneetT1Prosentti = tulokset.getMyohastyneet_t1() / (tulokset.getMyohastyneet_t1() + tulokset.getMyohastyneet_t2()) * 100;
        double myohastyneetT2Prosentti = tulokset.getMyohastyneet_t2() / (tulokset.getMyohastyneet_t1() + tulokset.getMyohastyneet_t2()) * 100;
    
        if (tulokset.getMyohastyneet_t1() > 0) {
            T1_myohastyneet_pros.setText(String.format("%.2f", myohastyneetT1Prosentti) + " %");
        }
        if (tulokset.getMyohastyneet_t2() > 0) {
            T2_myohastyneet_pros.setText(String.format("%.2f", myohastyneetT2Prosentti) + " %");
        }

        // Chartit
        Platform.runLater(() -> {
            List<XYChart.Series<String, Double>> seriesList1 = new ArrayList<>();
            List<XYChart.Series<String, Double>> seriesList2 = new ArrayList<>();
            List<XYChart.Series<String, Double>> seriesList3 = new ArrayList<>();
            List<XYChart.Series<String, Double>> seriesList4 = new ArrayList<>();
            // Jonojen pituudet
            seriesList1.add(createSeries("Lähtöselvitys", lsTulos.getJononpituus()));
            seriesList1.add(createSeries("Passintarkastus", ptTulos.getJononpituus()));
            seriesList1.add(createSeries("Turvatarkastus", ttTulos.getJononpituus()));
            seriesList1.add(createSeries("T1", t1Tulos.getJononpituus()));
            seriesList1.add(createSeries("T2", t2Tulos.getJononpituus()));
            jononpituusChart.getData().addAll(seriesList1);
            // Jonotusajat
            seriesList2.add(createSeries("Lähtöselvitys", lsTulos.getJonotusaika()));
            seriesList2.add(createSeries("Passintarkastus", ptTulos.getJonotusaika()));
            seriesList2.add(createSeries("Turvatarkastus", ttTulos.getJonotusaika()));
            seriesList2.add(createSeries("T1", t1Tulos.getJonotusaika()));
            seriesList2.add(createSeries("T2", t2Tulos.getJonotusaika()));
            jonotusaikaChart.getData().addAll(seriesList2);
            // Suoritustehot
            seriesList3.add(createSeries("Lähtöselvitys", lsTulos.getSuoritusteho()));
            seriesList3.add(createSeries("Passintarkastus", ptTulos.getSuoritusteho()));
            seriesList3.add(createSeries("Turvatarkastus", ttTulos.getSuoritusteho()));
            seriesList3.add(createSeries("T1", t1Tulos.getSuoritusteho()));
            seriesList3.add(createSeries("T2", t2Tulos.getSuoritusteho()));
            suoritustehoChart.getData().addAll(seriesList3);
            // Käyttöasteet
            seriesList4.add(createSeries("Lähtöselvitys", lsTulos.getKayttoaste()));
            seriesList4.add(createSeries("Passintarkastus", ptTulos.getKayttoaste()));
            seriesList4.add(createSeries("Turvatarkastus", ttTulos.getKayttoaste()));
            seriesList4.add(createSeries("T1", t1Tulos.getKayttoaste()));
            seriesList4.add(createSeries("T2", t2Tulos.getKayttoaste()));
            kayttoasteChart.getData().addAll(seriesList4);
            // Pie chart
            myohastyneetPie.getData().add(new PieChart.Data("T1", myohastyneetT1Prosentti));
            myohastyneetPie.getData().add(new PieChart.Data("T2", myohastyneetT2Prosentti));

            // Asetetaan värit
            for (PieChart.Data data : myohastyneetPie.getData()) {
                if (data.getName().equals("T1")) {
                    data.getNode().setStyle("-fx-pie-color: red;");
                } else if (data.getName().equals("T2")) {
                    data.getNode().setStyle("-fx-pie-color: blue;");
                }
            }
        });
    }

    // Luodaan uusi series bar chartteja varten
    /**
     * Metodi, joka luo uuden series bar chartteja varten
     * @param name nimi
     * @param value arvo
     * @return palauttaa uuden series bar chartteja varten
     */
    private XYChart.Series<String, Double> createSeries(String name, double value) {
        XYChart.Series<String, Double> series = new XYChart.Series<>();
        series.setName(name);
        series.getData().add(new XYChart.Data<>("", value));
        return series;
    }

    // Tyhjennä graaffien data
    /**
     * Metodi, joka tyhjentää graaffien data
     */
    public void tyhjennaChartit() {
        jononpituusChart.getData().clear();
        myohastyneetPie.getData().clear();
        jonotusaikaChart.getData().clear();
        kayttoasteChart.getData().clear();
        suoritustehoChart.getData().clear();
        T1_myohastyneet_pros.setText("0");
        T2_myohastyneet_pros.setText("0");
    }

    // Asetetaan tulokset näkymään viereseen Vboxiin kun valinta muuttuu
    /** Metodi asettaa tulokset näkymään viereseen Vboxiin kun valinta muuttuu
     * @param observable observable
     * @param oldValue vanha arvo
     * @param newValue uusi arvo
     */
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
    /**
     * Metodi, joka resetoi staattiset muuttujat
     */
    private void resetoiStaattisetMuuttujat() {
        Asiakas.T1myohastyneet = 0;
        Asiakas.T2myohastyneet = 0;
        Asiakas.lennolleEhtineet = 0;
        Asiakas.i = 0;
        Palvelupiste.palvellutAsiakkaatTotal = 0;
    }

    /**
     * Metodi, joka testaa tietokanta yhteyden
     */
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
