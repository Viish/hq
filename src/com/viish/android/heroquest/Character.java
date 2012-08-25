package com.viish.android.heroquest;

import java.io.Serializable;

public class Character implements Serializable
{
	private static final long serialVersionUID = -4929122020815956744L;
	private String avatar;
	private String name;
	private int po, esprit, corps, courage;
	private double level;
	private Classes classe;
	private Races race;
	
	public Character(String name, Classes classe, Races race, double level, int po, int esprit, int corps, String avatar) 
	{
		setName(name);
		setClasse(classe);
		setRace(race);
		setLevel(level);
		setPo(po);
		setEsprit(esprit);
		setCorps(corps);
		setAvatar(avatar);		
	}
	
	public void setRace(Races r) {
		this.race = r;
	}
	
	public Races getRace() {
		return this.race;
	}

	public void setCourage(int c)
	{
		this.courage = c;
	}
	
	public int getCourage()
	{
		return this.courage;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setLevel(double level) 
	{
		this.level = level;
	}
	
	public double getLevel() 
	{
		return level;
	}
	
	public void setPo(int po) 
	{
		this.po = po;
	}
	
	public int getPo() 
	{
		return po;
	}
	
	public void setClasse(Classes classe) 
	{
		this.classe = classe;
	}
	
	public Classes getClasse() 
	{
		return classe;
	}

	public void setEsprit(int esprit) 
	{
		this.esprit = esprit;
	}

	public int getEsprit() 
	{
		return esprit;
	}

	public void setCorps(int corps) 
	{
		this.corps = corps;
	}

	public int getCorps() 
	{
		return corps;
	}

	public void setAvatar(String avatar) 
	{
		this.avatar = avatar;
	}

	public String getAvatar() 
	{
		return avatar;
	}

	public void incrementEsprit(int bonus) 
	{
		esprit += bonus;
	}

	public void incrementCorps(int bonus) 
	{
		corps += bonus;
	}

	public void decrementCorps(int malus) 
	{
		corps -= malus;
	}

	public void decrementEsprit(int malus) 
	{
		esprit -= malus;
	}
}
