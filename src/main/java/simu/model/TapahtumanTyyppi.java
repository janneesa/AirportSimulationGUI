package simu.model;

import simu.framework.ITapahtumanTyyppi;

// TODO:
// Tapahtumien tyypit määritellään simulointimallin vaatimusten perusteella
public enum TapahtumanTyyppi implements ITapahtumanTyyppi{
	ARRIVAL, CHECK_IN_VALMIS, SELF_CHECK_IN_VALMIS, TURVATARKASTUS_VALMIS, PORTTI_VALMIS;

}