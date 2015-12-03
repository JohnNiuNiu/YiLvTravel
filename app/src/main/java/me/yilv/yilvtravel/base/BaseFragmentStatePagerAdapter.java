package me.yilv.yilvtravel.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import me.yilv.yilvtravel.LogUtil;

/**
 * Created by liliu on 15/4/1.
 */
public class BaseFragmentStatePagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public BaseFragmentStatePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<Fragment> datas) {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        } else {
            mFragments.clear();
        }
        mFragments.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return this.mFragments.get(position);
    }

    @Override
    public int getCount() {
        return this.mFragments == null ? 0 : this.mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        try {
            return mFragments.get(position).getArguments().getString("title").toString();
        } catch (Exception e) {
            LogUtil.e("", "ViewPage getPageTitle", e);
            return super.getPageTitle(position);
        }
    }

}
