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
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class Capacities extends TabActivity implements OnItemClickListener, OnItemLongClickListener 
{
	public static final boolean DEBUG = true;
	
	private  ArrayList<Object> otherClassList, myClassList;
	private TabHost th;
	private String myClass;
	
	public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.capacities);

        myClass = getIntent().getExtras().getString("Class");

		th = getTabHost();
        th.addTab(th.newTabSpec("all").setIndicator("Autres Classes").setContent(R.id.OtherClasses));
	    th.addTab(th.newTabSpec("myclass").setIndicator(myClass).setContent(R.id.MyClasses));
        updateList();
        th.setCurrentTab(1);
    }
	
	private void updateList() 
	{
	    ListView otherLV, myLV;
	    otherLV = (ListView) findViewById(R.id.OtherClasses);
	    myLV = (ListView) findViewById(R.id.MyClasses);
	    
        DatabaseStream dbs = new DatabaseStream(this);
        otherClassList = dbs.getClassCapacities("All");
        if (myClass.equals("Assassin"))
	    {
		    myClassList = dbs.getClassCapacities("Assassin");
	    	otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur")); otherClassList.addAll(dbs.getClassCapacities("Necromancien"));
	    	otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Rodeur")); otherClassList.addAll(dbs.getClassCapacities("Pretre"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit"));
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);	
		    otherLV.setOnItemLongClickListener(this);
	    }
	    else if (myClass.equals("Barbare"))
	    {
		    myClassList = dbs.getClassCapacities("Barbare");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur")); otherClassList.addAll(dbs.getClassCapacities("Necromancien"));
	    	otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Rodeur")); otherClassList.addAll(dbs.getClassCapacities("Pretre"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit")); 
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);	
		    otherLV.setOnItemLongClickListener(this);	
	    }
	    else if (myClass.equals("Mage"))
	    {
		    myClassList = dbs.getClassCapacities("Mage");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Voleur")); otherClassList.addAll(dbs.getClassCapacities("Necromancien"));
	    	otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Rodeur")); otherClassList.addAll(dbs.getClassCapacities("Pretre"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit"));
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);	
		    otherLV.setOnItemLongClickListener(this);	
	    }
	    else if (myClass.equals("Voleur"))
	    {
		    myClassList = dbs.getClassCapacities("Voleur");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Necromancien"));
	    	otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Rodeur")); otherClassList.addAll(dbs.getClassCapacities("Pretre"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit"));
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);	
		    otherLV.setOnItemLongClickListener(this);	
	    }
	    else if (myClass.equals("Necromancien"))
	    {
		    myClassList = dbs.getClassCapacities("Necromancien");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Rodeur")); otherClassList.addAll(dbs.getClassCapacities("Pretre"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit"));
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);	
		    otherLV.setOnItemLongClickListener(this);	
	    }
	    else if (myClass.equals("Skaven"))
	    {
		    myClassList = dbs.getClassCapacities("Skaven");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Necromancien")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Rodeur")); otherClassList.addAll(dbs.getClassCapacities("Pretre"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit")); 
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);	
		    otherLV.setOnItemLongClickListener(this);	
	    }
	    else if (myClass.equals("Moine"))
	    {
		    myClassList = dbs.getClassCapacities("Moine");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Necromancien")); otherClassList.addAll(dbs.getClassCapacities("Rodeur")); otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Pretre"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit")); 
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);	
		    otherLV.setOnItemLongClickListener(this);	
	    }
	    else if (myClass.equals("Rodeur"))
	    {
		    myClassList = dbs.getClassCapacities("Rodeur");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Necromancien")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Pretre"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit"));
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);		
		    otherLV.setOnItemLongClickListener(this);
	    }
	    else if (myClass.equals("Pretre"))
	    {
		    myClassList = dbs.getClassCapacities("Pretre");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Necromancien")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Rodeur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit"));
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);		
		    otherLV.setOnItemLongClickListener(this);
	    }
	    else if (myClass.equals("Ogre"))
	    {
		    myClassList = dbs.getClassCapacities("Ogre");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Necromancien")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Rodeur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Pretre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Hobbit"));
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);		
		    otherLV.setOnItemLongClickListener(this);
	    }
	    else if (myClass.equals("Barde"))
	    {
		    myClassList = dbs.getClassCapacities("Barde");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Necromancien")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Rodeur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Pretre")); otherClassList.addAll(dbs.getClassCapacities("Hobbit"));
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);		
		    otherLV.setOnItemLongClickListener(this);
	    }
	    else if (myClass.equals("Hobbit"))
	    {
		    myClassList = dbs.getClassCapacities("Hobbit");
	    	otherClassList.addAll(dbs.getClassCapacities("Assassin")); otherClassList.addAll(dbs.getClassCapacities("Barbare")); otherClassList.addAll(dbs.getClassCapacities("Mage")); otherClassList.addAll(dbs.getClassCapacities("Voleur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Necromancien")); otherClassList.addAll(dbs.getClassCapacities("Moine")); otherClassList.addAll(dbs.getClassCapacities("Skaven")); otherClassList.addAll(dbs.getClassCapacities("Rodeur"));
	    	otherClassList.addAll(dbs.getClassCapacities("Ogre")); otherClassList.addAll(dbs.getClassCapacities("Barde")); otherClassList.addAll(dbs.getClassCapacities("Pretre"));
	    	myLV.setAdapter(new HQAdapter(this, null, R.layout.row, myClassList, "ItemOrCapacity", false, false));
		    myLV.setOnItemClickListener(this);
		    myLV.setOnItemLongClickListener(this);
	    	otherLV.setAdapter(new HQAdapter(this, null, R.layout.row, otherClassList, "ItemOrCapacity", true, false));
		    otherLV.setOnItemClickListener(this);		
		    otherLV.setOnItemLongClickListener(this);
	    }
        dbs.close();
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	{
		int tab = th.getCurrentTab();
		String capacity = null;
		if (tab == 0)
		{
			capacity = ((ItemOrCapacity) otherClassList.get(position)).getName();
		}
		else
		{
			capacity = ((ItemOrCapacity) myClassList.get(position)).getName();
		}
		
		Intent i = new Intent();
		i.putExtra("SelectedCapacity", capacity);
		setResult(RESULT_OK, i);
		finish();
	}
	
	public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) 
	{
		int tab = th.getCurrentTab();
		ItemOrCapacity capacity;
		if (tab == 0)
		{
			capacity = (ItemOrCapacity) otherClassList.get(position);
			
		}
		else
		{
			capacity = (ItemOrCapacity) myClassList.get(position);
		}

		final String capacityName = capacity.getName();
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final Context context = this;
		
		alert.setTitle(capacityName);
		InputStream is;
		try 
		{
			is = getAssets().open("Capacites/" + capacity.getIconName().replace(" ", "_") + ".gif");
			alert.setIcon(Drawable.createFromStream(is, capacityName));
		} 
		catch (IOException e) {	}
		
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		final EditText etName = new EditText(this);
		etName.setSingleLine();
		etName.setText(capacity.getName());
		ll.addView(etName);
		final EditText etCost = new EditText(this);
		etCost.setSingleLine();
		etCost.setText(capacity.getExtra());
		ll.addView(etCost);
		final EditText etDesc = new EditText(this);
		if (capacity.getDesc().lastIndexOf("\n") >= 0) etDesc.setText(capacity.getDesc().substring(0, capacity.getDesc().lastIndexOf("\n")));
		else etDesc.setText(capacity.getDesc());
		ll.addView(etDesc);
		
		alert.setView(ll);
		
		alert.setPositiveButton("Modifier", new OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				DatabaseStream dbs = new DatabaseStream(context);
				dbs.modifyCapacity(capacityName, etName.getText().toString(), etCost.getText().toString(), etDesc.getText().toString());
				dbs.close();
				updateList();
			}
		});
		
		alert.show();
		return false;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuItem menuItem;
    	menuItem = menu.add(1, 0, 1, "Rechercher par Classe");
    	menuItem.setIcon(android.R.drawable.ic_menu_search);
    	
		return true;
	}

	public boolean onMenuItemSelected(int featureId, MenuItem item) 
	{
		super.onMenuItemSelected(featureId, item);
		
		switch (item.getItemId())
		{
			case 0:
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setTitle("Rechercher par Classe");
				
				String[] classes = {"Assassin", "Barbare", "Mage", "Voleur", "Necromancien", "Moine", "Skaven", "Rodeur", "Pretre", "Ogre", "Barde", "Hobbit"};
    			final Spinner classe = new Spinner(this);
    			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, classes);
    			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    			classe.setAdapter(adapter);
    			alert.setView(classe);
    			
    			alert.setPositiveButton("Rechercher", new OnClickListener()
    			{
    				public void onClick(DialogInterface dialog, int which) 
    				{
    					String selectedClasse = (String) classe.getSelectedItem();
    					if (selectedClasse.equals(myClass)) th.setCurrentTab(1);
    					else
    					{
	    					for (Object o : otherClassList)
	    					{
	    						ItemOrCapacity capacity = (ItemOrCapacity) o;
	    						if (capacity.getClasse().equals(selectedClasse))
	    						{
	    							int position = otherClassList.indexOf(o);
	    							ListView lv = (ListView) th.getTabContentView().getChildAt(0);
	    							lv.setSelection(position);
	    							th.setCurrentTab(0);
	    							break;
	    						}
	    					}
    					}
    				}
    			});
    			
				alert.show();
				break;
		}
		
		return false;
	}
}
