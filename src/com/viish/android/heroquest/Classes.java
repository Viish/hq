package com.viish.android.heroquest;

public enum Classes 
{
	All,
	Barbare, 
	Mage, 
	Voleur, 
	Assassin, 
	Necromancien { public String toShortString() { return "Necro"; }},
	Skaven, 
	Moine, 
	Rodeur, 
	Pretre { public String toString() { return "Prêtre"; }}, 
	Ogre, 
	Hobbit, 
	Barde;
	
	public String toShortString()
	{
		return this.toString();
	}
	
	public static Classes fromString(String classe) {
		if (classe.equals("Barbare")) {
			return Barbare;
		} else if (classe.equals("Mage")) {
			return Mage;
		} else if (classe.equals("Voleur")) {
			return Voleur;
		} else if (classe.equals("Assassin")) {
			return Assassin;
		} else if (classe.equals("Necromancien")) {
			return Necromancien;
		} else if (classe.equals("Skaven")) {
			return Skaven;
		} else if (classe.equals("Moine")) {
			return Moine;
		} else if (classe.equals("Rodeur")) {
			return Rodeur;
		} else if (classe.equals("Pretre")) {
			return Pretre;
		} else if (classe.equals("Ogre")) {
			return Ogre;
		} else if (classe.equals("Hobbit")) {
			return Hobbit;
		} else if (classe.equals("Barde")) {
			return Barde;
		}
		return All;
	}
}