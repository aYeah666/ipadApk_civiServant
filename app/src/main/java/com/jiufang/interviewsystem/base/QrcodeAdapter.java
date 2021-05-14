package com.jiufang.interviewsystem.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jiufang.interviewsystem.R;
import com.jiufang.interviewsystem.bean.QrcodeBean;
import com.jiufang.interviewsystem.bean.StudentBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by aixy on 2019/11/7.
 * Desc:最多10个二维码显示（也就是最多9个要素）
 */


//角色库
public class QrcodeAdapter extends BaseAdapter {

    private Activity activity;
    private List<QrcodeBean> datas;
    private Handler handler;

    public QrcodeAdapter(Activity activity, List<QrcodeBean> datas, Handler handler) {
        this.activity = activity;
        this.datas = datas;
        this.handler = handler;
    }

    /**
     * 设置listview对象
     *
     * @param lisv
     */
    public void setListView(GridView lisv) {
        this.mListView = lisv;
    }

    @Override
    public int getCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    int po;


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder ;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.item_erweima_saomiao, null);
            holder.img = (ImageView) convertView
                    .findViewById(R.id.image);
            holder.text = (TextView) convertView
                    .findViewById(R.id.text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

setData(position,holder);
        return convertView;
    }


    GridView mListView;

    /**
     * 刷新指定item
     *
     * @param index item在listview中的位置
     */
    public void updateItem(int index)
    {
        if (mListView == null) {
            return;
        }
        datas.get(index).setType(1);
        //获取第一个可以看到的item 位置
        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        //获取点击更新的view
        if (index - firstVisiblePosition >= 0) {
            View view = mListView.getChildAt(index - firstVisiblePosition);
            ViewHolder tag = (ViewHolder) view.getTag();
            TextView txt = (TextView) view.findViewById(R.id.text);
            ImageView image = (ImageView) view.findViewById(R.id.image);
            setData(index, tag);
        }

    }

    QrcodeBean bean;

    private void setData(final int position, ViewHolder holder) {
        bean = datas.get(position);
        if (position == 0) {
            holder.img.setBackgroundResource(R.mipmap.icon_has_saomiao);
            holder.text.setText("初始化二维码已扫描");
            holder.img.setEnabled(false);//不让在点击
            holder.img.setClickable(false);
        } else {

            if ( bean.getType() == 1) {
                holder.img.setBackgroundResource(R.mipmap.icon_has_saomiao);
                holder.text.setText("二维码已扫描");
                holder.img.setEnabled(false);//不让在点击
                holder.img.setClickable(false);
            } else {
                holder.img.setBackgroundResource(R.mipmap.icon_hasnot_saomiao);
                holder.text.setText("待扫描二维码");
                holder.img.setEnabled(true);//不让在点击
                holder.img.setClickable(true);
                holder.img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        po = position;
                        bean.setPo(position);
                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = bean;
                        handler.sendMessage(msg);
                    }
                });
            }
        }
    }


class ViewHolder {
    public TextView text;
    public ImageView img;

}}


