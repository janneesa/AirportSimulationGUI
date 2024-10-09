package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import simu.framework.Kello;
import simu.model.*;

public class Visualisointi3 extends Canvas implements IVisualisointi {

    private static int CANVAS_WIDTH = 700;
    private static int CANVAS_HEIGHT = 575;
    private static final int BOX_WIDTH = 180;
    private static final int BOX_HEIGHT = 225;
    private static final int SPACING_X = 30;
    private static final int SPACING_Y = 40;
    private static final int START_Y = 50;
    private static final int PROGRESS_BAR_HEIGHT = 100;
    private static final int PROGRESS_BAR_MARGIN = 10;
    private static final int FONT_SIZE = 14;
    private static final int MAX_QUEUE_DISPLAY = 11;
    private static final double CIRCLE_SIZE = 20.0;
    private static final double PROGRESS_BAR_WIDTH_RATIO = 0.5;

    private GraphicsContext gc;
    private String[] names = {"Check-In", "Self-Check-In", "Turvatarkastus", "Portti"};
    private int[] customerAmounts = {0, 0, 0, 0};
    private double[] usageRates = {0.0, 0.0, 0.0, 0.0};
    private int[] servicePointAmounts = {0, 0, 0, 0};
    private double simulointiaika;
    private double previousProgress = 0.0;

    public Visualisointi3(int w, int h, double simulointiaika) {
        super(w, h);
        CANVAS_WIDTH = w;
        CANVAS_HEIGHT = h;
        gc = this.getGraphicsContext2D();
        this.simulointiaika = simulointiaika;
        tyhjennaNaytto();
    }

    public void tyhjennaNaytto() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawServicePoints();
        drawProgressBar();
        simulointiaika = SimulaattorinGUI.haeAika();
        previousProgress = 0.0;
    }

    public void paivitaVisualisointi(int servicePoint, int customerCount, double usageRate, int servicePointCount) {
        if (servicePoint >= 1 && servicePoint <= 4) {
            customerAmounts[servicePoint - 1] = customerCount;
            usageRates[servicePoint - 1] = usageRate;
            servicePointAmounts[servicePoint - 1] = servicePointCount;
            drawServicePoint(servicePoint - 1);
        }
        drawProgressBar();
        drawGenericDetails();
    }

    private void drawProgressBar() {
        double progressBarWidth = this.getWidth() * PROGRESS_BAR_WIDTH_RATIO;
        double progress = Kello.getInstance().getAika() / simulointiaika;

        if (progress > previousProgress) {
            previousProgress = progress;
        } else {
            progress = previousProgress;
        }

        int progressBarY = 450;
        int progressBarX = 250;

        gc.setFill(Color.WHITE);
        gc.fillRect(progressBarX, progressBarY, progressBarWidth, PROGRESS_BAR_HEIGHT);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(FONT_SIZE));
        gc.fillText("Kello: " + String.format("%.2f", Kello.getInstance().getAika()) + " / " + String.format("%.2f", simulointiaika), progressBarX, progressBarY + 10);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(progressBarX, progressBarY + 30, progressBarWidth, 20);

        gc.setFill(Color.GREEN);
        gc.fillRect(progressBarX, progressBarY + 30, progressBarWidth * progress, 20);

        gc.setFill(Color.WHITE);
        gc.fillRect(progressBarX, progressBarY + 50, progressBarWidth, 20);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(FONT_SIZE));
        gc.fillText(String.format("Progress: %.2f%%", progress * 100), progressBarX, progressBarY + 80);
    }

    private void drawServicePoints() {
        for (int i = 0; i < names.length; i++) {
            drawServicePoint(i);
        }
    }

    private void drawServicePoint(int index) {
        int startX = (CANVAS_WIDTH - (3 * BOX_WIDTH + 2 * SPACING_X)) / 2;
        int x = startX + index * (BOX_WIDTH + SPACING_X);
        int y = START_Y;

        if (index == 1) {
            y = 325;
            x = startX;
        } else if (index > 1) {
            x = startX + (index - 1) * (BOX_WIDTH + SPACING_X);
        }

        drawServicePointBox(x, y, index);
        drawQueue(x, y, index);
        drawServicePointDetails(x, y, index);
    }

    private void drawServicePointBox(int x, int y, int index) {
        gc.setFill(Color.LIGHTSALMON);
        gc.fillRect(x, y - 40, BOX_WIDTH, BOX_HEIGHT / 2);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x, y, BOX_WIDTH, BOX_HEIGHT);
    }

    private void drawQueue(int x, int y, int index) {
        int queue = Math.max(0, customerAmounts[index] - servicePointAmounts[index]);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(FONT_SIZE));
        gc.fillText("Jonossa: " + queue, x + 10, y - 5);

        for (int i = 0; i < queue; i++) {
            gc.setFill(Color.BLUEVIOLET);
            gc.fillOval(x + 10 + i * 11, y - 30, 10, 10);
            if (i >= MAX_QUEUE_DISPLAY) {
                gc.fillText("+", x + 10 + i * 11, y - 5);
                break;
            }
        }
    }

    private void drawServicePointDetails(int x, int y, int index) {
        int customers = Math.min(customerAmounts[index], servicePointAmounts[index]);
        int servicePoints = servicePointAmounts[index];
        double circleSize = Math.min(CIRCLE_SIZE, (BOX_WIDTH - 20) / (double) servicePoints);
        double spacing = (circleSize / 3) / servicePoints;

        for (int i = 0; i < customers; i++) {
            gc.setFill(Color.RED);
            gc.fillOval(x + 10 + i * (circleSize + spacing), y + 50, circleSize, circleSize);
        }
        for (int i = customers; i < servicePoints; i++) {
            gc.setFill(Color.GREEN);
            gc.fillOval(x + 10 + i * (circleSize + spacing), y + 50, circleSize, circleSize);
        }

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(FONT_SIZE * 1.5));
        gc.fillText(names[index], x + 10, y + 20);
        gc.setFont(new Font(FONT_SIZE));
        gc.fillText("Asiakkaat: " + customers + " / " + servicePoints, x + 10, y + 100);
        gc.setFill(usageRates[index] > 100 ? Color.RED : Color.BLACK);
        gc.fillText("Käyttö prosentti: " + String.format("%.2f", usageRates[index]) + "%", x + 10, y + 120);

        String aloitetutPalvelut = "Aloitetut palvelut: " + getAloitetutPalvelut(index);
        String keskiPalveluaika = "Palveluaikojen KA: " + String.format("%.2f", getKeskiPalveluaika(index));
        String totalPalveluaika = "Täysi palveluaika: " + String.format("%.2f", getTotalPalveluaika(index));
        String kayttoAste = "Käyttöaste: " + String.format("%.2f", getKayttoAste(index));

        gc.fillText(aloitetutPalvelut, x + 10, y + 150);
        gc.fillText(keskiPalveluaika, x + 10, y + 170);
        gc.fillText(totalPalveluaika, x + 10, y + 190);
        gc.fillText(kayttoAste, x + 10, y + 210);

    }

    private void drawGenericDetails() {
        String saapuneetAsiakkaat = "Saapuneet asiakkaat: " + Asiakas.getSaapuneetAsiakkaat();
        String valmiitAsiakkaat = "Valmiit asiakkaat: " + Asiakas.getValmiitAsiakkaat();
        String keskiViipyminen = "Läpimenon keskiarvo: " + String.format("%.2f", Asiakas.getKeskiViipyminen());

        int detailsX = 250;
        int detailsY = 350;
        int detailsWidth = 300;
        int detailsHeight = 60;

        gc.clearRect(detailsX, detailsY - 10, detailsWidth, detailsHeight);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(FONT_SIZE));
        gc.fillText(saapuneetAsiakkaat, detailsX, detailsY);
        gc.fillText(valmiitAsiakkaat, detailsX, detailsY + 20);
        gc.fillText(keskiViipyminen, detailsX, detailsY + 40);
    }

    private int getAloitetutPalvelut(int index) {
        switch (index) {
            case 0:
                return CheckIn.getAloitetutPalvelut();
            case 1:
                return SelfCheckIn.getAloitetutPalvelut();
            case 2:
                return Turvatarkastus.getAloitetutPalvelut();
            case 3:
                return Portti.getAloitetutPalvelut();
            default:
                return 0;
        }
    }

    private double getKeskiPalveluaika(int index) {
        switch (index) {
            case 0: return CheckIn.getKeskiPalveluaika();
            case 1: return SelfCheckIn.getKeskiPalveluaika();
            case 2: return Turvatarkastus.getKeskiPalveluaika();
            case 3: return Portti.getKeskiPalveluaika();
            default: return 0.0;
        }
    }

    private double getTotalPalveluaika(int index) {
        switch (index) {
            case 0: return CheckIn.getTotalPalveluaika();
            case 1: return SelfCheckIn.getTotalPalveluaika();
            case 2: return Turvatarkastus.getTotalPalveluaika();
            case 3: return Portti.getTotalPalveluaika();
            default: return 0.0;
        }
    }

    private double getKayttoAste(int index) {
        switch (index) {
            case 0: return CheckIn.getKayttoAste();
            case 1: return SelfCheckIn.getKayttoAste();
            case 2: return Turvatarkastus.getKayttoAste();
            case 3: return Portti.getKayttoAste();
            default: return 0.0;
        }
    }

    private double getLapimeno(int index) {
        switch (index) {
            case 0: return CheckIn.getLapimeno();
            case 1: return SelfCheckIn.getLapimeno();
            case 2: return Turvatarkastus.getLapimeno();
            case 3: return Portti.getLapimeno();
            default: return 0.0;
        }
    }

    @Override
    public void uusiAsiakas() {
        // Not needed
    }
}