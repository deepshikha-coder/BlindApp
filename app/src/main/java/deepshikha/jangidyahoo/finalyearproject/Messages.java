package deepshikha.jangidyahoo.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import deepshikha.jangidyahoo.finalyearproject.Adapter.MessageViewPagerAdapter;

public class Messages extends AppCompatActivity {
    TabLayout MessageTabLayout;
    ViewPager MessageViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_layout);


        MessageTabLayout = findViewById(R.id.MessageTabLayout);
        MessageViewPager= findViewById(R.id.MessagesPager);

        MessageTabLayout.addTab(MessageTabLayout.newTab().setText("Inbox"));
        MessageTabLayout.addTab(MessageTabLayout.newTab().setText("Sent"));
        MessageTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        MessageTabLayout.setTabTextColors(Color.BLACK, Color.RED);

        final MessageViewPagerAdapter adapter = new MessageViewPagerAdapter(this,getSupportFragmentManager(), MessageTabLayout.getTabCount());
        MessageViewPager.setAdapter(adapter);
        MessageViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(MessageTabLayout));

        MessageTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                MessageViewPager.setCurrentItem(tab.getPosition());
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