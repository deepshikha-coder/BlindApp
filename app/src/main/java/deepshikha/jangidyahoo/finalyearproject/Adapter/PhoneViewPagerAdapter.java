package deepshikha.jangidyahoo.finalyearproject.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import deepshikha.jangidyahoo.finalyearproject.MessageInboxFragment;
import deepshikha.jangidyahoo.finalyearproject.SentMessagesFragment;
import deepshikha.jangidyahoo.finalyearproject.call_logFragment;
import deepshikha.jangidyahoo.finalyearproject.dialer_fragment;

public class PhoneViewPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public PhoneViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                dialer_fragment dialer_fragment = new dialer_fragment();
                return dialer_fragment;
            case 1:
                call_logFragment CallLogFragment = new call_logFragment();
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
