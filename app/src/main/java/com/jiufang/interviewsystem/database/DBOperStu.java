package com.jiufang.interviewsystem.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jiufang.interviewsystem.base.MyApplication;
import com.jiufang.interviewsystem.bean.FactorBean;
import com.jiufang.interviewsystem.bean.StudentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 类的介绍：信息
 */
public class DBOperStu {

    private DBHelperStu helper;
    private SQLiteDatabase db;
    private static DBOperStu dbOper;

    private DBOperStu() {
        helper = new DBHelperStu(MyApplication.applicationContext);
    }

    public static DBOperStu getInstance() {
        if (dbOper == null) {
            dbOper = new DBOperStu();
        }
        return dbOper;
    }

    /*查询所有数据*/
    public List<StudentBean> queryDatas() {
        List<StudentBean> datas = new ArrayList<StudentBean>();
        // 获取数据库的读权限
        db = helper.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(DBHelperStu.DBTABLE, null, null, null, null, null,
                    null);
            while (cursor.moveToNext()) {// 循环查询数据

                String device_time = cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Device_time));
                String examiner_name = cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Examiner_name));
                String student_seq = cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Student_seq));
                String config_id = cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Config_id));
                String qc_name = cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Qc_name));
                String scores = cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.scores));

                Float total_score = cursor.getFloat(cursor
                        .getColumnIndex(DBHelperStu.total_score));
/**/
                StudentBean bean = new StudentBean();
                bean.setConfig_id(config_id);
                bean.setStudent_seq(student_seq);
                bean.setDevice_time(device_time);
                bean.setExaminer_name(examiner_name);
                bean.setTotal_score(total_score);
                bean.setQc_name(qc_name);
                bean.setScores(scores);
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
    public long insertData(StudentBean bean) {
        try {
            // 获取数据库写权限
            db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DBHelperStu.Device_time, bean.getDevice_time());
            values.put(DBHelperStu.Examiner_name, bean.getExaminer_name());
            values.put(DBHelperStu.Student_seq, bean.getStudent_seq());
            values.put(DBHelperStu.Qc_name, bean.getQc_name());
            values.put(DBHelperStu.Config_id, bean.getConfig_id());
            values.put(DBHelperStu.scores, bean.getScores());
            values.put(DBHelperStu.total_score, bean.getTotal_score());

            return db.insert(DBHelperStu.DBTABLE, null, values);
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
    public int updateData(StudentBean bean) {
        try {
            db = helper.getWritableDatabase();
            // 要修改的东西
            ContentValues values = new ContentValues();
            values.put(DBHelperStu.Device_time, bean.getDevice_time());
            values.put(DBHelperStu.Examiner_name, bean.getExaminer_name());
            values.put(DBHelperStu.Student_seq, bean.getStudent_seq());
            values.put(DBHelperStu.Qc_name, bean.getQc_name());
            values.put(DBHelperStu.Config_id, bean.getConfig_id());
            values.put(DBHelperStu.scores, bean.getScores());
            values.put(DBHelperStu.total_score, bean.getTotal_score());
            // 修改的依据
            String whereClause = DBHelperStu.Student_seq + "=?";
            String[] whereArgs = new String[]{String.valueOf(bean.getStudent_seq())};
            return db.update(DBHelperStu.DBTABLE, values, whereClause, whereArgs);
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
    public void deleteData(StudentBean bean) {
        try {
            db = helper.getWritableDatabase();
            //String whereClause = DBHelperStu.Now_qc_num + "=?";//编号
            //String[] whereArgs = new String[]{String.valueOf(bean.getNow_qc_num())};
            // db.delete(DBHelper1.DBTABLE, whereClause, whereArgs);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    /**
     * 根据考生序号查询数据
     */
    public StudentBean queryStubean(String kaohao) {
        db = helper.getWritableDatabase();
        StudentBean stu = new StudentBean();

        Cursor cursor = null;
        try {
            cursor = db.query(DBHelperStu.DBTABLE, null, "student_seq=?", new String[]{kaohao}, null, null,
                    null);
            while (cursor.moveToNext()) {// 循环查询数据

                stu.setDevice_time(cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Device_time)));
                stu.setExaminer_name(cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Examiner_name)));
                stu.setStudent_seq(cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Student_seq)));
                stu.setConfig_id(cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Config_id)));
                stu.setQc_name(cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Qc_name)));
                stu.setScores(cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.scores)));
                stu.setTotal_score(cursor.getFloat(cursor
                        .getColumnIndex(DBHelperStu.total_score)));
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
        return stu;
    }

    /**
     * 倒叙全查，取第一条也就是数据库的最后一条
     */
    public String queryLastData() {
        db = helper.getWritableDatabase();
        String currentXuhao=null;
        Cursor cursor = null;
        try {
             cursor = db.rawQuery("select * from studentInfo ", null);
            if (cursor.moveToLast()) {
// 该cursor是最后一条数据
                currentXuhao=cursor.getString(cursor
                        .getColumnIndex(DBHelperStu.Student_seq));
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
        return currentXuhao;
    }
}
