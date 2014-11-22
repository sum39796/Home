package com.example.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class Page3 extends Fragment {

	private static final String PREF_KEY = "preferenceHome";
	private static final String KEY_TEXT = "addres";
	private static final String CHECK_BOX = "cheked";

	SharedPreferences pref;
	SharedPreferences.Editor editor;

	EditText mEditText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.page3, null);
		// SharedPrefernces の取得
		pref = getActivity().getSharedPreferences(PREF_KEY,
				Activity.MODE_PRIVATE);
		mEditText = (EditText) v.findViewById(R.id.addres);

		String preAddress = pref.getString(KEY_TEXT, "アドレスを入力してください");
		if (preAddress.equals("アドレスを入力してください")) {
			mEditText.setHint(preAddress);
		} else {
			mEditText.setText(preAddress);
		}
		
		TextView txt = (TextView) v.findViewById(R.id.tv2);
	    Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
	    txt.setTypeface(font);
	    
	    TextView txt2 = (TextView) v.findViewById(R.id.mail);
	    Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
	    txt2.setTypeface(font2);

		CheckBox chk1 = (CheckBox) v.findViewById(R.id.checkBox1);
		Boolean preCheck = pref.getBoolean(CHECK_BOX, false);
		chk1.setChecked(preCheck);

		Button b =  (Button)v.findViewById(R.id.enterbutton1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.enterbutton1) {  
				      // Editor の設定  
				      editor = pref.edit();  
				      // Editor に値を代入  
				      editor.putString(  
				          KEY_TEXT,
				          mEditText.getText().toString()
				      );  
				      // データの保存  
				      editor.commit();
				     
					}
			}
		});
		
		return v;
	}

}
