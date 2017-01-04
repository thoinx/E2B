package com.uncommontrade.e2b;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uncommontrade.e2b.Entities.EnParamsService;
import com.uncommontrade.e2b.Entities.EnServiceResponse;
import com.uncommontrade.e2b.customview.TextViewPlus;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.E2BProgress;
import com.uncommontrade.e2b.utilities.HttpNetService;
import com.uncommontrade.e2b.utilities.Keys;
import com.uncommontrade.e2b.utilities.SharedPreferenceManager;

/**
 * Created by thoin_000 on 5/16/2016.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private SharedPreferenceManager sharedPreferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        sharedPreferenceManager = new SharedPreferenceManager(getApplicationContext());
        boolean isLogin = sharedPreferenceManager.getBoolean(Keys.KEY_PREF_LOGIN_SUCCESS, false);
        if (isLogin) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        ImageView imvBack = (ImageView) findViewById(R.id.imv_login_activity_back);
        imvBack.setOnClickListener(this);

        TextViewPlus txtSignIn = (TextViewPlus) findViewById(R.id.txt_login_activity_sign_in);
        txtSignIn.setOnClickListener(this);

        Button btSignUpMail = (Button) findViewById(R.id.btn_login_activity_email);
        btSignUpMail.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Handle onClick event of next button
            case R.id.imv_login_activity_back:
                finish();
                break;
            case R.id.txt_login_activity_sign_in:
                EditText edtEmail = (EditText) findViewById(R.id.edt_login_activity_email);
                EditText edtPassword = (EditText) findViewById(R.id.edt_login_activity_password);
                final String email = edtEmail.getText().toString();
                final String password = edtPassword.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (TextUtils.isEmpty(email)) {
                    edtEmail.requestFocus();
                    imm.showSoftInput(edtEmail, InputMethodManager.SHOW_IMPLICIT);
                    Toast.makeText(LoginActivity.this, "Input Email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.requestFocus();
                    imm.showSoftInput(edtPassword, InputMethodManager.SHOW_IMPLICIT);
                    Toast.makeText(LoginActivity.this, "Input Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginApp loginTask = new LoginApp(getApplicationContext(), email, password);
                loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                break;

            //goi activity SignUp
            case R.id.btn_login_activity_email:
                Intent intent= new Intent(this, SignupActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private class LoginApp extends AsyncTask<Void, Void, Void> {

        private E2BProgress progress = new E2BProgress(LoginActivity.this);
        private String email;
        private String password;
        private Context context;
        private int errorCode = 0;
        private String errorMess;

        public LoginApp(Context context, String email, String password) {
            this.context = context;
            this.email = email;
            this.password = password;
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
            Gson gson = new Gson();
            EnParamsService enParamsService = new EnParamsService();
            enParamsService.setAccount(email);
            enParamsService.setPassword(password);
           /* try {
                byte[] data = password.getBytes("UTF-8");
                String base64 = Base64.encodeToString(data, Base64.DEFAULT);
                enParamsService.setPassword(base64);
                this.password = base64;
            } catch (Exception e) {
                e.printStackTrace();
            }*/

           /* enParamsService.setDevice_id(Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID));
            String response = HttpNetService.instance.postLogin(gson.toJson(enParamsService));
            if (!TextUtils.isEmpty(response)) {
                EnServiceResponse enServiceResponse = gson.fromJson(response, EnServiceResponse.class);
                if (enServiceResponse.getCode() != Const.CODE_SUCCESS) {
                    errorCode = 1;
                    errorMess = enServiceResponse.getError_message();
                } else {
                    StringBuilder uid = new StringBuilder();
                    uid.append(enServiceResponse.getProfile().getUid());
                    uid.append("|");
                    uid.append(enServiceResponse.getAccess_token());
                    sharedPreferenceManager.saveBoolean(Keys.KEY_PREF_LOGIN_SUCCESS, true);
                    sharedPreferenceManager.saveString(Keys.KEY_PREF_UID, uid.toString());
                    sharedPreferenceManager.saveString(Keys.KEY_PREF_PROFILE, gson.toJson(enServiceResponse.getProfile()));
                }
            } else {
                errorCode = 1;
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (progress != null && progress.isShowing()) {
                progress.dismiss();
            }
//            if (errorCode == 0) {
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
//            } else {
//                Toast.makeText(getApplicationContext(), errorMess, Toast.LENGTH_LONG).show();
//            }
        }
    }
}
