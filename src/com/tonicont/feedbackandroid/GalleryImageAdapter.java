package com.tonicont.feedbackandroid;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryImageAdapter extends BaseAdapter {
	private Context mContext;
	private Integer[] imagesId;
	
	public GalleryImageAdapter(Context context, Integer[] imagesId){
		mContext = context;
		this.imagesId = imagesId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imagesId.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		// TODO Auto-generated method stub
		ImageView i = new ImageView(mContext);

        i.setImageResource(imagesId[position]);
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));
    
        i.setScaleType(ImageView.ScaleType.FIT_XY);

        return i;
	}

}
