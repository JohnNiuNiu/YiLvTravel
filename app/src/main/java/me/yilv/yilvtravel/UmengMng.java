package me.yilv.yilvtravel;

import android.app.Activity;

import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateConfig;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

/**
 * Created by liliu on 15/10/19.
 */
public class UmengMng {
    private static final String TAG = UmengMng.class.getSimpleName();
    private Activity mActivity;

    public UmengMng(Activity context) {
        this.mActivity = context;
    }

    private UmengDialogButtonListener mUmengDlgBtnListener = new UmengDialogButtonListener() {
        @Override
        public void onClick(int i) {
            switch (i) {
                case UpdateStatus.Update:
                    break;
                default:
                    mActivity.finish();
                    break;
            }
        }
    };

    private UmengUpdateListener mUmengUpdateListener = new UmengUpdateListener() {
        @Override
        public void onUpdateReturned(int i, UpdateResponse updateResponse) {
            // case 0: // has update
            // case 1: // has no update
            // case 2: // none wifi
            // case 3: // time out
            boolean hasUpdate = false;
            if (updateResponse != null) {
                hasUpdate = updateResponse.hasUpdate;
                LogUtil.d(TAG, "onUpdateReturned->status:" + i + "//updateResponse.hasUpdate:" + updateResponse.hasUpdate);
            } else {
                LogUtil.w(TAG, "onUpdateReturned->status:" + i + "//updateResponse is null");
            }
        }
    };

    public void forceUpdate() {
        //umeng update
        UpdateConfig.setDebug(Config.IS_DEBUG);
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        UmengUpdateAgent.setDialogListener(mUmengDlgBtnListener);
        UmengUpdateAgent.setUpdateListener(mUmengUpdateListener);
        UmengUpdateAgent.forceUpdate(mActivity);
    }
}
