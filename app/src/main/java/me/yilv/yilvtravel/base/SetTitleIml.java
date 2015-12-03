package me.yilv.yilvtravel.base;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by liliu on 15/5/8.
 */
public class SetTitleIml implements SetTitle {
    private Activity mContext;
    private View mRootView;

    private TextView topLeftTv, topRightTv;
    private TextView topTvTitle;

    public SetTitleIml(Activity c) {
        this.mContext = c;
        mRootView = this.mContext.findViewById(android.R.id.content);
    }

    public SetTitleIml(View v) {
        this.mRootView = v;
    }

    public SetTitleIml(Activity c, View v) {
        this.mContext = c;
        this.mRootView = v;
    }

    @Override
    public void setTopTitle(String title) {
        if (topTvTitle == null) {
            //topTvTitle = (TextView) mRootView.findViewById(R.id.top_title_tv);
        }
        if (topTvTitle != null) {
            topTvTitle.setText(title);
        }
    }

    @Override
    public void setTopTitleAndLeft(String title) {
        setTopTitle(title);
        if (topLeftTv == null) {
            //topLeftTv = (TextView) mRootView.findViewById(R.id.top_left_tv);
            if (topLeftTv != null) {
                topLeftTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContext.onBackPressed();
                    }
                });
            }
        }
    }

    @Override
    public void setTopTitleAndLeftAndRight(String title) {
        setTopTitleAndLeft(title);
        if (topRightTv == null) {
            //topRightTv = (TextView) mRootView.findViewById(R.id.top_right_tv);
        }
    }

    @Override
    public View getTitleView() {
        return topTvTitle;
    }

    @Override
    public View getTitleLeftView() {
        return topLeftTv;
    }

    @Override
    public View getTitleRightView() {
        return topRightTv;
    }
}
