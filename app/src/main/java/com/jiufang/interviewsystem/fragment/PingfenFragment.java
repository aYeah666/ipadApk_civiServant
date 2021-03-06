package com.jiufang.interviewsystem.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.activity.UpdateStuinfoActivity;
import com.jiufang.interviewsystem.base.BaseFragment;
import com.jiufang.interviewsystem.base.PingfenListAdapter;
import com.jiufang.interviewsystem.bean.FactorBean;
import com.jiufang.interviewsystem.bean.RecordInfoBean;
import com.jiufang.interviewsystem.bean.StudentBean;
import com.jiufang.interviewsystem.database.DBOper1;
import com.jiufang.interviewsystem.database.DBOperRecord;
import com.jiufang.interviewsystem.database.DBOperStu;
import com.jiufang.interviewsystem.dialog.ConfirmPwdDialog;
import com.jiufang.interviewsystem.dialog.FinishScoreDialog;
import com.jiufang.interviewsystem.dialog.ModifytextDialog;
import com.jiufang.interviewsystem.dialog.PingfenConfirmDialog;
import com.jiufang.interviewsystem.dialog.TuwenConfirmDialog;
import com.jiufang.interviewsystem.dialog.ZeroConfirmDialog;
import com.jiufang.interviewsystem.utils.AESUtils;
import com.jiufang.interviewsystem.utils.MessageEvent;
import com.jiufang.interviewsystem.utils.PublicStatic;
import com.jiufang.interviewsystem.utils.QRCodeProduceUtils;
import com.jiufang.interviewsystem.utils.SpNames;
import com.jiufang.interviewsystem.utils.SpUtils;
import com.jiufang.interviewsystem.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jiufang.interviewsystem.utils.AESUtils.completionKey;
import static com.jiufang.interviewsystem.utils.CheckExaminerName.checkRule;
import static com.jiufang.interviewsystem.utils.CheckExaminerName.isStuXuhaoNumeric;

/**
 * Created by aixy on 2019/10/31.
 * Desc:????????????
 */

public class PingfenFragment extends BaseFragment {
    @BindView(R.id.tv_print_title)
    TextView tvPrintTitle;
    @BindView(R.id.tv_pingwei_name)
    TextView tvPingweiName;
    @BindView(R.id.tv_student_xuhao)
    TextView tvStudentXuhao;
    @BindView(R.id.tv_total_score)
    TextView tvTotalScore;
    /*  @BindView(R.id.ll_finish_score)
      LinearLayout llFinishScore;*/
    @BindView(R.id.lv_pingfen)
    ListView lvPingfen;

    @Override
    protected int provideContentViewId() {
        return R.layout.fragment_pingfen;
    }

    View bottomView;
    LinearLayout ll_finish;

    @Override
    protected void initView(View rootView) {
/*????????????????????????*/
        bottomView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_finishscore, null);
        ll_finish = ((LinearLayout) bottomView.findViewById(R.id.ll_finish_score));
        lvPingfen.addFooterView(bottomView);
        ll_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!PublicStatic.isEmpty(pingweiName)) {
                    if (!PublicStatic.isEmpty(kaoshengXuhao) && kaoshengXuhao.length() < 4) {
                        pingfenConfirmDialog.show();

                        pingfenConfirmDialog.getTv_cancel().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pingfenConfirmDialog.dismiss();
                            }
                        });
                        pingfenConfirmDialog.getTv_ok().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                pingfenConfirmDialog.dismiss();

                        /*?????????????????????????????????????????????0 ?????????*/
                                for (int i = 0; i < mingxiList.size(); i++) {
                                    if (mingxiList.get(i).getFinishScore() == 0) {
                                        zeroText = zeroText + mingxiList.get(i).getFactor_name() + ":?????????0" + "\n";
                                    }
                                }
                                Log.e("zeroText???", zeroText);
                                //?????????0????????????????????????
                                if (zeroText.equals("")) {
                                    showFinish();
                                } else {
                                    zeroDialog.show();
                                    zeroDialog.getTv_title().setText(zeroText);
                                    zeroDialog.getTv_ok().setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            zeroDialog.dismiss();
                                            zeroText = "";
                             /*0???????????????????????????????????????*/
                                            showFinish();
                                        }
                                    });
                                    zeroDialog.getTv_fanhui().setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            zeroDialog.dismiss();
                                            zeroText = "";
                                        }
                                    });
                                }
                            }
                        });
                        /*------------------*/
                    } else {
                        ToastUtil.showToast("??????????????????1-3??????");
                    }
                } else {
                    ToastUtil.showToast("????????????????????????");
                }
            }
        });
    }

    ModifytextDialog modifyDialog; //  ??????????????????
    FinishScoreDialog finishDialog;//??????????????????
    TuwenConfirmDialog tuwenDialog;
    ConfirmPwdDialog confirmPwdDialog;
    PingfenConfirmDialog pingfenConfirmDialog;
    ZeroConfirmDialog zeroDialog;//0?????????
    private List<FactorBean> datas;
    private PingfenListAdapter adapter;


    @Override
    public void initData() {
        title = SpUtils.getParam(getActivity(), SpNames.TITLE, "").toString();
        config_id = SpUtils.getParam(getActivity(), SpNames.ConfigId, "").toString();
        year = SpUtils.getParam(getActivity(), SpNames.YEAR, "").toString();
        tvPrintTitle.setText(year + title);
        modifyDialog = new ModifytextDialog(getActivity(),
                getResources().getIdentifier("MyDialog", "style", pkgName));
        modifyDialog.setCanceledOnTouchOutside(false);
//??????????????????????????????????????????????????????????????????????????????????????????
        // setCanceledOnTouchOutside(false);
//????????????????????????????????????????????????????????????????????????????????????
        //.setCancelable(false);
        finishDialog = new FinishScoreDialog(getActivity(),
                getResources().getIdentifier("MyDialog", "style", pkgName));
        finishDialog.setCanceledOnTouchOutside(false);
        tuwenDialog = new TuwenConfirmDialog(getActivity(),
                getResources().getIdentifier("MyDialog", "style", pkgName));
        tuwenDialog.setCanceledOnTouchOutside(false);
        zeroDialog = new ZeroConfirmDialog(getActivity(),
                getResources().getIdentifier("MyDialog", "style", pkgName));
        zeroDialog.setCanceledOnTouchOutside(false);
/*??????????????????*/
        confirmPwdDialog = new ConfirmPwdDialog(getActivity(),
                getResources().getIdentifier("MyDialog", "style", pkgName));
        confirmPwdDialog.setCanceledOnTouchOutside(false);

        /*????????????????????????*/
        pingfenConfirmDialog = new PingfenConfirmDialog(getActivity(),
                getResources().getIdentifier("MyDialog", "style", pkgName));
        pingfenConfirmDialog.setCanceledOnTouchOutside(false);
        datas = DBOper1.getInstance().queryDatas();
        Log.e("????????????--", datas.size() + "");
        adapter = new PingfenListAdapter(getActivity(), datas);
        lvPingfen.setAdapter(adapter);

        pingweiName = SpUtils.getParam(getActivity(), SpNames.PingweiName, "").toString();
        tvPingweiName.setText(pingweiName);
        if (!PublicStatic.isEmpty(DBOperStu.getInstance().queryLastData())) {
            kaoshengXuhao = String.valueOf(Integer.valueOf(DBOperStu.getInstance().queryLastData()) + 1);
            Log.e("????????????????????????1??????-", kaoshengXuhao + "");
        }
        tvStudentXuhao.setText(kaoshengXuhao);

    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    int totalScores;//?????????
    String scoreinfo;//????????????
    List<FactorBean> mingxiList;

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        totalScores = event.getTotal();//??????
        tvTotalScore.setText(event.getTotal() + "???");
        /*????????????*/
        scoreinfo = event.getScoreinfo().toString();
        Log.e("????????????--", scoreinfo);//????????????:7;????????????:3;??????(??????)??????:12
        /*????????????list*/
        mingxiList = event.getDatas();

    }


    String zeroText = "";

    @OnClick({R.id.tv_pingwei_name, R.id.tv_student_xuhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pingwei_name://??????????????????
                modifyTextDialog("name", tvPingweiName);
                break;
            case R.id.tv_student_xuhao://??????????????????,???0514????????????????????????
             modifyTextDialog("xuhao", tvStudentXuhao);
                break;
//            case R.id.ll_finish_score://???????????????????????????
//
//
//                break;
        }
    }

    String deviceTime;

    public void showFinish() {
        deviceTime = PublicStatic.getCurrentTime();
        finishDialog.show();
        finishDialog.getFinishName().setText("?????? : " + pingweiName);
        finishDialog.getFinishXuhao().setText("???????????? : " + kaoshengXuhao);
        finishDialog.getFinishScore().setText("?????? : " + totalScores);
        /*------------???????????????=-----------------*/
        mHandler.post(mUpdateImageRunnable);
   /*------------------------------------*/

        //???????????????????????????
        addStudentInfo();
                    /*???????????????????????????????????????*/
        SpUtils.setParam(getActivity(), SpNames.PingweiName, pingweiName);//????????????
    /*???????????????????????????*/
        finishDialog.getBtnFinishNext().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTuwenDialog("next");
            }
        });
    /*??????????????????*/
        finishDialog.getBtnFinishXiugai().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTuwenDialog("xiugai");
            }
        });
    }

    String config_id;
    String title, year;
    //???????????????
    private Runnable mUpdateImageRunnable = new Runnable() {
        @Override
        public void run() {
            String key = completionKey("CI1B3u3VNE0xBZ7F" + config_id);
            String pingweiCode = config_id + "^" + "score" + "^" + deviceTime + "^" + kaoshengXuhao + "^" + pingweiName + "^" + totalScores + "^" + scoreinfo;
            Log.e("???????????????????????????", pingweiCode);
            String encrypt = AESUtils.encrypt(pingweiCode, key).replace("\n", "");
            finishDialog.getmBarcodeImageView().setImageBitmap(QRCodeProduceUtils.create(encrypt, 360, 360, PublicStatic.getBitmap(R.mipmap.logo160x160, getActivity())));

        }
    };
    StudentBean stu;//??????????????????

    /*erc*/
    public void showTuwenDialog(final String type) {
        tuwenDialog.show();
        if (type.equals("xiugai")) {
            tuwenDialog.getImg_icon().setBackgroundResource(R.mipmap.ic_modify_info);
            tuwenDialog.getTv_title().setText("??????????????????");
        } else if (type.equals("next")) {
            tuwenDialog.getImg_icon().setBackgroundResource(R.mipmap.ic_modify_person);
            tuwenDialog.getTv_title().setText("???????????????????????????");
        }

        tuwenDialog.getTv_ok().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuwenDialog.dismiss();
                finishDialog.dismiss();

                if (type.equals("next")) {//?????????,????????????????????????????????????
                    initData();
                         /*????????????????????????????????????*/
//                    int kaohaoNum = Integer.valueOf(kaoshengXuhao);
//                    kaohaoNum += 1;
//                    kaoshengXuhao = kaohaoNum + "";
//                    tvStudentXuhao.setText(kaoshengXuhao);

                } else if (type.equals("xiugai")) {
                    //?????????????------------------------

                    stu = DBOperStu.getInstance().queryStubean(kaoshengXuhao);
                    Log.e("123123", stu.toString());
                    bundle.putSerializable("studentinfo", stu);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UpdateStuinfoActivity.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, 10002);
                      /*????????????????????????????????????????????????
                    confirmPwdDialog.show();
                    confirmPwdDialog.getTvDialogPwdOk().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String pwd = confirmPwdDialog.getEtDialogPwd().getText().toString();
                            if (!PublicStatic.isEmpty(pwd)) {
                                if (pwd.equals(SpNames.MyselfPwd)) {
                                    // ?????????????????????????????????????????????????????????????????????
                                    stu = DBOperStu.getInstance().queryStubean(kaoshengXuhao);
                                    Log.e("123123", stu.toString());
                                    bundle.putSerializable("studentinfo", stu);
                                    Intent intent = new Intent();
                                    intent.setClass(getActivity(), UpdateStuinfoActivity.class);
                                    intent.putExtras(bundle);
                                    startActivityForResult(intent, 10002);
                                    confirmPwdDialog.dismiss();
                                    confirmPwdDialog.getEtDialogPwd().setText("");
                                } else {
                                    ToastUtil.showToast("????????????????????????????????????");
                                }
                            } else {
                                ToastUtil.showToast("?????????????????????");
                            }
                        }
                    });

                    confirmPwdDialog.getTvDialogPwdCancel().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            confirmPwdDialog.dismiss();
                            confirmPwdDialog.getEtDialogPwd().setText("");
                        }
                    });*/
                }

            }
        });

        tuwenDialog.getTv_cancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuwenDialog.dismiss();
                finishDialog.dismiss();
            }
        });

    }

    String modifyValue;
    String pingweiName;//
    String kaoshengXuhao="1";//

    public void modifyTextDialog(final String type, final TextView tv_modify) {
        modifyDialog.show();

        if (type.equals("name")) {
            modifyDialog.getEtDialogModifyText().setHint("?????????????????????");
        } else if (type.equals("xuhao")) {
            modifyDialog.getEtDialogModifyText().setHint("?????????????????????");
        }
        modifyDialog.getEtDialogModifyText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                modifyValue = editable.toString();
                Log.e("afterTextChanged_ping", modifyValue);
            }
        });
        modifyDialog.getTvDialogModOk().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (type.equals("name")) {
                    if (!PublicStatic.isEmpty(modifyValue)) {
                        if (modifyValue.length() > 1 && modifyValue.length() < 7) {
                            if (checkRule(modifyValue)) {
                                modifyDialog.dismiss();
                                pingweiName = modifyValue;
                                tv_modify.setText(modifyValue);
                            } else {
                                ToastUtil.showToast("????????????????????????");
                            }
                        } else {
                            ToastUtil.showToast("??????????????????2-6??????");
                        }
                    } else {
                        ToastUtil.showToast("???????????????????????????");
                    }
                } else if (type.equals("xuhao")) {
                    if (!PublicStatic.isEmpty(modifyValue) && modifyValue.length() < 4) {
                        if (isStuXuhaoNumeric(modifyValue)) {
                            modifyDialog.dismiss();
                            kaoshengXuhao = modifyValue;
                            tv_modify.setText(modifyValue);
                        } else {
                            ToastUtil.showToast("????????????????????????");
                        }
                    } else {
                        ToastUtil.showToast("??????????????????1-3??????");
                    }

                }
                /*?????????????????????????????????*/
                modifyDialog.getEtDialogModifyText().setText("");
            }
        });
        modifyDialog.getTvDialogModCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modifyDialog.dismiss();
                modifyDialog.getEtDialogModifyText().setText("");
            }
        });

    }

    StudentBean bean;

    public void addStudentInfo() {
        bean = new StudentBean();
        bean.setConfig_id(config_id);
        bean.setQc_name("score");
        bean.setDevice_time(deviceTime);
        bean.setStudent_seq(kaoshengXuhao);
        bean.setExaminer_name(pingweiName);
        bean.setTotal_score(totalScores);
        bean.setScores(scoreinfo);
        /**/
        if (DBOperStu.getInstance().insertData(bean) != -1) {
            ToastUtil.showToast("????????????");

        } else {
            ToastUtil.showToast("?????????????????????????????????????????????????????????????????????????????????");
        }
    /*?????????????????????????????????*/
        addRecordInfo();
    }

    /*????????????*/
    public void addRecordInfo() {
        RecordInfoBean bean = new RecordInfoBean();
        bean.setConfig_id(config_id);
        bean.setStudent_seq(kaoshengXuhao);
        bean.setExaminer_name(pingweiName);
        bean.setDevice_time(deviceTime);
        bean.setTotal_score(totalScores);
        bean.setScores(scoreinfo);
        bean.setType("??????");
        /**/
        if (DBOperRecord.getInstance().insertData(bean) != -1) {
            Log.d("addRecordInfo", "??????-????????????");
        } else {
            Log.d("addRecordInfo", "??????-????????????");
        }

    }


    @Override
    public void onStart() {
        super.onStart();
        registerEventBus(PingfenFragment.this);
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterEventBus(PingfenFragment.this);
    }

    @Override
    public void onResume() {
        super.onResume();
//        initData();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 10001 && data != null) {
            String type = data.getStringExtra("type");
            if (type.equals("1")) {
                Log.e("123123", "??????");
                initData();
            }
        }
    }
}
