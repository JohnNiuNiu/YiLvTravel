package me.yilv.yilvtravel.base;

import android.view.View;

/**
 * Created by liliu on 15/5/8.
 */
public interface SetTitle {
    /**
     * 设置顶部标题
     *
     * @param title
     */
    void setTopTitle(String title);

    /**
     * 设置顶部标题，开启左边按钮返回功能；
     *
     * @param title
     */
    void setTopTitleAndLeft(String title);

    /**
     * 设置顶部标题，开启左边按钮返回功能,并实例化右边按钮；
     *
     * @param title`
     */
    void setTopTitleAndLeftAndRight(String title);

    View getTitleView();

    View getTitleLeftView();

    View getTitleRightView();
}
