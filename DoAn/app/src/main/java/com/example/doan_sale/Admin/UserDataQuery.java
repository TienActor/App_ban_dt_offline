package com.example.doan_sale.Admin;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.doan_sale.model.user;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.Ultils;
import java.util.ArrayList;

public class UserDataQuery {
    public static long insert(Context context, user us) {
        DBHelper userDBHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = userDBHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Ultils.USER_NAME, us.getUserName());
        cv.put(Ultils.USER_PASS, us.getPassword());
        cv.put(Ultils.USER_EMAIL, us.getEmail());
        cv.put(Ultils.USER_PHONE, us.getPhoneNumber());
        cv.put(Ultils.USER_AVATAR, String.valueOf(us.getAvatar()));
        long rs = sqLiteDatabase.insert(Ultils.TABLE_USER, null, cv);
        return (rs);
    }
    //lay danh sach
    public static ArrayList<user> getAll(Context context) {
        ArrayList<user> lstUser = new ArrayList<>();
        DBHelper userDBHelper = new DBHelper(context);
        SQLiteDatabase db = userDBHelper.getReadableDatabase();

        Cursor cs = db.rawQuery("Select * from " + Ultils.TABLE_USER, null);
        cs.moveToFirst();
        while (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String password = cs.getString(2);
            String email = cs.getString(3);
            String phonenumber = cs.getString(4);
            int avarta = cs.getInt(5);
            user item = new user(id, name, password, email, phonenumber, avarta);
            lstUser.add(item);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lstUser;
    }

    public static boolean delete(Context context, int id) {
        DBHelper userDBHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = userDBHelper.getWritableDatabase();
        int rs = sqLiteDatabase.delete(Ultils.TABLE_USER, Ultils.USER_ID + "=?", new String[]{String.valueOf(id)});

        return (rs > 0);
    }

    //Cap nhat
    public static int update(Context context, user us) {
        DBHelper userDBHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = userDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Ultils.USER_NAME, us.getUserName());
        values.put(Ultils.USER_PASS, us.getPassword());
        values.put(Ultils.USER_EMAIL, us.getEmail());
        values.put(Ultils.USER_PHONE, us.getPhoneNumber());
        values.put(Ultils.USER_AVATAR, us.getAvatar());

        int rs = sqLiteDatabase.update(Ultils.TABLE_USER, values, Ultils.USER_ID + "=?", new String[]{String.valueOf(us.getUserID())});
        return rs;
    }
    public static user getUserByUsername(Context context, String username) {
        DBHelper userDBHelper = new DBHelper(context);
        SQLiteDatabase db = userDBHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from " + Ultils.TABLE_USER + " where " + Ultils.USER_NAME + "=?", new String[]{username});
        cs.moveToFirst();
        user item = null;
        if (!cs.isAfterLast()) {
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String password = cs.getString(2);
            String email = cs.getString(3);
            String phonenumber = cs.getString(4);
            int avarta = cs.getInt(5);
            item = new user(id, name, password, email, phonenumber, avarta);
        }
        cs.close();
        db.close();
        return item;
    }

}
