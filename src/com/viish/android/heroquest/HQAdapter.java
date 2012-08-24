package com.viish.android.heroquest;

import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class HQAdapter extends ArrayAdapter<Object>
{
	public static final boolean ALTERNATIVE_DISPLAY = true;
	
	private ArrayList<Object> objects;
	private String objectType;
	private Context context;
	private boolean showClass, showIcon;
	private Typeface typeface;
	
	public HQAdapter(Context context, Typeface typeface, int textViewResourceId, ArrayList<Object> objects, String type, boolean showClass, boolean showIcon) 
	{
        super(context, textViewResourceId, objects);
        this.typeface = typeface;
        this.objects = objects;
        this.objectType = type;
        this.context = context;
        this.showClass = showClass;
        this.showIcon = showIcon;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) 
	{
        View v = convertView;
        if (v == null) 
        {
            LayoutInflater vi = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (ALTERNATIVE_DISPLAY && objectType.equals("ItemOrCapacity") && !showIcon) v = vi.inflate(R.layout.row_alt, null);
            else v = vi.inflate(R.layout.row, null);
        }
        
        String s1 = null, s2 = null, s3 = null;
        Bitmap bm = null;
        
        if (objectType.equals("Character"))
        {
        	Character o = (Character) objects.get(position);
        	if (o != null) 
	        {
	        	s1 = o.getName(); // name
	        	if (o.getClasse().toString().equals("Pretre")) s2 = "Classe : Prêtre\nEsprit : " + o.getEsprit() +"\nCorps : " + o.getCorps() + "\nPO : " + o.getPo(); // desc
	        	else if (o.getClasse().toString().equals("Hobbit")) s2 = "Classe : Hobbit\nEsprit : " + o.getEsprit() +"\nCorps : " + o.getCorps() + "\nCourage : " + o.getCourage() + "\nPO : " + o.getPo(); // desc
	        	else s2 = "Classe : " + o.getClasse().toString() + "\nEsprit : " + o.getEsprit() +"\nCorps : " + o.getCorps() + "\nPO : " + o.getPo(); // desc
	        	
//	        	if (o.getNote() != "") s2 += "\nNote : " + o.getNote();
	        	s3 = "Level " + o.getLevel(); // level
	        	try 
	    		{
	    			bm = BitmapFactory.decodeStream(context.getAssets().open(o.getAvatar() + ".png"));
	    		} 
	    		catch (IOException e) 
	    		{
	    			try 
		    		{
		    			bm = BitmapFactory.decodeStream(context.getAssets().open(o.getAvatar() + ".jpg"));
		    		} 
		    		catch (IOException e2) {}
	    		}
	        }
        	v.setTag(o);
        }
        else if (objectType.equals("ItemOrCapacity"))
        {
        	ItemOrCapacity o = (ItemOrCapacity) objects.get(position);
        	if (o != null) 
	        {
	        	s1 = o.getName(); // name
	        	s2 = o.getDesc(); // desc
	        	if (o.isCumulable() && o.getLevel() >= 1) 
	        	{
	        		s1 += " (lvl " + o.getLevel() + ")";
	        	}
	        	if (showClass && !o.getClasse().equals("All"))
	        	{
	        		if (o.getClasse().equals("Pretre")) s1 += " (Prêtre)";
	        		else s1 += " (" +  o.getClasse() + ")";
	        	}
	        	s3 = o.getExtra(); // cost / amount
	        	bm = o.getIcon();
	        }
        	v.setTag(o);
        }
        
        TextView tt = (TextView) v.findViewById(R.id.Name);
        initTypeFace(tt);
    	TextView bt = (TextView) v.findViewById(R.id.Desc);
        initTypeFace(bt);
    	TextView p = (TextView) v.findViewById(R.id.Level);
        initTypeFace(p);
    	
    	if (tt != null) 
    	{
    		tt.setText(s1);
    		tt.setTextSize(15);
        }
    	if (bt != null)
    	{
    		bt.setTextSize(12);
    		bt.setText(s2);
        }
    	if (p != null)
    	{
    		p.setText(s3);
    	}
    	
    	if (showIcon) 
    	{
    		ImageView iv = (ImageView) v.findViewById(R.id.Icon);

        	if (iv != null)
        	{
        		iv.setImageBitmap(bm);
            }
    	}
    	
        return v;
	}
	
	private void initTypeFace(TextView menu) {
		if (typeface != null) {
			menu.setTypeface(typeface);
		}
	}
}
