package com.example.doan_sale.ui;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.doan_sale.Account.LoginValidation;
import com.example.doan_sale.Product.ProDataQuery;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Order;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.model.Voucher;
import com.example.doan_sale.model.user;
import com.example.doan_sale.Account.LoginActivity;
import com.example.doan_sale.Account.RegisterActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.internal.Util;

public class DBHelper extends SQLiteOpenHelper {
    private static final String NBTKSHOP = Ultils.DATABASE_NAME;
    private static final int DATABASE_VERSION = 26;

    public DBHelper(Context context) {
        super(context, NBTKSHOP,null,26);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_USER_TABLE = "CREATE TABLE " + Ultils.TABLE_USER+ "("
                + Ultils.USER_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.USER_NAME + " TEXT,"
                + Ultils.USER_PASS + " TEXT,"
                + Ultils.USER_EMAIL + " TEXT,"
                + Ultils.USER_PHONE + " TEXT,"
                + Ultils.USER_AVATAR + " TEXT"
                +")";
        String CREATE_ADMIN_TABLE = "CREATE TABLE " + Ultils.TABLE_ADMIN+ "("
                + Ultils.ADMIN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.ADMIN_NAME + " TEXT,"
                + Ultils.ADMIN_PASS + " TEXT,"
                + Ultils.ADMIN_AVATAR + " TEXT"
                +")";
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + Ultils.TABLE_CATEGORY+ "("
                + Ultils.CATEGORY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.CATEGORY_NAME+ " TEXT"
                +")";

        String CREATE_PRODUCT_TABLE = "CREATE TABLE " + Ultils.TABLE_PRODUCT + "("
                + Ultils.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.PRODUCT_IMAGE + " TEXT, "
                + Ultils.PRODUCT_NAME + " TEXT, "
                + Ultils.PRODUCT_PRICE + " TEXT, "
                + Ultils.PRODUCT_DESCRIPTION + " TEXT, "
                + Ultils.CATEGORY_ID + " INTEGER, "
                + "FOREIGN KEY(" + Ultils.CATEGORY_ID + ") REFERENCES " + Ultils.TABLE_CATEGORY + "(" + Ultils.CATEGORY_ID + ")"
                + ")";
        String CREATE_ORDER_TABLE = "CREATE TABLE " + Ultils.TABLE_ORDER + " ("
                + Ultils.ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.ORDER_DATE + " TEXT, "
                + Ultils.ORDER_STATUS + " TEXT, "
                + Ultils.ORDER_TOTAL + " REAL, "
                + Ultils.ORDER_ADDRESS + " TEXT, "
                + Ultils.USER_NAME + " TEXT, "
                + "FOREIGN KEY(" + Ultils.USER_NAME + ") REFERENCES " + Ultils.TABLE_USER + "(" + Ultils.USER_NAME + ")"
                + ")";



        String CREATE_ORDER_DETAIL_TABLE = "CREATE TABLE " + Ultils.TABLE_ORDER_DETAIL + "("
                + Ultils.ORDER_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.ORDER_ID + " INTEGER, "
                + Ultils.PRODUCT_ID + " INTEGER, "
                + Ultils.QUANTITY + " INTEGER, "
                + Ultils.PRICE + " REAL, "
                + "FOREIGN KEY(" + Ultils.ORDER_ID + ") REFERENCES " + Ultils.TABLE_ORDER + "(" + Ultils.ORDER_ID + "),"
                + "FOREIGN KEY(" + Ultils.PRODUCT_ID + ") REFERENCES " + Ultils.TABLE_PRODUCT + "(" + Ultils.PRODUCT_ID + ")"
                + ")";



        String CREATE_VOUCHER_TABLE = "CREATE TABLE " + Ultils.TABLE_VOUCHER + "("
                + Ultils.VOUCHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Ultils.VOUCHER_CODE + " TEXT, "
                + Ultils.VOUCHER_PRODUCT_ID + " INTEGER, "
                + Ultils.VOUCHER_DISCOUNT + " INTEGER, "
                + Ultils.VOUCHER_START_DATE + " INTEGER, "
                + Ultils.VOUCHER_END_DATE + " INTEGER"
                + ");";
        // Execute table creation statements
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ADMIN_TABLE);
        sqLiteDatabase.execSQL(CREATE_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_PRODUCT_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDER_DETAIL_TABLE);
        sqLiteDatabase.execSQL(CREATE_VOUCHER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDER_TABLE);


        // Execute data insertion statements
        String insertUserData = "INSERT INTO " + Ultils.TABLE_USER + "("
                + Ultils.USER_NAME + ", "
                + Ultils.USER_PASS + ", "
                + Ultils.USER_EMAIL + ", "
                + Ultils.USER_PHONE + ", "
                + Ultils.USER_AVATAR + ") VALUES "
                + "('test', '0123456', 'test@gmail.com', '0123456789', 'meo1.png') ";
        String insertCategoryData = "INSERT INTO " + Ultils.TABLE_CATEGORY + "(" + Ultils.CATEGORY_NAME + ") VALUES "
                + "('Điện thoại'), "
                + "('Tai nghe'), "
                + "('Sạc điện thoại')";
        String insertProductData = "INSERT INTO " + Ultils.TABLE_PRODUCT + "(" + Ultils.PRODUCT_IMAGE + ", " + Ultils.PRODUCT_NAME + ", " + Ultils.PRODUCT_PRICE + ", " + Ultils.PRODUCT_DESCRIPTION + ", " + Ultils.CATEGORY_ID + ") VALUES "
                + "('h1.png', 'iPhone 14 Pro Max', '24000000', 'Màn hình OLED kích thước 6,7 inch với độ phân giải cao. Vi xử lý A16 hoặc A17 hoạt động trên nền tảng iOS 16 hoặc 17 mới nhất. RAM 8GB hoặc 12GB và bộ nhớ trong lên đến 1TB.', 1), "
                + "('h2.png', 'Oppo A92', '5000000', 'Màn hình 6,5 inch độ phân giải Full HD+ và tần số quét 90Hz. Bộ vi xử lý Qualcomm Snapdragon 665. RAM 8GB và bộ nhớ trong 128GB. Pin dung lượng lớn 5000mAh hỗ trợ sạc nhanh 18W.', 1), "
                + "('h3.png', 'Samsung S22 Ultra', '21000000', 'Màn hình Dynamic AMOLED 2X kích thước 6.81 inch với độ phân giải Quad HD+ (1440 x 3200 pixels) và tốc độ làm tươi 120Hz hoặc 144Hz. Bộ vi xử lý Exynos 2200 hoặc Snapdragon 895. RAM từ 12GB đến 16GB và bộ nhớ trong lên đến 1TB. Pin dung lượng lớn từ 4500mAh đến 5000mAh, hỗ trợ sạc nhanh và sạc không dây.', 1), "
                + "('h4.png', 'Samsung S23+', '16000000', 'Màn hình với kích thước từ 6,5 đến 6,8 inch, độ phân giải Quad HD+ hoặc 4K. Chipset Snapdragon 895 hoặc Exynos 2200. Bộ nhớ RAM từ 8GB đến 16GB. Bộ nhớ trong từ 128GB đến 512GB, hỗ trợ thẻ nhớ microSD. Pin có dung lượng từ 4,000 đến 5,000mAh, hỗ trợ sạc nhanh và sạc không dây.', 1), "
                + "('h5.png', 'AirPod Pro', '3000000', 'Chế độ chủ động chống ồn hoàn toàn mới. Thiết kế in-ear với bộ cánh có thể tháo rời. Pin sạc có thể sử dụng trong khoảng thời gian lên đến 4,5 giờ và thêm 24 giờ với hộp sạc. Khả năng chống nước và mồ hôi theo tiêu chuẩn IPX4.', 2), "
                + "('h6.png', 'Tai nghe Hoco M1', '1000000', 'Loa động cơ độ lớn 14.2mm. Đáp ứng tần số: 20Hz-20KHz. Độ nhạy: 102dB ± 3dB. Trở kháng: 32Ω.', 2), "
                + "('h7.png', 'Tai nghe Heinler HS-P47', '7000000', 'Dải tần số đáp ứng từ 20Hz đến 20kHz. Pin dung lượng 2000mAh với thời gian chơi lên tới 8 giờ và thời gian chờ lên đến 200 giờ. Chế độ chống ồn và điều khiển cảm ứng trên tai nghe. Tích hợp microphone cho cuộc gọi và truy cập giọng nói thông minh.', 2), "
                + "('h8.png', 'MagSafe Ultra thin Q9 Huawei', '3000000', 'Dung lượng pin: 5,000mAh. Điện áp đầu vào: 5V-1A (max). Điện áp đầu ra: 5V-2.4A (max). Hỗ trợ sạc không dây MagSafe của Apple. Kích thước: 6.5cm x 6.5cm x 1.5cm.', 3), "
                + "('h9.png', 'Apple MagSafe', '8000000', 'Điện áp vào: 9V (max) hoặc 12V (max). Điện áp ra: 5V (min) hoặc 15V (max). Công suất đầu vào: 20W (max). Công suất đầu ra: 15W (max). Kích thước: 9.78mm x 86.4mm x 86.4mm.', 3), "
                + "('h10.png', 'Sạc E.VALU', '4000000', 'Dung lượng từ 10,000mAh đến 20,000mAh. Có 2 đầu ra USB với dòng điện đạt từ 1A đến 2.4A để sạc các thiết bị di động. Được trang bị tính năng sạc nhanh hoặc sạc không dây cho các thiết bị tương thích. Thiết kế nhỏ gọn, dễ mang theo khi di chuyển', 3)";
        sqLiteDatabase.execSQL(insertUserData);
        sqLiteDatabase.execSQL(insertCategoryData);
        sqLiteDatabase.execSQL(insertProductData);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_USER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_ADMIN);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_CATEGORY);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_PRODUCT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_ORDER_DETAIL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_VOUCHER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Ultils.TABLE_ORDER);
        onCreate(sqLiteDatabase);
    }
    //them moi 1 user(register)
    public boolean addOne(user newUser) {
        String username = newUser.getUserName();
        String pass = newUser.getPassword();
        String mail = newUser.getEmail();
        String phone = newUser.getPhoneNumber();
        if (username.length() > 0 && pass.length() > 0 && mail.length() > 0 && phone.length() > 0) {
            boolean check = CheckCondition(newUser);
            if(!check) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();
                cv.put(Ultils.USER_NAME, newUser.getUserName());
                cv.put(Ultils.USER_PASS, newUser.getPassword());
                cv.put(Ultils.USER_EMAIL, newUser.getEmail());
                cv.put(Ultils.USER_PHONE, newUser.getPhoneNumber());

                long insert = db.insert(Ultils.TABLE_USER, null, cv);
                db.close();
                return true;
            }
        }
        return false;
    }
    public boolean CheckCondition(user newUser) {
        SQLiteDatabase db = this.getReadableDatabase();
        String mail_convert = "'" + newUser.getEmail() + "'";
        String sdt_convert = "'" + newUser.getPhoneNumber() + "'";

        Cursor cursor = db.rawQuery("SELECT * FROM " + Ultils.TABLE_USER
                + " WHERE " + Ultils.USER_PHONE + " = " + sdt_convert
                + " OR " + Ultils.USER_EMAIL + " = " + mail_convert, null);

        if(cursor.moveToFirst()) {
            return true;
        }
        return false;
    }



    // Check login information
    public LoginValidation CheckCustomer(String name, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name_convert = "'" + name + "'";
        Cursor cursor = db.rawQuery("SELECT * FROM " + Ultils.TABLE_USER + " WHERE " + Ultils.USER_NAME + "=" + name_convert, null);
        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(Ultils.USER_ID);
            int nameIndex = cursor.getColumnIndex(Ultils.USER_NAME);
            int passIndex = cursor.getColumnIndex(Ultils.USER_PASS);
            int mailIndex = cursor.getColumnIndex(Ultils.USER_EMAIL);
            int phoneIndex = cursor.getColumnIndex(Ultils.USER_PHONE);
            int avatarIndex = cursor.getColumnIndex(Ultils.USER_AVATAR);

            int userID = cursor.getInt(idIndex);
            String userName = cursor.getString(nameIndex);
            String userPass = cursor.getString(passIndex);
            String userMail = cursor.getString(mailIndex);
            String userPhone = cursor.getString(phoneIndex);
            int userAvatar = cursor.getInt(avatarIndex);

            if(pass.equals(userPass)) {
                user newUser = new user(userID, userName, userPass, userMail, userPhone, userAvatar);
                return new LoginValidation(newUser, true);
            }
        }
        cursor.close();
        db.close();
        return new LoginValidation(false);
    }

    public List<Product> getProductsByCategoryId(int categoryId) {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Ultils.TABLE_PRODUCT + " WHERE " + Ultils.CATEGORY_ID + " = ?", new String[] { String.valueOf(categoryId) });


        if (cursor.moveToFirst()) {
            do {
                int productId = cursor.getInt(0);
                String productImage = cursor.getString(1);
                String productName = cursor.getString(2);
                int productPrice = cursor.getInt(3);
                String productDescription = cursor.getString(4);
                int productCategoryId = cursor.getInt(5);
                Product product = new Product(productId, productImage, productName, productPrice, productDescription, productCategoryId);
                productList.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return productList;
    }
    public Product getProductById(int productId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Ultils.TABLE_PRODUCT + " WHERE " + Ultils.PRODUCT_ID + " = " + productId, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        String image = cursor.getString(1);
        String name = cursor.getString(2);
        int price = cursor.getInt(3);
        String description = cursor.getString(4);
        cursor.close();
        db.close();
        return new Product(0, image, name, price, description);
    }
    public Voucher getVoucherByCode(String voucherCode) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db == null) {
            return null;
        }
        Cursor cursor = db.query(
                Ultils.TABLE_VOUCHER,
                new String[]{Ultils.VOUCHER_ID, Ultils.VOUCHER_CODE, Ultils.VOUCHER_PRODUCT_ID, Ultils.VOUCHER_DISCOUNT, Ultils.VOUCHER_START_DATE, Ultils.VOUCHER_END_DATE},
                Ultils.VOUCHER_CODE + " = ?",
                new String[]{voucherCode},
                null,
                null,
                null);

        Voucher voucher = null;
        if (cursor != null && cursor.moveToFirst()) {
            voucher = new Voucher(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getLong(4),
                    cursor.getLong(5)
            );
            cursor.close();
        }
        db.close();

        return voucher;
    }

    public List<Product> getHotProducts() {
        List<Product> productList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + Ultils.TABLE_PRODUCT + " ORDER BY " + Ultils.PRODUCT_PRICE + " DESC LIMIT 4";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int proIdIndex = cursor.getColumnIndex(Ultils.PRODUCT_ID);
                int proImageIndex = cursor.getColumnIndex(Ultils.PRODUCT_IMAGE);
                int proNameIndex = cursor.getColumnIndex(Ultils.PRODUCT_NAME);
                int proPriceIndex = cursor.getColumnIndex(Ultils.PRODUCT_PRICE);
                int proDesIndex = cursor.getColumnIndex(Ultils.PRODUCT_DESCRIPTION);
                int categoryIdIndex = cursor.getColumnIndex(Ultils.CATEGORY_ID);

                if (proIdIndex != -1 && proImageIndex != -1 && proNameIndex != -1 && proPriceIndex != -1 && proDesIndex != -1 && categoryIdIndex != -1) {
                    Product product = new Product();
                    product.setProID(cursor.getInt(proIdIndex));
                    product.setProImage(cursor.getString(proImageIndex));
                    product.setProName(cursor.getString(proNameIndex));
                    product.setProPrice(cursor.getInt(proPriceIndex));
                    product.setProDes(cursor.getString(proDesIndex));
                    product.setCategoryID(cursor.getInt(categoryIdIndex));


                    productList.add(product);
                }
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return productList;
    }
        // Phương thức để thêm đơn hàng mới và chi tiết đơn hàng vào cơ sở dữ liệu
        public long addOrderWithDetails(String username, ArrayList<GioHang> cartItems,String Address) {
            SQLiteDatabase db = this.getWritableDatabase();

            // Thêm dữ liệu vào bảng order
            ContentValues orderValues = new ContentValues();
            orderValues.put(Ultils.ORDER_DATE, getCurrentDateTime());
            orderValues.put(Ultils.ORDER_STATUS, "Thành công");
            orderValues.put(Ultils.ORDER_ADDRESS,Address );
            orderValues.put(Ultils.ORDER_TOTAL, calculateTotal(cartItems));
            orderValues.put(Ultils.USER_NAME, username);

            long orderId = db.insert(Ultils.TABLE_ORDER, null, orderValues);

            // Log thông tin đơn hàng sau khi thêm
            if (orderId != -1) {
                logOrderInfo(db, orderId);  // Gọi phương thức logOrderInfo

                for (GioHang item : cartItems) {
                    ContentValues detailValues = new ContentValues();
                    detailValues.put(Ultils.ORDER_ID, orderId);
                    detailValues.put(Ultils.PRODUCT_ID, item.getIdsp());
                    detailValues.put(Ultils.QUANTITY, item.getSoluong());
                    detailValues.put(Ultils.PRICE, item.getGiasp());

                    long detailId = db.insert(Ultils.TABLE_ORDER_DETAIL, null, detailValues);

                    // Log thông tin chi tiết đơn hàng sau khi thêm
                    if (detailId != -1) {
                        logOrderDetailInfo(db, orderId);  // Gọi phương thức logOrderDetailInfo
                    }
                }
            }

            db.close();
            return orderId; // Trả về ID của đơn hàng mới được thêm hoặc -1 nếu có lỗi
        }


    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    private double calculateTotal(ArrayList<GioHang> cartItems) {
        double total = 0;
        for (GioHang item : cartItems) {
            total += item.getGiasp() * item.getSoluong();
        }
        return total;
    }
    private void logOrderInfo(SQLiteDatabase db, long orderId) {
        Cursor cursor = db.query(Ultils.TABLE_ORDER, null, Ultils.ORDER_ID + " = ?",
                new String[] { String.valueOf(orderId) }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(Ultils.ORDER_ID);
            int dateIndex = cursor.getColumnIndex(Ultils.ORDER_DATE);
            int statusIndex = cursor.getColumnIndex(Ultils.ORDER_STATUS);
            int totalIndex = cursor.getColumnIndex(Ultils.ORDER_TOTAL);
            int userNameIndex = cursor.getColumnIndex(Ultils.USER_NAME); // Sử dụng USER_NAME thay vì USER_ID
//            int paymentMethodIndex = cursor.getColumnIndex(Ultils.PAYMENT_METHOD);

            // Kiểm tra xem các chỉ số có hợp lệ hay không
            if (idIndex != -1 && dateIndex != -1 && statusIndex != -1 &&
                    totalIndex != -1 && userNameIndex != -1 ) {
                String orderInfo = "Order ID: " + cursor.getInt(idIndex) +
                        ", Date: " + cursor.getString(dateIndex) +
                        ", Status: " + cursor.getString(statusIndex) +
                        ", Total: " + cursor.getDouble(totalIndex) +
                        ", Username: " + cursor.getString(userNameIndex)  // Hiển thị Username
                     ;
                Log.d("DBLog", orderInfo);
            }
            cursor.close();
        }
    }


    private void logOrderDetailInfo(SQLiteDatabase db, long orderId) {
        Cursor cursor = db.query(Ultils.TABLE_ORDER_DETAIL, null, Ultils.ORDER_ID + " = ?",
                new String[] { String.valueOf(orderId) }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int detailIdIndex = cursor.getColumnIndex(Ultils.ORDER_DETAIL_ID);
            int productIdIndex = cursor.getColumnIndex(Ultils.PRODUCT_ID);
            int quantityIndex = cursor.getColumnIndex(Ultils.QUANTITY);
            int priceIndex = cursor.getColumnIndex(Ultils.PRICE);

            // Kiểm tra xem các chỉ số có hợp lệ hay không
            if (detailIdIndex != -1 && productIdIndex != -1 &&
                    quantityIndex != -1 && priceIndex != -1) {
                do {
                    String detailInfo = "Order Detail ID: " + cursor.getInt(detailIdIndex) +
                            ", Order ID: " + orderId +
                            ", Product ID: " + cursor.getInt(productIdIndex) +
                            ", Quantity: " + cursor.getInt(quantityIndex) +
                            ", Price: " + cursor.getDouble(priceIndex);
                    Log.d("DBLog", detailInfo);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
    }

    public ArrayList<Product> searchProducts(String query) {
        ArrayList<Product> productList = new ArrayList<>();
        // Get readable database
        SQLiteDatabase db = this.getReadableDatabase();

        // SQL query to perform the search
        String searchQuery = "SELECT * FROM " + Ultils.TABLE_PRODUCT + " WHERE " + Ultils.PRODUCT_NAME + " LIKE ?";
        Cursor cursor = db.rawQuery(searchQuery, new String[]{"%" + query + "%"});

        // Log the query
        Log.d("Search Query", "Query: " + searchQuery);

        // Iterate over the cursor to create Product objects and add them to the list
        if (cursor.moveToFirst()) {
            do {
                int idColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_ID);
                int id = cursor.getInt(idColumnIndex);

                int imageColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_IMAGE);
                String image = cursor.getString(imageColumnIndex);

                int nameColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_NAME);
                String name = cursor.getString(nameColumnIndex);

                int priceColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_PRICE);
                int price = cursor.getInt(priceColumnIndex);

                int descriptionColumnIndex = cursor.getColumnIndex(Ultils.PRODUCT_DESCRIPTION);
                String description = cursor.getString(descriptionColumnIndex);

                int categoryIdColumnIndex = cursor.getColumnIndex(Ultils.CATEGORY_ID);
                int categoryId = cursor.getInt(categoryIdColumnIndex);

                // Log the retrieved data
                Log.d("Search Result", "ID: " + id + ", Image: " + image + ", Name: " + name + ", Price: " + price + ", Description: " + description + ", Category ID: " + categoryId);

                // Assuming you have a Product constructor like this
                Product product = new Product(id, image, name, price, description, categoryId);
                productList.add(product);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return productList;
    }


    // Phương thức để lấy danh sách đơn hàng dựa trên tên người dùng
    public List<Order> getOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                Ultils.TABLE_ORDER,
                new String[] {Ultils.ORDER_ID, Ultils.ORDER_DATE, Ultils.ORDER_TOTAL},
                Ultils.USER_NAME + "=?",
                new String[]{username},
                null, null, null, null);

        if (cursor.moveToFirst()) {
            int orderIdIndex = cursor.getColumnIndex(Ultils.ORDER_ID);
            int orderDateIndex = cursor.getColumnIndex(Ultils.ORDER_DATE);
            int orderTotalIndex = cursor.getColumnIndex(Ultils.ORDER_TOTAL);
            int orderAddress =cursor.getColumnIndex(Ultils.ORDER_ADDRESS);
            // Make sure all indices are valid
            if (orderIdIndex != -1 && orderDateIndex != -1 && orderTotalIndex != -1) {
                do {
                    Order order = new Order(
                            cursor.getInt(orderIdIndex),
                            cursor.getString(orderDateIndex),
                            cursor.getString(orderAddress),
                            cursor.getDouble(orderTotalIndex)

                    );
                    orders.add(order);
                } while (cursor.moveToNext());
            } else {
                // Handle the error or throw an exception
                Log.e("DBHelper", "Không tồn tại");
            }
        }
        cursor.close();
        db.close();
        return orders;
    }

}

