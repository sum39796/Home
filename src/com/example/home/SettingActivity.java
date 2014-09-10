package com.example.home;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingActivity extends Activity {

	private static final String PREF_KEY = "preferenceHome";
	private static final String KEY_TEXT = "addres";
	private static final String CHECK_BOX = "cheked";

	SharedPreferences pref;
	SharedPreferences.Editor editor;

	EditText mEditText;

	public void check(View v) {
		CheckBox chk1 = (CheckBox) findViewById(R.id.checkBox1);
		editor = pref.edit();
		// �f�[�^�̕ۑ�
		editor.putBoolean(CHECK_BOX, chk1.isChecked());
		editor.commit();

	}

	public void mainSetting(View v) {
		Intent intent = new Intent("android.settings.SETTINGS");
		startActivity(intent);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);

		// SharedPrefernces �̎擾
		pref = getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);
		mEditText = (EditText) findViewById(R.id.addres);
		String preAddress = pref.getString(KEY_TEXT, "�A�h���X����͂��Ă�������");
		if (preAddress.equals("�A�h���X����͂��Ă�������")) {
			mEditText.setHint(preAddress);
		} else {
			mEditText.setText(preAddress);
		}

		CheckBox chk1 = (CheckBox) findViewById(R.id.checkBox1);
		Boolean preCheck = pref.getBoolean(CHECK_BOX, false);
		chk1.setChecked(preCheck);

	}

	public void enter(View v) {
		if (v.getId() == R.id.enterbutton1) {
			// Editor �̐ݒ�
			editor = pref.edit();
			// Editor �ɒl����
			editor.putString(KEY_TEXT, mEditText.getText().toString());
			// �f�[�^�̕ۑ�
			editor.commit();
			finish();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
		return true;
	}

}
