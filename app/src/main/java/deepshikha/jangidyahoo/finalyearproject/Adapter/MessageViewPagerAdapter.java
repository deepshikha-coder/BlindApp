package deepshikha.jangidyahoo.finalyearproject.Adapter;

import android.content.Context;
import android.media.AudioManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import deepshikha.jangidyahoo.finalyearproject.Fragment.InboxMessageFragment;
import deepshikha.jangidyahoo.finalyearproject.Fragment.SentMessagesFragment;

public class MessageViewPagerAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    private String[] tabTitles = new String[]{"INBOX", "SENT"};
    AudioManager audioManager;
    public MessageViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;

    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

                InboxMessageFragment InboxMessages = new InboxMessageFragment();
                return InboxMessages;
            case 1:
                SentMessagesFragment SentMessages = new SentMessagesFragment();
                return SentMessages;

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
