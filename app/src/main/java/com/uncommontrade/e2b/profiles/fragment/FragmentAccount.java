package com.uncommontrade.e2b.profiles.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uncommontrade.e2b.Entities.EnProfile;
import com.uncommontrade.e2b.Entities.EnServiceResponse;
import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.HttpNetService;

/**
 * Created by NGUYENHUUTA on 5/13/2016.
 */
public class FragmentAccount extends Fragment implements View.OnClickListener {
    private TextView tvFirstName;
    private TextView tvLastName;
    private TextView tvEmail;
    private Button btLogout;

    Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_account, container, false);

        init(view);

        return view;
    }

    public void init(View view) {
        tvFirstName = (TextView) view.findViewById(R.id.tvFirstName);
        tvLastName = (TextView) view.findViewById(R.id.tvLastName);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        btLogout = (Button) view.findViewById(R.id.btLogout);

        new GetProf().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        btLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btLogout):
                //set log out
                break;
            default:
                break;
        }
    }

    public class GetProf extends AsyncTask<Void, Void, String> {
        private EnProfile enProfile;
        private EnServiceResponse enServiceResponse;

        @Override
        protected String doInBackground(Void... params) {

            return HttpNetService.instance.getProfile();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            enServiceResponse = gson.fromJson(s, EnServiceResponse.class);
            enProfile = enServiceResponse.getProfile();
            tvEmail.setText(enProfile.getEmail());
        }
    }
}
