package com.uncommontrade.e2b.categories.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.uncommontrade.e2b.Entities.EnProducts;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.collections.ProductDetailActivity;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.Keys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoangdinh on 4/24/2016.
 */
public class AdapterCategorieItem extends RecyclerView.Adapter<AdapterCategorieItem.MyHolder> {
    LayoutInflater inflater;
    Context context;
    List<EnProducts> list;

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .cacheInMemory(true) // default
            .cacheOnDisk(true) // default
            .considerExifParams(false) // default
            .bitmapConfig(Bitmap.Config.ARGB_8888) // default
            .showImageForEmptyUri(R.drawable.ic_launcher)
            .showImageOnFail(R.drawable.ic_launcher)
            .showImageOnLoading(R.drawable.ic_launcher)
            .displayer(new FadeInBitmapDisplayer(500, true, false, false))
            .resetViewBeforeLoading(true).build();
    ImageLoader imageLoader;

    public AdapterCategorieItem(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context)
                .defaultDisplayImageOptions(options)
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

    public void clearList() {
        list= new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setList(List<EnProducts> list) {
        this.list= list;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_categories, parent, false);

        return new MyHolder(view, list, context);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        EnProducts enProducts = list.get(position);

        String title = enProducts.getTitle();
        String description = enProducts.getDescription();
        String image = enProducts.getImage();
        float selling_price = enProducts.getSelling_price();

        holder.tvTitle.setText(title);
        holder.tvNature.setText(description);
        holder.tvPrice.setText(selling_price + "$");
        imageLoader.displayImage("http://app.vapecode.co/" + image, holder.imageView, options);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageView;
        TextView tvTitle, tvNature, tvPrice;
        Context context;
        List<EnProducts> list;

        public MyHolder(View itemView, List<EnProducts> list, Context context) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivItem);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitleItem);
            tvNature = (TextView) itemView.findViewById(R.id.tvNatureItem);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPriceItem);

            this.list = list;
            this.context = context;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            EnProducts enProducts = list.get(getAdapterPosition());
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra(Keys.KEY_PRODUCT_ID, enProducts.getProduct_id());
            intent.putExtra(Keys.KEY_PRODUCT_TITLE, enProducts.getTitle());
            ((Activity) context).startActivityForResult(intent, Const.REQUEST_CODE_PRODUCTS_DETAIL);
        }
    }
}