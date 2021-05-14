package com.jiufang.interviewsystem.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.qrcode.Constant;
import com.example.qrcode.ScannerActivity;
import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.base.QrcodeAdapter;
import com.jiufang.interviewsystem.bean.FactorBean;
import com.jiufang.interviewsystem.bean.QrcodeBean;
import com.jiufang.interviewsystem.database.DBOper1;
import com.jiufang.interviewsystem.utils.AESUtils;
import com.jiufang.interviewsystem.utils.SpNames;
import com.jiufang.interviewsystem.utils.SpUtils;
import com.jiufang.interviewsystem.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by aixy on 2019/10/29.
 * Desc:二维码页面（最多10个算上初始化的）
 */

public class AllQrcodeActivity extends AppCompatActivity {

    @BindView(R.id.btn_jumpto_maintab)
    Button btnJumptoMaintab;
    public AllQrcodeActivity ac;
    public static final int FACTOR_CODE = 9;
    @BindView(R.id.grid_view)
    GridView gridView;
    List<FactorBean> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_erweima);
        ButterKnife.bind(this);
        ac = AllQrcodeActivity.this;
        factCompleKey = SpUtils.getParam(ac, SpNames.CompleKey, "").toString();
        InitializeQcNum = Integer.valueOf(SpUtils.getParam(ac, SpNames.InitializeQcNum, "").toString());
        Log.e("二维码总的数量--", InitializeQcNum + "");
          datas = DBOper1.getInstance().queryDatas();
        Log.e("数据库要素二维码数量--", InitializeQcNum + "");
        if (datas.size() == InitializeQcNum - 1) {//就是重新运行的时候
            initErweimaData(1);
        } else {
            initErweimaData(0);
        }
    }
/*--------------------------------------------------------------*/

    private void goScanner() {
        Intent intent = new Intent(this, ScannerActivity.class);
        //这里可以用intent传递一些参数，比如扫码聚焦框尺寸大小，支持的扫码类型。
       //设置扫码框的宽\高
        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_WIDTH, 400);
        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_HEIGHT, 400);
        //设置扫码框距顶部的位置
        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_TOP_PADDING, 100);
        //设置是否启用从相册获取二维码。
        intent.putExtra(Constant.EXTRA_IS_ENABLE_SCAN_FROM_PIC, true);
//        Bundle bundle = new Bundle();
//        //设置支持的扫码类型
//        bundle.putSerializable(Constant.EXTRA_SCAN_CODE_TYPE, mHashMap);
//        intent.putExtras(bundle);
        startActivityForResult(intent, FACTOR_CODE);

    }

    String factCompleKey;
    int InitializeQcNum;//初始二维码数
    String factorCode1 = "";//扫出的内容

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FACTOR_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                factorCode1 = data.getStringExtra(Constant.EXTRA_RESULT_CONTENT);
                Log.e("要素扫描结果", factorCode1);
                if (AESUtils.decrypt(factorCode1, AESUtils.completionKey(factCompleKey)) != null) {
                    String factorData1 = AESUtils.decrypt(factorCode1, AESUtils.completionKey(factCompleKey)).toString();
                    Log.e("要素解密结果", factorData1);
                    //要素二维码string: hrbms20190001^conf^9^10^5^言语表达^0^15
                    String[] argsYs = factorData1.split("\\^");

                    FactorBean bean = new FactorBean();
                    bean.setConfig_id(argsYs[0]);//面试编号
                    bean.setQc_name("conf");//二维码类型  固定conf
                    bean.setFactor_num(Integer.valueOf(argsYs[2]));//要素数量
                    bean.setInitialize_qc_num(Integer.valueOf(argsYs[3]));//初始化二维码数量
                    int now_qc_num = Integer.valueOf(argsYs[4]);
                    bean.setFactor_name(argsYs[5]);//要素名
                    bean.setFactor_min_score(argsYs[6]);//	要素最低分
                    bean.setFactor_max_score(argsYs[7]);//要素最高分
                    bean.setNow_qc_num(now_qc_num);//当前二维码编号
                    bean.setShowValue(Integer.valueOf(argsYs[6]));

                    if (DBOper1.getInstance().insertData(bean) != -1) {
                        Toast.makeText(ac, argsYs[5] + "二维码扫描成功", Toast.LENGTH_SHORT).show();
                            /*根据编号让二维码变背景*/
                        adapter.updateItem(now_qc_num - 1);
                    } else {
                        Toast.makeText(ac, "添加失败", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    ToastUtil.showToast("解析失败,请重新扫描");

                }

            }
        }

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case 1://扫描要素
                    goScanner();
                    break;
            }
        }
    };

    @OnClick({R.id.btn_jumpto_maintab})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_jumpto_maintab://跳转之前要进行验证（要素数量和初始化二维码数量）？
                datas = DBOper1.getInstance().queryDatas();
                //查询数据库，要素二维码数一致就OK
                 if (datas.size() == InitializeQcNum - 1) {
                    startActivity(new Intent(AllQrcodeActivity.this, MainTabActivity.class));
                    SpUtils.setParam(AllQrcodeActivity.this, SpNames.isFirstLogin, "yes");
                    finish();
                } else {
                    ToastUtil.showToast("二维码信息还没有全部获取！请继续扫描");
                }
           /*  这个是可以直接跳转的*/
             //   startActivity(new Intent(AllQrcodeActivity.this, MainTabActivity.class));
                break;
        }

    }

    private List<QrcodeBean> dataList = new ArrayList<>();
    private QrcodeAdapter adapter;
    QrcodeBean bean = new QrcodeBean();

    public void initErweimaData(final int type) {
        for (int a = 0; a < InitializeQcNum; a++) {
            bean.setType(type);//初始化都是待扫描状态
            bean.setPo(a);
            dataList.add(bean);
        }
        adapter = new QrcodeAdapter(this, dataList, handler);
        adapter.setListView(gridView);
        gridView.setAdapter(adapter);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));

    }

}


