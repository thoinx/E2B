package com.uncommontrade.e2b.carts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uncommontrade.e2b.R;

/**
 * Created by thoin_000 on 4/13/2016.
 */
public class CartsFragment extends android.support.v4.app.Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View vCarts = inflater.inflate(R.layout.carts_fragment, container, false);
        return vCarts;
    }
}
