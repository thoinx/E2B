package com.uncommontrade.e2b.profiles.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.utilities.L;

/**
 * Created by NGUYENHUUTA on 5/13/2016.
 */
public class FragmentSupport extends Fragment implements View.OnClickListener {

    private TextView tvCall;
    private TextView tvEmail;
    private TextView tvTerms;
    private TextView tvPrivacy;

    static final String NUMBER = "tel:01647882708";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_support, container, false);

        init(view);
        return view;
    }

    public void init(View view) {
        tvCall = (TextView) view.findViewById(R.id.tvCall);
        tvEmail = (TextView) view.findViewById(R.id.tvEmail);
        tvTerms = (TextView) view.findViewById(R.id.tvTerms);
        tvPrivacy = (TextView) view.findViewById(R.id.tvPrivacy);

        tvCall.setOnClickListener(this);
        tvEmail.setOnClickListener(this);
        tvTerms.setOnClickListener(this);
        tvPrivacy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.tvCall):
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.dialog_yesno);

                Button btDeny = (Button) dialog.findViewById(R.id.btDeny);
                Button btAccept = (Button) dialog.findViewById(R.id.btAccept);
                TextView tvNumber = (TextView) dialog.findViewById(R.id.tvNumber);
                tvNumber.setText(NUMBER);

                btDeny.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btAccept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse(NUMBER));
                        startActivity(intent);
                    }
                });
                dialog.show();
                L.d("CLICK", "call");
                break;
            case (R.id.tvEmail):
                L.d("CLICK", "email");
                emailSupport(new String[]{"1234@gamil.com"}, null, null, "fuckSub", "Fuck!!!!Body");
                break;
            case (R.id.tvTerms):
                break;
            case (R.id.tvPrivacy):
                break;
            default:
                break;
        }
    }

    public void emailSupport(String[] email, String cc, String bcc, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        if (!TextUtils.isEmpty(cc)) {
            intent.putExtra(Intent.EXTRA_CC, cc);
        }
        if (!TextUtils.isEmpty(bcc)) {
            intent.putExtra(Intent.EXTRA_BCC, bcc);
        }
        if (!TextUtils.isEmpty(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (!TextUtils.isEmpty(text)) {
            intent.putExtra(Intent.EXTRA_TEXT, text);
        }

//        try {
//            startActivity(Intent.createChooser(intent, "Send mail..."));
//            getActivity().finish();
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(getContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
        startActivity(intent);
    }
}
