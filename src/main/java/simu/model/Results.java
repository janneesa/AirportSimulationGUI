package simu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "results")
public class Results {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int loppuaika;
    @Column (name = "valmiit_asiakkaat")
    private int valmiitAsiakkaat;
    @Column (name = "mean_lapimenoaika")
    private int meanLapimenoaika;
    @Column (name = "check_in_mean_palveluaika")
    private int checkInMeanPalveluaika;
    @Column (name = "check_in_koko_palveluaika")
    private int checkInKokoPalveluaika;
    @Column (name = "check_in_kayttoaste")
    private int checkInKayttoaste;
    @Column (name = "check_in_suorituskyky")
    private int checkInSuorituskyky;
    @Column (name = "self_check_in_mean_palveluaika")
    private int selfCheckInMeanPalveluaika;
    @Column (name = "self_check_in_koko_palveluaika")
    private int selfCheckInKokoPalveluaika;
    @Column (name = "self_check_in_kayttoaste")
    private int selfCheckInKayttoaste;
    @Column (name = "self_check_in_suorituskyky")
    private int selfCheckInSuorituskyky;
    @Column (name = "turvatarkastus_mean_palveluaika")
    private int turvatarkastusMeanPalveluaika;
    @Column (name = "turvatarkastus_koko_palveluaika")
    private int turvatarkastusKokoPalveluaika;
    @Column (name = "turvatarkastus_kayttoaste")
    private int turvatarkastusKayttoaste;
    @Column (name = "turvatarkastus_suorituskyky")
    private int turvatarkastusSuorituskyky;
    @Column (name = "portti_mean_palveluaika")
    private int porttiMeanPalveluaika;
    @Column (name = "portti_koko_palveluaika")
    private int porttiKokoPalveluaika;
    @Column (name = "portti_kayttoaste")
    private int porttiKayttoaste;
    @Column (name = "portti_suorituskyky")
    private int porttiSuorituskyky;

    public Results (int loppuaika, int valmiitAsiakkaat, int meanLapimenoaika, int checkInMeanPalveluaika, int checkInKokoPalveluaika, int checkInKayttoaste, int checkInSuorituskyky, int selfCheckInMeanPalveluaika, int selfCheckInKokoPalveluaika, int selfCheckInKayttoaste, int selfCheckInSuorituskyky, int turvatarkastusMeanPalveluaika, int turvatarkastusKokoPalveluaika, int turvatarkastusKayttoaste, int turvatarkastusSuorituskyky, int porttiMeanPalveluaika, int porttiKokoPalveluaika, int porttiKayttoaste, int porttiSuorituskyky) {
        this.loppuaika = loppuaika; this.valmiitAsiakkaat = valmiitAsiakkaat;
        this.meanLapimenoaika = meanLapimenoaika; this.checkInMeanPalveluaika = checkInMeanPalveluaika;
        this.checkInKokoPalveluaika = checkInKokoPalveluaika; this.checkInKayttoaste = checkInKayttoaste;
        this.checkInSuorituskyky = checkInSuorituskyky; this.selfCheckInMeanPalveluaika = selfCheckInMeanPalveluaika;
        this.selfCheckInKokoPalveluaika = selfCheckInKokoPalveluaika; this.selfCheckInKayttoaste = selfCheckInKayttoaste;
        this.selfCheckInSuorituskyky = selfCheckInSuorituskyky; this.turvatarkastusMeanPalveluaika = turvatarkastusMeanPalveluaika;
        this.turvatarkastusKokoPalveluaika = turvatarkastusKokoPalveluaika; this.turvatarkastusKayttoaste = turvatarkastusKayttoaste;
        this.turvatarkastusSuorituskyky = turvatarkastusSuorituskyky; this.porttiMeanPalveluaika = porttiMeanPalveluaika;
        this.porttiKokoPalveluaika = porttiKokoPalveluaika; this.porttiKayttoaste = porttiKayttoaste;
        this.porttiSuorituskyky = porttiSuorituskyky;
    }

    public Results() {}

    public void setLoppuaika(int loppuaika) {
        this.loppuaika = loppuaika;
    }

    public void setValmiitAsiakkaat(int valmiitAsiakkaat) {
        this.valmiitAsiakkaat = valmiitAsiakkaat;
    }

    public void setMeanLapimenoaika(int meanLapimenoaika) {
        this.meanLapimenoaika = meanLapimenoaika;
    }

    public void setCheckInMeanPalveluaika(int checkInMeanPalveluaika) {
        this.checkInMeanPalveluaika = checkInMeanPalveluaika;
    }

    public void setSelfCheckInMeanPalveluaika(int selfCheckInMeanPalveluaika) {
        this.selfCheckInMeanPalveluaika = selfCheckInMeanPalveluaika;
    }

    public void setTurvatarkastusMeanPalveluaika(int turvatarkastusMeanPalveluaika) {
        this.turvatarkastusMeanPalveluaika = turvatarkastusMeanPalveluaika;
    }

    public void setPorttiMeanPalveluaika(int porttiMeanPalveluaika) {
        this.porttiMeanPalveluaika = porttiMeanPalveluaika;
    }

    public int getLoppuaika() {
        return loppuaika;
    }

    public int getValmiitAsiakkaat() {
        return valmiitAsiakkaat;
    }

    public int getMeanLapimenoaika() {
        return meanLapimenoaika;
    }

    public int getCheckInMeanPalveluaika() {
        return checkInMeanPalveluaika;
    }

    public int getSelfCheckInMeanPalveluaika() {
        return selfCheckInMeanPalveluaika;
    }

    public int getTurvatarkastusMeanPalveluaika() {
        return turvatarkastusMeanPalveluaika;
    }

    public int getPorttiMeanPalveluaika() {
        return porttiMeanPalveluaika;
    }
}
