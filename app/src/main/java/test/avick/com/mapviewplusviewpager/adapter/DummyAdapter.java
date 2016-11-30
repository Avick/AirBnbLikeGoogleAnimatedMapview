package test.avick.com.mapviewplusviewpager.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.HashMap;

import test.avick.com.mapviewplusviewpager.Fragment.DummyFragment;

/**
 * Created by avick on 11/30/16.
 */

public class DummyAdapter extends FragmentStatePagerAdapter {

    int PAGE_COUNT = 4;
    private HashMap<Integer, Fragment> fragmentHashMap;

    public DummyAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        fragmentHashMap = new HashMap<>(4);
    }

    @Override
    public Fragment getItem(int position) {
        if(!fragmentHashMap.containsKey(position)) {
            fragmentHashMap.put(position, DummyFragment.newInstance(position));
        }

        return fragmentHashMap.get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
