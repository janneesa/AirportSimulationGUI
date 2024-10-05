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
    @Column (name = "self_check_in_mean_palveluaika")
    private int selfCheckInMeanPalveluaika;
    @Column (name = "turvatarkastus_mean_palveluaika")
    private int turvatarkastusMeanPalveluaika;
    @Column (name = "portti_mean_palveluaika")
    private int porttiMeanPalveluaika;

    public Results (int loppuaika, int valmiitAsiakkaat, int meanLapimenoaika, int checkInMeanPalveluaika, int selfCheckInMeanPalveluaika, int turvatarkastusMeanPalveluaika, int porttiMeanPalveluaika) {
        super();
        this.loppuaika = loppuaika; this.valmiitAsiakkaat = valmiitAsiakkaat; this.meanLapimenoaika = meanLapimenoaika;
        this.checkInMeanPalveluaika = checkInMeanPalveluaika; this.selfCheckInMeanPalveluaika = selfCheckInMeanPalveluaika;
        this.turvatarkastusMeanPalveluaika = turvatarkastusMeanPalveluaika; this.porttiMeanPalveluaika = porttiMeanPalveluaika;
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
