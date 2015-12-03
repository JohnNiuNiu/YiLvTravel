package me.yilv.yilvtravel.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by liliu on 15/3/23.
 */
public class BaseFragment extends Fragment implements SetTitle {
    public String TAG = getClass().getSimpleName();
    public boolean isAnalytics = true;
    private View mView;
    private boolean isNewView = true;
    private SetTitle mSetTitle;
    private long mStartTime, mEndTime;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = onCreateCacheView(inflater, container, savedInstanceState);
            isNewView = true;
        } else {
            isNewView = false;
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isNewView) {
            mSetTitle = new SetTitleIml(getActivity(), view);
            onCacheViewCreated(view, savedInstanceState);
        }
    }

    /**
     * 创建可以被缓存的view；
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateCacheView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return null;
    }

    /**
     * 当cacheView被创建
     *
     * @param view
     * @param savedInstanceState
     */
    public void onCacheViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void setTopTitle(String title) {
        mSetTitle.setTopTitle(title);
    }

    @Override
    public void setTopTitleAndLeft(String title) {
        mSetTitle.setTopTitleAndLeft(title);
    }

    @Override
    public void setTopTitleAndLeftAndRight(String title) {
        mSetTitle.setTopTitleAndLeftAndRight(title);
    }

    @Override
    public View getTitleView() {
        return mSetTitle.getTitleView();
    }

    @Override
    public View getTitleLeftView() {
        return mSetTitle.getTitleLeftView();
    }

    @Override
    public View getTitleRightView() {
        return mSetTitle.getTitleRightView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isAnalytics) {
            mStartTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isAnalytics) {
            mEndTime = System.currentTimeMillis();
        }
    }

    /**
     * 在fragment使用的时候，需要注意isAnalytics
     *
     * @return
     */
    public int browserTime() {
        return (int) ((mEndTime - mStartTime) / 1000);
    }
}
