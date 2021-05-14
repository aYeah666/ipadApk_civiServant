package com.jiufang.interviewsystem.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;

import butterknife.OnClick;

/**
 * Created by Administrator on 2017/11/20.
 */

public class AdderView extends LinearLayout implements View.OnClickListener {


    private int value = 0;
    private int minValue ;
    private int maxValue ;
    private final TextView tvCount;

    public AdderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.number_adder, this);
        ImageView btn_reduce = (ImageView) view.findViewById(R.id.btn_reduce);
        tvCount = (TextView) view.findViewById(R.id.tv_count);
        ImageView btn_add = (ImageView) view.findViewById(R.id.btn_add);
        btn_reduce.setOnClickListener(this);
        btn_add.setOnClickListener(this);

    }

    @OnClick({R.id.btn_reduce, R.id.btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reduce://减
                reduce();
                break;
            case R.id.btn_add://加
                add();
                break;
        }
    }

    /**
     * 如果当前值大于最小值   减
     */
    private void reduce() {
        if (value > minValue) {
            value--;
        }
        setCurrentValue(value);
        if (onValueChangeListene != null) {
            onValueChangeListene.onValueChange(value,mPosition);
        }
    }

    /**
     * 如果当前值小于最小值  加
     */
    private void add() {
        if (value < maxValue) {
            value++;
        }
        setCurrentValue(value);
        if (onValueChangeListene != null) {
            onValueChangeListene.onValueChange(value,mPosition);
        }
    }

    //获取具体值
    public int getValue() {
        String countStr = tvCount.getText().toString().trim();
        if (countStr != null) {
            value = Integer.valueOf(countStr);
        }
        return value;
    }

    public void setCurrentValue(int value) {
        this.value = value;
        tvCount.setText(value + "");
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reduce://减
                reduce();
                break;
            case R.id.btn_add://加
                add();
                break;
        }
    }


    //监听回调
    public interface OnValueChangeListener {
      //  public void onValueChange(int value);
        void onValueChange(int value, int position);
    }

    private OnValueChangeListener onValueChangeListene;

    public void setOnValueChangeListene(OnValueChangeListener onValueChangeListene) {
        this.onValueChangeListene = onValueChangeListene;
    }
    private int mPosition = 0; // 设置改变的位置，默认是0; //集合数据中会用到
    public AdderView setPosition(int position) {
        mPosition = position;
        return this;
    }
    public int getPosition() {
        return mPosition;
    }
}
