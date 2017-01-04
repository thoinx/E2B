package com.uncommontrade.e2b.profiles.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.uncommontrade.e2b.R;

/**
 * Created by NGUYENHUUTA on 5/13/2016.
 */
public class FragmentCards extends Fragment {
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.layout_cards, container,false);
	}

}
