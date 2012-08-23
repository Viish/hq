package com.viish.android.heroquest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;

public class Characters extends ListActivity implements OnItemClickListener, OnItemLongClickListener 
{	
	private  ArrayList<Object> characters;
	private String icon = "";
	private String characterSelected = "";
	private Typeface typeface;
	
    @SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
    {
    	Typeface tempTF = Typeface.createFromAsset(getAssets(), "HeroQuest.ttf");
        typeface = Typeface.create(tempTF, Typeface.BOLD);
        
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        updateList();
        
        getListView().setOnItemClickListener(this);	
        getListView().setOnItemLongClickListener(this);
        
        getListView().setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
    }

	private void updateList() 
	{
		DatabaseStream dbs = new DatabaseStream(this);
        characters = dbs.getCharacters();
        dbs.close();
        
        getListView().setAdapter(new HQAdapter(this, typeface, R.layout.row, characters, "Character", false, true));		
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	{
		Character character = (Character) characters.get(position);
		Intent i = new Intent(this, Sheet.class);
		i.putExtra("Character", character);
		startActivityForResult(i, 0x1515);
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
		
		alert.setPositiveButton("Rename", new OnClickListener()
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
		
		alert.setNeutralButton("Icon", new OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				characterSelected = heroName;
				Intent i = new Intent(context, Icons.class);
				i.putExtra("Path", "");
				startActivityForResult(i, 0x1989);
			}
		});
		
		alert.setNegativeButton("Delete", new OnClickListener()
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