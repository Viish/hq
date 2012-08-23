package com.viish.android.heroquest;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Icons extends Activity implements OnClickListener 
{	
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.icons);
        
        String path = this.getIntent().getExtras().getString("Path");
        
        GridView gridview = (GridView) findViewById(R.id.icons);
		
		ArrayList<String> icons = new ArrayList<String>();
		try 
		{
			String[] temp = getAssets().list(path);
			for (String icon : temp)
			{
				if (icon.lastIndexOf(".") != -1) icons.add(icon);
			}
		} 
		catch (IOException e) {	}
		
 		gridview.setAdapter(new GridAdapter(this, icons, path));
 		gridview.setNumColumns(3);
    }
    
	public void onClick(View v) 
	{
		String icon = v.getTag().toString();
		Intent i = new Intent();
		i.putExtra("IconName", icon);
		setResult(RESULT_OK, i);
		finish();
	}
}

class GridAdapter extends BaseAdapter
{
	private Context mContext;
    private ArrayList<String> mThumbIds;
    private OnClickListener ock;
    private String path;

    public GridAdapter(Icons c, ArrayList<String> champs, String path) 
    {
        mContext = c;
        ock = c;
        mThumbIds = champs;
        this.path = path;
    }

    public int getCount() 
    {
        return mThumbIds.size();
    }

    public Object getItem(int position) 
    {
        return null;
    }

    public long getItemId(int position) 
    {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) 
    {
        ImageView imageView;
        if (convertView == null) 
        { 
            imageView = new ImageView(mContext);
            imageView.setOnClickListener(ock);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(5, 5, 5, 5);
            imageView.setTag(mThumbIds.get(position));
        } 
        else 
        {
            imageView = (ImageView) convertView;
            imageView.setOnClickListener(ock);
            imageView.setAdjustViewBounds(true);
            imageView.setPadding(5, 5, 5, 5);
            imageView.setTag(mThumbIds.get(position));
        }

        try 
	 	{
        	Bitmap bm = BitmapFactory.decodeStream(mContext.getResources().getAssets().open(path + "/" + mThumbIds.get(position)));
        	imageView.setImageBitmap(bm);
	 	} 
	 	catch (Exception e) 
	 	{
	 		Bitmap bm;
			try 
			{
				bm = BitmapFactory.decodeStream(mContext.getResources().getAssets().open(mThumbIds.get(position)));
		 		imageView.setImageBitmap(bm);
			} 
			catch (IOException e1) {}
	 	}
        
        return imageView;
    }
}
