package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Visualisointi3 extends Canvas implements IVisualisointi {

    private GraphicsContext gc;
    private String[] names = {"Check-In", "Self-Check-In", "Turvatarkastus", "Portti"};
    private int[] customerAmounts = {0, 0, 0, 0};
    private double[] usageRates = {0.0, 0.0, 0.0, 0.0};
    private int[] servicePointAmounts = {0, 0, 0, 0};

    public Visualisointi3(int w, int h) {
        super(w, h);
        gc = this.getGraphicsContext2D();
        tyhjennaNaytto();
    }

    public void tyhjennaNaytto() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawServicePoints();
    }

    public void paivitaVisualisointi(int palvelupiste, int asiakasMaara, double kayttoaste, int palvelupisteMaara) {
        if (palvelupiste >= 1 && palvelupiste <= 4) {
            customerAmounts[palvelupiste - 1] = asiakasMaara;
            usageRates[palvelupiste - 1] = kayttoaste;
            servicePointAmounts[palvelupiste - 1] = palvelupisteMaara;
            drawServicePoint(palvelupiste - 1);
        }
    }

    private void drawServicePoints() {
        for (int i = 0; i < 4; i++) {
            drawServicePoint(i);
        }
    }

    private void drawServicePoint(int index) {
        int boxWidth = 150;
        int boxHeight = 100;
        int spacingX = 20;
        int spacingY = 40;
        int startX = 50;
        int startY = 50;
        int x, y;

        if (index == 1) {
            x = startX + (index % 2) * (boxWidth + spacingX);
        } else {
            x = startX;
        }

        if (index == 3) {
            y = startY + 2 * (boxHeight + spacingY);
        } else {
            y = startY + (index / 2) * (boxHeight + spacingY);
        }

        gc.setFill(Color.LIGHTSALMON);
        gc.fillRect(x, y-20, boxWidth, (double) boxHeight / 2);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(14));

        int jonossa = customerAmounts[index] - servicePointAmounts[index];
        if (jonossa < 0) {
            jonossa = 0;
        }

        gc.fillText("Jonossa: " + jonossa, x + 10, y - 5);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x, y, boxWidth, boxHeight);

        int asiakkaat = customerAmounts[index];
        if (customerAmounts[index] > servicePointAmounts[index]) {
            asiakkaat = servicePointAmounts[index];
        }

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(14));
        gc.fillText(names[index], x + 10, y + 20);
        gc.fillText("Asiakkaat: " + asiakkaat + " / " + servicePointAmounts[index], x + 10, y + 50);

        if (usageRates[index] > 100) {
            gc.setFill(Color.RED);
        } else {
            gc.setFill(Color.GREEN);
        }
        gc.fillText("Käyttö: " + String.format("%.2f", usageRates[index]) + "%", x + 10, y + 80);
    }

    @Override
    public void uusiAsiakas() {
        // Not needed
    }
}