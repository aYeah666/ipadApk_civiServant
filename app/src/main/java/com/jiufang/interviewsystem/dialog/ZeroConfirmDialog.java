package com.jiufang.interviewsystem.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;

/**
 * 每项逐一判断 0分弹窗
 */
public class ZeroConfirmDialog extends Dialog {

    public View view;
    private Context c;
    private TextView tv_title;
    private TextView tv_ok;
    private TextView tv_fanhui;

    /**
     * 构造确认信息弹窗
     *
     * @param context 上下文
     */
    public ZeroConfirmDialog(Context context, int theme) {
        super(context, theme);
        view = View.inflate(context, R.layout.dialog_zero_confirm, null);
        this.setContentView(view);
        this.c = context;

        tv_title = (TextView) findViewById(R.id.tv_dialog_tishi);
        tv_ok = (TextView) findViewById(R.id.tv_dialog_ok);
        tv_fanhui = (TextView) findViewById(R.id.tv_dialog_fanhui);
    }

    /**
     * 显示确认信息弹窗
     *
     * @param title 正文 提示语
     */
    public void showTitle(String title) {

        if (!"".equals(title) || null != title) {
            tv_title.setText(title);
        } else {
            tv_title.setText("请确认相关信息。");
        }

        this.show();
    }

    public void cancel() {
        if (!isShowing()) {
            return;
        }
        super.cancel();
    }

    public void show() {
        if (isShowing()) {
            return;
        }
        super.show();
    }

    public void setView(View view) {
        this.view = view;
        this.setContentView(view);
    }


    public TextView getTv_title() {
        return tv_title;
    }

    public void setTv_title(TextView tv_title) {
        this.tv_title = tv_title;
    }

    public TextView getTv_ok() {
        return tv_ok;
    }

    public void setTv_ok(TextView tv_ok) {
        this.tv_ok = tv_ok;
    }

    public TextView getTv_fanhui() {
        return tv_fanhui;
    }

    public void setTv_fanhui(TextView tv_fanhui) {
        this.tv_fanhui = tv_fanhui;
    }
}
