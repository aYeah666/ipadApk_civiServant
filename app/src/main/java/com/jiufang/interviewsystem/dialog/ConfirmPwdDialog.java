package com.jiufang.interviewsystem.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;

/**
 * 输入8位密码，进行确认
 */
public class ConfirmPwdDialog extends Dialog {

    public View view;
    private Context c;

    EditText etDialogPwd;
    TextView tvDialogPwdOk, tvDialogPwdCancel;

    /**
     * 构造确认信息弹窗
     *
     * @param context 上下文
     */
    public ConfirmPwdDialog(Context context, int theme) {
        super(context, theme);
        view = View.inflate(context, R.layout.dialog_confirm_pwd, null);
        this.setContentView(view);
        this.c = context;
        etDialogPwd = (EditText) findViewById(R.id.et_dialog_pwd);
        tvDialogPwdOk = (TextView) findViewById(R.id.tv_dialog_pwd_ok);
        tvDialogPwdCancel = (TextView) findViewById(R.id.tv_dialog_pwd_cancel);
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

    public EditText getEtDialogPwd() {
        return etDialogPwd;
    }

    public void setEtDialogPwd(EditText etDialogPwd) {
        this.etDialogPwd = etDialogPwd;
    }

    public TextView getTvDialogPwdOk() {
        return tvDialogPwdOk;
    }

    public void setTvDialogPwdOk(TextView tvDialogPwdOk) {
        this.tvDialogPwdOk = tvDialogPwdOk;
    }

    public TextView getTvDialogPwdCancel() {
        return tvDialogPwdCancel;
    }

    public void setTvDialogPwdCancel(TextView tvDialogPwdCancel) {
        this.tvDialogPwdCancel = tvDialogPwdCancel;
    }
}
