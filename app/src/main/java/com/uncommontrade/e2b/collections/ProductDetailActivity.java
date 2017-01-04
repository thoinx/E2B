package com.uncommontrade.e2b.collections;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uncommontrade.e2b.Entities.EnOptions;
import com.uncommontrade.e2b.Entities.EnParamsService;
import com.uncommontrade.e2b.Entities.EnProductResponse;
import com.uncommontrade.e2b.Entities.EnProducts;
import com.uncommontrade.e2b.Entities.EnServiceResponse;
import com.uncommontrade.e2b.R;

import com.uncommontrade.e2b.collections.adapter.DialogAttributesAdapter;
import com.uncommontrade.e2b.collections.adapter.ProductsListAdapter;
import com.uncommontrade.e2b.collections.adapter.SlidingImageAdapter;
import com.uncommontrade.e2b.customview.TextViewPlus;

import com.uncommontrade.e2b.customview.WrapContentViewPager;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.E2BProgress;
import com.uncommontrade.e2b.utilities.HttpNetService;
import com.uncommontrade.e2b.utilities.Keys;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by thoin_000 on 4/25/2016.
 */
public class ProductDetailActivity extends Activity implements AbsListView.OnScrollListener {
    private EnProductResponse mProDuctDetail;
    private static Gson gson = new Gson();
    private View footerView;
    private List<EnProducts> mListProductRelated = new ArrayList<>();
    private int mCurrentPage = 0;
    private boolean isLoadPageMore = true;
    private ProductsListAdapter adapter;
    private GetProductsList getProductsList;
    private RatingBar ratBar;
    private GetProductDetail getDetailTask;
    private float mProductID;
    private TextViewPlus txtAttributeName;
    private TextViewPlus txtQty;
    private LinearLayout llayoutAttribute;
    private View viewAttribute;
    private int mMaxQty = 0;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail_activity);
        Intent intent = this.getIntent();
        String title = Const.BLANK_STRING;
        if (intent != null) {
            mProductID = intent.getFloatExtra(Keys.KEY_PRODUCT_ID, 0);
            title = intent.getStringExtra(Keys.KEY_PRODUCT_TITLE);
        }
        TextViewPlus txtTitle = (TextViewPlus) findViewById(R.id.txt_product_detail_title);
        txtTitle.setText(title);
        getDetailTask = new GetProductDetail();
        getDetailTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        ImageView imvBack = (ImageView) findViewById(R.id.imv_product_detail_back);
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        LinearLayout btnCollections = (LinearLayout) findViewById(R.id.product_detail_btn_collections);
        LinearLayout btnCategory = (LinearLayout) findViewById(R.id.product_detail_btn_productions);
        LinearLayout btnCarts = (LinearLayout) findViewById(R.id.product_detail_btn_carts);
        LinearLayout btnProfile = (LinearLayout) findViewById(R.id.product_detail_btn_profiles);
        btnCollections.setSelected(true);
        btnCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                returnIntent.putExtra(Keys.KEY_OPEN_TAB, Const.TAB_COLLECTIONS);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                returnIntent.putExtra(Keys.KEY_OPEN_TAB, Const.TAB_CATEGORY);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        btnCarts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                returnIntent.putExtra(Keys.KEY_OPEN_TAB, Const.TAB_CARTS);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                returnIntent.putExtra(Keys.KEY_OPEN_TAB, Const.TAB_PROFILE);
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        });
    }

    private class GetProductDetail extends AsyncTask<Void, Void, Void> {

        private int errorCode = 0;
        private String errorMess;
        private E2BProgress progress = new E2BProgress(ProductDetailActivity.this);

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
            enParamsService.setProduct_id(mProductID);
            String response = HttpNetService.instance.getProductDetail(gson.toJson(enParamsService));
            if (!TextUtils.isEmpty(response)) {
                mProDuctDetail = gson.fromJson(response, EnProductResponse.class);
                if (mProDuctDetail.getCode() != Const.CODE_SUCCESS) {
                    errorCode = 1;
                    errorMess = mProDuctDetail.getError_message();
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
                if (mListProductRelated != null) {
                    mListProductRelated.clear();
                }

                GridViewWithHeaderAndFooter listView = (GridViewWithHeaderAndFooter) findViewById(R.id.gv_product_detail);
                if (null != headerView) {
                    listView.removeHeaderView(headerView);
                }
                if (null != footerView) {
                    listView.removeFooterView(footerView);
                }
                initLayout();
            } else {
                Toast.makeText(getApplicationContext(), errorMess, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initLayout() {
        if (mListProductRelated == null) {
            mListProductRelated = new ArrayList<>();
        }
        mListProductRelated.addAll(mProDuctDetail.getRelateds());
        adapter = new ProductsListAdapter(getApplicationContext(), mListProductRelated);
        GridViewWithHeaderAndFooter listView = (GridViewWithHeaderAndFooter) findViewById(R.id.gv_product_detail);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        headerView = layoutInflater.inflate(R.layout.item_product_detail, null);
        headerView.setTag(this.getClass().getSimpleName() + "header");
        TextViewPlus txtName = (TextViewPlus) headerView.findViewById(R.id.txt_item_detail_name);
        TextViewPlus txtDescription = (TextViewPlus) headerView.findViewById(R.id.txt_item_detail_Description);
        TextViewPlus txtPrice = (TextViewPlus) headerView.findViewById(R.id.txt_item_detail_price);
        WrapContentViewPager pager = (WrapContentViewPager) headerView.findViewById(R.id.pager_item_product_detail);
        CirclePageIndicator indicator = (CirclePageIndicator) headerView.findViewById(R.id.indicator_item_product_detail);
        TextViewPlus txtSku = (TextViewPlus) headerView.findViewById(R.id.txt_item_detail_sku);
        llayoutAttribute = (LinearLayout) headerView.findViewById(R.id.llayout_item_detail_attribute);
        viewAttribute = (View) headerView.findViewById(R.id.view_item_detail_attribute);
        TextViewPlus txtAttribute = (TextViewPlus) headerView.findViewById(R.id.txt_item_detail_attributes);
        txtAttributeName = (TextViewPlus) headerView.findViewById(R.id.txt_item_detail_attributes_value);
        txtQty = (TextViewPlus) headerView.findViewById(R.id.txt_item_detail_qty);
        Button btnAddToCart = (Button) headerView.findViewById(R.id.btn_item_detail_add_to_cart);
        LinearLayout llayoutAttributeValue = (LinearLayout) headerView.findViewById(R.id.llayout_item_detail_attribute_name);
        LinearLayout llayoutQty = (LinearLayout) headerView.findViewById(R.id.llayout_item_detail_attribute_qty);

        llayoutQty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                List<String> listQty =  new ArrayList<String>();
                for (int i = 0; i <= mMaxQty; i++) {
                    String qty = String.valueOf(i);
                    listQty.add(qty);
                }
                showDialogAttribute(null, listQty);
            }
        });

        if (null != mProDuctDetail.getAttribute()) {
            txtAttribute.setText(mProDuctDetail.getAttribute().getAttr_name());
        } else {
            llayoutAttribute.setVisibility(View.VISIBLE);
            viewAttribute.setVisibility(View.VISIBLE);
            llayoutAttributeValue.setVisibility(View.INVISIBLE);
            llayoutQty.setVisibility(View.VISIBLE);
            mMaxQty = Integer.valueOf(mProDuctDetail.getProduct().getQty());
            displayQty("0");
        }
        llayoutAttributeValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAttribute(mProDuctDetail.getOptions(), null);
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mProDuctDetail.getAttribute() && llayoutAttribute.getVisibility() != View.VISIBLE) {
                    showDialogAttribute(mProDuctDetail.getOptions(), null);
                } else {
                    AddToCart addToCartTask = new AddToCart(txtQty.getText().toString());
                    addToCartTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });

        ratBar = (RatingBar) headerView.findViewById(R.id.rat_item_detail);
        LinearLayout llayoutReview = (LinearLayout) headerView.findViewById(R.id.llayout_product_detail_reviews);
        llayoutReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductDetailActivity.this, ListReviewsActivity.class);
                intent.putExtra(Keys.KEY_PRODUCT_ID, mProductID);
                intent.putExtra(Keys.KEY_PRODUCT_REVIEW, mProDuctDetail.getProduct().getReview_rate());
                startActivityForResult(intent, Const.REQUEST_CODE_LIST_REVIEW);
            }
        });
        txtSku.setText(mProDuctDetail.getProduct().getDescription());

        LayerDrawable stars = (LayerDrawable) ratBar.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(getResources().getColor(R.color.yellow), PorterDuff.Mode.SRC_ATOP);
        ratBar.setRating(Float.valueOf(mProDuctDetail.getProduct().getReview_rate()));

        txtName.setText(mProDuctDetail.getProduct().getTitle());

        txtDescription.setText(mProDuctDetail.getProduct().getBrand().getBrand_name());

        StringBuilder price = new StringBuilder();
        price.append(Const.DOLLAR_SIGN_STRING);
        price.append(mProDuctDetail.getProduct().getSelling_price());
        txtPrice.setText(price.toString());
        if (null != mProDuctDetail.getImages_list()) {
            pager.setAdapter(new SlidingImageAdapter(getApplicationContext(), mProDuctDetail.getImages_list()));
            indicator.setViewPager(pager);
        }
        final float density = getResources().getDisplayMetrics().density;
        indicator.setRadius(5 * density);

        listView.addHeaderView(headerView);
        // init footer
        footerView = layoutInflater.inflate(R.layout.item_footer_list, null);
        footerView.setTag(this.getClass().getSimpleName() + "header");
        footerView.setVisibility(View.GONE);
        listView.addFooterView(footerView);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextViewPlus txtTitle = (TextViewPlus) findViewById(R.id.txt_product_detail_title);
                txtTitle.setText(mListProductRelated.get(position).getTitle());
                mProductID = mListProductRelated.get(position).getProduct_id();
                getDetailTask = new GetProductDetail();
                getDetailTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    private class GetProductsList extends AsyncTask<Void, Void, Void> {
        private int getListError = 0;
        private List<EnProducts> listProducts = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            EnParamsService enParamsService = new EnParamsService();
            enParamsService.setProduct_id(mProductID);
            enParamsService.setPage(mCurrentPage);
            String response = HttpNetService.instance.getProductsRelateList(gson.toJson(enParamsService));
            if (!TextUtils.isEmpty(response)) {
                EnServiceResponse enServiceResponse = gson.fromJson(response, EnServiceResponse.class);
                if (enServiceResponse.getCode() == Const.CODE_SUCCESS) {
                    listProducts.clear();
                    listProducts.addAll(enServiceResponse.getData()

                    );
                } else {
                    getListError = 1;
                }
            } else {
                getListError = 1;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (getListError == 0 && null != listProducts) {
                if (listProducts.size() == 0) {
                    isLoadPageMore = false;
                    if (footerView.getVisibility() == View.VISIBLE) {
                        footerView.setVisibility(View.GONE);
                    }
                } else {
                    mListProductRelated.addAll(listProducts);
                    if (null != adapter) {
                        adapter.notifyDataSetChanged();
                    }
                }
            } else {
                isLoadPageMore = false;
            }
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
            if (isLoadPageMore && ((null != getProductsList
                    && getProductsList.getStatus() != AsyncTask.Status.RUNNING)
                    || getProductsList == null)) {
                mCurrentPage++;
                getProductsList = new GetProductsList();
                getProductsList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                footerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_LIST_REVIEW) {
            if (resultCode == Activity.RESULT_CANCELED) {
                String rate = data.getStringExtra(Keys.KEY_RATE_REVIEW);
                if (!TextUtils.isEmpty(rate)) {
                    ratBar.setRating(Float.valueOf(rate));
                }
            }
        }
    }

    private void showDialogAttribute(final List<EnOptions> listOption, final List<String> listQty) {
        final Dialog dialog = new Dialog(ProductDetailActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.dialog_choose_attributes);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.CENTER;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_BLUR_BEHIND;
        window.setAttributes(wlp);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        TextViewPlus txtTitle = (TextViewPlus) dialog.findViewById(R.id.txt_dialog_choose_attribute_title);
        StringBuilder title = new StringBuilder();
        title.append(getString(R.string.choose));
        title.append(" ");
        if (null != listOption) {
            title.append(mProDuctDetail.getAttribute().getAttr_name());
        } else {
            title.append(getString(R.string.qty));
        }
        txtTitle.setText(title.toString());
        DialogAttributesAdapter attributesAdapter = new DialogAttributesAdapter(ProductDetailActivity.this, listOption, listQty);
        ListView lvAttribute = (ListView) dialog.findViewById(R.id.lv_dialog_choose_attribute);
        lvAttribute.setAdapter(attributesAdapter);
        lvAttribute.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialog.dismiss();
                if (null != listOption) {
                    displayAttribute(position);
                } else {
                    displayQty(listQty.get(position));
                }
            }
        });
        dialog.show();
    }

    private void displayAttribute(int posotion) {
        String attribute = mProDuctDetail.getOptions().get(posotion).getOption_name();
        if (!attribute.equals(txtAttributeName.getText().toString())) {
            txtAttributeName.setText(Html.fromHtml(attribute));
            mMaxQty = Integer.valueOf(mProDuctDetail.getOptions().get(posotion).getQty());
            if (llayoutAttribute.getVisibility() == View.INVISIBLE || llayoutAttribute.getVisibility() == View.GONE) {
                llayoutAttribute.setVisibility(View.VISIBLE);
                viewAttribute.setVisibility(View.VISIBLE);
            }
            displayQty("0");
        }
    }

    private void displayQty(String qty) {
        txtQty.setText(qty);
        if (txtQty.getVisibility() == View.INVISIBLE || txtQty.getVisibility() == View.GONE) {
            txtQty.setVisibility(View.VISIBLE);
        }
    }

    private class AddToCart extends AsyncTask<Void, Void, Void> {

        private int errorCode = 0;
        private String errorMess;
        private E2BProgress progress = new E2BProgress(ProductDetailActivity.this);
        private String qty;

        public AddToCart(String qty) {
            this.qty = qty;
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
            EnParamsService enParamsService = new EnParamsService();
            enParamsService.setProduct_id(mProductID);
            enParamsService.setQty(qty);
            String response = HttpNetService.instance.addToCart(gson.toJson(enParamsService));
            if (!TextUtils.isEmpty(response)) {
                mProDuctDetail = gson.fromJson(response, EnProductResponse.class);
                if (mProDuctDetail.getCode() != Const.CODE_SUCCESS) {
                    errorCode = 1;
                    errorMess = mProDuctDetail.getError_message();
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
                Toast.makeText(getApplicationContext(), getString(R.string.add_to_cart_success), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), errorMess, Toast.LENGTH_LONG).show();
            }
        }
    }

}
