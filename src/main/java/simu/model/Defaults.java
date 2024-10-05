package simu.model;

import jakarta.persistence.*;

@Entity
@Table(name = "defaults")
public class Defaults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    int aika;
    int viive;
    @Column (name = "check_in_koko")
    int checkInKoko;
    @Column (name = "self_check_in_koko")
    int selfCheckInKoko;
    @Column (name = "turvatarkastus_koko")
    int turvatarkastusKoko;
    @Column (name = "portti_koko")
    int porttiKoko;
    @Column (name = "mean_palveluaika")
    int meanPalveluaika;
    @Column (name = "variance_palveluaika")
    int variancePalveluaika;
    @Column (name = "mean_saapumisvali")
    int meanSaapumisvali;
    @Column (name = "variance_saapumisvali")
    int varianceSaapumisvali;

    public Defaults(int aika, int viive, int checkInKoko, int selfCheckInKoko, int turvatarkastusKoko, int porttiKoko, int meanPalveluaika, int variancePalveluaika, int meanSaapumisvali, int varianceSaapumisvali) {
        super();
        this.aika = aika; this.viive = viive; this.checkInKoko = checkInKoko; this.selfCheckInKoko = selfCheckInKoko;
        this.turvatarkastusKoko = turvatarkastusKoko; this.porttiKoko = porttiKoko;
        this.meanPalveluaika = meanPalveluaika; this.variancePalveluaika = variancePalveluaika;
        this.meanSaapumisvali = meanSaapumisvali; this.varianceSaapumisvali = varianceSaapumisvali;
    }

    public Defaults() {}

}
