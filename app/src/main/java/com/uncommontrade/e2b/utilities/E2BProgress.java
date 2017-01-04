package com.uncommontrade.e2b.utilities;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uncommontrade.e2b.R;

/**
 * Created by thoin_000 on 4/20/2016.
 */
public class E2BProgress extends Dialog{
    private ProgressBar mProgress;
    private TextView mMessageView;

    private Context mContext;

    public E2BProgress(Context context) {
        super(context, R.style.AppTheme);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_progress);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        mProgress = (ProgressBar) findViewById(R.id.pbWaiting);
        mMessageView = (TextView) findViewById(R.id.tvMegWaiting);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void setMessage(CharSequence message) {
        if (mProgress != null && null != mMessageView) {
            mMessageView.setText(message);
        } else {
            //
        }
    }
}
