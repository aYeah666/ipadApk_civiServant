package com.jiufang.interviewsystem.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.base.BaseActivity;
import com.jiufang.interviewsystem.base.UpdateScoresListAdapter;
import com.jiufang.interviewsystem.bean.FactorBean;
import com.jiufang.interviewsystem.bean.RecordInfoBean;
import com.jiufang.interviewsystem.bean.StudentBean;
import com.jiufang.interviewsystem.bean.UpdateStuBean;
import com.jiufang.interviewsystem.database.DBOper1;
import com.jiufang.interviewsystem.database.DBOperRecord;
import com.jiufang.interviewsystem.database.DBOperStu;
import com.jiufang.interviewsystem.dialog.ModifytextDialog;
import com.jiufang.interviewsystem.dialog.TuwenConfirmDialog;
import com.jiufang.interviewsystem.dialog.UpdateScoreDialog;
import com.jiufang.interviewsystem.dialog.ZeroConfirmDialog;
import com.jiufang.interviewsystem.utils.AESUtils;
import com.jiufang.interviewsystem.utils.CheckExaminerName;
import com.jiufang.interviewsystem.utils.MessageEvent2;
import com.jiufang.interviewsystem.utils.PublicStatic;
import com.jiufang.interviewsystem.utils.QRCodeProduceUtils;
import com.jiufang.interviewsystem.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jiufang.interviewsystem.utils.AESUtils.completionKey;
import static com.jiufang.interviewsystem.utils.CheckExaminerName.checkRule;

/**
 * Created by aixy on 2020/8/13.
 * Desc:修改页面
 */

public class UpdateStuinfoActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_aboutus)
    TextView tvAboutus;
    @BindView(R.id.tv_print_title)
    TextView tvPrintTitle;
    @BindView(R.id.tv_pingwei_name)
    TextView tvPingweiName;
    @BindView(R.id.tv_student_xuhao)
    TextView tvStudentXuhao;
    @BindView(R.id.tv_total_score)
    TextView tvTotalScore;
    @BindView(R.id.lv_pingfen)
    ListView lvPingfen;
    @BindView(R.id.ll_update_score)
    LinearLayout llUpdateScore;

    @Override
    public int getLayoutId() {
        return R.layout.activity_update_stuinfo;
    }

    private String student_seq;//考生序号
    private String config_id;//面试编号
    private String examiner_name;//考官姓名
    private List<FactorBean> factorDatas;
    UpdateScoresListAdapter adapter;
    UpdateStuinfoActivity activity;
    ModifytextDialog modifyDialog; //  编辑评委姓名！考生序号不可修改
    UpdateScoreDialog updateScoreDialog;//修改评分弹窗
    TuwenConfirmDialog tuwenDialog;//确认提示
    ZeroConfirmDialog zeroDialog;//0分提示弹窗
    List<UpdateStuBean> updateStuBeans;//修改的
    String[] singleScore = new String[10];//单个要素分数
    UpdateStuBean bean;

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        activity = UpdateStuinfoActivity.this;
        tvAboutus.setVisibility(View.GONE);//隐藏关于按钮
        rlBack.setVisibility(View.VISIBLE);
           /*initdata*/
        modifyDialog = new ModifytextDialog(activity,
                getResources().getIdentifier("MyDialog", "style", pkgName));
        modifyDialog.setCanceledOnTouchOutside(false);
        updateScoreDialog = new UpdateScoreDialog(activity,
                getResources().getIdentifier("MyDialog", "style", pkgName));
        updateScoreDialog.setCanceledOnTouchOutside(false);
        tuwenDialog = new TuwenConfirmDialog(activity,
                getResources().getIdentifier("MyDialog", "style", pkgName));
        tuwenDialog.setCanceledOnTouchOutside(false);
        zeroDialog = new ZeroConfirmDialog(activity,
                getResources().getIdentifier("MyDialog", "style", pkgName));
        zeroDialog.setCanceledOnTouchOutside(false);
        /**/
        StudentBean model = (StudentBean) getIntent().getSerializableExtra("studentinfo");
        config_id = model.getConfig_id();
        student_seq = model.getStudent_seq();
        examiner_name = model.getExaminer_name();
        totalScores = model.getTotal_score();
        scoreinfo = model.getScores();
        /*显示*/
        tvPingweiName.setText(examiner_name);
        tvStudentXuhao.setText(student_seq);
        tvTotalScore.setText(totalScores + "分");
/*从数据库取出要素名以及最低分最高分*/
        factorDatas = DBOper1.getInstance().queryDatas();
        String[] result = scoreinfo.split(";");//用;分离  综合分析：33

        updateStuBeans = new ArrayList<>();//修改的
        for (int i = 0; i < result.length; i++) {
            String reqResult = result[i];
            Log.e("reqResult", reqResult);
            //获取开始截取的位置，之后截取逗号后面的所有内容
            singleScore[i] = reqResult.substring(reqResult.indexOf(":") + 1);
            bean = new UpdateStuBean();
            bean.setFactor_name(factorDatas.get(i).getFactor_name());
            bean.setFactor_min_score(factorDatas.get(i).getFactor_min_score());
            bean.setFactor_max_score(factorDatas.get(i).getFactor_max_score());
            bean.setNow_qc_num(factorDatas.get(i).getNow_qc_num());
            bean.setFinishScore(Integer.valueOf(singleScore[i]));
            updateStuBeans.add(bean);
        }

        adapter = new UpdateScoresListAdapter(activity, updateStuBeans);
        lvPingfen.setAdapter(adapter);
    }

    public void updateStuInfo() {
        StudentBean bean = new StudentBean();
        bean.setConfig_id(config_id);
        bean.setQc_name("score");
        bean.setDevice_time(deviceTime);
        bean.setStudent_seq(student_seq);
        bean.setExaminer_name(examiner_name);
        bean.setTotal_score(totalScores);
        bean.setScores(scoreinfo);
        /**/
        if (DBOperStu.getInstance().updateData(bean) != 0) {
            ToastUtil.showToast("修改成功");
        } else {
            ToastUtil.showToast("修改失败");
        }
    }

    String zeroText = "";
    String modifyValue;

    @OnClick({R.id.rl_back, R.id.ll_update_score, R.id.tv_pingwei_name, R.id.tv_student_xuhao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_pingwei_name://修改评委姓名
                modifyDialog.show();
                modifyDialog.getEtDialogModifyText().setHint("请输入评委姓名");
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
                        Log.e("afterTextChanged_update", modifyValue);
                    }
                });
                modifyDialog.getTvDialogModOk().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!PublicStatic.isEmpty(modifyValue)) {
                            if (modifyValue.length() > 1 && modifyValue.length() < 7) {
                                if (checkRule(modifyValue)) {
                                    modifyDialog.dismiss();
                                    examiner_name = modifyValue;
                                    tvPingweiName.setText(modifyValue);
                                } else {
                                    ToastUtil.showToast("姓名只能输入汉字！");
                                }
                            } else {
                                ToastUtil.showToast("评委姓名有效2-6位！");
                            }
                        } else {
                            ToastUtil.showToast("输入内容不能为空！");
                        }
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
                break;
            case R.id.ll_update_score://完成评分（可修改）
                if (!PublicStatic.isEmpty(examiner_name)) {
                    if (!PublicStatic.isEmpty(student_seq)) {
                        //1-0分确认 2-修改确认 3-生成二维码
                        for (int i = 0; i < mingxiList.size(); i++) {
                            if (mingxiList.get(i).getFinishScore() == 0) {
                                zeroText = zeroText + mingxiList.get(i).getFactor_name() + ":分数为0" + "\n";
                            }
                        }
                        Log.e("zeroText外", zeroText);
                        if (zeroText.equals("")) {
                            showIfUpdate();
                        } else {//0分提示
                            zeroDialog.show();
                            zeroDialog.getTv_title().setText(zeroText);
                            zeroDialog.getTv_ok().setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    zeroDialog.dismiss();
                                    zeroText = "";
                             /*0分确认完事再弹“是否修改”*/
                                    showIfUpdate();
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
                    } else {
                        ToastUtil.showToast("请输入考生序号！");
                    }
                } else {
                    ToastUtil.showToast("请输入评委姓名！");
                }
                break;
        }
    }

    String deviceTime;

    /*是否确认修改弹窗*/
    public void showIfUpdate() {
        tuwenDialog.show();
        tuwenDialog.getImg_icon().setBackgroundResource(R.mipmap.ic_modify_info);
        tuwenDialog.getTv_title().setText("确定修改吗？");
        tuwenDialog.getTv_ok().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deviceTime = PublicStatic.getCurrentTime();
                //然后将修改后的数据存入！
                updateStuInfo();
                //然后将修改后的数据存入操作记录！
                addRecordInfo();
                updateScoreDialog.show();
                updateScoreDialog.getFinishName().setText("评委 : " + examiner_name);
                updateScoreDialog.getFinishXuhao().setText("考生序号 : " + student_seq);
                updateScoreDialog.getFinishScore().setText("总分 : " + totalScores);
        /*------------显示二维码=-----------------*/
                mHandler.post(mUpdateImageRunnable);
                updateScoreDialog.getBtnResultClose().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateScoreDialog.dismiss();
                        tuwenDialog.dismiss();
                        /*关闭页面*/
                        Intent intent = new Intent();
                        intent.putExtra("type", "1");
                        setResult(10001, intent);

                        finish();
                    }
                });

            }
        });

        tuwenDialog.getTv_cancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuwenDialog.dismiss();
            }
        });

    }

    float totalScores;//总分
    String scoreinfo;//成绩明细
    List<UpdateStuBean> mingxiList;

    @Subscribe
    public void onMessageEvent2(MessageEvent2 event) {
        totalScores = event.getTotal();//总分
        tvTotalScore.setText(event.getTotal() + "分");
        scoreinfo = event.getScoreinfo().toString();
        //  Log.e("成绩明细--修改后", scoreinfo);//应急应变:7;举止仪表:3;专业(创新)能力:12
        /*成绩明细list*/
        mingxiList = event.getDatas();

    }

    private Handler mHandler = new Handler(Looper.getMainLooper());

    //生成二维码
    private Runnable mUpdateImageRunnable = new Runnable() {
        @Override
        public void run() {
            String key = completionKey("CI1B3u3VNE0xBZ7F" + config_id);
            String pingweiCode = config_id + "^" + "score" + "^" + deviceTime + "^" + student_seq + "^" + examiner_name + "^" + totalScores + "^" + scoreinfo;
            Log.e("评委打分二维码-修改后", pingweiCode);
            String encrypt = AESUtils.encrypt(pingweiCode, key).replace("\n", "");
            updateScoreDialog.getmBarcodeImageView().setImageBitmap(QRCodeProduceUtils.create(encrypt, 360, 360, PublicStatic.getBitmap(R.mipmap.logo160x160, activity)));

        }
    };

    /*操作记录*/
    public void addRecordInfo() {
        RecordInfoBean bean = new RecordInfoBean();
        bean.setConfig_id(config_id);
        bean.setStudent_seq(student_seq);
        bean.setExaminer_name(examiner_name);
        bean.setDevice_time(deviceTime);
        bean.setTotal_score(totalScores);
        bean.setScores(scoreinfo);
        bean.setType("修改");
        /**/
        if (DBOperRecord.getInstance().insertData(bean) != -1) {
            Log.d("addRecordInfo", "修改-添加成功");
        } else {
            Log.d("addRecordInfo", "修改-添加失败");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

