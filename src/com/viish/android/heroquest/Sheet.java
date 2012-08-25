package com.viish.android.heroquest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class Sheet extends TabActivity implements View.OnClickListener, OnItemClickListener
{
	private static final long ANIMATION_DURATION = 500;
	
	private Typeface typeface;
	private Character character;
	private ArrayList<Object> items, capacities;
	private int espritTemp, corpsTemp;
	private String notes;
	private TextView notesTV;
	private ListView capacitiesLV;
	private ListView itemsLV;
	private WakeLock wl;
	
	public void onCreate(Bundle savedInstanceState) 
    {
    	Typeface tempTF = Typeface.createFromAsset(getAssets(), "HeroQuest.ttf");
        typeface = Typeface.create(tempTF, Typeface.BOLD);
        
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sheet);
        
        character = (Character) this.getIntent().getExtras().getSerializable("Character");
        if (character == null)
        {
        	setResult(RESULT_CANCELED);
        	finish();
        	return;
        }
        
        espritTemp = character.getEsprit();
        corpsTemp = character.getCorps();
        
		afficherStats();
		afficherObjetsEtCapacites(false);
		initButtons();
		
		if (wl == null)
		{
			PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
	        wl = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "HQ");
	        wl.acquire();
		}
    }
	
	private void initTypeFace(TextView menu) {
		menu.setTypeface(typeface);
	}

	private void afficherObjetsEtCapacites(boolean update) 
	{
		if (!update)
		{
			TabHost th = getTabHost();
			final TabSpec tsItems = th.newTabSpec("items").setIndicator("Objets", getResources().getDrawable(R.drawable.item)).setContent(R.id.Items);
		    th.addTab(tsItems);
		    final TabSpec tsCapacities = th.newTabSpec("capacities").setIndicator("Capacités", getResources().getDrawable(R.drawable.capacity)).setContent(R.id.Capacities);
		    th.addTab(tsCapacities);
		    final TabSpec tsNotes = th.newTabSpec("notes").setIndicator("Notes", getResources().getDrawable(R.drawable.pokemon)).setContent(R.id.Notes);
			th.addTab(tsNotes);
			
		    th.setCurrentTab(1);
		    th.getTabWidget().setLongClickable(true);
		    th.getTabWidget().getChildTabViewAt(0).setOnLongClickListener(new OnLongClickListener()
		    {
				public boolean onLongClick(View v) 
				{
					showItems();
					return true;
				}
			});
		    th.getTabWidget().getChildTabViewAt(1).setOnLongClickListener(new OnLongClickListener()
		    {
				public boolean onLongClick(View v) 
				{
					showCapacities();
					return true;
				}
			});
		}
	    
		itemsLV = (ListView) findViewById(R.id.Items);
		itemsLV.setOnItemClickListener(this);
		capacitiesLV = (ListView) findViewById(R.id.Capacities);
		capacitiesLV.setOnItemClickListener(this);
		
		DatabaseStream dbs = new DatabaseStream(this);
		
		items = sortList(dbs.getItems(character.getName()));
		capacities = dbs.getCapacities(character.getName());
		itemsLV.setAdapter(new HQAdapter(this, typeface, R.layout.row, items, "ItemOrCapacity", false, true));
		capacitiesLV.setAdapter(new HQAdapter(this, typeface, R.layout.row, capacities, "ItemOrCapacity", false, false));
		notes = dbs.getHeroNote(character.getName());
		
		dbs.close();		
		
		notesTV = (TextView) findViewById(R.id.Notes);
		notesTV.setOnClickListener(this);
		notesTV.setText(notes);
	}
	
	public void onClick(View v) 
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		alert.setTitle("Notes");
		
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		final EditText etNote = new EditText(this);
		etNote.setMinLines(notes.split("\n").length);
		etNote.setText(notes);
		ll.addView(etNote);
		alert.setView(ll);
		
		alert.setPositiveButton("Enregistrer", new OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				notes = etNote.getText().toString();
				DatabaseStream dbs = new DatabaseStream(Sheet.this);
				dbs.modifyHeroNote(character.getName(), notes);
				dbs.close();
				notesTV.setText(notes);
			}
		});
		
		alert.show();
	}

	private void afficherStats() 
	{
		TextView nom = (TextView) findViewById(R.id.Name); nom.setText(character.getName());
		TextView classe = (TextView) findViewById(R.id.Classe); classe.setText(character.getClasse().toString());
		TextView race = (TextView) findViewById(R.id.Race); race.setText(character.getRace().toString());
		TextView level = (TextView) findViewById(R.id.Level); level.setText("Level " + character.getLevel());
		final TextView po = (TextView) findViewById(R.id.PO); po.setText(" " + character.getPo());
		ImageView dpo = (ImageView) findViewById(R.id.dPO);
		TextView esprit = (TextView) findViewById(R.id.Esprit); esprit.setText(" " + espritTemp + "  ");
		TextView corps = (TextView) findViewById(R.id.Corps); corps.setText(" " + corpsTemp + "  ");
		ImageView avatar = (ImageView) findViewById(R.id.Avatar);
		TextView courage = (TextView) findViewById(R.id.Courage); courage.setText(" " + character.getCourage());
		
		initTypeFace(nom);
		initTypeFace(classe);
		initTypeFace(race);
		initTypeFace(level);
		initTypeFace(po);
		initTypeFace(esprit);
		initTypeFace(corps);
		initTypeFace(courage);
		
		final Context context = this;
		View.OnClickListener ocl = new View.OnClickListener()
		{
			public void onClick(View v) 
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(context);				
				alert.setTitle("PO !");
				final EditText etPO = new EditText(context);
				etPO.setText(String.valueOf(character.getPo()));
				alert.setView(etPO);
				alert.setInverseBackgroundForced(true);
				
				alert.setPositiveButton("Change",  new OnClickListener() 
				{
					public void onClick(DialogInterface dialog, int which) 
					{
						character.setPo(Integer.parseInt(etPO.getText().toString()));
						po.setText(" " + character.getPo());
						DatabaseStream dbs = new DatabaseStream(context);
						dbs.updateHeroPO(character.getName(), String.valueOf(character.getPo()));
						dbs.close();
					}
				});
				alert.show();
			}
		};
		po.setOnClickListener(ocl);
		dpo.setOnClickListener(ocl);
		
		Bitmap bm = null;
		try 
		{
			bm = BitmapFactory.decodeStream(getAssets().open(character.getAvatar() + ".png"));
		} 
		catch (IOException e) 
		{
			try 
			{
				bm = BitmapFactory.decodeStream(getAssets().open(character.getAvatar() + ".jpg"));
			} 
			catch (IOException e1) {}
		}
		avatar.setImageBitmap(bm);
		
		avatar.setOnLongClickListener(new View.OnLongClickListener() 
		{
			public boolean onLongClick(View v) 
			{
				DatabaseStream dbs = new DatabaseStream(context);
				character.setLevel(character.getLevel() - 0.5);
				dbs.updateHeroLevel(character.getName(), character.getLevel());
				dbs.close();
				afficherStats();
				return true;
			}
		});
		avatar.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				DatabaseStream dbs = new DatabaseStream(context);
				character.setLevel(character.getLevel() + 0.5);
				dbs.updateHeroLevel(character.getName(), character.getLevel());
				dbs.close();
				afficherStats();
			}
		});
	}
	
	public void onItemClick(final AdapterView<?> av, View v, final int position, long id) 
	{
		if (av.equals(itemsLV))
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(this);  
			final ItemOrCapacity item = (ItemOrCapacity) items.get(position);
			
			final String itemName = item.getName();
			String iconName = item.getIconName();
			InputStream is;
			try 
			{
				is = getAssets().open("Objets/" + iconName.toLowerCase().replace(" ", "_") + ".gif");
				alert.setIcon(Drawable.createFromStream(is, iconName));
			} 
			catch (IOException e) {	}
			
			alert.setTitle(itemName);
			alert.setMessage(item.getDesc());
			alert.setInverseBackgroundForced(true);
			
			final DatabaseStream dbs = new DatabaseStream(this);
			alert.setPositiveButton("+",  new OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					dbs.addItem(itemName, character.getName());
					afficherObjetsEtCapacites(true);
					dbs.close();
				}
			});
			alert.setNeutralButton("-",  new OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					dbs.removeItem(itemName, character.getName());
					afficherObjetsEtCapacites(true);
					dbs.close();
				}
			});
			alert.setNegativeButton("X",  new OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					dbs.deleteItem(itemName, character.getName());
					afficherObjetsEtCapacites(true);
					dbs.close();
				}
			});
			alert.show();
		}
		else if (av.equals(capacitiesLV))
		{
			AlertDialog.Builder alert = new AlertDialog.Builder(this);  
			final ItemOrCapacity capacity = (ItemOrCapacity) capacities.get(position);
			
			final String capacityName = capacity.getName();
			String iconName = capacity.getIconName();
			InputStream is;
			try 
			{
				is = getAssets().open("Capacites/" + iconName.toLowerCase().replace(" ", "_") + ".gif");
				alert.setIcon(Drawable.createFromStream(is, iconName));
			} 
			catch (IOException e) {	}
			
			alert.setTitle(capacityName);
			alert.setMessage(capacity.getDesc());
			alert.setInverseBackgroundForced(true);
			
			final DatabaseStream dbs = new DatabaseStream(this);
			alert.setPositiveButton("Supprimer",  new OnClickListener() 
			{
				public void onClick(DialogInterface dialog, int which) 
				{
					dbs.removeCapacity(capacityName, character.getName());
					afficherObjetsEtCapacites(true);
					dbs.close();
				}
			});
			alert.show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == 0x1337 && resultCode == RESULT_OK)
		{
			String selectedCapacity = data.getStringExtra("SelectedCapacity");
			DatabaseStream dbs = new DatabaseStream(this);
			dbs.addCapacity(selectedCapacity, character.getName());
			dbs.close();
		}
		else if (requestCode == 0x1664 && resultCode == RESULT_OK)
		{
			// Item choisi
			String selectedItem = data.getStringExtra("SelectedItem");
			DatabaseStream dbs = new DatabaseStream(this);
			dbs.addItem(selectedItem, character.getName());
			dbs.close();
		}

		afficherObjetsEtCapacites(true);
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
    {
    	super.onCreateOptionsMenu(menu);
    	
    	LinearLayout ll = (LinearLayout) findViewById(R.id.buttons);
    	if (ll.getVisibility() == View.VISIBLE)
    	{
    		ll.setVisibility(View.GONE);
    	}
    	else
    	{
    		ll.setVisibility(View.VISIBLE);
    	}
    	return false;
    }
    
	private void showCapacities()
	{
		Intent i = new Intent(this, Capacities.class);
		i.putExtra("Class", character.getClasse().toString());
		startActivityForResult(i, 0x1337);
	}
	
	private void showItems()
	{
		Intent i2 = new Intent(this, Items.class);
		startActivityForResult(i2, 0x1664);
	}

	private void initButtons() 
	{        
		LinearLayout ll = (LinearLayout) findViewById(R.id.buttons);
		ll.setVisibility(View.GONE);
		
		Button eP, eM, cP, cM, courageP, courageM;
		eP = (Button) findViewById(R.id.bEspritP); eM = (Button) findViewById(R.id.bEspritM);
		cP = (Button) findViewById(R.id.bCorpsP); cM = (Button) findViewById(R.id.bCorpsM);
		
		eP.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { incrementEsprit(1); } });
		eP.setOnLongClickListener(new OnLongClickListener() {public boolean onLongClick(View v) { incrementEspritDefinitively(1); return true; }});
		eM.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { decrementEsprit(1); } });
		eM.setOnLongClickListener(new OnLongClickListener() {public boolean onLongClick(View v) { decrementEspritDefinitively(1); return true; }});
		cP.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { incrementCorps(1); } });
		cP.setOnLongClickListener(new OnLongClickListener() {public boolean onLongClick(View v) { incrementCorpsDefinitively(1); return true; }});
		cM.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { decrementCorps(1); } });
		cM.setOnLongClickListener(new OnLongClickListener() {public boolean onLongClick(View v) { decrementCorpsDefinitively(1); return true; }});
		
		courageP = (Button) findViewById(R.id.bCourageP); courageM = (Button) findViewById(R.id.bCourageM);
		if (character.getClasse() == Classes.Hobbit)
		{
			courageP.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { incrementCourage(1); } });
			courageM.setOnClickListener(new View.OnClickListener() { public void onClick(View v) { decrementCourage(1); } });
		}
		else
		{
			courageP.setVisibility(View.GONE);
			courageM.setVisibility(View.GONE);
			((ImageView) findViewById(R.id.dCourage)).setVisibility(View.GONE);
			((TextView) findViewById(R.id.Courage)).setVisibility(View.GONE);
		}
	}
	
	private void incrementCourage(int bonus)
	{
		character.setCourage(character.getCourage() + (bonus));
		DatabaseStream dbs = new DatabaseStream(this);
    	dbs.updateHeroCourage(character.getName(), String.valueOf(character.getCourage()));
    	dbs.close();
    	afficherStats();
	}
	
	private void decrementCourage(int malus)
	{
		character.setCourage(character.getCourage() - (malus));
		DatabaseStream dbs = new DatabaseStream(this);
    	dbs.updateHeroCourage(character.getName(), String.valueOf(character.getCourage()));
    	dbs.close();
    	afficherStats();
	}
    
    private void incrementCorpsDefinitively(int bonus)
    {
    	character.incrementCorps(bonus);
    	DatabaseStream dbs = new DatabaseStream(this);
    	dbs.updateHero(character.getName(), "Corps", character.getCorps());
    	dbs.close();
    	corpsTemp = character.getCorps();
    	afficherStats();
    }
    
    private void decrementCorpsDefinitively(int malus)
    {
    	character.decrementCorps(malus);
    	DatabaseStream dbs = new DatabaseStream(this);
    	dbs.updateHero(character.getName(), "Corps", character.getCorps());
    	dbs.close();
    	corpsTemp = character.getCorps();
    	afficherStats();
    }
    
    private void incrementCorps(int bonus)
    {
    	if (corpsTemp + bonus <= character.getCorps()) corpsTemp += bonus;
    	else corpsTemp = character.getCorps();
    	afficherStats();
    }
    
    private void decrementCorps(int malus)
    {
    	if (corpsTemp - malus >= 0) corpsTemp -= malus;
    	else corpsTemp = 0;
    	afficherStats();
    }
    
    private void incrementEspritDefinitively(int bonus)
    {
    	character.incrementEsprit(bonus);
    	DatabaseStream dbs = new DatabaseStream(this);
    	dbs.updateHero(character.getName(), "Esprit", character.getEsprit());
    	dbs.close();
    	espritTemp = character.getEsprit();
    	afficherStats();
    }
    
    private void decrementEspritDefinitively(int malus)
    {
    	character.decrementEsprit(malus);
    	DatabaseStream dbs = new DatabaseStream(this);
    	dbs.updateHero(character.getName(), "Esprit", character.getEsprit());
    	dbs.close();
    	espritTemp = character.getEsprit();
    	afficherStats();
    }
    
    private void incrementEsprit(int bonus)
    {
    	if (espritTemp + bonus <= character.getEsprit()) espritTemp += bonus;
    	else espritTemp = character.getEsprit();
    	afficherStats();
    }
    
    private void decrementEsprit(int malus)
    {
    	if (espritTemp - malus >= 0) espritTemp -= malus;
    	else espritTemp = 0;
    	afficherStats();
    }
    
    public Animation inFromRightAnimation() 
	{
		Animation inFromRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(ANIMATION_DURATION);
		inFromRight.setInterpolator(new AccelerateInterpolator());
		
		return inFromRight;
	}
	
	public Animation outToLeftAnimation() 
	{
		
		Animation outtoLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(ANIMATION_DURATION);
		outtoLeft.setInterpolator(new AccelerateInterpolator());
		
		return outtoLeft;
	}
	
	public Animation inFromLeftAnimation() 
	{
		Animation inFromLeft = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(ANIMATION_DURATION);
		inFromLeft.setInterpolator(new AccelerateInterpolator());
		return inFromLeft;
	}
		
	public Animation outToRightAnimation() 
	{
		Animation outtoRight = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, +1.0f, Animation.RELATIVE_TO_PARENT, 0.0f, Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(ANIMATION_DURATION);
		outtoRight.setInterpolator(new AccelerateInterpolator());
		
		return outtoRight;
	}
    
    private ArrayList<Object> sortList(ArrayList<Object> list)
    {
    	ArrayList<Object> sortedList = new ArrayList<Object>();
    	for (int i = 0; i < 5; i++)
    	{
    		for (int j = 0; j < list.size(); j++)
    		{
    			if (((ItemOrCapacity) list.get(j)).getLevel() == i)
    			{
    				sortedList.add(list.get(j));
    			}
    		}
    	}
		return sortedList;
    }
	
	protected void onDestroy() 
	{
		super.onDestroy();
		if (wl != null) wl.release();
	}
}
