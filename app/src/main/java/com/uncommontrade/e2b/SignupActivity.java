package com.uncommontrade.e2b;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uncommontrade.e2b.Entities.EnParamsService;
import com.uncommontrade.e2b.Entities.EnServiceResponse;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.HttpNetService;
import com.uncommontrade.e2b.utilities.HttpUtil;

import java.util.Calendar;

/**
 * Created by HoangDinh on 5/17/2016.
 */
public class SignupActivity extends Activity implements View.OnClickListener {

    ImageView imClose;
    EditText edEmail;
    TextView tvLocation;
    LinearLayout llBirth;
    TextView tvText;
    TextView tvBirthMonth;
    TextView tvBirthDay;
    TextView tvBirthYear;
    EditText edPassword;
    TextView tvSignUp;
    Button btSignIn;
    Button btSignUpFb;

    String location;
    String[] strLocation = {"us", "uk", "vn", "is", "nato", "russia"};

    String[] strMonth = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    int day, month, year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        init();
    }

    public void init() {
        imClose = (ImageView) findViewById(R.id.imv_login_activity_back);
        edEmail = (EditText) findViewById(R.id.edt_signup_activity_email);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        llBirth = (LinearLayout) findViewById(R.id.llBirth);
        tvText = (TextView) findViewById(R.id.tvText);
        tvBirthMonth = (TextView) findViewById(R.id.tvBirthMonth);
        tvBirthDay = (TextView) findViewById(R.id.tvBirthDay);
        tvBirthYear = (TextView) findViewById(R.id.tvBirthYear);
        edPassword = (EditText) findViewById(R.id.edt_signup_activity_password);
        tvSignUp = (TextView) findViewById(R.id.tvSignUp);
        btSignIn = (Button) findViewById(R.id.btn_login);
        btSignUpFb = (Button) findViewById(R.id.btn_login_activity_facebook);

        imClose.setOnClickListener(this);
        edEmail.setOnClickListener(this);
        tvLocation.setOnClickListener(this);
        llBirth.setOnClickListener(this);
        edPassword.setOnClickListener(this);
        tvSignUp.setOnClickListener(this);
        btSignIn.setOnClickListener(this);
        btSignUpFb.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        year = c.get(Calendar.YEAR);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imv_login_activity_back:
                finish();
                break;
            case R.id.edt_signup_activity_email:
                break;
            case R.id.tvLocation:
                setPickerLocation(new Dialog(this));
                break;
            case R.id.llBirth:
                setPickerDate(new Dialog(this));
                break;
            case R.id.edt_signup_activity_password:
                break;
            case R.id.tvSignUp:
                String email = String.valueOf(edEmail.getText());
                String password = String.valueOf(edPassword.getText());
                String username = null;
                String fbId = null;
                if (!TextUtils.isEmpty(email)) {
                    username = email.split("@")[0];
                }
                if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)) {
                    Register register = new Register(email, username, password, fbId);
                    register.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    Toast.makeText(this, "Input Information", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_login:
                finish();
                break;
            case R.id.btn_login_activity_facebook:
                break;
            default:
                break;
        }
    }

    public void setPickerLocation(final Dialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_location);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);

        TextView tvDone = (TextView) dialog.findViewById(R.id.tvDone);
        NumberPicker np = (NumberPicker) dialog.findViewById(R.id.np);

        setDividerColor(np, Color.WHITE);

        np.setMinValue(0);
        np.setMaxValue(strLocation.length - 1);
        np.setDisplayedValues(strLocation);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                location = strLocation[newVal];
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLocation.setText(location);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void setPickerDate(final Dialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_birthday);

        WindowManager.LayoutParams lpDate = new WindowManager.LayoutParams();
        lpDate.copyFrom(dialog.getWindow().getAttributes());
        lpDate.width = WindowManager.LayoutParams.MATCH_PARENT;
        lpDate.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lpDate.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lpDate);

        TextView tvDoneDate = (TextView) dialog.findViewById(R.id.tvDone);
        NumberPicker pickerMonth = (NumberPicker) dialog.findViewById(R.id.pickerMonth);
        NumberPicker pickerDay = (NumberPicker) dialog.findViewById(R.id.pickerDay);
        NumberPicker pickerYear = (NumberPicker) dialog.findViewById(R.id.pickerYear);

        setDividerColor(pickerMonth, Color.WHITE);
        setDividerColor(pickerDay, Color.WHITE);
        setDividerColor(pickerYear, Color.WHITE);

        pickerMonth.setMinValue(0);
        pickerMonth.setMaxValue(strMonth.length - 1);
        pickerMonth.setDisplayedValues(strMonth);
        pickerMonth.setWrapSelectorWheel(true);
        pickerMonth.setValue(month);
        pickerMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                month = newVal;
            }
        });

        pickerDay.setMinValue(1);
        pickerDay.setMaxValue(31);
        pickerDay.setWrapSelectorWheel(true);
        pickerDay.setValue(day);
        pickerDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                day = newVal;
            }
        });

        pickerYear.setMinValue(1900);
        pickerYear.setMaxValue(Calendar.getInstance().get(Calendar.YEAR));
        pickerYear.setWrapSelectorWheel(true);
        pickerYear.setValue(year);
        pickerYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                year = newVal;
            }
        });

        tvDoneDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                if (day > maxDay) {
                    day = maxDay;
                }
                c.set(Calendar.DAY_OF_MONTH, day);

                tvBirthMonth.setText(String.valueOf(month + 1));
                tvBirthDay.setText(String.valueOf(day));
                tvBirthYear.setText(String.valueOf(year));
                tvText.setVisibility(View.GONE);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public class Register extends AsyncTask<Void, Void, String> {
        private String email;
        private String userName;
        private String password;
        private String fbId;

        private int errorCode = 0;
        private String errorMess;
        private Gson gson = new Gson();

        public Register(String email, String userName, String password, String fbId) {
            this.email = email;
            this.userName = userName;
            this.fbId = fbId;

            try {
                byte[] data = password.getBytes("UTF-8");
                String base64 = Base64.encodeToString(data, Base64.DEFAULT);

                this.password = base64;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... params) {

            EnParamsService enParamsService = new EnParamsService();
            enParamsService.setEmail(email);
            enParamsService.setUsername(userName);
            enParamsService.setPassword(password);
            enParamsService.setFbid(fbId);

            String response = HttpNetService.instance.postSignUpEmail(gson.toJson(enParamsService));

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (!TextUtils.isEmpty(s)) {
                EnServiceResponse enServiceResponse = gson.fromJson(s, EnServiceResponse.class);
                if (enServiceResponse.getCode() != Const.CODE_SUCCESS) {
                    errorCode = 1;
                    errorMess = enServiceResponse.getError_message();
                    Toast.makeText(getApplicationContext(), errorMess, Toast.LENGTH_LONG).show();
                } else {
                    finish();
                }
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
