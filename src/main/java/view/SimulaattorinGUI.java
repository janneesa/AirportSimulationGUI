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

public class SimulaattorinGUI extends Application implements ISimulaattorinUI {

    private IKontrolleriForV kontrolleri;
    private TextField checkInKoko;
    private TextField selfCheckInKoko;
    private TextField turvatarkastusKoko;
    private TextField porttiKoko;
    private static TextField aika;
    private TextField viive;
    private TextField meanCheckIn, varianceCheckIn, meanSelfCheckIn, varianceSelfCheckIn;
    private TextField meanTurvatarkastus, varianceTurvatarkastus, meanPortti, variancePortti;
    // private TextField meanPalveluaika, variancePalveluaika;
    private TextField meanSaapumisvali, varianceSaapumisvali;
    private Label tulos, selfCheckInValueLabel;
    private Button kaynnistaButton, hidastaButton, nopeutaButton;
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

        hidastaButton = createButton("Hidasta", e -> kontrolleri.hidasta());
        nopeutaButton = createButton("Nopeuta", e -> kontrolleri.nopeuta());

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
        // meanPalveluaika = createTextField("10");
        // variancePalveluaika = createTextField("6");
        meanSaapumisvali = createTextField("15");
        varianceSaapumisvali = createTextField("5");
        tulos = new Label();
        tulos.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

        selfCheckInSlider = new Slider(0, 1, 0.5);
        selfCheckInValueLabel = new Label(String.format("Self-Check-in-todennäköisyys: %.2f%%", selfCheckInSlider.getValue() * 100));
        selfCheckInSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            selfCheckInValueLabel.setText(String.format("Self-Check-in-todennäköisyys: %.2f%%", newValue.doubleValue() * 100));
        });

        GridPane grid = createGridPane();
        addGridRow(grid, "Simulointiaika (min):", aika, "Viive (min):", viive, 0);
        addGridRow(grid, "Check-in-pisteiden määrä:", checkInKoko, "Self-Check-in-pisteiden määrä:", selfCheckInKoko, 1);
        addGridRow(grid, "Turvatarkastus pisteiden määrä:", turvatarkastusKoko, "Portti pisteiden määrä:", porttiKoko, 2);
        grid.add(new Label("Palveluaikojen keskiarvot ja varianssit (min): "), 0, 3, 2, 1);
        addGridRow(grid, "Check-in (min):", meanCheckIn, "Check-In varianssi (min):", varianceCheckIn, 4);
        addGridRow(grid, "Self-Check-In keskiarvo (min):", meanSelfCheckIn, "Self-Check-In varianssi (min):", varianceSelfCheckIn, 5);
        addGridRow(grid, "Turvatarkastus keskiarvo (min):", meanTurvatarkastus, "Turvatarkastus varianssi (min):", varianceTurvatarkastus, 6);
        addGridRow(grid, "Portti keskiarvo (min):", meanPortti, "Portti varianssi (min):", variancePortti, 7);
        // addGridRow(grid, "Palveluajan keskiarvo (min):", meanPalveluaika, "Palveluajan varianssi (min):", variancePalveluaika, 3);
        addGridRow(grid, "Saapumisvälin keskiarvo (min):", meanSaapumisvali, "Saapumisvälin varianssi (min):", varianceSaapumisvali, 8);
        addGridRow(grid, selfCheckInValueLabel, selfCheckInSlider, 9);
        addGridRow(grid, "Kokonaisaika:", tulos, 10);
        grid.add(kaynnistaButton, 0, 11);
        grid.add(nopeutaButton, 0, 12);
        grid.add(hidastaButton, 1, 12);

        naytto = new Visualisointi3(400, 700, getAika());

        HBox hBox = new HBox(10, grid, (Canvas) naytto);
        hBox.setPadding(new Insets(15));

        primaryStage.setScene(new Scene(hBox));
        primaryStage.show();
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
        return textField;
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(10);
        return grid;
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

    /* public int getMeanPalveluaika() {
        return Integer.parseInt(meanPalveluaika.getText());
    }

    public int getVariancePalveluaika() {
        return Integer.parseInt(variancePalveluaika.getText());
    }
    */

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
        tulos.setText(String.format("%.2f h", aika/60));
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
}