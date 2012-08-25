package com.viish.android.heroquest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class Characters extends Activity implements OnClickListener, OnItemClickListener, OnItemLongClickListener 
{	
	private static final String[] races = {"Humain", "Elfe", "Drow", "Nain", "Orc", "Goblin", "Gnome", "Fée", "Demi-Elfe", "Demi-Drow", "Demi-Nain", "Demi-Orc", "Skaven", "Ogre"};
	private static final String[] classes = {"Assassin", "Barbare", "Mage", "Voleur", "Necromancien", "Moine", "Skaven", "Rodeur", "Pretre", "Ogre", "Barde", "Hobbit"};
	
	private  ArrayList<Object> characters;
	private String icon = "";
	private String characterSelected = "";
	private Typeface typeface;
	private ListView lv;
	private Spinner classe, race;
	
	public void onCreate(Bundle savedInstanceState) 
    {
    	Typeface tempTF = Typeface.createFromAsset(getAssets(), "HeroQuest.ttf");
        typeface = Typeface.create(tempTF, Typeface.BOLD);
        
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu);
        
        lv = (ListView) findViewById(R.id.characters);
        lv.setOnItemClickListener(this);	
        lv.setOnItemLongClickListener(this);
        
        updateList();
        
        initMenuItem((TextView) findViewById(R.id.newcharacter));
    }
	
	private void initMenuItem(TextView menu) {
		menu.setOnClickListener(this);
		menu.setTypeface(typeface);
	}

	private void updateList() 
	{
		DatabaseStream dbs = new DatabaseStream(this);
        characters = dbs.getCharacters();
        dbs.close();
        
        lv.setAdapter(new HQAdapter(this, typeface, R.layout.row, characters, "Character", false, true));		
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	{
		Character character = (Character) characters.get(position);
		Intent i = new Intent(this, Sheet.class);
		i.putExtra("Character", character);
		startActivityForResult(i, 0x1515);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {			
		case R.id.newcharacter:
			AlertDialog.Builder alert = new AlertDialog.Builder(this);
			final Context context = this;
			
			alert.setTitle("Nouveau Personnage");
			
			LinearLayout ll = new LinearLayout(this);
			ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			ll.setOrientation(LinearLayout.VERTICAL);
			final EditText etName = new EditText(this);
			etName.setHint("Nom du nouveau Héro ?");
			alert.setView(etName);
			ll.addView(etName);
			
			classe = new Spinner(this);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, classes);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			classe.setAdapter(adapter);
			ll.addView(classe);
			
			race = new Spinner(this);
			adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, races);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			race.setAdapter(adapter);
			ll.addView(race);
			
			Button bIcon = new Button(this);
			bIcon.setText("Icon");
			bIcon.setOnClickListener(new View.OnClickListener() 
			{
				public void onClick(View v) 
				{
					Intent i = new Intent(context, Icons.class);
					i.putExtra("Path", "");
					startActivityForResult(i, 0x1989);
				}
			});
			ll.addView(bIcon);
			
			alert.setView(ll);
			
			alert.setPositiveButton("Créer", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					String newHeroName = etName.getText().toString();
					DatabaseStream dbs = new DatabaseStream(context);
					dbs.createHero(newHeroName, (String) classe.getSelectedItem(), (String) race.getSelectedItem(), icon);
					if (((String) classe.getSelectedItem()).equals("Necromancien"))
					{
						dbs.addCapacity("Maîtrise de la Nécromancie", newHeroName);
					}
					else if (((String) classe.getSelectedItem()).equals("Skaven"))
					{
						dbs.addCapacity("Skaven", newHeroName);
					}
					else if (((String) classe.getSelectedItem()).equals("Moine"))
					{
						dbs.addCapacity("Moine", newHeroName);
					}
					else if (((String) classe.getSelectedItem()).equals("Barde"))
					{
						dbs.addCapacity("Barde", newHeroName);
						dbs.addItem("Triangle", newHeroName);
					}
					else if (((String) classe.getSelectedItem()).equals("Hobbit"))
					{
						dbs.addCapacity("Hobbit", newHeroName);
					}
					else if (((String) classe.getSelectedItem()).equals("Ogre"))
					{
						dbs.addCapacity("Ogre", newHeroName);
						dbs.addCapacity("Monstre", newHeroName);
						dbs.addCapacity("Chienchien", newHeroName);
						dbs.addCapacity("Peau Résistante", newHeroName);
						dbs.addCapacity("Gros débile", newHeroName);
						dbs.addCapacity("Affamé", newHeroName);
						dbs.addCapacity("Démarche Chaloupée", newHeroName);
						dbs.addCapacity("Acharnement", newHeroName);
						dbs.addCapacity("Vous ne passerez pas !", newHeroName);
						dbs.addCapacity("Pagne sans poche", newHeroName);
						dbs.addCapacity("Olvo Zlatoum PamPam", newHeroName);
						dbs.addCapacity("Charogne", newHeroName);
						dbs.addItem("Tronc", newHeroName);
					}
					dbs.close();
			        updateList();
				}
			});
			
			alert.show();
			break;
		}
	}

	public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) 
	{
		Character character = (Character) characters.get(position);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final String heroName = character.getName();
		final Context context = this;
		
		alert.setTitle(heroName);
		InputStream is;
		try 
		{
			is = getAssets().open(heroName.replace(" ", "_") + ".png");
			alert.setIcon(Drawable.createFromStream(is, heroName));
		} 
		catch (IOException e) {	}
		
		final EditText et = new EditText(this);
		et.setHint("Nouveau nom du Héro ?");
		alert.setView(et);
		
		alert.setPositiveButton("Rename", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				String newHeroName = et.getText().toString();
				DatabaseStream dbs = new DatabaseStream(context);
				dbs.renameHero(heroName, newHeroName);
				dbs.close();
				updateList();
			}
		});
		
		alert.setNeutralButton("Icon", new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				characterSelected = heroName;
				Intent i = new Intent(context, Icons.class);
				i.putExtra("Path", "");
				startActivityForResult(i, 0x1989);
			}
		});
		
		alert.setNegativeButton("Delete", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				DatabaseStream dbs = new DatabaseStream(context);
				dbs.deleteHero(heroName);
				dbs.close();
				updateList();
			}
		});
		
		alert.show();
		return false;
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		updateList();
		if (requestCode == 0x1989 && resultCode == RESULT_OK)
		{
			icon = (String) data.getExtras().get("IconName");
			icon = icon.substring(0, icon.lastIndexOf('.'));
			
			if (!characterSelected.equals(""))
			{
				DatabaseStream dbs = new DatabaseStream(this);
				dbs.updateHeroIcon(characterSelected ,icon);
				dbs.close();
				characterSelected = "";
				updateList();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
}