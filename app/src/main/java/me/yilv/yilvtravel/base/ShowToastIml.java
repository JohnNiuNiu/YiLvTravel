package me.yilv.yilvtravel.base;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by liliu on 15/4/30.
 */
public class ShowToastIml implements ShowToast {
    private Context mContext;
    private Toast mToast = null;

    public ShowToastIml(Context c) {
        mContext = c;
    }

    @Override
    public void showToast(String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void showToast(int resId) {
        showToast(mContext.getResources().getString(resId));
    }

    @Override
    public void showToastLongTime(String msg) {
        showToast(msg, Toast.LENGTH_LONG);
    }

    @Override
    public void showToastLongTime(int resId) {
        showToastLongTime(mContext.getResources().getString(resId));
    }

    private void showToast(String msg, int during) {
        if (!TextUtils.isEmpty(msg)) {
            if (mToast == null) {
                mToast = Toast.makeText(mContext, msg, during);
            } else {
                mToast.setText(msg);
            }
            mToast.show();
        }
    }
}
