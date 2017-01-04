package com.uncommontrade.e2b.collections;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.uncommontrade.e2b.Entities.EnCollectionsList;
import com.uncommontrade.e2b.Entities.EnServiceResponse;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.collections.adapter.CollectionsAdapter;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.HttpNetService;
import com.uncommontrade.e2b.utilities.Keys;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thoin_000 on 4/13/2016.
 */
public class CollectionsFragment extends android.support.v4.app.Fragment {
    private List<EnCollectionsList> mListCollections = new ArrayList<>();
    private LinearLayout mLlayoutProgress;
    private ProgressBar mProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View vCollections = inflater.inflate(R.layout.collections_fragment, container, false);
        mLlayoutProgress = (LinearLayout) vCollections.findViewById(R.id.llayout_collection_fragment_progress);
        mProgressBar = (ProgressBar) vCollections.findViewById(R.id.pb_collections_fragment);
        GetCollectionLists getList = new GetCollectionLists();
        getList.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        return vCollections;
    }

    private class GetCollectionLists extends AsyncTask<Void, Void, Void> {
        private int getListError = 0;
        private List<EnCollectionsList> listCollections = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String response = HttpNetService.instance.getCollectionList();
            if (!TextUtils.isEmpty(response)) {
                Gson gson = new Gson();
                EnServiceResponse enServiceResponse = gson.fromJson(response, EnServiceResponse.class);
                if (enServiceResponse.getCode() == Const.CODE_SUCCESS) {
                    listCollections.clear();
                    listCollections.addAll(enServiceResponse.getCollections());
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
            mLlayoutProgress.setVisibility(View.GONE);
            if (getListError == 0 && listCollections != null
                    && listCollections.size() > 0) {
                mListCollections.clear();
                mListCollections.addAll(listCollections);
                CollectionsAdapter adapter = new CollectionsAdapter(getActivity(), mListCollections);
                ListView lvCollections = (ListView) getView().findViewById(R.id.listview_collection);
                lvCollections.setAdapter(adapter);
                lvCollections.setVisibility(View.VISIBLE);
                lvCollections.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), ProductsListActivity.class);
                        intent.putExtra(Keys.KEY_COLLECTIONS_ID, mListCollections.get(position).getCollection_id());
                        intent.putExtra(Keys.KEY_COLLECTIONS_NAME, mListCollections.get(position).getName());
                        intent.putExtra(Keys.KEY_COLLECTIONS_DESCRIPTION, mListCollections.get(position).getDescription());
                        intent.putExtra(Keys.KEY_COLLECTIONS_IMAGE, mListCollections.get(position).getImage());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        getActivity().startActivityForResult(intent, Const.REQUEST_CODE_PRODUCTS_LIST);
                    }
                });
            }
        }
    }
}
