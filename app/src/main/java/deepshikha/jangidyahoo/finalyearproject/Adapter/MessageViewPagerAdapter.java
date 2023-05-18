package deepshikha.jangidyahoo.finalyearproject.Adapter;

import android.content.Context;
import android.provider.Telephony;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import deepshikha.jangidyahoo.finalyearproject.MessageInboxFragment;
import deepshikha.jangidyahoo.finalyearproject.SentMessagesFragment;

public class MessageViewPagerAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MessageViewPagerAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MessageInboxFragment InboxMessages = new MessageInboxFragment();
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
