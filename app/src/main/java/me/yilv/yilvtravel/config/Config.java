package me.yilv.yilvtravel.config;

import me.yilv.yilvtravel.BuildConfig;

/**
 * Created by liliu on 15/3/22.
 */
public class Config {
    /*app scheme,manifest is using*/
    public static final String SCHEME = "yilv";
    /*test channel*/
    public static final String APP_TEST_CHANNEL = "TEST";
    /*debug channel,Notice:Config.IS_DEBUG = true*/
    public static final String APP_DEBUG_CHANNEL = "DEBUG";
    /*TEST_ENV_DOMAIN is used for testChannel*/
    public static final String TEST_ENV_DOMAIN = "http://120.26.119.159:9994";
    public static String BEAN_REQUEST_DOMAIN = "http://120.26.119.159:9994";

    /**
     * APP是否在Debug模式
     */
    public static boolean IS_DEBUG = BuildConfig.DEBUG;

    public static void setDomain(String domain) {
        BEAN_REQUEST_DOMAIN = domain;
    }
}
