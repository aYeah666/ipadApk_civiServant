package com.jiufang.interviewsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.activity.ScanCodeActivity;
import com.jiufang.interviewsystem.base.BaseFragment;
import com.jiufang.interviewsystem.bean.FactorBean;
import com.jiufang.interviewsystem.database.DBHelper1;
import com.jiufang.interviewsystem.database.DBHelperRecord;
import com.jiufang.interviewsystem.database.DBHelperStu;
import com.jiufang.interviewsystem.database.DBOper1;
import com.jiufang.interviewsystem.dialog.TuwenConfirmDialog;
import com.jiufang.interviewsystem.utils.PublicStatic;
import com.jiufang.interviewsystem.utils.SpNames;
import com.jiufang.interviewsystem.utils.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.jiufang.interviewsystem.utils.PublicStatic.setListViewHeightBasedOnChildren;

/**
 * Created by aixy on 2019/10/31.
 * Desc:设置
 */

public class SettingFragment extends BaseFragment {
    @BindView(R.id.tv_set_kaoqu)
    TextView tvSetKaoqu;
    @BindView(R.id.tv_set_biaoti)
    TextView tvSetBiaoti;
    @BindView(R.id.tv_set_daoru_time)
    TextView tvSetDaoruTime;
    @BindView(R.id.tv_set_outdata_time)
    TextView tvSetOutdataTime;
    @BindView(R.id.tv_set_mianshi_no)
    TextView tvSetMianshiNo;
    @BindView(R.id.tv_set_clear)
    TextView tvSetClear;
    @BindView(R.id.tv_set_saoyisao)
    TextView tvSetSaoyisao;

    @BindView(R.id.tv_set_version_name)
    TextView tvSetVersionName;
    @BindView(R.id.info)
    LinearLayout info;
    @BindView(R.id.lv_factor)
    ListView lvFactor;
    TuwenConfirmDialog dialog;
    @BindView(R.id.tv_set_year)
    TextView tvSetYear;
    Unbinder unbinder;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_setting;
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected void initData() {
        dialog = new TuwenConfirmDialog(getActivity(),
                getResources().getIdentifier("MyDialog", "style", pkgName));
        showInfo();
    }


    /*初始化数据*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private List<FactorBean> datas;

    /**/
    public void showInfo() {

        String versionName = PublicStatic.getVersionName(getActivity());
        tvSetVersionName.setText("版本号 :V" + versionName);
        String year= SpUtils.getParam(getActivity(), SpNames.YEAR, "").toString();
        String kaoqu = SpUtils.getParam(getActivity(), SpNames.AREA, "").toString();
        String expiration_date = SpUtils.getParam(getActivity(), SpNames.Expiration_date, "").toString();
        String print_title = SpUtils.getParam(getActivity(), SpNames.TITLE, "").toString();
        String daoru_date = SpUtils.getParam(getActivity(), SpNames.Daoru_date, "").toString();
        String config_id = SpUtils.getParam(getActivity(), SpNames.ConfigId, "").toString();

        tvSetYear.setText("年度:" + year);
        tvSetKaoqu.setText("考区:" + kaoqu);
        tvSetBiaoti.setText("标题:" + print_title);
        tvSetMianshiNo.setText(config_id);
        tvSetOutdataTime.setText(expiration_date);
        tvSetDaoruTime.setText(daoru_date);
        datas = DBOper1.getInstance().queryDatas();
        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < datas.size(); i++) {
            Map<String, Object> listem = new HashMap<String, Object>();
            listem.put("desc", (i + 1) + ". “" + datas.get(i).getFactor_name() + "” 最高分" + datas.get(i).getFactor_max_score());
            listems.add(listem);
        }

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), listems,
                R.layout.item_setting_factor, new String[]{"desc"},
                new int[]{R.id.tv_set_factor});
        lvFactor.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lvFactor);

    }


    /*实际上2个按钮功能一样的*/
    @OnClick({R.id.tv_set_clear, R.id.tv_set_saoyisao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_set_clear://将二维码存储的数据清空，然后跳转到扫一扫按钮页面
                dialog.show();
                dialog.getImg_icon().setBackgroundResource(R.mipmap.ic_dialog_delete);
                dialog.getTv_title().setText("确定清空设置信息？");
                dialog.getTv_cancel().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.getTv_ok().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        clearAllData();
                        startActivity(new Intent(getActivity(), ScanCodeActivity.class));
                        getActivity().finish();
                    }
                });

                break;
            case R.id.tv_set_saoyisao:
                //扫一扫 打开相机
                startActivity(new Intent(getActivity(), ScanCodeActivity.class));
                getActivity().finish();
                break;
        }

    }


    /*----------------清空数据-----------*/

    public void clearAllData() {
        /*清空数据库之前做下备份*/
        DBHelper1.copyToSdCard(getActivity());
        DBHelperStu.copyToSdCard(getActivity());
        DBHelperRecord.copyToSdCard(getActivity());
        /*清除二维码基础信息*/
        SpUtils.clear(getActivity());
        /*清除二维码的要素信息和评委生成信息*/
        DBHelper1.deleteDatabase_fac(getActivity());
        DBHelperStu.deleteDatabase_stu(getActivity());
        DBHelperRecord.deleteDatabase_record(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
