package com.uncommontrade.e2b.collections;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uncommontrade.e2b.Entities.EnListReviewsResponse;
import com.uncommontrade.e2b.Entities.EnParamsService;
import com.uncommontrade.e2b.Entities.EnReviewList;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.collections.adapter.ListReviewAdapter;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.E2BProgress;
import com.uncommontrade.e2b.utilities.HttpNetService;
import com.uncommontrade.e2b.utilities.Keys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thoin_000 on 5/5/2016.
 */
public class ListReviewsActivity extends Activity {

    private float mProductId;
    private int mCurrentPage = 0;
    private static Gson gson = new Gson();
    private List<EnReviewList> mList = new ArrayList<>();
    private ListReviewAdapter adapter;
    private String mRateReviews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_reviews_activity);
        Intent intent = this.getIntent();
        String rating = "0";
        if (intent != null) {
            mProductId = intent.getFloatExtra(Keys.KEY_PRODUCT_ID, 0);
            rating = intent.getStringExtra(Keys.KEY_PRODUCT_REVIEW);
        }
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rat_list_reviews);
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        ratingBar.setRating(Float.valueOf(rating));

        LinearLayout llayoutLeaveReview = (LinearLayout) findViewById(R.id.llayout_list_review_leave);
        llayoutLeaveReview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startProductReview();
            }
        });
        ImageView imvBack = (ImageView) findViewById(R.id.imv_list_reviews_back);
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                returnIntent.putExtra(Keys.KEY_RATE_REVIEW, mRateReviews);
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        ListView lvReview = (ListView) findViewById(R.id.lv_list_reviews);
        adapter = new ListReviewAdapter(ListReviewsActivity.this, mList);
        lvReview.setAdapter(adapter);
        GetListReviews getListReviews = new GetListReviews();
        getListReviews.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void startProductReview(){
        Intent intent = new Intent(ListReviewsActivity.this, ProductReviewActivity.class);
        intent.putExtra(Keys.KEY_PRODUCT_ID, mProductId);
        startActivityForResult(intent, Const.REQUEST_CODE_PRODUCTS_REVIEW);
    }

    private class GetListReviews extends AsyncTask<Void, Void, Void> {

        private int errorCode = 0;
        private String errorMess;
        private E2BProgress progress = new E2BProgress(ListReviewsActivity.this);
        private EnListReviewsResponse enListReviewsResponse;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progress != null) {
                progress.show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            EnParamsService enParamsService = new EnParamsService();
            enParamsService.setProduct_id(mProductId);
            enParamsService.setPage(mCurrentPage);
            String response = HttpNetService.instance.getListReview(gson.toJson(enParamsService));
            if (!TextUtils.isEmpty(response)) {
                enListReviewsResponse = gson.fromJson(response, EnListReviewsResponse.class);
                if (enListReviewsResponse.getCode() != Const.CODE_SUCCESS) {
                    errorCode = 1;
                    errorMess = enListReviewsResponse.getError_message();
                }
            } else {
                errorCode = 1;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progress != null && progress.isShowing()) {
                progress.dismiss();
            }
            if (errorCode == 0) {
               if (null == mList) {
                   mList = new ArrayList<>();
               }
                mList.clear();
                mList.addAll(enListReviewsResponse.getData());
                if (null != adapter) {
                    adapter.notifyDataSetChanged();
                }
                RatingBar ratingBar = (RatingBar) findViewById(R.id.rat_list_reviews);
                mRateReviews = enListReviewsResponse.getReview_rate();
                if (!TextUtils.isEmpty(mRateReviews)) {
                    ratingBar.setRating(Float.valueOf(mRateReviews));
                }
            } else {
                Toast.makeText(getApplicationContext(), errorMess, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_PRODUCTS_REVIEW) {
            if(resultCode == Activity.RESULT_OK){
                GetListReviews getListReviews = new GetListReviews();
                getListReviews.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        }
    }



    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        returnIntent.putExtra(Keys.KEY_RATE_REVIEW, mRateReviews);
        setResult(Activity.RESULT_CANCELED, returnIntent);
        finish();
    }
}
