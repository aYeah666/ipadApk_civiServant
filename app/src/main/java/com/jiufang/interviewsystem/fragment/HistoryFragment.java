package com.jiufang.interviewsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.activity.UpdateStuinfoActivity;
import com.jiufang.interviewsystem.base.BaseFragment;
import com.jiufang.interviewsystem.base.HistoryListAdapter;
import com.jiufang.interviewsystem.bean.StudentBean;
import com.jiufang.interviewsystem.database.DBOperStu;
import com.jiufang.interviewsystem.dialog.ConfirmPwdDialog;
import com.jiufang.interviewsystem.utils.PublicStatic;
import com.jiufang.interviewsystem.utils.SpNames;
import com.jiufang.interviewsystem.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by aixy on 2019/10/31.
 * Desc:历史记录碎片
 */

public class HistoryFragment extends BaseFragment {
    @BindView(R.id.lv_history)
    ListView lvHistory;


    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_history;
    }

    @Override
    protected void initView(View rootView) {

    }

//    @Override
//    protected void initD() {
//
//    }
    ConfirmPwdDialog confirmPwdDialog;
    @Override
    protected void initData() {
        confirmPwdDialog = new ConfirmPwdDialog(getActivity(),
                getResources().getIdentifier("MyDialog", "style", pkgName));
        confirmPwdDialog.setCanceledOnTouchOutside(false);
        datas = DBOperStu.getInstance().queryDatas();
            if (datas.size() > 0) {
            adapter = new HistoryListAdapter(getActivity(), datas, handler);
            lvHistory.setAdapter(adapter);
            lvHistory.setSelection(0);// 显示数据的时候，默认从第一条开始
        }
    }

    private List<StudentBean> datas;
    private HistoryListAdapter adapter;

    /*初始化数据*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1:
                    StudentBean model = (StudentBean) msg.obj;
                    bundle.putSerializable("studentinfo", model);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UpdateStuinfoActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                                /*先弹窗窗口，进行确认密码是否对应
                    confirmPwdDialog.show();
                    confirmPwdDialog.getTvDialogPwdOk().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String pwd = confirmPwdDialog.getEtDialogPwd().getText().toString();
                            if (!PublicStatic.isEmpty(pwd)) {
                                if (pwd.equals(SpNames.MyselfPwd)) {

                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), UpdateStuinfoActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    confirmPwdDialog.dismiss();
                                } else {
                                    ToastUtil.showToast("密码不正确，请重新输入！");
                                }
                            } else {
                                ToastUtil.showToast("密码不能为空！");
                            }
                        }
                    });

                    confirmPwdDialog.getTvDialogPwdCancel().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            confirmPwdDialog.dismiss();
                        }
                    });*/
                    break;
            }
        }
    };

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
}
