package me.yilv.yilvtravel.splash;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.yilv.yilvtravel.Config;
import me.yilv.yilvtravel.R;
import me.yilv.yilvtravel.WebBrowserActivity;
import me.yilv.yilvtravel.base.BaseFragmentActivity;
import me.yilv.yilvtravel.picturebrowser.ImgBrowserHolder;
import me.yilv.yilvtravel.picturebrowser.ImgFragment;
import me.yilv.yilvtravel.utils.SpUtil;


/**
 * Created by liliu on 15/4/23.
 */
public class SplashActivity extends BaseFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = "SplashActivity";
        setContentView(R.layout.activity_splash);

        View rootV = findViewById(android.R.id.content);
        ImgBrowserHolder holder = new ImgBrowserHolder(this, rootV);

        SpUtil spUtil = SpUtil.getInstance();
        boolean isShowLaunchSplash = spUtil.getBoolean(SpUtil.SP_IS_SHOW_LAUCNH_SPLASH, true);
        if (isShowLaunchSplash) {
            holder.setDatas(getLaunchDatas());
            rootV.postDelayed(mStartMainRunnable, Config.SPLASH_LAUNCH_SHOW_TIME);
            spUtil.putBoolean(SpUtil.SP_IS_SHOW_LAUCNH_SPLASH, false);
        } else {
            //holder.setDatas(getWelcomeDatas());
            //rootV.postDelayed(mStartMainRunnable, Config.SPLASH_WELCOME_SHOW_TIME);
            //TODO 暂且不需要欢饮页
            rootV.post(mStartMainRunnable);
        }
    }

    private List<Fragment> getWelcomeDatas() {
        List<Fragment> fragments = new ArrayList<>();
        //欢迎页图片数量为1
        for (int i = 0; i < 1; i++) {
            Fragment imgF = createImgFragment("welcome_img" + i);
            if (imgF != null) {
                fragments.add(imgF);
            }
        }
        return fragments;
    }

    private List<Fragment> getLaunchDatas() {
        List<Fragment> fragments = new ArrayList<>();
        //启动页图片数量为3
        for (int i = 0; i < 3; i++) {
            Fragment imgF = createImgFragment("launch_img" + i);
            if (imgF != null) {
                fragments.add(imgF);
            }
        }
        return fragments;
    }

    private int getImgResByName(String name) {
        Resources res = getResources();
        int indentify = res.getIdentifier(getPackageName() + ":drawable/" + name, null, null);
        /*if (indentify > 0) {
            d = res.getDrawable(indentify, getTheme());
        }*/
        return indentify;
    }

    private Fragment createImgFragment(String resName) {
        ImgFragment imgFragment = null;
        int imgRes = getImgResByName(resName);
        if (imgRes > 0) {
            imgFragment = new ImgFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ImgFragment.KEY_IMG_RES, imgRes);
            imgFragment.setArguments(bundle);

        }
        return imgFragment;
    }

    private Runnable mStartMainRunnable = new Runnable() {
        @Override
        public void run() {
            Intent mainIt = new Intent(SplashActivity.this, WebBrowserActivity.class);
            mainIt.putExtras(getIntent());
            startActivity(mainIt);
            finish();
        }
    };

}
