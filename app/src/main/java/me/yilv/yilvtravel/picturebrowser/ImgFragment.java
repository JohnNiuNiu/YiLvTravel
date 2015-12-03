package me.yilv.yilvtravel.picturebrowser;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;

import me.yilv.yilvtravel.R;
import me.yilv.yilvtravel.base.BaseFragment;
import me.yilv.yilvtravel.common.GlobalContext;


/**
 * Created by liliu on 15/4/1.
 */
public class ImgFragment extends BaseFragment {
    public static final String KEY_IMG_URL = "key_img_url";
    private String imgUrl = "";
    public static final String KEY_IMG_RES = "key_img_res";
    private int imgRes = -1;

    /*parceld obj*/
    public static final String KEY_PARCELD_OBJ = "key_parceld_obj";
    private Parcelable mGetedParcel;

    private ImageView mBrowserImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = "ImgFragment";
        isAnalytics = false;

        imgUrl = getArguments() != null ? getArguments().getString(KEY_IMG_URL) : "";
        mGetedParcel = getArguments() != null ? getArguments().getParcelable(KEY_PARCELD_OBJ) : null;
        imgRes = getArguments() != null ? getArguments().getInt(KEY_IMG_RES) : null;
    }

    @Override
    public View onCreateCacheView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_img, null);
    }

    @Override
    public void onCacheViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onCacheViewCreated(view, savedInstanceState);
        mBrowserImg = (ImageView) view.findViewById(R.id.img_browser_iv);
        if (imgRes > 0) {
            displayImg(imgRes);
        } else {
            displayImg(imgUrl);
        }
    }

    public void displayImg(String url) {
        if (TextUtils.equals(url, this.imgUrl)) {
            this.imgUrl = url;
        }
        if (!TextUtils.isEmpty(imgUrl) && mBrowserImg != null) {
            if (mGetedParcel != null) {
                mBrowserImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            ImageLoader.getInstance().displayImage(imgUrl, mBrowserImg, ((GlobalContext) getActivity().getApplicationContext()).createDisplayImgOptions());
        }
    }

    public void displayImg(int res) {
        if (mBrowserImg != null) {
            mBrowserImg.setImageResource(res);
        }
    }
}
