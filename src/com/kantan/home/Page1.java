package com.kantan.home;

import com.kantan.home.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Page1 extends Fragment {

	LinearLayout LinearLayout1;
	LinearLayout Layouttate;
	ImageView imageView1;
	Animation animation;

	TextView dateText;

	SharedPreferences pref;
	SharedPreferences.Editor editor;

	private static final String PREF_KEY = "preferenceHome";
	private static final String KEY_TEXT = "lastlogin_year";
	private static final String CHECK_BOX = "cheked";

	private int[] images = new int[3];
	private int[] imaget = new int[3];

	private android.os.Handler mHandler = new android.os.Handler();

	static final int PICK_CONTACT_REQUEST = 1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.page1, container, false);
		
		TextView txt = (TextView) v.findViewById(R.id.tv1);
	    Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
	    txt.setTypeface(font);
	    
	    TextView txt2 = (TextView) v.findViewById(R.id.textView4);
	    Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
	    txt2.setTypeface(font2);
	    
	    TextView txt3 = (TextView) v.findViewById(R.id.tv2);
	    Typeface font3 = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
	    txt3.setTypeface(font3);
	    
	    TextView txt4 = (TextView) v.findViewById(R.id.textView3);
	    Typeface font4 = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
	    txt4.setTypeface(font4);
	    
	    TextView txt5 = (TextView) v.findViewById(R.id.textView7);
	    Typeface font5 = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
	    txt5.setTypeface(font5);
	    
	    TextView txt6 = (TextView) v.findViewById(R.id.textView6);
	    Typeface font6 = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
	    txt6.setTypeface(font6);

		return v;
		
	}

}
