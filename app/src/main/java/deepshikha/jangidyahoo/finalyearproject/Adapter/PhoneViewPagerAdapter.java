package deepshikha.jangidyahoo.finalyearproject.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import deepshikha.jangidyahoo.finalyearproject.Fragment.CallLogFragment;
import deepshikha.jangidyahoo.finalyearproject.Fragment.DialerFragment;

public class PhoneViewPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;
    private String[] tabTitles = new String[]{"DIALER", "CALL LOGS"};
    public PhoneViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                DialerFragment dialer_fragment = new DialerFragment();
                return dialer_fragment;
            case 1:
                CallLogFragment CallLogFragment = new CallLogFragment();
                return CallLogFragment;

            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}
