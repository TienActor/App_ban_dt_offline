package com.example.doan_sale.Product;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.Ultils;
import java.util.ArrayList;

public class ProDataQuery {
    public static long insert(Context context, Product product){
        DBHelper proDBHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = proDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Ultils.PRODUCT_IMAGE,product.getProImage());
        values.put(Ultils.PRODUCT_NAME,product.getProName());
        values.put(Ultils.PRODUCT_PRICE,product.getProPrice());
        values.put(Ultils.PRODUCT_DESCRIPTION,product.getProDes());
        values.put(Ultils.CATEGORY_ID,product.getCategoryID());
        long rs = sqLiteDatabase.insert(Ultils.TABLE_PRODUCT,null,values);
        return (rs);
    }

    //lay danh sach
    public static ArrayList<Product> getALL(Context context){
        ArrayList<Product> lsPro = new ArrayList<>();
        DBHelper proDBHelper = new DBHelper(context);
        SQLiteDatabase db = proDBHelper.getReadableDatabase();
        Cursor cs = db.rawQuery("Select * from "+Ultils.TABLE_PRODUCT,null);
        cs.moveToFirst();
        while (!cs.isAfterLast()){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String avatar = cs.getString(2);
            Integer price = cs.getInt(3);
            String des=cs.getString(4);
            int categoryID=cs.getInt(5);
            Product item = new Product(id,name,avatar,price,des,categoryID);
            lsPro.add(item);
            cs.moveToNext();
        }
        cs.close();
        db.close();
        return lsPro;
    }
    public static boolean delete (Context context, int id){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        int rs = sqLiteDatabase.delete(Ultils.TABLE_PRODUCT,Ultils.PRODUCT_ID+"=?", new String[]{String.valueOf(id)});
        return (rs>0);
    }
    //cap nhat
    public static int update(Context context, Product pro){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Ultils.PRODUCT_IMAGE,pro.getProImage());
        values.put(Ultils.PRODUCT_NAME,pro.getProName());
        values.put(Ultils.PRODUCT_PRICE,pro.getProPrice());
        values.put(Ultils.PRODUCT_DESCRIPTION,pro.getProDes());
        values.put(Ultils.CATEGORY_ID,pro.getCategoryID());
        int rs = sqLiteDatabase.update(Ultils.TABLE_PRODUCT,values,Ultils.PRODUCT_ID+"=?",new String[]{String.valueOf(pro.getProID())});
        return (rs);
    }
}
