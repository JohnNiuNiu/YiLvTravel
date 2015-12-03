package me.yilv.yilvtravel.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * SharedPreferences工具类
 *
 * @author liliu
 */
public final class SpUtil {
    static final String TAG = SpUtil.class.getSimpleName();

    private static Context mContext;
    public static SpUtil mInstance;
    private static SharedPreferences mSp;

    //------basic config------//
    /*是否显示启动引导页*/
    public static final String SP_IS_SHOW_LAUCNH_SPLASH = "sp_is_show_laucnh_splash";
    /*屏幕宽度*/
    public static final String SP_SCREEN_WIDTH = "sp_screen_width";
    /*屏幕高度*/
    public static final String SP_SCREEN_HEIGHT = "sp_screen_height";
    /*公共Dialog宽度*/
    public static final String SP_DIALOG_WIDTH = "sp_dialog_width";
    /*标题栏高度*/
    public static final String SP_TOP_TITLE_HEIGHT = "sp_top_height";
    /*状态栏高度*/
    public static final String SP_STATUSBAR_HEIGHT = "sp_statusbar_height";
    //------setting------//

    //------user infor------//
    //---意见反馈---//
    public static final String SP_FEEDBACK_DETAIL = "sp_feedback_detail";

    /*默认收获地址Id*/
    private static final String SP_DEFAULT_DELIVERY_ADDR = "sp_default_delivery_addr";

    /*手机登陆，账号缓存*/
    public static final String SP_LOGIN_PHONE_ACCOUNT = "sp_login_phone_account";

    /*登陆cookie*/
    public static final String SP_CKS = "sp_cks";

    /*user*/
    public static final String SP_U = "sp_u";

    /*当前地理位置*/
    public static final String SP_CURRENT_LOCATION = "sp_current_location";

    public static final String SP_DEBUG_IP = "sp_debug_ip";

    private SpUtil(Context c) {
        mContext = c;
        mSp = mContext.getSharedPreferences(c.getPackageName(), Context.MODE_PRIVATE);
        mSp.edit().commit();
    }

    public static SpUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SpUtil(context);
        }
        return mInstance;
    }

    public static SpUtil getInstance() {
        if (mInstance == null) {
            throw new NullPointerException("getInstance(context) do not called");
        }
        return mInstance;
    }

    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

    public long getLong(String key, int defValue) {
        return mSp.getLong(key, defValue);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    public void putString(String key, String value) {
        mSp.edit().putString(key, value).commit();
    }

    public void putInt(String key, int value) {
        mSp.edit().putInt(key, value).commit();
    }

    public void putLong(String key, long value) {
        mSp.edit().putLong(key, value).commit();
    }

    public void putBoolean(String key, boolean value) {
        mSp.edit().putBoolean(key, value).commit();
    }

    public void removeByKey(String key) {
        mSp.edit().remove(key).commit();
    }

    /**
     * 获取dialog width
     *
     * @return
     */
    public int getDialogWidth() {
        int dw = getInt(SP_DIALOG_WIDTH, 0);
        if (dw == 0) {
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
            dw = (int) (dm.widthPixels * 0.9);
            putInt(SP_DIALOG_WIDTH, dw);
        }
        return dw;
    }

    /**
     * @param key
     * @return
     */
    public int getLastVersionCode(String key) {
        return getInt(key, 0);
    }

    /**
     * 获取屏幕宽度
     *
     * @return
     */
    public int getScreenWidth() {
        int sw = getInt(SP_SCREEN_WIDTH, 0);
        if (sw == 0) {
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
            sw = dm.widthPixels;
        }
        return sw;
    }

    /**
     * 获取屏幕高度
     *
     * @return
     */
    public int getScreenHeight() {
        int sh = getInt(SP_SCREEN_HEIGHT, 0);
        if (sh == 0) {
            DisplayMetrics dm = mContext.getResources().getDisplayMetrics();// 获得屏幕参数：主要是分辨率，像素等。
            sh = dm.heightPixels;
        }
        return sh;
    }

    /**
     * 获取状态栏高度
     *
     * @return
     */
    public int getStatusBarHeight() {
        int h = getInt(SP_STATUSBAR_HEIGHT, 0);
        return h;
    }

    /**
     * 获取标题栏高度
     *
     * @return
     */
    public int getTitleTopHeight() {
        int h = getInt(SP_TOP_TITLE_HEIGHT, 0);
        return h;
    }

    /**
     * 设置全局收获地址
     *
     * @param jsonAddr
     */
    public boolean setDeliveryAddr(String jsonAddr) {
        if (!TextUtils.isEmpty(jsonAddr)) {
            putString(SP_DEFAULT_DELIVERY_ADDR, jsonAddr);
            return true;
        }
        return false;
    }

    /**
     * 删除默认收获地址
     *
     * @return
     */
    public void removeDeliveryAddr() {
        removeByKey(SP_DEFAULT_DELIVERY_ADDR);
    }

    /**
     * 设置当前位置
     *
     * @param location
     */
    public void setCurrentLocation(String... location) {
        String temp = "";
        for (String s : location) {
            temp = temp + s + ",";
        }
        putString(SP_CURRENT_LOCATION, temp);
    }

    /**
     * 获取当前位置 province,district,city
     *
     * @return
     */
    public String[] getCurrentLocation() {
        String[] locations = {"", "", ""};
        String[] temps = getString(SP_CURRENT_LOCATION, "").split(",");
        for (int i = 0; i < temps.length; i++) {
            locations[i] = temps[i];
        }
        return locations;
    }
}
