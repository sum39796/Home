package com.kantan.home;

import com.kantan.home.R;

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
		
		TextView txt = (TextView) v.findViewById(R.id.tv2);
	    Typeface font = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
	    txt.setTypeface(font);
	    
		CheckBox chk2 = (CheckBox) v.findViewById(R.id.checkBox2);
		Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(),"mplus-1c-thin.ttf");
		chk2.setTypeface(font2);

		Button b =  (Button)v.findViewById(R.id.enterbutton1);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.enterbutton1) {  
				      // Editor �̐ݒ�  
				      editor = pref.edit();  
				      // Editor �ɒl����  
				      editor.putString(  
				          KEY_TEXT,
				          mEditText.getText().toString()
				      );  
				      // �f�[�^�̕ۑ�  
				      editor.commit();
				     
					}
			}
		});
		
		return v;
	}

}
