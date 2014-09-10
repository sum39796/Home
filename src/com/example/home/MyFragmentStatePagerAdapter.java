package com.example.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

	public MyFragmentStatePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {

		switch (i) {
		case 0:
			return new Page1();
		case 1:
			return new Page2();
		default:
			return new Page3();
		}

	}

	@Override
	public CharSequence getPageTitle(int position) {
		return "ÉyÅ[ÉW" + (position+1);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

}
