package com.jiufang.interviewsystem.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;

import butterknife.BindView;

/**
 * 结束评分
 */
public class FinishScoreDialog extends Dialog {

    public View view;
    private Context c;
    TextView finishName, finishXuhao, finishScore;
    Button btnFinishXiugai, btnFinishNext;
    ImageView mBarcodeImageView;

    /**
     * 构造确认信息弹窗
     *
     * @param context 上下文
     */
    public FinishScoreDialog(Context context, int theme) {
        super(context, theme);
        view = View.inflate(context, R.layout.dialog_finish_result, null);
        this.setContentView(view);
        this.c = context;
        mBarcodeImageView = (ImageView) findViewById(R.id.barcode_image);
        finishName = (TextView) findViewById(R.id.tv_dia_finish_name);
        finishXuhao = (TextView) findViewById(R.id.tv_dia_finish_xuhao);
        finishScore = (TextView) findViewById(R.id.tv_dia_finish_score);

        btnFinishXiugai = (Button) findViewById(R.id.btn_dia_finish_xiugai);
        btnFinishNext = (Button) findViewById(R.id.btn_dia_finish_next);

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

    public ImageView getmBarcodeImageView() {
        return mBarcodeImageView;
    }

    public void setmBarcodeImageView(ImageView mBarcodeImageView) {
        this.mBarcodeImageView = mBarcodeImageView;
    }

    public TextView getFinishName() {
        return finishName;
    }

    public void setFinishName(TextView finishName) {
        this.finishName = finishName;
    }

    public TextView getFinishXuhao() {
        return finishXuhao;
    }

    public void setFinishXuhao(TextView finishXuhao) {
        this.finishXuhao = finishXuhao;
    }

    public TextView getFinishScore() {
        return finishScore;
    }

    public void setFinishScore(TextView finishScore) {
        this.finishScore = finishScore;
    }

    public Button getBtnFinishXiugai() {
        return btnFinishXiugai;
    }

    public void setBtnFinishXiugai(Button btnFinishXiugai) {
        this.btnFinishXiugai = btnFinishXiugai;
    }

    public Button getBtnFinishNext() {
        return btnFinishNext;
    }

    public void setBtnFinishNext(Button btnFinishNext) {
        this.btnFinishNext = btnFinishNext;
    }
}
