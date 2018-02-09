package cn.bry.yueke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;


import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.bry.yueke.base.BaseActivity;
import cn.bry.yueke.base.GuidBannerAdapter;
import cn.bry.yueke.util.Utils;


 public class GuideActivity extends BaseActivity implements OnItemClickListener, ViewPager.OnPageChangeListener, View.OnClickListener {
    List<Integer> mInts = new ArrayList<>();
    private View mGuideButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
//            hideVirtualKey();
        initView();

    }


    private void initView() {
        ConvenientBanner mMGuideViewPager = findViewById(R.id.guide_viewPager);
        mGuideButton = findViewById(R.id.guide_button);
        mGuideButton.setOnClickListener(this);
        CBViewHolderCreator<GuidBannerAdapter> mHolderCreator = new CBViewHolderCreator<GuidBannerAdapter>() {
            @Override
            public GuidBannerAdapter createHolder() {
                return new GuidBannerAdapter();
            }
        };
        mInts.add(R.mipmap.guide1);
        mInts.add(R.mipmap.guide2);
        mInts.add(R.mipmap.guide3);
        mInts.add(R.mipmap.guide4);
        mMGuideViewPager.setCanLoop(false);
        mMGuideViewPager.setPages(mHolderCreator, mInts);
        mMGuideViewPager.setOnItemClickListener(this);
        mMGuideViewPager.setOnPageChangeListener(this);
    }





    @Override
    public void finish() {
        super.finish();
     }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3) {
            mGuideButton.setVisibility(View.VISIBLE);
        } else {
            mGuideButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

     @Override
     public void onClick(View view) {
         Utils.getSpUtils().put("isFirst", false);
        startActivity(new Intent(this,WelcomeActivity.class));
         finish();
     }
 }
