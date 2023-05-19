package deepshikha.jangidyahoo.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import deepshikha.jangidyahoo.finalyearproject.Adapter.PhoneViewPagerAdapter;

public class Phone extends AppCompatActivity {

    TabLayout PhoneTabLayout;
    ViewPager PhoneViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        PhoneTabLayout = findViewById(R.id.PhoneTabLayout);
        PhoneViewPager= findViewById(R.id.PhoneViewPager);

        PhoneTabLayout.addTab(PhoneTabLayout.newTab().setText("Phone"));
        PhoneTabLayout.addTab(PhoneTabLayout.newTab().setText("Call Log"));
        PhoneTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        PhoneTabLayout.setBackgroundColor(Color.GRAY);
        PhoneTabLayout.setTabTextColors(Color.RED, Color.BLACK);

        final PhoneViewPagerAdapter adapter = new PhoneViewPagerAdapter(this,getSupportFragmentManager(), PhoneTabLayout.getTabCount());
        PhoneViewPager.setAdapter(adapter);
        PhoneViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(PhoneTabLayout));

        PhoneTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                PhoneViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}