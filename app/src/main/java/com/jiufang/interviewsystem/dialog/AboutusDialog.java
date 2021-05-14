package com.jiufang.interviewsystem.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.jiufang.interviewsystem.R;

/**
 * 标题栏的关于
 */
public class AboutusDialog extends Dialog {

    public View view;
    TextView tvAboutus;
    ImageView btnClose;
    private Context c;

    /**
     * 构造确认信息弹窗
     *
     * @param context 上下文
     */
    public AboutusDialog(Context context, int theme) {
        super(context, theme);
        view = View.inflate(context, R.layout.dialog_aboutus, null);
        this.setContentView(view);
        this.c = context;
        tvAboutus = (TextView) findViewById(R.id.tv_aboutus);
        btnClose = (ImageView) findViewById(R.id.btn_close);
    }

    /**
     * 显示确认信息弹窗
     *
     * @param title 正文 提示语
     */
    public void showTitle(String title) {

        if (!"".equals(title) || null != title) {
            tvAboutus.setText(title);
        } else {
            tvAboutus.setText("请确认相关信息。");
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

    public ImageView getBtnClose() {
        return btnClose;
    }

    public void setBtnClose(ImageView btnClose) {
        this.btnClose = btnClose;
    }
}
