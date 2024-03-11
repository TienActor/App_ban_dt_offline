package com.example.doan_sale.Admin;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.example.doan_sale.Account.LoginActivity;
import com.example.doan_sale.Product.ProductAdapter;
import com.example.doan_sale.Product.Product_Activity;
import com.example.doan_sale.R;
import com.example.doan_sale.fragment.DSSPFragment;
import com.example.doan_sale.fragment.HomeFragment;
import com.example.doan_sale.fragment.InfoFragment;
import com.example.doan_sale.fragment.SettingFragment;
import com.example.doan_sale.model.Product;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button btn_khachHang = (Button) findViewById(R.id.btn_KhachHang);
        Button btn_sanPham = (Button) findViewById(R.id.btn_Sanpham);
        Button btn_voucher = (Button) findViewById(R.id.btn_Voucher);
        Button LogOut=(Button) findViewById(R.id.adminThoat);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_KhachHang:
                        startActivity(new Intent(getApplicationContext(), Admin_User_Activity.class));
                        break;
                    case R.id.btn_Sanpham:
                        startActivity(new Intent(getApplicationContext(), Product_Activity.class));
                        break;
                    case R.id.btn_Voucher:
                        startActivity(new Intent(getApplicationContext(), AddVoucherActivity.class));
                        break;
                    case R.id.adminThoat:r:
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        break;
                }
            }
        };
        btn_khachHang.setOnClickListener(clickListener);
        btn_sanPham.setOnClickListener(clickListener);
        btn_voucher.setOnClickListener(clickListener);
        LogOut.setOnClickListener(clickListener);
    }
}
