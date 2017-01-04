package com.uncommontrade.e2b.categories.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import com.uncommontrade.e2b.Entities.EnCategoriResponse;
import com.uncommontrade.e2b.Entities.EnCategories;
import com.uncommontrade.e2b.Entities.EnParamsService;
import com.uncommontrade.e2b.Entities.EnProducts;
import com.uncommontrade.e2b.Entities.EnServiceResponse;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.categories.adapter.AdapterCategorieItem;
import com.uncommontrade.e2b.categories.adapter.AdapterCategoriesTab;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.HttpNetService;
import com.uncommontrade.e2b.utilities.L;

import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.categories.adapter.AdapterCategorieItem;
import com.uncommontrade.e2b.categories.adapter.AdapterCategoriesTab;
import com.uncommontrade.e2b.utilities.E2BProgress;
import com.uncommontrade.e2b.utilities.HttpNetService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by thoin_000 on 4/13/2016.
 */
public class CategoriesFragment extends Fragment implements View.OnClickListener {

    ViewPager mViewPager;
    TabLayout mTab;
    View search, ll_search, delete;
    EditText edSearch;

    AdapterCategoriesTab adapter;
    RecyclerView recyclerView;
//    private LinearLayout mLlayoutProgress;
//    private ProgressBar mProgressBar;

    Gson gson = new Gson();
//    private E2BProgress progress = new E2BProgress(getContext());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View vCategories = inflater.inflate(R.layout.categories_fragment, container, false);

        initView(vCategories);
        return vCategories;
    }

    void initView(View view) {

        mTab = (TabLayout) view.findViewById(R.id.categories_tabs);
        mViewPager = (ViewPager) view.findViewById(R.id.categories_viewpager);
        ll_search = view.findViewById(R.id.ll_search);
        search = view.findViewById(R.id.categories_search);
        delete = view.findViewById(R.id.delete);
        edSearch = (EditText) view.findViewById(R.id.categories_edit_search);
//        mLlayoutProgress = (LinearLayout) view.findViewById(R.id.llayout_progress);
//        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_fragment);
        recyclerView = (RecyclerView) view.findViewById(R.id.categories_recycler);
        adapter = new AdapterCategoriesTab(getActivity().getSupportFragmentManager());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.addItemDecoration(new ItemOffsetDecoration(2, 20, true, 0));

        new GetCategoriesTab().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        mTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("Select_Tab", tab.getPosition() + "");
                mViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.d("UnSelect_Tab", tab.getPosition() + "");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.d("ReSelect_Tab", tab.getPosition() + "");
            }
        });

        search.setOnClickListener(this);
        delete.setOnClickListener(this);
        edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    hideKeyBroad(v);
//                    searchText = edSearch.getText().toString();
//                    Toast.makeText(getActivity(), searchText, Toast.LENGTH_LONG).show();

                    int cat_id = ((Fragment_Tab) adapter.getItem(mTab.getSelectedTabPosition())).getCat_id();
                    int mPage = ((Fragment_Tab) adapter.getItem(mTab.getSelectedTabPosition())).getmPage();

                    new GetCategoriesItem().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (cat_id + ""), (mPage + ""), edSearch.getText().toString());

                    edSearch.setText(R.string.search_results);
                    edSearch.setEnabled(false);

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.categories_search:
                mTab.setVisibility(View.GONE);
                edSearch.setText("");
                edSearch.setEnabled(true);

                ll_search.setVisibility(View.VISIBLE);
                mViewPager.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                break;

            case R.id.delete:
                hideKeyBroad(v);
                mTab.setVisibility(View.VISIBLE);
                ll_search.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                mViewPager.setVisibility(View.VISIBLE);
                break;

            default:
                break;
        }
    }

    public void hideKeyBroad(View v) {
        v = getActivity().getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    //lay du lieu tu JSON
    private class GetCategoriesTab extends AsyncTask<Void, Void, Void> {
        String data;
        List<EnCategories> enCategoriesList = new ArrayList<>();

        @Override
        protected Void doInBackground(Void... params) {
            data = HttpNetService.instance.getListCategories();
            return null;
        }

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            if (progress != null) {
//                progress.show();
//            }
//        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
//            if (progress != null && progress.isShowing()) {
//                progress.dismiss();
//            }
//            mLlayoutProgress.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(data)) {
                EnCategoriResponse enCategoriResponse = gson.fromJson(data, EnCategoriResponse.class);
                if (enCategoriResponse.getCode() == Const.CODE_SUCCESS) {
                    enCategoriesList.clear();
                    enCategoriesList.addAll(enCategoriResponse.getCategories());

                    for (int i = enCategoriesList.size() - 1; i >= 0; i--) {
                        int cat_id = enCategoriesList.get(i).getCat_id();
                        String name = enCategoriesList.get(i).getName();

                        adapter.addFragment(new Fragment_Tab(cat_id, name), name);
                    }

                    adapter.notifyDataSetChanged();
                    mViewPager.setAdapter(adapter);
                    mTab.setupWithViewPager(mViewPager);
                } else {
                    L.d("EnCategoriResponse", enCategoriResponse.getCode() + "");
                }
            } else {
                L.d("JSON_DATA", "empty");
            }
        }
    }

    //lay du lieu search
    private class GetCategoriesItem extends AsyncTask<String, Void, Boolean> {
        String data;
        List<EnProducts> enProductsList = new ArrayList<>();

        @Override
        protected Boolean doInBackground(String... params) {
            EnParamsService enParamsService = new EnParamsService();

            enParamsService.setPage(Integer.valueOf(params[1]));
            enParamsService.setKey(params[2]);

            if (Integer.valueOf(params[0]) != 1) {
                enParamsService.setCat_id(Integer.valueOf(params[0]));
                data = HttpNetService.instance.getCategoriProduct(gson.toJson(enParamsService));
                return true;
            } else {
                enParamsService.setCollection_id(Integer.valueOf(params[0]));
                data = HttpNetService.instance.getCategoriProductAll(gson.toJson(enParamsService));
                return false;
            }
        }
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            if (progress != null) {
//                progress.show();
//            }
//        }
        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
//            if (progress != null && progress.isShowing()) {
//                progress.dismiss();
//            }
//            mLlayoutProgress.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(data)) {
                EnServiceResponse enServiceResponse = gson.fromJson(data, EnServiceResponse.class);

                if (enServiceResponse.getCode() == Const.CODE_SUCCESS) {
                    enProductsList.clear();
                    if (aVoid) {
                        enProductsList.addAll(enServiceResponse.getData());
                    } else {
                        enProductsList.addAll(enServiceResponse.getProducts());
                    }

                    AdapterCategorieItem adapterSearch = new AdapterCategorieItem(getContext());

                    recyclerView.setAdapter(adapterSearch);
                    adapterSearch.clearList();
                    adapterSearch.setList(enProductsList);

                    recyclerView.setVisibility(View.VISIBLE);
                    mViewPager.setVisibility(View.GONE);
                } else {
                    Log.d("EnServiceResponse", enServiceResponse.getCode() + "");
                }
            } else {
                Log.d("Data search", "empty");
            }
        }
    }
}
