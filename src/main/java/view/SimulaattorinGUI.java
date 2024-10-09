package view;

import controller.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import simu.framework.Trace;
import simu.model.Defaults;

public class SimulaattorinGUI extends Application implements ISimulaattorinUI {

    private IKontrolleriForV kontrolleri;
    private TextField checkInKoko, selfCheckInKoko, turvatarkastusKoko, porttiKoko;
    private static TextField aika;
    private TextField viive, meanCheckIn, varianceCheckIn, meanSelfCheckIn, varianceSelfCheckIn;
    private TextField meanTurvatarkastus, varianceTurvatarkastus, meanPortti, variancePortti;
    private TextField meanSaapumisvali, varianceSaapumisvali;
    private Label tulos, selfCheckInValueLabel;
    private Button kaynnistaButton;
    private Button hidastaButton;
    private Button nopeutaButton;
    private Button haeEdellinenButton;
    private static Button resetButton;
    private static Button tallennaButton;

    private IVisualisointi naytto;
    private Slider selfCheckInSlider;

    @Override
    public void init() {
        Trace.setTraceLevel(Trace.Level.INFO);
        kontrolleri = new Kontrolleri(this);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setOnCloseRequest(t -> {
            Platform.exit();
            System.exit(0);
        });

        primaryStage.setTitle("Simulaattori");

        kaynnistaButton = createButton("Käynnistä simulointi", e -> {
            kontrolleri.kaynnistaSimulointi();
            kaynnistaButton.setDisable(true);
        });

        resetButton = createButton("Reset", e -> resetSimulation());
        resetButton.setVisible(false);

        hidastaButton = createButton("Hidasta", e -> kontrolleri.hidasta());
        nopeutaButton = createButton("Nopeuta", e -> kontrolleri.nopeuta());
        haeEdellinenButton = createButton("Hae edellinen", e -> loadDefaults());
        tallennaButton = createButton("Tallenna", e -> tallennaSimulaatio());
        tallennaButton.setVisible(false);

        initializeTextFields();
        tulos = new Label();
        tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        selfCheckInSlider = new Slider(0, 1, 0.5);
        selfCheckInValueLabel = new Label(String.format("Self-Check-in-todennäköisyys: %.2f%%", selfCheckInSlider.getValue() * 100));
        selfCheckInSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            selfCheckInValueLabel.setText(String.format("Self-Check-in-todennäköisyys: %.2f%%", newValue.doubleValue() * 100));
        });

        GridPane grid = createGridPane();
        setupGrid(grid);

        naytto = new Visualisointi3(700, 700, getAika());

        HBox hBox = new HBox(10, grid, (Canvas) naytto);
        hBox.setPadding(new Insets(15));

        primaryStage.setScene(new Scene(hBox));
        primaryStage.show();
    }

    private void tallennaSimulaatio() {
        Defaults defaults = getDefaults();
        kontrolleri.persistDef(defaults);
    }

    private void loadDefaults() {
        try {
            Defaults defaults = kontrolleri.haeEdellinen();
            setDefaults(defaults);
        } catch (Exception ex) {
            showAlert("Virhe", "Tietokantayhteys epäonnistui", "Yhteyttä tietokantaan ei saatu.");
        }
    }

    private void initializeTextFields() {
        aika = createTextField("500");
        viive = createTextField("500");
        checkInKoko = createTextField("2");
        selfCheckInKoko = createTextField("2");
        turvatarkastusKoko = createTextField("2");
        porttiKoko = createTextField("2");
        meanCheckIn = createTextField("2");
        varianceCheckIn = createTextField("1");
        meanSelfCheckIn = createTextField("1");
        varianceSelfCheckIn = createTextField("1");
        meanTurvatarkastus = createTextField("2");
        varianceTurvatarkastus = createTextField("5");
        meanPortti = createTextField("1");
        variancePortti = createTextField("2");
        meanSaapumisvali = createTextField("15");
        varianceSaapumisvali = createTextField("5");
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Button createButton(String text, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setOnAction(handler);
        return button;
    }

    private TextField createTextField(String promptText) {
        TextField textField = new TextField(promptText);
        textField.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        textField.setPrefWidth(150);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                textField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        return textField;
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        return grid;
    }

    private void setupGrid(GridPane grid) {
        addGridRow(grid, "Simulointiaika (min):", aika, "Viive (min):", viive, 1);
        addGridRow(grid, "Check-in-pisteiden määrä:", checkInKoko, "Self-Check-in-pisteiden määrä:", selfCheckInKoko, 2);
        addGridRow(grid, "Turvatarkastus pisteiden määrä:", turvatarkastusKoko, "Portti pisteiden määrä:", porttiKoko, 3);
        grid.add(new Label("Palveluaikojen keskiarvot ja varianssit (min): "), 0, 4, 2, 1);
        addGridRow(grid, "Check-in (min):", meanCheckIn, "Check-In varianssi (min):", varianceCheckIn, 5);
        addGridRow(grid, "Self-Check-In keskiarvo (min):", meanSelfCheckIn, "Self-Check-In varianssi (min):", varianceSelfCheckIn, 6);
        addGridRow(grid, "Turvatarkastus keskiarvo (min):", meanTurvatarkastus, "Turvatarkastus varianssi (min):", varianceTurvatarkastus, 7);
        addGridRow(grid, "Portti keskiarvo (min):", meanPortti, "Portti varianssi (min):", variancePortti, 8);
        addGridRow(grid, "Saapumisvälin keskiarvo (min):", meanSaapumisvali, "Saapumisvälin varianssi (min):", varianceSaapumisvali, 9);
        addGridRow(grid, selfCheckInValueLabel, selfCheckInSlider, 10);
        addGridRow(grid, "Kokonaisaika:", tulos, 11);
        grid.add(kaynnistaButton, 0, 12);
        grid.add(tallennaButton, 1, 12);
        grid.add(nopeutaButton, 0, 13);
        grid.add(hidastaButton, 1, 13);
        grid.add(haeEdellinenButton, 0, 0);
        grid.add(resetButton, 1, 0);

    }

    private void addGridRow(GridPane grid, String labelText1, Control control1, String labelText2, Control control2, int rowIndex) {
        VBox vbox1 = new VBox(5, new Label(labelText1), control1);
        VBox vbox2 = new VBox(5, new Label(labelText2), control2);
        grid.add(vbox1, 0, rowIndex);
        grid.add(vbox2, 1, rowIndex);
    }

    private void addGridRow(GridPane grid, Label label, Control control, int rowIndex) {
        VBox vbox = new VBox(5, label, control);
        grid.add(vbox, 0, rowIndex, 2, 1);
    }

    private void addGridRow(GridPane grid, String labelText, Control control1, int rowIndex) {
        VBox vbox = new VBox(5, new Label(labelText), control1);
        grid.add(vbox, 0, rowIndex, 2, 1);
    }

    @Override
    public double getAika() {
        return Double.parseDouble(aika.getText());
    }

    @Override
    public long getViive() {
        return Long.parseLong(viive.getText());
    }

    @Override
    public int getCheckInKoko() {
        return Integer.parseInt(checkInKoko.getText());
    }

    @Override
    public int getSelfCheckInKoko() {
        return Integer.parseInt(selfCheckInKoko.getText());
    }

    @Override
    public int getTurvatarkastusKoko() {
        return Integer.parseInt(turvatarkastusKoko.getText());
    }

    @Override
    public int getPorttiKoko() {
        return Integer.parseInt(porttiKoko.getText());
    }

    public int getMeanCheckIn() {
        return Integer.parseInt(meanCheckIn.getText());
    }

    public int getVarianceCheckIn() {
        return Integer.parseInt(varianceCheckIn.getText());
    }

    public int getMeanSelfCheckIn() {
        return Integer.parseInt(meanSelfCheckIn.getText());
    }

    public int getVarianceSelfCheckIn() {
        return Integer.parseInt(varianceSelfCheckIn.getText());
    }

    public int getMeanTurvatarkastus() {
        return Integer.parseInt(meanTurvatarkastus.getText());
    }

    public int getVarianceTurvatarkastus() {
        return Integer.parseInt(varianceTurvatarkastus.getText());
    }

    public int getMeanPortti() {
        return Integer.parseInt(meanPortti.getText());
    }

    public int getVariancePortti() {
        return Integer.parseInt(variancePortti.getText());
    }

    public int getMeanSaapumisvali() {
        return Integer.parseInt(meanSaapumisvali.getText());
    }

    public int getVarianceSaapumisvali() {
        return Integer.parseInt(varianceSaapumisvali.getText());
    }

    public double getSelfCheckInTodennakoisyys() {
        return selfCheckInSlider.getValue();
    }

    @Override
    public void setLoppuaika(double aika) {
        tulos.setText(String.format("%.2f h", aika / 60));
    }

    @Override
    public IVisualisointi getVisualisointi() {
        return naytto;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Double haeAika() {
        return Double.parseDouble(aika.getText());
    }

    public Defaults getDefaults() {
        Defaults defaults = new Defaults(
                Integer.parseInt(aika.getText()), Integer.parseInt(viive.getText()),
                Integer.parseInt(checkInKoko.getText()), Integer.parseInt(selfCheckInKoko.getText()),
                Integer.parseInt(turvatarkastusKoko.getText()), Integer.parseInt(porttiKoko.getText()),
                Integer.parseInt(meanCheckIn.getText()), Integer.parseInt(varianceCheckIn.getText()),
                Integer.parseInt(meanSelfCheckIn.getText()), Integer.parseInt(varianceSelfCheckIn.getText()),
                Integer.parseInt(meanTurvatarkastus.getText()), Integer.parseInt(varianceTurvatarkastus.getText()),
                Integer.parseInt(meanPortti.getText()), Integer.parseInt(variancePortti.getText()),
                Integer.parseInt(meanSaapumisvali.getText()), Integer.parseInt(varianceSaapumisvali.getText()),
                selfCheckInSlider.getValue()
        );
        return defaults;
    }

    public void setDefaults(Defaults defaults) {
        aika.setText(String.valueOf(defaults.getAika()));
        viive.setText(String.valueOf(defaults.getViive()));
        checkInKoko.setText(String.valueOf(defaults.getCheckInKoko()));
        selfCheckInKoko.setText(String.valueOf(defaults.getSelfCheckInKoko()));
        turvatarkastusKoko.setText(String.valueOf(defaults.getTurvatarkastusKoko()));
        porttiKoko.setText(String.valueOf(defaults.getPorttiKoko()));
        meanCheckIn.setText(String.valueOf(defaults.getMeanCheckIn()));
        varianceCheckIn.setText(String.valueOf(defaults.getVarianceCheckIn()));
        meanSelfCheckIn.setText(String.valueOf(defaults.getMeanSelfCheckIn()));
        varianceSelfCheckIn.setText(String.valueOf(defaults.getVarianceSelfCheckIn()));
        meanTurvatarkastus.setText(String.valueOf(defaults.getMeanTurvatarkastus()));
        varianceTurvatarkastus.setText(String.valueOf(defaults.getVarianceTurvatarkastus()));
        meanPortti.setText(String.valueOf(defaults.getMeanPortti()));
        variancePortti.setText(String.valueOf(defaults.getVariancePortti()));
        meanSaapumisvali.setText(String.valueOf(defaults.getMeanSaapumisvali()));
        varianceSaapumisvali.setText(String.valueOf(defaults.getVarianceSaapumisvali()));
        selfCheckInSlider.setValue(defaults.getSelfCheckInTodennakoisyys());
    }

    public static Button getTallennaButton() {
        return tallennaButton;
    }

    private void resetSimulation() {
        aika.setText("500");
        viive.setText("500");
        checkInKoko.setText("2");
        selfCheckInKoko.setText("2");
        turvatarkastusKoko.setText("2");
        porttiKoko.setText("2");
        meanCheckIn.setText("2");
        varianceCheckIn.setText("1");
        meanSelfCheckIn.setText("1");
        varianceSelfCheckIn.setText("1");
        meanTurvatarkastus.setText("2");
        varianceTurvatarkastus.setText("5");
        meanPortti.setText("1");
        variancePortti.setText("2");
        meanSaapumisvali.setText("15");
        varianceSaapumisvali.setText("5");
        selfCheckInSlider.setValue(0.5);

        kaynnistaButton.setDisable(false);
        tallennaButton.setVisible(false);
        resetButton.setVisible(false);

        tulos.setText("");

        naytto.tyhjennaNaytto();

        kontrolleri.resetSimulation();


    }

    public static Button getResetButton() {
        return resetButton;
    }
}