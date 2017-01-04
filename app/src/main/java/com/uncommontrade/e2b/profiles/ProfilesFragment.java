package com.uncommontrade.e2b.profiles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;

import android.widget.TextView;

import com.uncommontrade.e2b.R;
import com.uncommontrade.e2b.profiles.fragment.FragmentAccount;
import com.uncommontrade.e2b.profiles.fragment.FragmentCards;
import com.uncommontrade.e2b.profiles.fragment.FragmentOrders;
import com.uncommontrade.e2b.profiles.fragment.FragmentSupport;
import com.uncommontrade.e2b.widgets.RoundedImageView;

public class ProfilesFragment extends Fragment {

    private TabLayout tabProfile;
    private ViewPager viewProfile;
    private RoundedImageView imgAvatar;
    private TextView tvName, tvPoints;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View vProfiles = inflater.inflate(R.layout.layout_profile, container, false);
        init(vProfiles);
        return vProfiles;
    }

    public void init(View view) {

        imgAvatar = (RoundedImageView) view.findViewById(R.id.imgAvatar);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvPoints = (TextView) view.findViewById(R.id.tvPoints);
        tabProfile = (TabLayout) view.findViewById(R.id.tabProfile);
        viewProfile = (ViewPager) view.findViewById(R.id.viewProfile);
        viewProfile.setAdapter(new CustomAdapterProfile(getFragmentManager()));
        tabProfile.setupWithViewPager(viewProfile);
        tabProfile.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewProfile.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewProfile.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewProfile.setCurrentItem(tab.getPosition());
            }
        });
    }

    private class CustomAdapterProfile extends FragmentPagerAdapter {
        private String fragment[] = {"ORDERS", "CARDS", "SUPPORT", "ACCOUNT"};

        public CustomAdapterProfile(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentOrders();
                case 1:
                    return new FragmentCards();
                case 2:
                    return new FragmentSupport();
                case 3:
                    return new FragmentAccount();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return fragment.length;
        }

        public CharSequence getPageTitle(int position) {
            return fragment[position];
        }
    }
}
