package me.yilv.yilvtravel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by liliu on 15/4/2.
 */
public class IntentHelper {
    private static final String TAG = IntentHelper.class.getSimpleName();

    /**
     * 更具地址进行视图跳转
     *
     * @param c
     * @param url
     */
    public static final void browserView(Context c, String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            c.startActivity(intent);
        } catch (Exception e) {
            LogUtil.e(TAG, "browserView", e);
        }
    }

}
