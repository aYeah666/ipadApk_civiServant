package com.jiufang.interviewsystem.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jiufang.interviewsystem.dialog.ModifytextDialog;
import com.jiufang.interviewsystem.dialog.TuwenConfirmDialog;
import com.jiufang.interviewsystem.utils.AppManager;
import com.jiufang.interviewsystem.utils.PublicStatic;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;


/*
* ━━━━━━神兽出没━━━━━━
*      ┏┓　 ┏┓
* 　　┏┛┻━━━┛┻┓
* 　　┃　　　     ┃
* 　　┃　　　━    ┃
* 　　┃　┳┛　┗┳   ┃
* 　　┃　　　     ┃
* 　　┃　　　┻    ┃
* 　　┃　　　　   ┃
* 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
* 　　  ┃　　　┃    神兽保佑,代码无bug
*       ┃　　　┃
*       ┃　　　┗━━━┓
*       ┃　　　　　 ┣┓
*       ┃　　　　　┏┛
*       ┗┓┓┏━┳┓┏┛
*        ┃┫┫　┃┫┫
*        ┗┻┛　┗┻┛
*
*/
public abstract class BaseActivity extends AppCompatActivity {
    public InputMethodManager imm;
    public Resources resource;
    public String pkgName;
    public Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        /*第三个参数--浅色系就true   深色就false*/
        PublicStatic.setStatusBar(this, false, true);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        bundle = new Bundle();
        resource = this.getResources();
        pkgName = this.getPackageName();
        imm = (InputMethodManager) this
                .getSystemService(Context.INPUT_METHOD_SERVICE);
/*初始化数据*/
        initData();
        AppManager.getAppManager().addActivity(this);
    }

    public abstract int getLayoutId();

    protected abstract void initData();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == 1) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制横屏
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制横屏
        }
    }


    @Override
    protected void onDestroy() {
        AppManager.getAppManager().finishActivity(this);

        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    // 点击EditText以外的任何区域隐藏键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v != null) {
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }

        return super.dispatchTouchEvent(ev);
    }

}