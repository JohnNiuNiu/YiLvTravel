package me.yilv.yilvtravel.picturebrowser;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import me.yilv.yilvtravel.R;
import me.yilv.yilvtravel.common.OnPageChangeListenerIml;
import me.yilv.yilvtravel.utils.SpUtil;


/**
 * Created by liliu on 15/5/8.
 */
public class ImgBrowserHolder {
    private Context mContext;
    private View mRootView;

    private LinearLayout dotsView;
    private int mDotsBeforeIndex = 0;
    private ViewPager imgVp;
    private ImgFragmentAdapter mImgFragmentAdapter;

    private float mPicFate = -1;
    private int mDotsMarginBottom = -1;

    public ImgBrowserHolder(Context c, View view) {
        initData(c, view, mPicFate, mDotsMarginBottom);
    }

    public ImgBrowserHolder(Context c, View view, float picFate) {
        initData(c, view, picFate, mDotsMarginBottom);
    }

    public ImgBrowserHolder(Context c, View view, float picFate, int dotsMargin) {
        initData(c, view, picFate, dotsMargin);
    }

    private void initData(Context c, View view, float picFate, int dotsMargin) {
        if (!(c instanceof FragmentActivity)) {
            new NullPointerException("context is not FragmentActivity");
        }
        this.mContext = c;
        this.mRootView = view;

        this.mPicFate = picFate;
        this.mDotsMarginBottom = dotsMargin;
        init();
    }

    private void init() {
        View viewRoot = mRootView.findViewById(R.id.imgs_view_root);
        if (mPicFate != -1) {
            ViewGroup.LayoutParams vl = viewRoot.getLayoutParams();
            vl.width = SpUtil.getInstance().getScreenWidth();
            vl.height = (int) (vl.width / mPicFate);
            viewRoot.setLayoutParams(vl);
        }

        imgVp = (ViewPager) mRootView.findViewById(R.id.imgs_vp);
        imgVp.setOnPageChangeListener(mPageChangeListener);
        mImgFragmentAdapter = new ImgFragmentAdapter(((FragmentActivity) mContext).getSupportFragmentManager());
        imgVp.setAdapter(mImgFragmentAdapter);

        dotsView = (LinearLayout) mRootView.findViewById(R.id.imgs_ll_dots);
        //TODO 暂且不需要
        dotsView.setVisibility(View.GONE);
        if (mDotsMarginBottom != -1) {
            FrameLayout.LayoutParams rl = (FrameLayout.LayoutParams) dotsView.getLayoutParams();
            rl.setMargins(0, 0, 0, mDotsMarginBottom);
            dotsView.setLayoutParams(rl);
        }
    }

    private void initTopBannerDotViews() {
        dotsView.removeAllViews();
        int dotCount = mImgFragmentAdapter.getCount();
        //size<2,return
        if (dotCount < 2) {
            return;
        }
        int normalMargin = mContext.getResources().getDimensionPixelSize(R.dimen.dimen_normal);
        for (int i = 0; i < dotCount; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setLayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            iv.setImageResource(R.drawable.shape_dot_white);
            dotsView.addView(iv);
            LinearLayout.LayoutParams ll = (LinearLayout.LayoutParams) iv.getLayoutParams();
            ll.setMargins(normalMargin, 0, 0, 0);
            iv.setLayoutParams(ll);
        }
        mDotsBeforeIndex = imgVp.getCurrentItem();
        setDotViewState(mDotsBeforeIndex, true);
    }

    private void setDotViewState(int position, boolean isChecked) {
        View checkedView = dotsView.getChildAt(position);
        if (checkedView instanceof ImageView) {
            if (isChecked) {
                ((ImageView) checkedView).setImageResource(R.drawable.shape_dot_green);
            } else {
                ((ImageView) checkedView).setImageResource(R.drawable.shape_dot_white);
            }
        }
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new OnPageChangeListenerIml() {
        @Override
        public void onPageSelected(int position) {
            if (mDotsBeforeIndex != position) {
                setDotViewState(mDotsBeforeIndex, false);
            }
            setDotViewState(position, true);
            mDotsBeforeIndex = position;
        }
    };

    public void setDatas(List<Fragment> datas) {
        if (mImgFragmentAdapter != null) {
            mImgFragmentAdapter.setData(datas);
            initTopBannerDotViews();
        }
    }
}
