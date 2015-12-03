package me.yilv.yilvtravel.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

/**
 * Created by liliu on 15/3/23.
 */
public class BaseFragmentActivity extends FragmentActivity implements ShowToast, SetTitle {
    public String TAG = getClass().getSimpleName();

    private ShowToast mShowToast = new ShowToastIml(this);
    private SetTitle mSetTitle;
    private long mStartTime, mEndTime;

    public View loadingV, netErrorV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSetTitle = new SetTitleIml(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mStartTime = System.currentTimeMillis();
    }

    @Override
    public void onPause() {
        super.onPause();
        mEndTime = System.currentTimeMillis();
    }

    public int browserTime() {
        return (int) ((mEndTime - mStartTime) / 1000);
    }

    public void showToast(String msg) {
        mShowToast.showToast(msg);
    }

    public void showToast(int resId) {
        mShowToast.showToast(resId);
    }

    public void showToastLongTime(String msg) {
        mShowToast.showToastLongTime(msg);
    }

    public void showToastLongTime(int resId) {
        mShowToast.showToastLongTime(resId);
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

}
