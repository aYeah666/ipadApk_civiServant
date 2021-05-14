package com.jiufang.interviewsystem.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;

/**
 * 带有图文的二次确认对话框 （点击外面可以消失）
 */
public class TuwenConfirmDialog extends Dialog {

    public View view;
    private Context c;
    private TextView tv_title;
    private TextView tv_ok;
    private TextView tv_cancel;
    private ImageView img_icon;

    /**
     * 构造确认信息弹窗
     *
     * @param context 上下文
     */
    public TuwenConfirmDialog(Context context, int theme) {
        super(context, theme);
        view = View.inflate(context, R.layout.dialog_tuwen_confirm, null);
        this.setContentView(view);
        this.c = context;
        img_icon = (ImageView) findViewById(R.id.iv_tvwen_icon);
        tv_title = (TextView) findViewById(R.id.tv_dialog_tishi);
        tv_ok = (TextView) findViewById(R.id.tv_dialog_ok);
        tv_cancel = (TextView) findViewById(R.id.tv_dialog_cancel);
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

    public ImageView getImg_icon() {
        return img_icon;
    }

    public void setImg_icon(ImageView img_icon) {
        this.img_icon = img_icon;
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

    public TextView getTv_cancel() {
        return tv_cancel;
    }

    public void setTv_cancel(TextView tv_cancel) {
        this.tv_cancel = tv_cancel;
    }
}
