package com.jiufang.interviewsystem.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.jiufang.interviewsystem.base.MyApplication;
import com.jiufang.interviewsystem.bean.FactorBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 类的介绍：要素信息
 */
public class DBOper1 {

    private DBHelper1 helper;
    private SQLiteDatabase db;
    private static DBOper1 dbOper;

    private DBOper1() {
        helper = new DBHelper1(MyApplication.applicationContext);
    }

    public static DBOper1 getInstance() {
        if (dbOper == null) {
            dbOper = new DBOper1();
        }
        return dbOper;
    }

    /*查询所有数据*/
    public List<FactorBean> queryDatas() {
        List<FactorBean> datas = new ArrayList<FactorBean>();
        // 获取数据库的读权限
        db = helper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DBHelper1.DBTABLE, null, null, null, null, null,
                    "now_qc_num");
            while (cursor.moveToNext()) {// 循环查询数据

                int now_qc_num = cursor.getInt(cursor.getColumnIndex(DBHelper1.Now_qc_num));
                int factor_num = cursor.getInt(cursor.getColumnIndex(DBHelper1.Factor_num));
                int initialize_qc_num = cursor.getInt(cursor.getColumnIndex(DBHelper1.Initialize_qc_num));

                String factor_min_score = cursor.getString(cursor
                        .getColumnIndex(DBHelper1.Factor_min_score));
                String factor_max_score = cursor.getString(cursor
                        .getColumnIndex(DBHelper1.Factor_max_score));
                String factor_name = cursor.getString(cursor
                        .getColumnIndex(DBHelper1.Factor_name));
                String config_id = cursor.getString(cursor
                        .getColumnIndex(DBHelper1.Config_id));
                String qc_name = cursor.getString(cursor
                        .getColumnIndex(DBHelper1.Qc_name));
                int show_value = cursor.getInt(cursor
                        .getColumnIndex(DBHelper1.Show_value));
                FactorBean bean = new FactorBean();
                bean.setNow_qc_num(now_qc_num);
                bean.setFactor_num(factor_num);
                bean.setInitialize_qc_num(initialize_qc_num);
                /**/
                bean.setFactor_max_score(factor_max_score);
                bean.setFactor_min_score(factor_min_score);
                bean.setConfig_id(config_id);
                bean.setQc_name(qc_name);
                bean.setFactor_name(factor_name);
                bean.setShowValue(show_value);

                datas.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
        return datas;
    }

    /**
     * 插入数据
     *
     * @param bean
     * @return 返回 -1 表示添加失败，返回1表示添加成功
     */
    public long insertData(FactorBean bean) {
        try {
            ContentValues values = new ContentValues();
            values.put(DBHelper1.Now_qc_num, bean.getNow_qc_num());
            values.put(DBHelper1.Factor_num, bean.getFactor_num());
            values.put(DBHelper1.Initialize_qc_num, bean.getInitialize_qc_num());
			/**/
            values.put(DBHelper1.Factor_min_score, bean.getFactor_min_score());
            values.put(DBHelper1.Factor_max_score, bean.getFactor_max_score());
            values.put(DBHelper1.Factor_name, bean.getFactor_name());
            values.put(DBHelper1.Config_id, bean.getConfig_id());
            values.put(DBHelper1.Qc_name, bean.getQc_name());
            values.put(DBHelper1.Show_value, bean.getShowValue());
            // 获取数据库写权限
            db = helper.getWritableDatabase();
/*原来是insert*/
            return db.replace(DBHelper1.DBTABLE, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }

        }
        return -1;
    }

    /**
     * 更新数据
     *
     * @param bean
     * @return 返回0 表示修改失败 返回1表示修改成功
     */
    public int updateData(FactorBean bean) {
        try {
            db = helper.getWritableDatabase();
            // 要修改的东西
            ContentValues values = new ContentValues();
            //	values.put(DBHelper.NAME, bean.getName());
            //values.put(DBHelper.IMAGE, bean.getImage());
            //values.put(DBHelper.TYPE, bean.getType());
            // 修改的依据
     //       String whereClause = DBHelper1.ID + "=?";
            //	String[] whereArgs = new String[] { String.valueOf(bean.getId()) };
            //	return db.update(DBHelper.DBTABLE, values, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
    }

    /**
     * 删除数据
     *
     * @param bean
     */
    public void deleteData(FactorBean bean) {
        try {
            db = helper.getWritableDatabase();
            String whereClause = DBHelper1.Now_qc_num + "=?";//编号
            String[] whereArgs = new String[]{String.valueOf(bean.getNow_qc_num())};
            db.delete(DBHelper1.DBTABLE, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

}
