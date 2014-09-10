package com.example.home;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
		View v = inflater.inflate(R.layout.page1, null);

		return v;
	}

}
