package com.uncommontrade.e2b.collections.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.uncommontrade.e2b.Entities.EnCollectionsList;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.customview.TextViewPlus;
import com.uncommontrade.e2b.utilities.Const;

import java.util.List;

/**
 * Created by thoin_000 on 4/15/2016.
 */
public class CollectionsAdapter extends BaseAdapter{

    private Context mContext;
    private List<EnCollectionsList> mList;
    public CollectionsAdapter(Context context, List<EnCollectionsList> mList) {
        this.mList = mList;
        this.mContext = context;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_collections, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextViewPlus) convertView.findViewById(R.id.txt_item_collection_title);
            holder.txtDescription = (TextViewPlus) convertView.findViewById(R.id.txt_item_collection_description);
            holder.imvBackGround = (ImageView) convertView.findViewById(R.id.imv_item_collection_bg);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        EnCollectionsList item = mList.get(position);
        if (null != item) {
            holder.txtTitle.setText(item.getName());
            holder.txtDescription.setText(item.getDescription());
            StringBuilder imageUrl = new StringBuilder();
            imageUrl.append(Const.DOMAIN_IMAGE);
            imageUrl.append(item.getImage());
            Picasso.with(mContext).load(imageUrl.toString()).into(holder.imvBackGround);
        }
        return convertView;
    }

    private static class ViewHolder {
        TextViewPlus txtTitle;
        TextViewPlus txtDescription;
        ImageView imvBackGround;
    }
}
