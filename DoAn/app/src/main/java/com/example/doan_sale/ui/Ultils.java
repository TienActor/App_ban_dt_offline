package com.example.doan_sale.ui;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
public class Ultils {
    //Biến
    public static final String DATABASE_NAME="shop";
    public static final String ADMIN_ID="id";
    public static final String ADMIN_PASS="pass";
    public static final String ADMIN_NAME="name";
    public static final String ADMIN_AVATAR="avatar";
    public static final String USER_ID="id";
    public static final String USER_NAME="name";
    public static final String USER_PASS="pass";
    public static final String USER_EMAIL="email";
    public static final String USER_PHONE="phone";
    public static final String USER_AVATAR="avatar";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_NAME="name";
    public static final String PRODUCT_PRICE="price";
    public static final String PRODUCT_IMAGE="image";
    public static final String PRODUCT_DESCRIPTION="description";
    public static final String CATEGORY_ID="cateid";
    public static final String CATEGORY_NAME="name";
    public static final String ORDER_ADDRESS="address";
    public static final String ORDER_ID="orderid";
    public static final String QUANTITY="quantity";
    public static final String PRICE="price";
    public static final String DISCOUNT="discount";
    public static final String VOUCHER_ID ="id_voucher";
    public static final String VOUCHER_CODE ="code";
    public static final String VOUCHER_PRODUCT_ID = "id_vpro";
    public static final String VOUCHER_DISCOUNT = "discount";
    public static final String VOUCHER_START_DATE = "start_date";
    public static final String VOUCHER_END_DATE = "end_date";
    //TABLE
    public static final String TABLE_USER="user";
    public static final String TABLE_ADMIN="admin";
    public static final String TABLE_CATEGORY="category";
    public static final String TABLE_PRODUCT="product";
    public static final String TABLE_VOUCHER = "voucher";
    public static final String PAYMENT_METHOD = "payment_method";
    public static final String TABLE_ORDER = "orders"; // Thay vì "order"
    public static final String ORDER_DATE = "date";
    public static final String ORDER_STATUS = "status";
    public static final String ORDER_TOTAL = "total";
    public static final String TABLE_ORDER_DETAIL = "order_detail";
    public static final String ORDER_DETAIL_ID = "order_detail_id";
    public static Bitmap convertToBitmapFromAssets(Context context, String nameImage){
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("images/"+nameImage);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
