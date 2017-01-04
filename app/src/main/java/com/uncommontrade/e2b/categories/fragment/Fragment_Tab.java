package com.uncommontrade.e2b.categories.fragment;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import com.uncommontrade.e2b.Entities.EnParamsService;
import com.uncommontrade.e2b.Entities.EnProducts;
import com.uncommontrade.e2b.Entities.EnServiceResponse;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.categories.adapter.AdapterCategorieItem;
import com.uncommontrade.e2b.customview.TextViewPlus;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.HttpNetService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoangdinh on 4/24/2016.
 */
@SuppressLint("ValidFragment")
public class Fragment_Tab extends Fragment {

    int cat_id;
    int mPage = 0;
    String name;

    public Fragment_Tab(int cat_id, String name) {
        this.cat_id = cat_id;
        this.name = name;
    }

    ImageView imageView;
    TextViewPlus tvName;
    RecyclerView recyclerView;
    AdapterCategorieItem adapter;

    Gson gson = new Gson();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.categories_fragment_tabs, container, false);

        initView(view);
        return view;
    }

    public void initView(View view) {
        imageView = (ImageView) view.findViewById(R.id.categories_image_tab);
        tvName = (TextViewPlus) view.findViewById(R.id.txt_item_collection_title);
        recyclerView = (RecyclerView) view.findViewById(R.id.categories_recycler);
        adapter = new AdapterCategorieItem(getActivity());
        adapter.clearList();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(2, 20, true, 0));

        tvName.setText(name);

//        imageView.setBackgroundResource(R.drawable.devicesmall);
        new GetCategoriesItem().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

    }

    //lay du lieu tu JSON
    private class GetCategoriesItem extends AsyncTask<Void, Void, Boolean> {
        String data;
        private List<EnProducts> enProductsList = new ArrayList<>();

        @Override
        protected Boolean doInBackground(Void... params) {
            EnParamsService enParamsService = new EnParamsService();
            enParamsService.setPage(mPage);

            if (cat_id != 1) {
                enParamsService.setCat_id(cat_id);
                data = HttpNetService.instance.getCategoriProduct(gson.toJson(enParamsService));
                return true;
            } else {
                enParamsService.setCollection_id(cat_id);
                data = HttpNetService.instance.getCategoriProductAll(gson.toJson(enParamsService));
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);

            if (!TextUtils.isEmpty(data)) {
                EnServiceResponse enServiceResponse = gson.fromJson(data, EnServiceResponse.class);

                if (enServiceResponse.getCode() == Const.CODE_SUCCESS) {
                    enProductsList.clear();
                    if (aVoid) {
                        enProductsList.addAll(enServiceResponse.getData());
                    } else {
                        enProductsList.addAll(enServiceResponse.getProducts());
                    }

                    adapter.setList(enProductsList);
                } else {
                    Log.d("EnServiceResponse", enServiceResponse.getCode() + "");
                }
            } else {
                Log.d("JSON_DATA", "empty");
            }
        }
    }

    public int getCat_id() {
        return cat_id;
    }

    public int getmPage() {
        return mPage;
    }
}
