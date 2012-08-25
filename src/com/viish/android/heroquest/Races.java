package com.viish.android.heroquest;

public enum Races {
	Elfe,
	Drow,
	Nain,
	Orc,
	Goblin,
	Gnome,
	Fée,
	Humain,
	Demi_Elfe { public String toString() { return "Demi-Elfe"; }},
	Demi_Nain { public String toString() { return "Demi-Nain"; }},
	Demi_Drow { public String toString() { return "Demi-Drow"; }},
	Demi_Orc { public String toString() { return "Demi-Orc"; }},
	Skaven,
	Ogre;
	
	public static Races fromString(String race) {
		if (race.equals("Elfe")) {
			return Elfe;
		} else if (race.equals("Drow")) {
			return Drow;
		} else if (race.equals("Nain")) {
			return Nain;
		} else if (race.equals("Orc")) {
			return Orc;
		} else if (race.equals("Goblin")) {
			return Goblin;
		} else if (race.equals("Gnome")) {
			return Gnome;
		} else if (race.equals("Fée")) {
			return Fée;
		} else if (race.equals("Demi-Elfe")) {
			return Demi_Elfe;
		} else if (race.equals("Demi-Nain")) {
			return Demi_Nain;
		} else if (race.equals("Demi-Drow")) {
			return Demi_Drow;
		} else if (race.equals("Demi-Orc")) {
			return Demi_Orc;
		} else if (race.equals("Skaven")) {
			return Skaven;
		} else if (race.equals("Ogre")) {
			return Ogre;
		}
		return Humain;
	}
}
