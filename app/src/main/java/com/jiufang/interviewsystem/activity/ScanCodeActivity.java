package com.jiufang.interviewsystem.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.qrcode.Constant;
import com.example.qrcode.ScannerActivity;
import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.utils.AESUtils;
import com.jiufang.interviewsystem.utils.CheckPermissionUtils;
import com.jiufang.interviewsystem.utils.PublicStatic;
import com.jiufang.interviewsystem.utils.SpNames;
import com.jiufang.interviewsystem.utils.SpUtils;
import com.jiufang.interviewsystem.utils.ToastUtil;
import java.util.List;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by aixy on 2019/10/29.
 * Desc:扫描二维码初始化
 */

public class ScanCodeActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public LinearLayout ll_scancode = null;
    public ScanCodeActivity ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_scancode);
        ac = ScanCodeActivity.this;
        initView();
        //初始化权限
        initPermission();
    }

    /**
     * 初始化权限事件
     */
    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtils.checkPermission(this);
        if (permissions.length == 0) {     //权限都申请了
            //...do
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    /**
     * 初始化组件
     */
    private void initView() {
        ll_scancode = (LinearLayout) findViewById(R.id.ll_scancode);
        /*打开二维码扫描界面--扫描初始的*/
        ll_scancode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (EasyPermissions.hasPermissions(ScanCodeActivity.this, Manifest.permission.CAMERA)) {
                    // 有权限了，就可以跳转到所有二维码页面操作了。。。
                    goScanner(ac, 1);
                } else {
                    EasyPermissions.requestPermissions(ScanCodeActivity.this, "需要请求camera权限",
                            REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
                }

            }
        });

    }

    public static void goScanner(Context context, int index) {
        Intent intent = new Intent(context, ScannerActivity.class);
        //这里可以用intent传递一些参数，比如扫码聚焦框尺寸大小，支持的扫码类型。
//        //设置扫码框的宽
        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_WIDTH, 400);
//        //设置扫码框的高
        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_HEIGHT, 400);
//        //设置扫码框距顶部的位置
        intent.putExtra(Constant.EXTRA_SCANNER_FRAME_TOP_PADDING, 100);
//        //设置是否启用从相册获取二维码。
        intent.putExtra(Constant.EXTRA_IS_ENABLE_SCAN_FROM_PIC, true);
        ((Activity) context).startActivityForResult(intent, index);
    }

    //初始二维码数据的存储
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {

            if (data != null) {
                String content = data.getStringExtra(Constant.EXTRA_RESULT_CONTENT);
                Log.e("初始二维码content", content);
                String compleKey = AESUtils.completionKey("CI1B3u3VNE0xBZ7F");
                if (AESUtils.decrypt(content, compleKey) != null) {
                    String baseData = AESUtils.decrypt(content, compleKey).toString();
                    Log.e("初始二维码string", baseData);
                    String daoruTime = PublicStatic.getCurrentTime();
                    SpUtils.setParam(ac, SpNames.Daoru_date, daoruTime);

                    String[] args = baseData.split("\\^");

                    String configID = args[0];
                                                   /*要素二维码密钥*/
                    SpUtils.setParam(ac, SpNames.CompleKey, AESUtils.completionKey("CI1B3u3VNE0xBZ7F" + configID));
                    SpUtils.setParam(ac, SpNames.ConfigId, configID);//面试编号
                    SpUtils.setParam(ac, SpNames.QCName, "init");//二维码类型 固定init
                    SpUtils.setParam(ac, SpNames.AREA, args[3]);
                    SpUtils.setParam(ac, SpNames.YEAR, args[2]);
                    SpUtils.setParam(ac, SpNames.TITLE, args[4]);//打印标题
                    SpUtils.setParam(ac, SpNames.Expiration_date, args[5]);//过期时间，格式yyyy-MM-dd
                    SpUtils.setParam(ac, SpNames.FactorNum, args[6]);//要素数量
                    SpUtils.setParam(ac, SpNames.InitializeQcNum, args[7]);//初始化二维码数量
                    SpUtils.setParam(ac, SpNames.NowQcNum, args[8]);//当前二维码编号

                    ToastUtil.showToast("初始化二维码扫描成功！");
                    startActivity(new Intent(this, AllQrcodeActivity.class));
                } else {
                    ToastUtil.showToast("解析失败,请重新扫描");
                }

            } else {
                ToastUtil.showToast("请重新扫描");
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;


    /**
     * EsayPermissions接管权限处理逻辑
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        // Toast.makeText(this, "执行onPermissionsGranted()...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        //  Toast.makeText(this, "执行onPermissionsDenied()...", Toast.LENGTH_SHORT).show();
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前App需要申请camera权限,需要打开设置页面么?")
                    .setTitle("权限申请")
                    .setPositiveButton("确认")
                    .setNegativeButton("取消", null /* click listener */)
                    .setRequestCode(REQUEST_CAMERA_PERM)
                    .build()
                    .show();
        }
    }

}


