package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import simu.framework.Kello;

public class Visualisointi3 extends Canvas implements IVisualisointi {

    private GraphicsContext gc;
    private String[] names = {"Check-In", "Self-Check-In", "Turvatarkastus", "Portti"};
    private int[] customerAmounts = {0, 0, 0, 0};
    private double[] usageRates = {0.0, 0.0, 0.0, 0.0};
    private int[] servicePointAmounts = {0, 0, 0, 0};
    private double simulointiaika;

    public Visualisointi3(int w, int h, double simulointiaika) {
        super(w, h);
        gc = this.getGraphicsContext2D();
        this.simulointiaika = simulointiaika;
        tyhjennaNaytto();
    }

    public void tyhjennaNaytto() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawServicePoints();
        drawProgressBar();
    }

    public void paivitaVisualisointi(int palvelupiste, int asiakasMaara, double kayttoaste, int palvelupisteMaara) {
        if (palvelupiste >= 1 && palvelupiste <= 4) {
            customerAmounts[palvelupiste - 1] = asiakasMaara;
            usageRates[palvelupiste - 1] = kayttoaste;
            servicePointAmounts[palvelupiste - 1] = palvelupisteMaara;
            drawServicePoint(palvelupiste - 1);
        }
        drawProgressBar();
    }

    private void drawProgressBar() {
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 640, this.getWidth(), 50);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(14));
        gc.fillText("Kello: " + Kello.getInstance().getAika() + " / " + simulointiaika, 10, 650);
        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(10, 650, this.getWidth() - 20, 20);

        double progress = (double) Kello.getInstance().getAika() / simulointiaika;



        gc.setFill(Color.GREEN);
        gc.fillRect(10, 650, (this.getWidth() - 20) * progress, 20);

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(14));
        gc.fillText(String.format("Progress: %.2f%%", progress * 100), 10, 715);
    }

    private void drawServicePoints() {
        for (int i = 0; i < 4; i++) {
            drawServicePoint(i);
        }
    }

    private void drawServicePoint(int index) {
        int boxWidth = 150;
        int boxHeight = 150;
        int spacingX = 20;
        int spacingY = 60;
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
        gc.fillRect(x, y-40, boxWidth, (double) boxHeight / 2);
        gc.setFill(Color.BLACK);
        gc.setFont(new Font(14));

        int jonossa = customerAmounts[index] - servicePointAmounts[index];
        if (jonossa < 0) {
            jonossa = 0;
        }

        for (int i = 0; i < jonossa; i++) {
            gc.setFill(Color.BLUEVIOLET);
            gc.fillOval(x + 10 + i * 11, y - 30, 10, 10);
            if (i >= 11) {
                gc.fillText("...", x + 10 + i * 11, y - 5);
                break;
            }
        }

        gc.setFill(Color.BLACK);
        gc.fillText("Jonossa: " + jonossa, x + 10, y - 5);

        gc.setFill(Color.LIGHTGRAY);
        gc.fillRect(x, y, boxWidth, boxHeight);

        int asiakkaat = customerAmounts[index];
        int palvelupisteet = servicePointAmounts[index];

        // Skaalatan ympyränkoot ja välit siten, että ne mahtuvat ruutuun
        double ympyranKoko = Math.min(20, (boxWidth - 20) / (double) palvelupisteet);
        double valit = (ympyranKoko / 3) / (palvelupisteet);

        if (customerAmounts[index] > servicePointAmounts[index]) {
            asiakkaat = servicePointAmounts[index];
        }

        int i = 0;
        for (; i < asiakkaat; i++) {
            gc.setFill(Color.RED);
            gc.fillOval(x + 10 + i * (ympyranKoko+valit), y + 50, ympyranKoko, ympyranKoko);
        }
        for (int j = 0; j < (palvelupisteet - asiakkaat); j++, i++) {
            gc.setFill(Color.GREEN);
            gc.fillOval(x + 10 + i * (ympyranKoko+valit), y + 50, ympyranKoko, ympyranKoko);
        }

        gc.setFill(Color.BLACK);
        gc.setFont(new Font(14));
        gc.fillText(names[index], x + 10, y + 20);
        gc.fillText("Asiakkaat: " + asiakkaat + " / " + palvelupisteet, x + 10, y + 100);

        if (usageRates[index] > 100) {
            gc.setFill(Color.RED);
        } else {
            gc.setFill(Color.GREEN);
        }
        gc.fillText("Käyttö: " + String.format("%.2f", usageRates[index]) + "%", x + 10, y + 130);
    }

    @Override
    public void uusiAsiakas() {
        // Not needed
    }
}