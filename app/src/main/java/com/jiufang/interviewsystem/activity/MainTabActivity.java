package com.jiufang.interviewsystem.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.base.BaseActivity;
import com.jiufang.interviewsystem.dialog.AboutusDialog;
import com.jiufang.interviewsystem.dialog.ModifytextDialog;
import com.jiufang.interviewsystem.fragment.HistoryFragment;
import com.jiufang.interviewsystem.fragment.PingfenFragment;
import com.jiufang.interviewsystem.fragment.SettingFragment;
import com.jiufang.interviewsystem.utils.NoPreloadViewPager;
import com.jiufang.interviewsystem.utils.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by axy on 2019/8/6.
 * Desc:主页面
 */
public class MainTabActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_aboutus)
    TextView tvAboutus;
    @BindView(R.id.iv_score)
    ImageView ivScore;
    @BindView(R.id.tv_score)
    TextView tvScore;
    @BindView(R.id.ll_score)
    LinearLayout llScore;
    @BindView(R.id.iv_record)
    ImageView ivRecord;
    @BindView(R.id.tv_record)
    TextView tvRecord;
    @BindView(R.id.ll_record)
    LinearLayout llRecord;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;
    @BindView(R.id.tv_setting)
    TextView tvSetting;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;
    private LinearLayout llCurrent;
    private TextView tvCurrent;
    private List<Fragment> fragments = new ArrayList<>();
    public MainTabActivity activity;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected void initData() {
        activity = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String packageName = getPackageName();
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                try {
                    //some device doesn't has activity to handle this intent
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        }

        initView();
        initMainData();

    }

    private void initMainData() {
        PingfenFragment pingfenFragment = new PingfenFragment();
        fragments.add(pingfenFragment);
        HistoryFragment historyFragment = new HistoryFragment();
        fragments.add(historyFragment);
        SettingFragment settingFragment = new SettingFragment();
        fragments.add(settingFragment);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.setAdapter(adapter);

        int toMain = getIntent().getIntExtra("to_main", 0);
        if (toMain == 0) {

        } else {
            Log.e("toMain", toMain + "---");
            viewPager.setCurrentItem(toMain);
            switch (toMain) {
                case 0://评分
                    tvCurrent = tvScore;
                    llCurrent = llScore;
                    break;
                case 1://历史记录
                    tvCurrent = tvRecord;
                    llCurrent = llRecord;
                    tvScore.setSelected(false);
                    llScore.setSelected(false);
                    break;
                case 2://设置
                    tvCurrent = tvSetting;
                    llCurrent = llSetting;
                    tvScore.setSelected(false);
                    llScore.setSelected(false);

                    break;

            }
            tvCurrent.setSelected(true);
            llCurrent.setSelected(true);
        }
    }

    int index;
    AboutusDialog dialog;

    //初始化默认页面
    private void initView() {
        dialog = new AboutusDialog(this,
                getResources().getIdentifier("MyDialog", "style", pkgName));
        llScore.setOnClickListener(this);
        llRecord.setOnClickListener(this);
        llSetting.setOnClickListener(this);

        tvCurrent = tvScore;
        llCurrent = llScore;
        tvCurrent.setSelected(true);
        llCurrent.setSelected(true);
        viewPager.setOnPageChangeListener(new NoPreloadViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changeTab(position);
                index = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setNoScroll(true);
        viewPager.setOffscreenPageLimit(0); //设置向左和向右都缓存limit个页面

    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }

    /**
     * 点击事件
     *
     * @param id
     */
    private void changeTab(int id) {
        llCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        switch (id) {
            case R.id.ll_score://环境数据
                viewPager.setCurrentItem(0, false);
            case 0:
                llCurrent = llScore;
                tvCurrent = tvScore;
                llCurrent.setSelected(true);
                tvCurrent.setSelected(true);
                break;
            case R.id.ll_record://远程控制
                viewPager.setCurrentItem(1, false);
            case 1:
                llCurrent = llRecord;
                tvCurrent = tvRecord;
                llCurrent.setSelected(true);
                tvCurrent.setSelected(true);
                break;
            case R.id.ll_setting://设置
                viewPager.setCurrentItem(2, false);
            case 2:
                llCurrent = llSetting;
                tvCurrent = tvSetting;
                llCurrent.setSelected(true);
                tvCurrent.setSelected(true);
                break;

            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.rl_back, R.id.tv_aboutus})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_aboutus:
                dialog.show();
                dialog.setCancelable(false);
                dialog.showTitle("考试报名系统的关于内容");
                dialog.getBtnClose().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    /*禁用返回键
        @Override
        public boolean dispatchKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                //do something.
                return true;
            } else {
                return super.dispatchKeyEvent(event);
            }
        }*/
    @Override

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.i("TAG", "I'm Android 4.0");
    }

    //APP全程禁用返回键！
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //do something.
            return true;
        } else {
            return super.dispatchKeyEvent(event);
        }
    }
}
