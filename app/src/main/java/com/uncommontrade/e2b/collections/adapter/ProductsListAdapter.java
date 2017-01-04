package com.uncommontrade.e2b.collections.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.uncommontrade.e2b.Entities.EnProducts;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.customview.TextViewPlus;
import com.uncommontrade.e2b.utilities.Const;

import java.util.List;

/**
 * Created by thoin_000 on 4/21/2016.
 */
public class ProductsListAdapter extends BaseAdapter {

    private Context mContext;
    private ImageLoader imageLoader;
    private List<EnProducts> mList;

    public ProductsListAdapter(Context context, List<EnProducts> list) {
        this.mList = list;
        this.mContext = context;
        //init image loader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true)
                .build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(mContext)
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
    public int getCount() {
        if (null != mList) {
            return mList.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (null != mList && mList.size() > 0) {
            return mList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_products_list, null);
            holder = new ViewHolder();
            holder.txtName = (TextViewPlus) convertView.findViewById(R.id.txt_item_products_list_name);
            holder.txtDescription = (TextViewPlus) convertView.findViewById(R.id.txt_item_products_list_des);
            holder.imvImageView = (ImageView) convertView.findViewById(R.id.imv_item_products);
            holder.txtPrice = (TextViewPlus) convertView.findViewById(R.id.txt_item_products_list_price);
            holder.vLeft = (View) convertView.findViewById(R.id.view_item_products_list_left);
            holder.vRight = (View) convertView.findViewById(R.id.view_item_products_list_right);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EnProducts item = mList.get(position);
        if (null != item) {
            holder.txtName.setText(item.getTitle());
            holder.txtDescription.setText(item.getBrand().getBrand_name());
            StringBuilder imageUrl = new StringBuilder();
            imageUrl.append(Const.DOMAIN_IMAGE);
            imageUrl.append(item.getImage());
            ImageAware imageAware = new ImageViewAware(holder.imvImageView, false);
            imageLoader.displayImage(imageUrl.toString(), imageAware);
            StringBuilder price = new StringBuilder();
            price.append(Const.DOLLAR_SIGN_STRING);
            price.append(item.getSelling_price());
            holder.txtPrice.setText(price.toString());
            if (position == 0 || (position % 2 == 0)) {
                holder.vRight.setVisibility(View.GONE);
                holder.vLeft.setVisibility(View.VISIBLE);
            } else {
                holder.vRight.setVisibility(View.VISIBLE);
                holder.vLeft.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        TextViewPlus txtName;
        TextViewPlus txtDescription;
        TextViewPlus txtPrice;
        ImageView imvImageView;
        View vLeft;
        View vRight;
    }
}
