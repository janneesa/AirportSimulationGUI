package simu.model;

import controller.IKontrolleriForM;
import simu.framework.*;
import eduni.distributions.Negexp;
import eduni.distributions.Normal;

public class OmaMoottori extends Moottori {

	private Saapumisprosessi saapumisprosessi;

	private int checkInKoko = 2;
	private int turvatarkastusKoko = 2;
	private int porttiKoko = 2;

	private Palvelupiste[] checkInPisteet;
	private Palvelupiste[] turvatarkastusPisteet;
	private Palvelupiste[] porttiPisteet;

	public OmaMoottori(IKontrolleriForM kontrolleri){

		super(kontrolleri);
		checkInPisteet = new Palvelupiste[checkInKoko];
		turvatarkastusPisteet = new Palvelupiste[turvatarkastusKoko];
		porttiPisteet = new Palvelupiste[porttiKoko];

		// Luo check-in pisteet
		for (int i = 0; i < checkInKoko; i++) {
			checkInPisteet[i] = new CheckIn("Check-in " + (i + 1), new Normal(10, 6), tapahtumalista, TapahtumanTyyppi.CHECK_IN_VALMIS);
		}

		// Luo turvatarkastus pisteet
		for (int i = 0; i < turvatarkastusKoko; i++) {
			turvatarkastusPisteet[i] = new Turvatarkastus("Turvatarkastus " + (i + 1), new Normal(10, 10), tapahtumalista, TapahtumanTyyppi.TURVATARKASTUS_VALMIS);
		}

		// Luo portti pisteet
		for (int i = 0; i < porttiKoko; i++) {
			porttiPisteet[i] = new Portti("Portti " + (i + 1), new Normal(5, 3), tapahtumalista, TapahtumanTyyppi.PORTTI_VALMIS);
		}

		saapumisprosessi = new Saapumisprosessi(new Negexp(15, 5), tapahtumalista, TapahtumanTyyppi.ARRIVAL);
	}

	@Override
	protected void alustukset() {
		saapumisprosessi.generoiSeuraava();
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) {
		Asiakas a;
		switch ((TapahtumanTyyppi) t.getTyyppi()) {
			case ARRIVAL:
				getShortestQueue(checkInPisteet).lisaaJonoon(new Asiakas());
				saapumisprosessi.generoiSeuraava();
				kontrolleri.visualisoiAsiakas();
				break;
			case CHECK_IN_VALMIS:
				for (Palvelupiste p : checkInPisteet) {
					if (p.onVarattu() && p.onJonossa()) {
						if (p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
							a = p.otaJonosta();
							getShortestQueue(turvatarkastusPisteet).lisaaJonoon(a);
							break;
						}
					}
				}
				break;
			case TURVATARKASTUS_VALMIS:
				for (Palvelupiste p : turvatarkastusPisteet) {
					if (p.onVarattu() && p.onJonossa()) {
						if (p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
							a = p.otaJonosta();
							getShortestQueue(porttiPisteet).lisaaJonoon(a);
							break;
						}
					}
				}
				break;
			case PORTTI_VALMIS:
				for (Palvelupiste p : porttiPisteet) {
					if (p.onVarattu() && p.onJonossa()) {
						if (p.getJono().peek().getPalvelunPaattymisaika() == Kello.getInstance().getAika()) {
							a = p.otaJonosta();
							a.setPoistumisaika(Kello.getInstance().getAika());
							a.raportti();
							break;
						}
					}
				}
		}
	}

	@Override
	protected void yritaCTapahtumat() {
		for (Palvelupiste p : checkInPisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			} else {
				Trace.out(Trace.Level.INFO, p.getNimi() + " piste on varattu tai ei jonoa. Jonon koko: " + p.jononKoko());
			}
		}
		for (Palvelupiste p : turvatarkastusPisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			} else {
				Trace.out(Trace.Level.INFO, p.getNimi() + " Turvatarkastus piste on varattu tai ei jonoa. Jonon koko: " + p.jononKoko());
			}
		}
		for (Palvelupiste p : porttiPisteet) {
			if (!p.onVarattu() && p.onJonossa()) {
				p.aloitaPalvelu();
			} else {
				Trace.out(Trace.Level.INFO, p.getNimi() + " Portti piste on varattu tai ei jonoa. Jonon koko: " + p.jononKoko());
			}
		}
	}

	@Override
	protected void tulokset() {
		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");

		kontrolleri.naytaLoppuaika(Kello.getInstance().getAika());
	}

	private Palvelupiste getShortestQueue(Palvelupiste[] pisteet) {
		Palvelupiste shortest = pisteet[0];
		for (Palvelupiste p : pisteet) {
			if (p.jononKoko() < shortest.jononKoko()) {
				shortest = p;
			}
		}
		return shortest;
	}
}