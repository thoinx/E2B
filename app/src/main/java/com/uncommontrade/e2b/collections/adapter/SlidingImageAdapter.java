package com.uncommontrade.e2b.collections.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.uncommontrade.e2b.Entities.EnImageList;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.utilities.Const;

import java.util.List;

/**
 * Created by SONU on 29/08/15.
 */
public class SlidingImageAdapter extends PagerAdapter {


    private List<EnImageList> list;
    private LayoutInflater inflater;
    private Context context;
    private ImageLoader imageLoader;

    public SlidingImageAdapter(Context context, List<EnImageList> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        //init image loader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true)
                .build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.MAX_PRIORITY)
                .writeDebugLogs();
        ImageLoaderConfiguration config = builder.build();
        imageLoader = ImageLoader.getInstance();
        if (imageLoader.isInited())
        {
            imageLoader.destroy();
        }
        imageLoader.init(config);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (null != list) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        FrameLayout imageLayout = (FrameLayout) inflater.inflate(R.layout.slidingimages_layout, null);

        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.imv_sliding);
        StringBuilder imageUrl = new StringBuilder();
        imageUrl.append(Const.DOMAIN_IMAGE);
        imageUrl.append(list.get(position).getImage());
        ImageAware imageAware = new ImageViewAware(imageView, false);
        imageLoader.displayImage(imageUrl.toString(), imageAware);

        view.addView(imageLayout);


        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
