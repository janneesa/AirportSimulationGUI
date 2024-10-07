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
    @Column (name = "mean_check_in")
    private int meanCheckIn;
    @Column (name = "variance_check_in")
    private int varianceCheckIn;
    @Column (name = "mean_self_check_in")
    private int meanSelfCheckIn;
    @Column (name = "variance_self_check_in")
    private int varianceSelfCheckIn;
    @Column (name = "mean_turvatarkastus")
    private int meanTurvatarkastus;
    @Column (name = "variance_turvatarkastus")
    private int varianceTurvatarkastus;
    @Column (name = "mean_portti")
    private int meanPortti;
    @Column (name = "variance_portti")
    private int variancePortti;
    @Column (name = "mean_saapumisvali")
    private int meanSaapumisvali;
    @Column (name = "variance_saapumisvali")
    private int varianceSaapumisvali;

    public Defaults(int aika, int viive, int checkInKoko, int selfCheckInKoko, int turvatarkastusKoko, int porttiKoko, int meanCheckIn, int varianceCheckIn, int meanSelfCheckIn, int varianceSelfCheckIn, int meanTurvatarkastus, int varianceTurvatarkastus, int meanPortti, int variancePortti, int meanSaapumisvali, int varianceSaapumisvali) {
        super();
        this.aika = aika; this.viive = viive; this.checkInKoko = checkInKoko; this.selfCheckInKoko = selfCheckInKoko;
        this.turvatarkastusKoko = turvatarkastusKoko; this.porttiKoko = porttiKoko;
        this.meanCheckIn = meanCheckIn; this.varianceCheckIn = varianceCheckIn;
        this.meanSelfCheckIn = meanSelfCheckIn; this.varianceSelfCheckIn = varianceSelfCheckIn;
        this.meanTurvatarkastus = meanTurvatarkastus; this.varianceTurvatarkastus = varianceTurvatarkastus;
        this.meanPortti = meanPortti; this.variancePortti = variancePortti;
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

    public int getMeanCheckIn() {
        return meanCheckIn;
    }

    public int getVarianceCheckIn() {
        return varianceCheckIn;
    }

    public int getMeanSelfCheckIn() {
        return meanSelfCheckIn;
    }

    public int getVarianceSelfCheckIn() {
        return varianceSelfCheckIn;
    }

    public int getMeanTurvatarkastus() {
        return meanTurvatarkastus;
    }

    public int getVarianceTurvatarkastus() {
        return varianceTurvatarkastus;
    }

    public int getMeanPortti() {
        return meanPortti;
    }

    public int getVariancePortti() {
        return variancePortti;
    }

    public int getMeanSaapumisvali() {
        return meanSaapumisvali;
    }

    public int getVarianceSaapumisvali() {
        return varianceSaapumisvali;
    }
}
