package com.uncommontrade.e2b.collections;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uncommontrade.e2b.Entities.EnReview;
import com.uncommontrade.e2b.Entities.EnServiceResponse;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.E2BProgress;
import com.uncommontrade.e2b.utilities.HttpNetService;
import com.uncommontrade.e2b.utilities.Keys;

/**
 * Created by thoin_000 on 5/3/2016.
 */
public class ProductReviewActivity extends Activity{

    private float mProductId;
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_review_activity);
        Intent intent = this.getIntent();

        if (intent != null) {
            mProductId =intent.getFloatExtra(Keys.KEY_PRODUCT_ID, 0f);
        }
        //init view
        RatingBar ratingBar = (RatingBar) findViewById(R.id.rat_product_review);
        ratingBar.setRating(1.0f);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override public void onRatingChanged(RatingBar ratingBar, float rating,
                                                  boolean fromUser) {
                if(rating<1.0f) {
                    ratingBar.setRating(1.0f);
                } else if(rating == 1.5f) {
                    ratingBar.setRating(2.0f);
                }else if(rating == 2.5f) {
                    ratingBar.setRating(3.0f);
                }else if(rating == 3.5f) {
                    ratingBar.setRating(4.0f);
                }else if(rating == 4.5f) {
                    ratingBar.setRating(5.0f);
                }
            }
        });
        LayerDrawable stars = (LayerDrawable) ratingBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        hideSoftKeyboard();
        ImageView imvBack = (ImageView) findViewById(R.id.imv_product_review_back);
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        LinearLayout btnSubmit = (LinearLayout) findViewById(R.id.llayout_product_review_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RatingBar ratingBar = (RatingBar) findViewById(R.id.rat_product_review);
                float rating = ratingBar.getRating();
                EditText edtName = (EditText) findViewById(R.id.edt_product_review_name);
                EditText edtTitle = (EditText) findViewById(R.id.edt_product_review_title);
                EditText edtReview = (EditText) findViewById(R.id.edt_product_review_review);
                EnReview enReview = new EnReview();
                enReview.setName(edtName.getText().toString());
                enReview.setReview_content(edtReview.getText().toString());
                enReview.setReview_title(edtTitle.getText().toString());
                enReview.setProduct_id(mProductId);
                enReview.setRate(rating);
                RatingReview ratingReviewTask = new RatingReview(gson.toJson(enReview));
                ratingReviewTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            }
        });
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

    private class RatingReview extends AsyncTask<Void, Void, Void> {

        private int errorCode = 0;
        private String errorMess;
        private String data;
        private E2BProgress progress = new E2BProgress(ProductReviewActivity.this);

        public RatingReview(String data) {
            this.data = data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (progress != null) {
                progress.show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            String response = HttpNetService.instance.postRatingReview(data);
            if (!TextUtils.isEmpty(response)) {
                EnServiceResponse enServiceResponse = gson.fromJson(response, EnServiceResponse.class);
                if (enServiceResponse.getCode() != Const.CODE_SUCCESS) {
                    errorCode = 1;
                    errorMess = enServiceResponse.getError_message();
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
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), errorMess, Toast.LENGTH_LONG).show();
            }
        }
    }
}
