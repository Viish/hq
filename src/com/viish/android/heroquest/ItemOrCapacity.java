package com.viish.android.heroquest;

import android.graphics.Bitmap;

public class ItemOrCapacity
{
	private String name;
	private String desc;
	private String extra;
	private int level;
	private String iconName;
	private Bitmap icon;
	private boolean cumulable;
	private String classe;
	
	public ItemOrCapacity(String name, String desc, String extra, Bitmap bm, String iconName) 
	{
		setName(name);
		setDesc(desc);
		setExtra(extra);
		setIcon(bm);
		setIconName(iconName);
	}
	
	public ItemOrCapacity(String name, String desc, String cost, int level, Bitmap bm, String iconName) 
	{
		setName(name);
		setDesc(desc);
		setExtra(cost);
		setLevel(level);
		setIcon(bm);
		setIconName(iconName);
	}

	public ItemOrCapacity(String name, String desc, boolean isCumulable, String cost, int level, Bitmap bm, String iconName) 
	{
		setName(name);
		setDesc(desc);
		setExtra(cost);
		setLevel(level);
		setIcon(bm);
		setCumulable(isCumulable);
		setIconName(iconName);
	}

	public ItemOrCapacity(String name, String desc, String className, String cost, Bitmap bm, String icon) 
	{
		setName(name);
		setDesc(desc);
		setExtra(cost);
		setIcon(bm);
		setIconName(icon);
		setClasse(className);
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setDesc(String desc) 
	{
		this.desc = desc;
	}
	
	public String getDesc() 
	{
		return desc;
	}
	
	public void setExtra(String extra) 
	{
		this.extra = extra;
	}
	
	public String getExtra() 
	{
		return extra;
	}
	
	public void setLevel(int level) 
	{
		this.level = level;
	}
	
	public int getLevel() 
	{
		return level;
	}
	
	public void setIcon(Bitmap icon) 
	{
		this.icon = icon;
	}
	
	public Bitmap getIcon() 
	{
		return icon;
	}
	
	public void setCumulable(boolean cumulable) 
	{
		this.cumulable = cumulable;
	}
	
	public boolean isCumulable() 
	{
		return cumulable;
	}

	public void setIconName(String iconName) 
	{
		this.iconName = iconName;
	}

	public String getIconName() 
	{
		return iconName;
	}

	public void setClasse(String classe) 
	{
		this.classe = classe;
	}

	public String getClasse() 
	{
		return classe;
	}
}
