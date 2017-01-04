package com.uncommontrade.e2b.collections.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;

import com.uncommontrade.e2b.Entities.EnReviewList;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.customview.TextViewPlus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by thoin_000 on 5/5/2016.
 */
public class ListReviewAdapter extends BaseAdapter{
    private Context mContext;
    private List<EnReviewList> list;

    public ListReviewAdapter (Context context, List<EnReviewList> list) {
        this.mContext = context;
        this.list = list;
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
    public Object getItem(int position) {
        if (null != list && list.size() > 0) {
            return list.get(position);
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_review, null);
            holder = new ViewHolder();
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.rat_item_list_review);
            holder.txtName = (TextViewPlus) convertView.findViewById(R.id.txt_item_list_review_name);
            holder.txtTitle = (TextViewPlus) convertView.findViewById(R.id.txt_item_list_review_title);
            holder.txtTime = (TextViewPlus) convertView.findViewById(R.id.txt_item_list_review_time);
            holder.txtDes = (TextViewPlus) convertView.findViewById(R.id.txt_item_list_review_des);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EnReviewList enReviewList = list.get(position);
        if (null != enReviewList) {
            LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1).setColorFilter(mContext.getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
            holder.ratingBar.setRating(Float.valueOf(enReviewList.getRate()));
            holder.txtName.setText(enReviewList.getName());
            holder.txtTitle.setText(enReviewList.getReview_title());
            holder.txtDes.setText(enReviewList.getReview_content());
            holder.txtTime.setText(getDate(Long.valueOf(enReviewList.getCtime()), "dd/MM/yyyy"));
        }
        return convertView;
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    private static class ViewHolder{
        TextViewPlus txtTitle;
        TextViewPlus txtDes;
        TextViewPlus txtName;
        TextViewPlus txtTime;
        RatingBar ratingBar;
    }
}
