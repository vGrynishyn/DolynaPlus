package ua.com.dolina_plus.db_test;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TabHost;

import ua.com.dolina_plus.db_test.adapters.MyFragmentPagerAdapter;
import ua.com.dolina_plus.db_test.fragments.FragmentA;
import ua.com.dolina_plus.db_test.fragments.FragmentB;

public class TabActivity extends AppCompatActivity {

    private long CityId;
    String Route;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            CityId = extras.getLong("id");
            Route = extras.getString("route");
       }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        mViewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.titles_tab)));

        mTabLayout.setupWithViewPager(mViewPager);
    }
        public Long getCityId() {
            return CityId;
        }
        public String getRoute() {
            return Route;
        }


}
