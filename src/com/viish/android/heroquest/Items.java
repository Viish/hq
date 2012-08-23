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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Items extends ListActivity implements OnItemClickListener, OnItemLongClickListener
{
	private  ArrayList<Object> items;
	private String icon = "";
	
    @SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) 
    {
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
        items = dbs.getKnownItems();
        dbs.close();
        getListView().setAdapter(new HQAdapter(this, null, R.layout.row, items, "ItemOrCapacity", false, true));
	}

	public void onItemClick(AdapterView<?> parent, View v, int position, long id) 
	{
		ItemOrCapacity item = (ItemOrCapacity) items.get(position);
		Intent i = new Intent();
		i.putExtra("SelectedItem", item.getName());
		setResult(RESULT_OK, i);
		finish();
	}
	
	public boolean onItemLongClick(AdapterView<?> av, View v, int position, long id) 
	{
		ItemOrCapacity item = (ItemOrCapacity) this.getListView().getAdapter().getItem(position);

		final String itemName = item.getName();
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final Context context = this;
		
		alert.setTitle(itemName);
		InputStream is;
		try 
		{
			is = getAssets().open("Objets/" + item.getIconName().replace(" ", "_") + ".gif");
			alert.setIcon(Drawable.createFromStream(is, itemName));
		} 
		catch (IOException e) {	}
		
		LinearLayout ll = new LinearLayout(this);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		ll.setOrientation(LinearLayout.VERTICAL);
		final EditText etName = new EditText(this);
		etName.setText(itemName);
		ll.addView(etName);
		final EditText etDesc = new EditText(this);
		etDesc.setText(item.getDesc());
		ll.addView(etDesc);
		final EditText etPrice = new EditText(this);
		etPrice.setText(item.getExtra()); // price
		ll.addView(etPrice);
		final CheckBox cbStuff = new CheckBox(this);
		cbStuff.setText("Equipement ?");
		ll.addView(cbStuff);
		Button bIcon = new Button(this);
		bIcon.setText("Icon");
		bIcon.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent i = new Intent(context, Icons.class);
				i.putExtra("Path", "Objets");
				startActivityForResult(i, 0x1989);
			}
		});
		ll.addView(bIcon);
		alert.setView(ll);
		
		final DatabaseStream dbs = new DatabaseStream(this);
		alert.setPositiveButton("Update", new OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				try
				{
					int price = Integer.parseInt(etPrice.getText().toString());
					dbs.updateItem(etName.getText().toString(), etDesc.getText().toString(), cbStuff.isChecked() ? 0 : 1, icon, price);
					dbs.close();
					updateList();
				}
				catch (NumberFormatException nfe)
				{
					Toast.makeText(context, "Veuillez entrer un nombre pour le prix de l'item !", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		alert.setNegativeButton("Destroy", new OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				dbs.deleteItem(itemName);
				dbs.close();
				updateList();
			}
		});
		
		alert.show();
		return false;
	}
	
	private void ajouterObjet() 
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		final DatabaseStream dbs = new DatabaseStream(this);
		final Context context = this;
		
		alert.setTitle("Créer Objet");
		LinearLayout ll = new LinearLayout(this);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		ll.setOrientation(LinearLayout.VERTICAL);
		final EditText etName = new EditText(this);
		etName.setHint("Nom de l'objet");
		ll.addView(etName);
		final EditText etDesc = new EditText(this);
		etDesc.setHint("Description de l'objet");
		ll.addView(etDesc);
		final EditText etPrice = new EditText(this);
		etPrice.setHint("Prix de l'obojet");
		ll.addView(etPrice);
		final CheckBox cbStuff = new CheckBox(this);
		cbStuff.setText("Equipement ?");
		ll.addView(cbStuff);
		Button bIcon = new Button(this);
		bIcon.setText("Icon");
		bIcon.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent i = new Intent(context, Icons.class);
				i.putExtra("Path", "Objets");
				startActivityForResult(i, 0x1989);
			}
		});
		ll.addView(bIcon);
		alert.setView(ll);
		
		alert.setPositiveButton("Créer", new OnClickListener() 
		{
			public void onClick(DialogInterface dialog, int which) 
			{
				try
				{
					int price = Integer.parseInt(etPrice.getText().toString());
					dbs.createItem(etName.getText().toString(), etDesc.getText().toString(), cbStuff.isChecked() ? 0 : 1, icon, price);
					dbs.close();
					updateList();
				}
				catch (NumberFormatException nfe)
				{
					Toast.makeText(context, "Veuillez entrer un nombre pour le prix de l'item !", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		alert.show();
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if (requestCode == 0x1989 && resultCode == RESULT_OK)
		{
			icon = (String) data.getExtras().get("IconName");
			icon = icon.substring(0, icon.lastIndexOf('.'));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public boolean onCreateOptionsMenu(Menu menu) 
    {
    	super.onCreateOptionsMenu(menu);
    	
    	MenuItem menuItem;
    	menuItem = menu.add(1, 0, 0, "Ajouter Objet");
    	menuItem.setIcon(android.R.drawable.ic_input_add);
    	return true;
    }
    
    public boolean onMenuItemSelected(int featureId, MenuItem item) 
    {
    	super.onMenuItemSelected(featureId, item);
    	
    	switch (item.getItemId())
    	{
    		case 0:
    			ajouterObjet();
    			break;
    	}
    	return false;
    }
}
