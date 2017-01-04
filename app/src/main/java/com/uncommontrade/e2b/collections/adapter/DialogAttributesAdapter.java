package com.uncommontrade.e2b.collections.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.uncommontrade.e2b.Entities.EnOptions;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.customview.TextViewPlus;

import java.util.List;

/**
 * Created by thoin_000 on 5/7/2016.
 */
public class DialogAttributesAdapter extends BaseAdapter {
    private Context mContext;
    private List<EnOptions> mListOption;
    private List<String> mListString;

    public DialogAttributesAdapter(Context context, List<EnOptions> list, List<String> listString) {
        mContext = context;
        mListOption = list;
        mListString = listString;
    }

    @Override
    public int getCount() {
        if (null != mListOption) {
            return mListOption.size();
        } else if (null != mListString) {
            return mListString.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if (null != mListOption && mListOption.size() > 0) {
            return mListOption.get(position);
        } else if (null != mListString && mListString.size() > 0) {
            return mListString.get(position);
        }else {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_dialog_attributes, null);
            holder = new ViewHolder();
            holder.txtTitle = (TextViewPlus) convertView.findViewById(R.id.txt_item_dialog_attribute);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (null != mListString) {
            String qty = mListString.get(position);
            if (!TextUtils.isEmpty(qty)) {
                holder.txtTitle.setText(qty);
            }
        } else {
            EnOptions enOptions = mListOption.get(position);
            if (null != enOptions) {
                holder.txtTitle.setText(enOptions.getOption_name());
            }
        }
        return convertView;
    }

    private static class ViewHolder{
        TextViewPlus txtTitle;
    }
}
