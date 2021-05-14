package com.jiufang.interviewsystem.base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Fragmenet 基类
 **/
public abstract class BaseFragment extends Fragment {

    public Resources resource;
    protected Fragment fragment;
    public Bundle bundle;
    public String pkgName;
    public LayoutInflater mInflater;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pkgName = getActivity().getPackageName();
        fragment = this;
        bundle = new Bundle();
        mInflater = LayoutInflater.from(getActivity());
        resource = this.getResources();

    }
    public View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(provideContentViewId(), container, false);
            ButterKnife.bind(this, rootView);
            initView(rootView);
            initData();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
//        initD();
        return rootView;
    }

//    protected abstract void initD();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    /**
     * 初始化一些view
     *
     * @param rootView
     */
    protected abstract void initView(View rootView);

    /**
     * 初始化数据
     */
    protected abstract void initData();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    protected Context mContext;
    public boolean isEventBusRegisted(Object subscribe) {
        return EventBus.getDefault().isRegistered(subscribe);
    }

    public void registerEventBus(Object subscribe) {
        if (!isEventBusRegisted(subscribe)) {
            EventBus.getDefault().register(subscribe);
        }
    }

    public void unregisterEventBus(Object subscribe) {
        if (isEventBusRegisted(subscribe)) {
            EventBus.getDefault().unregister(subscribe);
        }}
}
