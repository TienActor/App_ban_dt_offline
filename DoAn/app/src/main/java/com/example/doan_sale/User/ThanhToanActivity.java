package com.example.doan_sale.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doan_sale.Product.ProductAdapter;
import com.example.doan_sale.R;
import com.example.doan_sale.ThankYouActivity;
import com.example.doan_sale.model.CreateOrder;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.model.Voucher;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.MainActivity;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity implements ThanhToanAdapter.ThanhToanCallBack {
    RecyclerView rvListCode;
    ArrayList<GioHang> lstPro;
   static TextView tongtienTT;
   EditText voucher;
   Button apply,confirm,btn_zalo;
   ImageButton back;
    ThanhToanAdapter productAdapter;
    EditText addre;
    DBHelper dbHelper = new DBHelper(ThanhToanActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        rvListCode = findViewById(R.id.rvList3);
         tongtienTT=(TextView) findViewById(R.id.payment_total_amount);
         voucher=(EditText) findViewById(R.id.payment_discount_code_input) ;
         apply=(Button)findViewById(R.id.payment_apply_coupon_button) ;
         confirm=(Button)findViewById(R.id.payment_confirm_button);
         back=(ImageButton) findViewById(R.id.backtt);
         btn_zalo=findViewById(R.id.payment_confirm_zalo);
         addre=findViewById(R.id.edt_Address);
         //zalo pay
        StrictMode.ThreadPolicy policy = new  StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);



         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent=new Intent(ThanhToanActivity.this,GioHangActivity.class);
                 startActivity(intent);
             }
         });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin phương thức thanh toán từ RadioGroup
//                String paymentMethod = getSelectedPaymentMethod();
                String username = getUsernameFromSharedPreferences();
                String address = addre.getText().toString().trim();
                if (address.isEmpty()) {
                    Toast.makeText(ThanhToanActivity.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    return;
                }
                long orderId = dbHelper.addOrderWithDetails(username, MainActivity.manggiohang, address);

                if (orderId != -1) {
                    // Thành công, thông báo cho người dùng và xóa giỏ hàng
                    Toast.makeText(ThanhToanActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                    MainActivity.manggiohang.clear();
                    productAdapter.notifyDataSetChanged();
                    EventUtil();
                    // Chuyển sang màn hình ThankYouActivity
                    Intent i = new Intent(ThanhToanActivity.this, ThankYouActivity.class);
                    startActivity(i);
                } else {
                    // Thất bại, thông báo lỗi
                    Toast.makeText(ThanhToanActivity.this, "Lỗi trong quá trình thanh toán", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_zalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getUsernameFromSharedPreferences();
                String address = addre.getText().toString().trim();
                if (address.isEmpty()) {
                    Toast.makeText(ThanhToanActivity.this, "Vui lòng nhập địa chỉ", Toast.LENGTH_SHORT).show();
                    return;
                }
                long orderId = dbHelper.addOrderWithDetails(username, MainActivity.manggiohang, address);
                if (orderId != -1) {
//                    // Thành công, thông báo cho người dùng và xóa giỏ hàng
//                    Toast.makeText(ThanhToanActivity.this, "Thanh toán thành ", Toast.LENGTH_SHORT).show();

                    productAdapter.notifyDataSetChanged();
                    EventUtil();
                    Zalorequest();
                    MainActivity.manggiohang.clear();
                    // Chuyển sang màn hình ThankYouActivity
//                    Intent i = new Intent(ThanhToanActivity.this, ThankYouActivity.class);
//                    startActivity(i);
                } else {
                    // Thất bại, thông báo lỗi
                    Toast.makeText(ThanhToanActivity.this, "Lỗi trong quá trình thanh toán", Toast.LENGTH_SHORT).show();
                }
            }
        });

        apply.setOnClickListener(v->applyVoucher());
        EventUtil();
        Intent intent = getIntent();
        ArrayList<GioHang> gioHangList = (ArrayList<GioHang>) intent.getSerializableExtra("giohang");
        // Hiển thị danh sách sản phẩm trên giao diện bằng cách sử dụng adapter
        productAdapter = new ThanhToanAdapter(this, gioHangList, this);
        rvListCode.setLayoutManager(new LinearLayoutManager(this));
        productAdapter.setProCallBack(this);
        rvListCode.setAdapter(productAdapter);

    }


    public void Zalorequest()
    {
        CreateOrder orderApi = new CreateOrder();
        EventUtil(); // Tính toán lại tổng tiền trước khi tạo order

        // Chuyển đổi giá trị từ TextView tongtienTT sang số để sử dụng
        String tongtienText = tongtienTT.getText().toString().replaceAll("[^\\d]", ""); // loại bỏ tất cả ký tự không phải số
        long tongtien = Long.parseLong(tongtienText); // chuyển đổi String sang long

        try {
            JSONObject data = orderApi.createOrder(String.valueOf(tongtien));
            Log.d("Amount", "Ban da du tien de thanh toan ");
//            lblZpTransToken.setVisibility(View.VISIBLE);
            String code = data.getString("return_code");
            Log.d("thong boa code", code);
//            Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

            if (code.equals("1")) {
               String token = data.getString("zp_trans_token");
                Log.d("thong boa code", token);



               ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
                   @Override
                   public void onPaymentSucceeded(String s, String s1, String s2) {
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               new AlertDialog.Builder(ThanhToanActivity.this)
                                       .setTitle("Payment Success")
                                       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                           @Override
                                           public void onClick(DialogInterface dialog, int which) {

                                           }
                                       })
                                       .setNegativeButton("Cancel", null).show();
                           }

                       });
                       Intent it = new Intent(ThanhToanActivity.this,ThankYouActivity.class);
                       startActivity(it);

                   }

                   @Override
                   public void onPaymentCanceled(String s, String s1) {
                       new AlertDialog.Builder(ThanhToanActivity.this)
                               .setTitle("User Cancel Payment")
                               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                   }
                               })
                               .setNegativeButton("Cancel", null).show();
                   }

                   @Override
                   public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                       new AlertDialog.Builder(ThanhToanActivity.this)
                               .setTitle("Payment Fail")
                               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {
                                   }
                               })
                               .setNegativeButton("Cancel", null).show();
                   }
               });
//                lblZpTransToken.setText("zptranstoken");

              //  txtToken.setText(data.getString("zp_trans_token"));
//                IsDone();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }


    public static void EventUtil()  {
        long tongtien=0;
        for (int i = 0; i< MainActivity.manggiohang.size(); i++){
            tongtien+=MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        tongtienTT.setText(decimalFormat.format(tongtien)+" Đ");
    }
    private void applyVoucher() {
        String voucherCode = voucher.getText().toString().trim();

        // Check if voucher code exists in database
        Voucher voucher = dbHelper.getVoucherByCode(voucherCode);
        if (voucher == null) {
            Toast.makeText(this, "Mã giảm giá không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check if voucher is applicable to any product in cart
        boolean isApplicable = false;
        int productId = voucher.getVoucherProductId();
        for ( GioHang gioHang : MainActivity.manggiohang) {
            if (gioHang.getIdsp() == productId) {
                isApplicable = true;
                break;
            }
        }
        if (!isApplicable) {
            Toast.makeText(this, "Mã giảm giá không áp dụng cho sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        // Apply voucher discount
        int discount = voucher.getDiscount();
        long tongtien = 0;
        boolean isDiscountAppliedAgain = false; // Biến flag để kiểm tra đã áp dụng mã giảm giá lần nữa

        for (GioHang gioHang : MainActivity.manggiohang) {
            if (gioHang.getIdsp() == productId) {
                if (!gioHang.isDiscountApplied()) {
                    long discountedPrice = gioHang.getGiasp() - discount;

                    if (discountedPrice < 0) {
                        discountedPrice = 0;  // Ensure the price doesn't go below 0
                    }

                    tongtien += discountedPrice;

                    gioHang.setGiasp(discountedPrice);
                    gioHang.setDiscountApplied(true); // Đánh dấu đã áp dụng mã giảm giá

                } else {
                    isDiscountAppliedAgain = true; // Đã áp dụng mã giảm giá lần nữa
                }
            } else {
                tongtien += gioHang.getGiasp();
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtienTT.setText(decimalFormat.format(tongtien) + " Đ");
        productAdapter.notifyDataSetChanged();
        EventUtil();

        if (isDiscountAppliedAgain) {
            Toast.makeText(this, "Mã giảm giá chỉ được sử dụng một lần.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Đã áp dụng mã giảm giá.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onItemLongClicked(GioHang gioHang) {
    }
    private String getUsernameFromSharedPreferences() {
        SharedPreferences preferences = getSharedPreferences("USER_INFO", MODE_PRIVATE);
        return preferences.getString("username", null); // null là giá trị mặc định nếu không tìm thấy
    }

}