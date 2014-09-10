package com.example.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.format.Time;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	ViewPager viewPager;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	private static final String PREF_KEY = "preferenceHome";
	private static final String KEY_TEXT = "lastlogin_year";
	private static final String CHECK_BOX = "cheked";
	private static final String CHECK_BOX2 = "cheked1";
	EditText mEditText;
	private int mPitch;
	private int mRoll;
	private SensorManager mSensorManager;

	long now;
	long last;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(new MyFragmentStatePagerAdapter(
				getSupportFragmentManager()));

		// SharedPrefernces の取得
		pref = getSharedPreferences(PREF_KEY, Activity.MODE_PRIVATE);

		int lastlogin = pref.getInt(KEY_TEXT, 0);
		Time time = new Time("Asia/Tokyo");
		time.setToNow();
		String date = time.year + "年" + (time.month + 1) + "月" + time.monthDay
				+ "日 " + time.hour + "時" + time.minute + "分" + time.second
				+ "秒";

		int nowlogin = time.year * 10000 + (time.month + 1) * 100
				+ time.monthDay * 1;

		if (lastlogin < nowlogin && pref.getBoolean(CHECK_BOX, false)) {
			Intent intent = new Intent(this, CameraActivity.class);
			startActivity(intent);

			// Editor の設定
			editor = pref.edit();
			editor.putInt(KEY_TEXT, nowlogin);
			// データの保存
			editor.commit();

		}

		// SensorManagerを取得
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

		// イベントハンドラを登録
		mSensorManager.registerListener(new SensorEventListener() {
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
			}

			@Override
			public void onSensorChanged(SensorEvent event) {
				// 傾きを更新
				mPitch = (int) event.values[SensorManager.DATA_Y];
				mRoll = (int) event.values[SensorManager.DATA_Z];

				 long now = System.currentTimeMillis();
				
				if (mRoll >= 20 && now - last >= 500 && pref.getBoolean(CHECK_BOX2, false)) {
					ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
					viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
					last = System.currentTimeMillis();
				} else if (mRoll <= (-20) && now - last >= 500 && pref.getBoolean(CHECK_BOX2, false)) {
					ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
					viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
					last = System.currentTimeMillis();
					
					

				}
			}
		}, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
				SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void mail(View v) {
		Intent intent = new Intent(this, MailActivity.class);
		startActivity(intent);
	}

	public void Internet(View v) {
		Uri uri = Uri.parse("http://google.com/");
		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(i);
	}

	public void Youtube(View v) {
		Uri uri = Uri.parse("https://www.youtube.com/?gl=JP&hl=ja");
		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(i);
	}

	public void LINE(View v) {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setClassName("jp.naver.line.android",
				"jp.naver.line.android.activity.SplashActivity");
		startActivity(intent);
	}

	public void Contact(View v) {
		Intent intent = new Intent(this, PhoneActivity.class);
		startActivity(intent);
	}

	public void Margan(View v) {
		PackageManager pm = getPackageManager();
		Intent intent = pm.getLaunchIntentForPackage("com.gamedesgin.mahjong");
		startActivity(intent);

	}

	public void Camera(View view) {
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(intent, 1);
	}

	public void settings(View v) {
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setCurrentItem(viewPager.getCurrentItem() + 2);
	}

	public void check1(View v) {
		CheckBox chk1 = (CheckBox) v.findViewById(R.id.checkBox1);
		editor = pref.edit();
		// データの保存
		editor.putBoolean(CHECK_BOX, chk1.isChecked());
		editor.commit();

	}
	
	public void check2(View v) {
		CheckBox chk2 = (CheckBox) v.findViewById(R.id.checkBox2);
		editor = pref.edit();
		// データの保存
		editor.putBoolean(CHECK_BOX2, chk2.isChecked());
		editor.commit();

	}

	public void mainSetting(View v) {
		Intent intent = new Intent("android.settings.SETTINGS");
		startActivity(intent);
	}

	

}