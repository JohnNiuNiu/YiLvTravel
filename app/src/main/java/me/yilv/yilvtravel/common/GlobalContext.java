package me.yilv.yilvtravel.common;/**
 * Created by liliu on 15/9/6.
 */

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.net.CookieHandler;
import java.net.CookieManager;

import me.yilv.yilvtravel.R;
import me.yilv.yilvtravel.config.Config;
import me.yilv.yilvtravel.utils.SpUtil;

public class GlobalContext extends Application {
    public DisplayImageOptions mImageOptions;

    @Override
    public void onCreate() {
        super.onCreate();
        //set CookieManager
        CookieHandler.setDefault(new CookieManager());
        SpUtil.getInstance(this);
        //com.about.wefresh.Config.IS_DEBUG = Config.IS_DEBUG;
        Config.setDomain(Config.BEAN_REQUEST_DOMAIN);
        //set cache cookie
        //AppHelper.setCacheCookie();

        String ip = SpUtil.getInstance().getString(SpUtil.SP_DEBUG_IP, "");
        if (!TextUtils.isEmpty(ip)) {
            Config.setDomain(ip);
        }

        //配置 UIL
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), getString(R.string.app_name) + "/imageloader/Cache");
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration
                .Builder(this)
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(10)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                        //.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 6 * 1000, 10 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .diskCache(new UnlimitedDiscCache(cacheDir));
        //.diskCacheFileCount(100);
        if (Config.IS_DEBUG) {
            builder.writeDebugLogs();
        }

        ImageLoaderConfiguration config = builder.build();//开始构建
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        mImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565).showImageOnLoading(android.R.color.white)
                .showImageOnFail(android.R.color.white)
                .cacheInMemory(true)
                .displayer(new FadeInBitmapDisplayer(100))
                .build();
    }

    public DisplayImageOptions createDisplayImgOptions() {
        return mImageOptions;
    }

}

