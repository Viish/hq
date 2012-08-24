package com.viish.android.heroquest;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class DatabaseStream 
{
	private SQLiteConnector connector;
	private SQLiteDatabase stream;
	private Context context;
	
	public DatabaseStream(Context c)
	{
		context = c;
		connector = new SQLiteConnector(context, "HeroQuest", 82);
		stream = connector.getWritableDatabase();
	}
	
	public void close()
	{
		connector.close();
	    stream.close();
	}

	public void addItem(String itemName, String heroName) {
		Cursor cursor = stream.query("HeroesItems", null, "HeroName LIKE \"" + heroName + "\" AND ItemName LIKE \"" + itemName + "\"", null, null, null, null);
		
	    ContentValues contentValues = new ContentValues();
	    if ((cursor != null) && (cursor.move(1)))
	    {
	    	int i = cursor.getInt(cursor.getColumnIndex("Amount"));
	    	contentValues.put("Amount", Integer.valueOf(i + 1));
	    } else {
	    	contentValues.put("Amount", Integer.valueOf(0));
	    }
	    
	    stream.update("HeroesItems", contentValues, "HeroName LIKE \"" + heroName + "\" AND ItemName LIKE \"" + itemName + "\"", null);
	}

	public void removeItem(String itemName, String name) {
		//TODO
	}

	public void deleteItem(String itemName, String name) {
		stream.delete("HeroesItems", "HeroName LIKE \"" + name + "\" AND ItemName LIKE \"" + itemName + "\"", null);
	}

	public void deleteItem(String itemName) {
		stream.delete("Items", "ItemName LIKE \"" + itemName + "\"", null);
		
	    for (Object caracter : getCharacters()) {
	        deleteItem(itemName, ((Character) caracter).getName());
	    }
	}

	public ArrayList<Object> getItems(String heroName) {
		ArrayList<Object> items = new ArrayList<Object>();
		
		Cursor cursor = stream.query("HeroesItems", null, "HeroName LIKE \"" + heroName + "\"", null, null, null, null);
		if (cursor != null) {
		    int indexItem = cursor.getColumnIndex("ItemName");
		    int indexCount = cursor.getColumnIndex("Amount");
			
		    while (cursor.move(1)) {
		    	items.add(getItem(cursor.getString(indexItem), cursor.getInt(indexCount)));
		    }
		    
			cursor.close();
		}
		
		return items;
	}
	
	public Object getItem(String itemName, int count) {
		Cursor cursor = stream.query("Items", null, "ItemName LIKE \"" + itemName + "\"", null, null, null, null);
		if (cursor != null && cursor.move(1)) {
	        int indexDesc = cursor.getColumnIndex("ItemDesc");
	        int indexIcon = cursor.getColumnIndex("Icon");
	        int indexCategory = cursor.getColumnIndex("ItemCat");
	        int indexPrice = cursor.getColumnIndex("Price");
	        
	        String description = cursor.getString(indexDesc);
	        String icon = cursor.getString(indexIcon);
	        int price = cursor.getInt(indexPrice);
	        int category = cursor.getInt(indexCategory);
	        
		      
			try
			{
		        Bitmap bitmap = BitmapFactory.decodeStream(this.context.getResources().getAssets().open("Objets/" + icon.toLowerCase().replace(" ", "_") + ".gif"));
		        ItemOrCapacity item = new ItemOrCapacity(itemName, description, String.valueOf(price), category, bitmap, icon);
				cursor.close();
				return item;
			}
			catch (IOException localIOException)
			{
			}
			
			ItemOrCapacity item = new ItemOrCapacity(itemName, description, String.valueOf(price), category, null, icon);
			cursor.close();
			return item;
		}
		return null;
	}

	public ArrayList<Object> getKnownItems() {
		ArrayList<Object> items = new ArrayList<Object>();
		
		Cursor cursor = stream.query("Items", null, null, null, null, null, "ItemCat DESC, ItemName ASC");
		if (cursor != null) {
		    int indexItem = cursor.getColumnIndex("ItemName");
			
		    while (cursor.move(1)) {
		    	items.add(getItem(cursor.getString(indexItem), 0));
		    }
		    
			cursor.close();
		}
		
		return items;
	}

	public void updateItem(String name, String desc, int category, String icon,
			int price) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put("ItemName", name);
	    contentValues.put("ItemDesc", desc);
	    if ((icon != null) && (icon != "")) {
	    	contentValues.put("Icon", icon);
	    }
	    contentValues.put("ItemCat", Integer.valueOf(category));
	    contentValues.put("Price", Integer.valueOf(price));
	    
	    stream.update("Items", contentValues, "ItemName LIKE \"" + name + "\"", null);
	}

	public void createItem(String name, String desc, int category, String icon,
			int price) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put("ItemName", name);
	    contentValues.put("ItemDesc", desc);
	    contentValues.put("Icon", icon);
	    contentValues.put("ItemCat", Integer.valueOf(category));
	    contentValues.put("Price", Integer.valueOf(price));
	    
	    stream.insert("Items", null, contentValues);
	}

	public void createHero(String newHeroName, String classeName, String icon) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put("HeroName", newHeroName);
	    contentValues.put("Avatar", icon);
	    contentValues.put("Class", classeName);
	    contentValues.put("Level", Integer.valueOf(1));
	    contentValues.put("PO", Integer.valueOf(0));
	    contentValues.put("Cola", Integer.valueOf(0));
	    contentValues.put("Wood", Integer.valueOf(0));
	    
	    if (classeName.equals("Assassin"))
	    {
	    	contentValues.put("Esprit", Integer.valueOf(5));
	    	contentValues.put("Corps", Integer.valueOf(5));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Barbare"))
	    {
	    	contentValues.put("Esprit", Integer.valueOf(2));
	    	contentValues.put("Corps", Integer.valueOf(8));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Mage"))
    	{
	        contentValues.put("Esprit", Integer.valueOf(6));
	        contentValues.put("Corps", Integer.valueOf(4));
    	    contentValues.put("Courage", Integer.valueOf(0));
    	}
	    else if (classeName.equals("Voleur"))
	    {
	        contentValues.put("Esprit", Integer.valueOf(4));
	        contentValues.put("Corps", Integer.valueOf(6));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Necromancien"))
	    {
	        contentValues.put("Esprit", Integer.valueOf(7));
	        contentValues.put("Corps", Integer.valueOf(3));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Skaven"))
	    {
	        contentValues.put("Esprit", Integer.valueOf(4));
	        contentValues.put("Corps", Integer.valueOf(4));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Moine"))
	    {
	        contentValues.put("Esprit", Integer.valueOf(5));
	        contentValues.put("Corps", Integer.valueOf(5));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Rodeur"))
	    {
	        contentValues.put("Esprit", Integer.valueOf(3));
	        contentValues.put("Corps", Integer.valueOf(7));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Pretre"))
	    {
	        contentValues.put("Esprit", Integer.valueOf(6));
	        contentValues.put("Corps", Integer.valueOf(4));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Ogre"))
	    {
	        contentValues.put("Esprit", Integer.valueOf(1));
	        contentValues.put("Corps", Integer.valueOf(9));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Barde"))
	    {
	        contentValues.put("Esprit", Integer.valueOf(5));
	        contentValues.put("Corps", Integer.valueOf(5));
    	    contentValues.put("Courage", Integer.valueOf(0));
	    }
	    else if (classeName.equals("Hobbit")) {
	    	contentValues.put("Esprit", Integer.valueOf(4));
	    	contentValues.put("Corps", Integer.valueOf(3));
	    	contentValues.put("Courage", Integer.valueOf(3));
	    }
	    
	    stream.insert("Characters", null, contentValues);
	}

	public ArrayList<Object> getCharacters() {
		ArrayList<Object> characters = new ArrayList<Object>();
		
		Cursor cursor = stream.query("Characters", null, null, null, null, null, "Level DESC");
		if (cursor != null) {
			int indexName = cursor.getColumnIndex("HeroName");
		    int indexClass = cursor.getColumnIndex("Class");
		    int indexIcon = cursor.getColumnIndex("Avatar");
		    int indexLevel = cursor.getColumnIndex("Level");
		    int indexCorps = cursor.getColumnIndex("Corps");
		    int indexEsprit = cursor.getColumnIndex("Esprit");
		    int indexPOs = cursor.getColumnIndex("PO");
		    int indexCourage = cursor.getColumnIndex("Courage");
			
		    while (cursor.move(1)) {
		    	String name = cursor.getString(indexName);
		    	double level = cursor.getDouble(indexLevel);
		    	int po = cursor.getInt(indexPOs);
		    	int esprit = cursor.getInt(indexEsprit);
		    	int corps = cursor.getInt(indexCorps);
		    	String icon = cursor.getString(indexIcon);
		    	String classe = cursor.getString(indexClass);
		    	
		    	Character character = new Character(name, Classes.fromString(classe), level, po, esprit, corps, icon);
		    	if (classe.equals("Hobbit")) {
		    		character.setCourage(cursor.getInt(indexCourage));
		    	}
		    	characters.add(character);
		    }
		    
			cursor.close();
		}
		
		return characters;
	}

	public void renameHero(String heroName, String newHeroName) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put("HeroName", newHeroName);
	    
	    stream.update("Characters", contentValues, "HeroName LIKE \"" + heroName + "\"", null);
	    stream.update("HeroesItems", contentValues, "HeroName LIKE \"" + heroName + "\"", null);
	    stream.update("HeroesCapacities", contentValues, "HeroName LIKE \"" + heroName + "\"", null);
	}

	public void deleteHero(String heroName) {
		stream.delete("Characters", "HeroName LIKE \"" + heroName + "\"", null);
	    stream.delete("HeroesItems", "HeroName LIKE \"" + heroName + "\"", null);
	    stream.delete("HeroesCapacities", "HeroName LIKE \"" + heroName + "\"", null);
	}

	public void updateHero(String heroName, String valueToUpdate, int value) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put(valueToUpdate, value);
	    
	    stream.update("Characters", contentValues, "HeroName LIKE \"" + heroName + "\"", null);
	}
	
	public void updateHero(String heroName, String valueToUpdate, String value) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put(valueToUpdate, value);
	    
	    stream.update("Characters", contentValues, "HeroName LIKE \"" + heroName + "\"", null);
	}
	
	public void updateHero(String heroName, String valueToUpdate, double value) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put(valueToUpdate, value);
	    
	    stream.update("Characters", contentValues, "HeroName LIKE \"" + heroName + "\"", null);
	}

	public void updateHeroIcon(String heroName, String icon) {
		updateHero(heroName, "Avatar", icon);
	}

	public void updateHeroLevel(String heroName, double level) {
		updateHero(heroName, "Level", level);
	}

	public void updateHeroPO(String heroName, String POs) {
		updateHero(heroName, "PO", POs);
	}

	public void updateHeroCourage(String heroName, String courage) {
		updateHero(heroName, "Courage", courage);
	}

	public void addCapacity(String capacityName, String heroName) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put("HeroName", heroName);
	    contentValues.put("CapacityName", capacityName);
	    
	    Cursor cursor = this.stream.query("HeroesCapacities", null, "HeroName LIKE \"" + heroName + "\" AND CapacityName LIKE \"" + capacityName + "\"", null, null, null, null);
	    if (cursor != null && cursor.move(1)) {
	    	contentValues.put("Level", Integer.valueOf(1 + cursor.getInt(cursor.getColumnIndex("Level"))));

	    	stream.update("HeroesCapacities", contentValues, "HeroName LIKE \"" + heroName + "\" AND CapacityName LIKE \"" + capacityName + "\"", null);
	    } else {
	    	if (!capacityName.equals("MaÎtrise de la NÉcromancie")) {
	    	      contentValues.put("Level", Integer.valueOf(1));
	    	}
	    	else {
	    	      contentValues.put("Level", Integer.valueOf(0));
	    	}
		    
	    	stream.insert("HeroesCapacities", null, contentValues);
	    }
	}

	public ArrayList<Object> getCapacities(String heroName) {
		ArrayList<Object> capacities = new ArrayList<Object>();
		
		Cursor cursor = stream.query("HeroesCapacities", null, "HeroName LIKE \"" + heroName + "\"", null, null, null, null);
		if (cursor != null) {
		    int indexCapacity = cursor.getColumnIndex("CapacityName");
		    int indexLevel = cursor.getColumnIndex("Level");
			
		    while (cursor.move(1)) {
		    	capacities.add(getCapacity(cursor.getString(indexCapacity), cursor.getInt(indexLevel)));
		    }
		    
			cursor.close();
		}
		
		return capacities;
	}
	
	public Object getCapacity(String capacityName, int level) {
		Cursor cursor = stream.query("ClassCapacities", null, "Name LIKE \"" + capacityName + "\"", null, null, null, "Name ASC");
		if (cursor != null && cursor.move(1)) {
			int indexDesc = cursor.getColumnIndex("Desc");
			int indexCost = cursor.getColumnIndex("Cost");
			int indexIcon = cursor.getColumnIndex("Icon");
			int indexClasse = cursor.getColumnIndex("Class");
			int indexCumulable = cursor.getColumnIndex("Cumulable");
		    int indexDescCumul = cursor.getColumnIndex("DescCumul");

			String desc = cursor.getString(indexDesc);
			String descCumul = cursor.getString(indexDescCumul);
			String cost = cursor.getString(indexCost);
			String icon = cursor.getString(indexIcon);
			String classe = cursor.getString(indexClasse);
			boolean cumulable = cursor.getInt(indexCumulable) == 1;
		      
			try
			{
		        Bitmap bitmap = BitmapFactory.decodeStream(this.context.getResources().getAssets().open("Capacites/" + icon.toLowerCase().replace(" ", "_") + ".gif"));
				ItemOrCapacity capacity = new ItemOrCapacity(capacityName, desc + "\n" + descCumul, cumulable, cost, level, bitmap, icon);
				capacity.setClasse(classe);
				cursor.close();
				return capacity;
			}
			catch (IOException e)
			{
			}
			
			ItemOrCapacity capacity = new ItemOrCapacity(capacityName, desc + "\n" + descCumul, cumulable, cost, level, null, icon);
			capacity.setClasse(classe);
			cursor.close();
			return capacity;
		}
		return null;
	}

	public void removeCapacity(String capacityName, String heroName) {
		//TODO
	}

	public ArrayList<Object> getClassCapacities(String classe) {
		ArrayList<Object> capacities = new ArrayList<Object>();
		
		Cursor cursor = this.stream.query("ClassCapacities", null, "Class LIKE \"" + classe + "\"", null, null, null, "Name ASC");
		if (cursor != null) {
		    int indexCapacity = cursor.getColumnIndex("Name");
			
		    while (cursor.move(1)) {
		    	capacities.add(getCapacity(cursor.getString(indexCapacity), 0));
		    }
		    
			cursor.close();
		}
		
		return capacities;
	}

	public void modifyCapacity(String capacityName, String name,
			String cost, String desc) {
		ContentValues contentValues = new ContentValues();
	    contentValues.put("Desc", desc);
	    contentValues.put("Name", name);
	    contentValues.put("Cost", cost);
	    
	    stream.update("ClassCapacities", contentValues, "Name LIKE \"" + capacityName + "\"", null);
	}
}
