package simu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "results")
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double loppuaika;
    @Column(name = "valmiit_asiakkaat")
    private double valmiitAsiakkaat;
    @Column(name = "mean_lapimenoaika")
    private double meanLapimenoaika;
    @Column(name = "check_in_mean_palveluaika")
    private double checkInMeanPalveluaika;
    @Column(name = "check_in_koko_palveluaika")
    private double checkInKokoPalveluaika;
    @Column(name = "check_in_kayttoaste")
    private double checkInKayttoaste;
    @Column(name = "check_in_suorituskyky")
    private double checkInSuorituskyky;
    @Column(name = "self_check_in_mean_palveluaika")
    private double selfCheckInMeanPalveluaika;
    @Column(name = "self_check_in_koko_palveluaika")
    private double selfCheckInKokoPalveluaika;
    @Column(name = "self_check_in_kayttoaste")
    private double selfCheckInKayttoaste;
    @Column(name = "self_check_in_suorituskyky")
    private double selfCheckInSuorituskyky;
    @Column(name = "turvatarkastus_mean_palveluaika")
    private double turvatarkastusMeanPalveluaika;
    @Column(name = "turvatarkastus_koko_palveluaika")
    private double turvatarkastusKokoPalveluaika;
    @Column(name = "turvatarkastus_kayttoaste")
    private double turvatarkastusKayttoaste;
    @Column(name = "turvatarkastus_suorituskyky")
    private double turvatarkastusSuorituskyky;
    @Column(name = "portti_mean_palveluaika")
    private double porttiMeanPalveluaika;
    @Column(name = "portti_koko_palveluaika")
    private double porttiKokoPalveluaika;
    @Column(name = "portti_kayttoaste")
    private double porttiKayttoaste;
    @Column(name = "portti_suorituskyky")
    private double porttiSuorituskyky;

    public Results(double loppuaika, double valmiitAsiakkaat, double meanLapimenoaika, double checkInMeanPalveluaika, double checkInKokoPalveluaika, double checkInKayttoaste, double checkInSuorituskyky, double selfCheckInMeanPalveluaika, double selfCheckInKokoPalveluaika, double selfCheckInKayttoaste, double selfCheckInSuorituskyky, double turvatarkastusMeanPalveluaika, double turvatarkastusKokoPalveluaika, double turvatarkastusKayttoaste, double turvatarkastusSuorituskyky, double porttiMeanPalveluaika, double porttiKokoPalveluaika, double porttiKayttoaste, double porttiSuorituskyky) {
        this.loppuaika = loppuaika;
        this.valmiitAsiakkaat = valmiitAsiakkaat;
        this.meanLapimenoaika = meanLapimenoaika;
        this.checkInMeanPalveluaika = checkInMeanPalveluaika;
        this.checkInKokoPalveluaika = checkInKokoPalveluaika;
        this.checkInKayttoaste = checkInKayttoaste;
        this.checkInSuorituskyky = checkInSuorituskyky;
        this.selfCheckInMeanPalveluaika = selfCheckInMeanPalveluaika;
        this.selfCheckInKokoPalveluaika = selfCheckInKokoPalveluaika;
        this.selfCheckInKayttoaste = selfCheckInKayttoaste;
        this.selfCheckInSuorituskyky = selfCheckInSuorituskyky;
        this.turvatarkastusMeanPalveluaika = turvatarkastusMeanPalveluaika;
        this.turvatarkastusKokoPalveluaika = turvatarkastusKokoPalveluaika;
        this.turvatarkastusKayttoaste = turvatarkastusKayttoaste;
        this.turvatarkastusSuorituskyky = turvatarkastusSuorituskyky;
        this.porttiMeanPalveluaika = porttiMeanPalveluaika;
        this.porttiKokoPalveluaika = porttiKokoPalveluaika;
        this.porttiKayttoaste = porttiKayttoaste;
        this.porttiSuorituskyky = porttiSuorituskyky;
    }

    public Results() {}

    public double getLoppuaika() {
        return loppuaika;
    }

    public void setLoppuaika(double loppuaika) {
        this.loppuaika = loppuaika;
    }

    public double getValmiitAsiakkaat() {
        return valmiitAsiakkaat;
    }

    public void setValmiitAsiakkaat(double valmiitAsiakkaat) {
        this.valmiitAsiakkaat = valmiitAsiakkaat;
    }

    public double getMeanLapimenoaika() {
        return meanLapimenoaika;
    }

    public void setMeanLapimenoaika(double meanLapimenoaika) {
        this.meanLapimenoaika = meanLapimenoaika;
    }

    public double getCheckInMeanPalveluaika() {
        return checkInMeanPalveluaika;
    }

    public void setCheckInMeanPalveluaika(double checkInMeanPalveluaika) {
        this.checkInMeanPalveluaika = checkInMeanPalveluaika;
    }

    public double getCheckInKokoPalveluaika() {
        return checkInKokoPalveluaika;
    }

    public void setCheckInKokoPalveluaika(double checkInKokoPalveluaika) {
        this.checkInKokoPalveluaika = checkInKokoPalveluaika;
    }

    public double getCheckInKayttoaste() {
        return checkInKayttoaste;
    }

    public void setCheckInKayttoaste(double checkInKayttoaste) {
        this.checkInKayttoaste = checkInKayttoaste;
    }

    public double getCheckInSuorituskyky() {
        return checkInSuorituskyky;
    }

    public void setCheckInSuorituskyky(double checkInSuorituskyky) {
        this.checkInSuorituskyky = checkInSuorituskyky;
    }

    public double getSelfCheckInMeanPalveluaika() {
        return selfCheckInMeanPalveluaika;
    }

    public void setSelfCheckInMeanPalveluaika(double selfCheckInMeanPalveluaika) {
        this.selfCheckInMeanPalveluaika = selfCheckInMeanPalveluaika;
    }

    public double getSelfCheckInKokoPalveluaika() {
        return selfCheckInKokoPalveluaika;
    }

    public void setSelfCheckInKokoPalveluaika(double selfCheckInKokoPalveluaika) {
        this.selfCheckInKokoPalveluaika = selfCheckInKokoPalveluaika;
    }

    public double getSelfCheckInKayttoaste() {
        return selfCheckInKayttoaste;
    }

    public void setSelfCheckInKayttoaste(double selfCheckInKayttoaste) {
        this.selfCheckInKayttoaste = selfCheckInKayttoaste;
    }

    public double getSelfCheckInSuorituskyky() {
        return selfCheckInSuorituskyky;
    }

    public void setSelfCheckInSuorituskyky(double selfCheckInSuorituskyky) {
        this.selfCheckInSuorituskyky = selfCheckInSuorituskyky;
    }

    public double getTurvatarkastusMeanPalveluaika() {
        return turvatarkastusMeanPalveluaika;
    }

    public void setTurvatarkastusMeanPalveluaika(double turvatarkastusMeanPalveluaika) {
        this.turvatarkastusMeanPalveluaika = turvatarkastusMeanPalveluaika;
    }

    public double getTurvatarkastusKokoPalveluaika() {
        return turvatarkastusKokoPalveluaika;
    }

    public void setTurvatarkastusKokoPalveluaika(double turvatarkastusKokoPalveluaika) {
        this.turvatarkastusKokoPalveluaika = turvatarkastusKokoPalveluaika;
    }

    public double getTurvatarkastusKayttoaste() {
        return turvatarkastusKayttoaste;
    }

    public void setTurvatarkastusKayttoaste(double turvatarkastusKayttoaste) {
        this.turvatarkastusKayttoaste = turvatarkastusKayttoaste;
    }

    public double getTurvatarkastusSuorituskyky() {
        return turvatarkastusSuorituskyky;
    }

    public void setTurvatarkastusSuorituskyky(double turvatarkastusSuorituskyky) {
        this.turvatarkastusSuorituskyky = turvatarkastusSuorituskyky;
    }

    public double getPorttiMeanPalveluaika() {
        return porttiMeanPalveluaika;
    }

    public void setPorttiMeanPalveluaika(double porttiMeanPalveluaika) {
        this.porttiMeanPalveluaika = porttiMeanPalveluaika;
    }

    public double getPorttiKokoPalveluaika() {
        return porttiKokoPalveluaika;
    }

    public void setPorttiKokoPalveluaika(double porttiKokoPalveluaika) {
        this.porttiKokoPalveluaika = porttiKokoPalveluaika;
    }

    public double getPorttiKayttoaste() {
        return porttiKayttoaste;
    }

    public void setPorttiKayttoaste(double porttiKayttoaste) {
        this.porttiKayttoaste = porttiKayttoaste;
    }

    public double getPorttiSuorituskyky() {
        return porttiSuorituskyky;
    }

    public void setPorttiSuorituskyky(double porttiSuorituskyky) {
        this.porttiSuorituskyky = porttiSuorituskyky;
    }
}
