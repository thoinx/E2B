package com.uncommontrade.e2b;


import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;


import com.uncommontrade.e2b.carts.CartsFragment;
import com.uncommontrade.e2b.categories.fragment.CategoriesFragment;
import com.uncommontrade.e2b.collections.CollectionsFragment;
import com.uncommontrade.e2b.customview.CustomViewPager;
import com.uncommontrade.e2b.profiles.ProfilesFragment;
import com.uncommontrade.e2b.utilities.Const;
import com.uncommontrade.e2b.utilities.Keys;
import com.uncommontrade.e2b.utilities.SharedPreferenceManager;

public class HomeActivity extends FragmentActivity {

    private CustomViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private View[] btnPagerArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        initView();
    }

    private void initView() {
        btnPagerArray = new View[]{
                findViewById(R.id.home_btn_collections),
                findViewById(R.id.home_btn_productions),
                findViewById(R.id.home_btn_carts),
                findViewById(R.id.home_btn_profiles)
        };

        mPager = (CustomViewPager) findViewById(R.id.home_vp_main);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setOffscreenPageLimit(4);
        mPager.setAdapter(mPagerAdapter);

        //Set default as HomeFragment when start Activity
        mPager.setCurrentItem(0);
        setButtonSelector(0);

        //setOnClickListener
        for (int i = 0; i < btnPagerArray.length; i++) {
            final int index = i;
            btnPagerArray[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPager.setCurrentItem(index);
                }
            });
        }

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setButtonSelector(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void setButtonSelector(int i) {
        for (View pager : btnPagerArray) {
            pager.setSelected(false);
        }
        btnPagerArray[i].setSelected(true);
    }

    public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new CollectionsFragment();
                case 1:
                    return new CategoriesFragment();
                case 2:
                    return new CartsFragment();
                case 3:
                    return new ProfilesFragment();
                default:
                    return new CollectionsFragment();
            }
        }

        @Override
        public int getCount() {
            return btnPagerArray.length;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CODE_PRODUCTS_LIST) {
            if(resultCode == FragmentActivity.RESULT_OK){
                int tabPosition  = data.getIntExtra(Keys.KEY_OPEN_TAB, 0);
                mPager.setCurrentItem(tabPosition);
            }
        }
    }
}
