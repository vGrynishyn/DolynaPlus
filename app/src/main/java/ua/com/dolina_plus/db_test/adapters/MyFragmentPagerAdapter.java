package ua.com.dolina_plus.db_test.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ua.com.dolina_plus.db_test.fragments.FragmentA;
import ua.com.dolina_plus.db_test.fragments.FragmentB;

/**
 * Created by VGrynishyn on 7/15/2016.
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTabTitles;

    public MyFragmentPagerAdapter(FragmentManager fm, String[] mTabTitles) {
        super(fm);
        this.mTabTitles = mTabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentA();
            case 1:
                return new FragmentB();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.mTabTitles[position];
    }
}
