package com.example.doan_sale.User;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.doan_sale.Account.LoginActivity;
import com.example.doan_sale.R;
import com.example.doan_sale.ThankYouActivity;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Voucher;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.MainActivity;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.HashMap;

public class GioHangActivity extends AppCompatActivity implements Serializable {
    Toolbar Toolbargiohang;
    TextView txtThongbao;
    static TextView txtTongtien;
    ListView listViewgiohang;
    Button btnthanhtoan, btntieptucmua, btnapdung;
    EditText edvouchercode;
    GiohangAdapter giohangAdapter;
    DBHelper dbHelper = new DBHelper(GioHangActivity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        ActionTollbar();
        CheckData();
        EventUtil();
        CacthOnItem();
        EventButton();
    }
    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckData();
                Intent intent=new Intent(GioHangActivity.this, MainActivity.class);
                giohangAdapter.notifyDataSetChanged();
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MainActivity.manggiohang.size()<=0){
                    Toast.makeText(GioHangActivity.this,"Giỏ hàng của bạn không có sản phẩm để thanh toán",Toast.LENGTH_LONG).show();
                }else{
                    if (isUserLoggedIn()) {
                        Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                        intent.putExtra("giohang", MainActivity.manggiohang);
                        startActivity(intent);
                    } else {
                        Toast.makeText(GioHangActivity.this, "Bạn cần đăng nhập để tiến hành thanh toán", Toast.LENGTH_LONG).show();
                        Intent loginIntent = new Intent(GioHangActivity.this, LoginActivity.class);
                        startActivity(loginIntent);
                    }
                }
            }
        });

    }
    private void CacthOnItem() {
        listViewgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.manggiohang.size()<=0){
                            txtThongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.manggiohang.remove(position);
                            giohangAdapter.notifyDataSetChanged();
                            EventUtil();
                            if(MainActivity.manggiohang.size()<=0) {
                                txtThongbao.setVisibility(View.VISIBLE);
                            }else{
                                txtThongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();
                                EventUtil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }
    public static void EventUtil()  {
        long tongtien=0;
        for (int i=0;i<MainActivity.manggiohang.size();i++){
            tongtien+=MainActivity.manggiohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien)+" Đ");
    }
    private void CheckData() {
        if(MainActivity.manggiohang.size()<=0)
        {
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            listViewgiohang.setVisibility(View.INVISIBLE);
        }else{
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            listViewgiohang.setVisibility(View.VISIBLE);
        }
    }

    private void applyVoucher() {
        String voucherCode = edvouchercode.getText().toString().trim();

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
        HashMap<Integer, Boolean> productIds = new HashMap<>(); // to avoid counting a product multiple times
        for (GioHang gioHang : MainActivity.manggiohang) {
            if (gioHang.getIdsp() == productId && !productIds.containsKey(productId)) {
                tongtien += gioHang.getGiasp() - discount;
                gioHang.setGiasp(gioHang.getGiasp() - discount); // apply discount to product
                productIds.put(productId, true);
            } else {
                tongtien += gioHang.getGiasp();
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtTongtien.setText(decimalFormat.format(tongtien) + " Đ");
        giohangAdapter.notifyDataSetChanged();
        EventUtil();
        Toast.makeText(this, "Đã áp dụng mã giảm giá", Toast.LENGTH_SHORT).show();
    }

    private void ActionTollbar() {
        setSupportActionBar(Toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(GioHangActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
    }
    private void Anhxa() {
        txtThongbao=(TextView) findViewById(R.id.textviewthongbao);
        txtTongtien=(TextView) findViewById(R.id.textviewtongtien);
        Toolbargiohang=(Toolbar) findViewById(R.id.toolbargiohang);
        listViewgiohang=(ListView) findViewById(R.id.listviewgiohang);
        btnthanhtoan=(Button) findViewById(R.id.btnthanhtoangiohang);
        btntieptucmua=(Button) findViewById(R.id.btntieptucmuahang);
        giohangAdapter=new GiohangAdapter(GioHangActivity.this,MainActivity.manggiohang);
        listViewgiohang.setAdapter(giohangAdapter);
    }
    private boolean isUserLoggedIn() {
        // Giả sử chúng ta lưu thông tin đăng nhập trong SharedPreferences
        SharedPreferences preferences = getSharedPreferences("USER_INFO", MODE_PRIVATE);
        // Kiểm tra có username không, nếu có nghĩa là đã đăng nhập
        return preferences.contains("username");
    }

}
