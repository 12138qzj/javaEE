package com.example.yexin.menu6.Index.Collect;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yexin.menu6.Index.MainActivity;
import com.example.yexin.menu6.R;

public class FocusActivity extends AppCompatActivity {
    private TabLayout tabLayout = null;
    private ViewPager viewPager;
    private ImageView focus_cancel;
    private android.support.v4.app.Fragment[] mFragmentArrays = new android.support.v4.app.Fragment[2];
    private String[] mTabTitles = new String[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.tab_viewpager);
        focus_cancel=(ImageView)findViewById(R.id.focus_cancel);


        focus_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(FocusActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        initView();
    }

    private void initView() {
        mTabTitles[0] = "球友关注";
        mTabTitles[1] = "球馆关注";
        //setIndicator(tabLayout,10,10);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //设置tablayout距离上下左右的距离
        //tab_title.setPadding(20,20,20,20);
        mFragmentArrays[0] = Fragment_Focus_BallPerson.newInstance();
        mFragmentArrays[1] = Fragment_Focus_BallClub.newInstance();
        PagerAdapter pagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),mFragmentArrays,mTabTitles);
        viewPager.setAdapter(pagerAdapter);
        //将ViewPager和TabLayout绑定
        tabLayout.setupWithViewPager(viewPager);
    }
    final class MyViewPagerAdapter extends FragmentPagerAdapter {
        private android.support.v4.app.Fragment[] mFragmentArrays;
        private String[] mTabTitles;
        public MyViewPagerAdapter(FragmentManager fm, android.support.v4.app.Fragment[] mFragmentArrays, String[] mTabTitles) {
            super(fm);
            this.mFragmentArrays=mFragmentArrays;
            this.mTabTitles=mTabTitles;
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentArrays[position];
        }

        @Override
        public int getCount() {
            return mFragmentArrays.length;
        }

        @Override
        public void destroyItem (ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitles[position];
        }
    }

}
