package com.uncommontrade.e2b.collections;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.uncommontrade.e2b.Entities.EnParamsService;
import com.uncommontrade.e2b.Entities.EnProducts;
import com.uncommontrade.e2b.Entities.EnServiceResponse;
import com.uncommontrade.e2b.R;

import com.uncommontrade.e2b.collections.adapter.ProductsListAdapter;
import com.uncommontrade.e2b.customview.TextViewPlus;

import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.HttpNetService;
import com.uncommontrade.e2b.utilities.Keys;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.GridViewWithHeaderAndFooter;

/**
 * Created by thoin_000 on 4/21/2016.
 */
public class ProductsListActivity extends Activity implements AbsListView.OnScrollListener{
    private static Gson gson = new Gson();
    private List<EnProducts> mListProducts = new ArrayList<>();
    private ProductsListAdapter adapter;
    private ImageLoader imageLoader;
    private View footerView;
    private int mCurrentPage = 0;
    private ProgressBar mPbLoading;
    private GridViewWithHeaderAndFooter gvCollections;
    private LinearLayout llayoutTab;
    private int myLastVisiblePos;
    private String collectionsID;
    private GetProductsList getProductsList;
    private boolean isLoadPageMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.products_list_activity);

        //init image loader
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).cacheInMemory(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.RGB_565).resetViewBeforeLoading(true)
                .build();
        ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(getApplicationContext())
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

        // get data from collection fragment
        Intent intent = getIntent();
        collectionsID = intent.getStringExtra(Keys.KEY_COLLECTIONS_ID);
        String collectionsName = intent.getStringExtra(Keys.KEY_COLLECTIONS_NAME);
        String collectionsDescription = intent.getStringExtra(Keys.KEY_COLLECTIONS_DESCRIPTION);
        String collectionsImage = intent.getStringExtra(Keys.KEY_COLLECTIONS_IMAGE);

        // init gridview product
        adapter = new ProductsListAdapter(this, mListProducts);
        gvCollections = (GridViewWithHeaderAndFooter) findViewById(R.id.gv_product_list);

        // init header products
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View headerView = layoutInflater.inflate(R.layout.item_collections, null);
        TextViewPlus txtTitleHeader = (TextViewPlus) headerView.findViewById(R.id.txt_item_collection_title);
        TextViewPlus txtDescriptionHeader = (TextViewPlus) headerView.findViewById(R.id.txt_item_collection_description);
        ImageView imvBackGroundHeader = (ImageView) headerView.findViewById(R.id.imv_item_collection_bg);
        imvBackGroundHeader.setVisibility(View.VISIBLE);
        txtTitleHeader.setText(collectionsName);
        txtDescriptionHeader.setText(collectionsDescription);
        StringBuilder imageUrl = new StringBuilder();
        imageUrl.append(Const.DOMAIN_IMAGE);
        imageUrl.append(collectionsImage);
        imageLoader.displayImage(imageUrl.toString(), imvBackGroundHeader);
        mPbLoading = (ProgressBar) headerView.findViewById(R.id.pb_item_collection_loading);
        mPbLoading.setVisibility(View.VISIBLE);
        gvCollections.addHeaderView(headerView);
        // init footer
        footerView = layoutInflater.inflate(R.layout.item_footer_list, null);
        footerView.setVisibility(View.GONE);
        gvCollections.addFooterView(footerView);
        gvCollections.setAdapter(adapter);
        gvCollections.setOnScrollListener(this);
        myLastVisiblePos = gvCollections.getFirstVisiblePosition();

        // start get data from service
        getProductsList = new GetProductsList(Integer.valueOf(collectionsID));
        getProductsList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        // init view
        ImageView imvBack = (ImageView) findViewById(R.id.imv_product_list_back);
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
        llayoutTab = (LinearLayout) findViewById(R.id.llayout_product_list_tab);
        LinearLayout btnCollections = (LinearLayout) findViewById(R.id.product_btn_collections);
        LinearLayout btnCategory = (LinearLayout) findViewById(R.id.product_btn_productions);
        LinearLayout btnCarts = (LinearLayout) findViewById(R.id.product_btn_carts);
        LinearLayout btnProfile = (LinearLayout) findViewById(R.id.product_btn_profiles);
        btnCollections.setSelected(true);
        btnCollections.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                setResult(Activity.RESULT_CANCELED, returnIntent);
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

        gvCollections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductsListActivity.this, ProductDetailActivity.class);
                intent.putExtra(Keys.KEY_PRODUCT_ID, mListProducts.get(position).getProduct_id());
                intent.putExtra(Keys.KEY_PRODUCT_TITLE, mListProducts.get(position).getTitle());
                startActivityForResult(intent, Const.REQUEST_CODE_PRODUCTS_DETAIL);
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0 && null != mListProducts &&
                (mListProducts.size() == 4 || mListProducts.size() == 3)) {
            if(llayoutTab.getVisibility() == View.VISIBLE) {
                llayoutTab.setVisibility(View.GONE);
            } else if(llayoutTab.getVisibility() == View.GONE) {
                llayoutTab.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int currentFirstVisPos = view.getFirstVisiblePosition();
        if(currentFirstVisPos > myLastVisiblePos) {
            llayoutTab.setVisibility(View.GONE);
        }
        if(currentFirstVisPos < myLastVisiblePos) {
            llayoutTab.setVisibility(View.VISIBLE);
        }
        myLastVisiblePos = currentFirstVisPos;
        if (firstVisibleItem + visibleItemCount >= totalItemCount) {
            if (isLoadPageMore && null != getProductsList
                    && getProductsList.getStatus() != AsyncTask.Status.RUNNING
                    && null != mListProducts && (mListProducts.size() % 2 == 0)) {
                mCurrentPage++;
                getProductsList = new GetProductsList(Integer.valueOf(collectionsID));
                getProductsList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                footerView.setVisibility(View.VISIBLE);
            }
        }

    }

    private class GetProductsList extends AsyncTask<Void, Void, Void> {
        private int getListError = 0;
        private List<EnProducts> listProducts = new ArrayList<>();
        private int collectionID;

        public GetProductsList(int collection_id) {
            this.collectionID = collection_id;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            EnParamsService enParamsService = new EnParamsService();
            enParamsService.setCollection_id(collectionID);
            enParamsService.setPage(mCurrentPage);
            String response = HttpNetService.instance.getProductsList(gson.toJson(enParamsService));
            if (!TextUtils.isEmpty(response)) {
                EnServiceResponse enServiceResponse = gson.fromJson(response, EnServiceResponse.class);
                if (enServiceResponse.getCode() == Const.CODE_SUCCESS) {
                    listProducts.clear();
                    listProducts.addAll(enServiceResponse.getProducts());
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
            if (mPbLoading.getVisibility() == View.VISIBLE) {
                mPbLoading.setVisibility(View.GONE);
            }
            if (getListError == 0 && null != listProducts) {
                if (listProducts.size() == 0) {
                    isLoadPageMore = false;
                    if (footerView.getVisibility() == View.VISIBLE) {
                        footerView.setVisibility(View.GONE);
                    }
                }else {
                    mListProducts.addAll(listProducts);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_PRODUCTS_DETAIL) {
            if(resultCode == Activity.RESULT_OK){
                if (data.hasExtra(Keys.KEY_PRODUCT_ID)) {
                    Intent intent = new Intent(ProductsListActivity.this, ProductDetailActivity.class);
                    intent.putExtra(Keys.KEY_PRODUCT_ID, data.getFloatExtra(Keys.KEY_PRODUCT_ID, 0));
                    intent.putExtra(Keys.KEY_PRODUCT_TITLE, data.getStringExtra(Keys.KEY_PRODUCT_TITLE));
                    startActivityForResult(intent, Const.REQUEST_CODE_PRODUCTS_DETAIL);
                } else {
                    int tabPosition = data.getIntExtra(Keys.KEY_OPEN_TAB, 0);
                    Intent returnIntent = new Intent();
                    returnIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    returnIntent.putExtra(Keys.KEY_OPEN_TAB, tabPosition);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        }
    }
}
