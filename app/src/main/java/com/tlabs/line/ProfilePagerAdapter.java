package com.tlabs.line;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by sureshkumar on 25/10/15.
 */
public class ProfilePagerAdapter extends FragmentPagerAdapter {

    private int mTabCount = 0;
    private Activity mActivity;
    private List<Profile> mProfileList;

    public ProfilePagerAdapter(FragmentManager fm, Activity activity, List<Profile> ProfileList) {
        super(fm);
        this.mActivity = activity;
        this.mProfileList = ProfileList;
        this.mTabCount = ProfileList.size();
    }

    @Override
    public Fragment getItem(int position) {
        for (int i = 0; i < mTabCount; i++) {
            if (position == i) {
                String profileJson = Utils.convertObjectToStringJson(mProfileList.get(position), new TypeToken<Profile>() {
                }.getType());
                return ProfileFragment.newInstance(profileJson);
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

}
