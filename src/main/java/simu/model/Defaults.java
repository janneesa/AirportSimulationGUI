package simu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "defaults")
public class Defaults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int aika;
    private int viive;
    @Column (name = "check_in_koko")
    private int checkInKoko;
    @Column (name = "self_check_in_koko")
    private int selfCheckInKoko;
    @Column (name = "turvatarkastus_koko")
    private int turvatarkastusKoko;
    @Column (name = "portti_koko")
    private int porttiKoko;
    @Column (name = "mean_palveluaika")
    private int meanPalveluaika;
    @Column (name = "variance_palveluaika")
    private int variancePalveluaika;
    @Column (name = "mean_saapumisvali")
    private int meanSaapumisvali;
    @Column (name = "variance_saapumisvali")
    private int varianceSaapumisvali;

    public Defaults(int aika, int viive, int checkInKoko, int selfCheckInKoko, int turvatarkastusKoko, int porttiKoko, int meanPalveluaika, int variancePalveluaika, int meanSaapumisvali, int varianceSaapumisvali) {
        super();
        this.aika = aika; this.viive = viive; this.checkInKoko = checkInKoko; this.selfCheckInKoko = selfCheckInKoko;
        this.turvatarkastusKoko = turvatarkastusKoko; this.porttiKoko = porttiKoko;
        this.meanPalveluaika = meanPalveluaika; this.variancePalveluaika = variancePalveluaika;
        this.meanSaapumisvali = meanSaapumisvali; this.varianceSaapumisvali = varianceSaapumisvali;
    }

    public Defaults() {}

    public int getId() {
        return id;
    }

    public int getAika() {
        return aika;
    }

    public int getViive() {
        return viive;
    }

    public int getCheckInKoko() {
        return checkInKoko;
    }

    public int getSelfCheckInKoko() {
        return selfCheckInKoko;
    }

    public int getTurvatarkastusKoko() {
        return turvatarkastusKoko;
    }

    public int getPorttiKoko() {
        return porttiKoko;
    }

    public int getMeanPalveluaika() {
        return meanPalveluaika;
    }

    public int getVariancePalveluaika() {
        return variancePalveluaika;
    }

    public int getMeanSaapumisvali() {
        return meanSaapumisvali;
    }

    public int getVarianceSaapumisvali() {
        return varianceSaapumisvali;
    }
}
