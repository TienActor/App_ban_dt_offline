package com.example.doan_sale.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import com.example.doan_sale.Account.LoginActivity;
import com.example.doan_sale.LSDH.LSDHFragment;
import com.example.doan_sale.R;
import com.example.doan_sale.User.GioHangActivity;
import com.example.doan_sale.fragment.DSSPFragment;
import com.example.doan_sale.fragment.HomeFragment;
import com.example.doan_sale.fragment.InfoFragment;
import com.example.doan_sale.fragment.LogOutFragment;
import com.example.doan_sale.fragment.SettingFragment;
import com.example.doan_sale.fragment.VoucherFragment;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Product;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements MainAdapter.MainCallBack {
Button backl;
    MainAdapter mainAdapter;
ArrayList<Product> lstPro;
    public static ArrayList<GioHang> manggiohang;
    public static ArrayList<Product> mangyeuthich;
    private DrawerLayout drawerLayout;
    DBHelper dbHelper = new DBHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout= findViewById(R.id.drawer_layout);
        loadFragment(new HomeFragment());
        backl=findViewById(R.id.BShopcart);

        backl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GioHangActivity.class);
                startActivity(intent);
            }
        });
        if(manggiohang!=null){

        }else{
            manggiohang=new ArrayList<>();
        }
        if(mangyeuthich!=null){

        }else{
            mangyeuthich=new ArrayList<>();
        }
        // khởi tạo menu
        initMenu();
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Cập nhật menu ngay lúc khởi tạo activity
        updateMenu(navigationView);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);}
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemClick(int id) {
    }
    void initMenu()
    {
        Toolbar toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // Add a drawer listener to the ActionBarDrawerToggle object
        drawerLayout.addDrawerListener(new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                updateMenu(navigationView);
            }
        });
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fmNew;
                switch (item.getItemId())
                {
                    case R.id.nav_home:
                        fmNew = new HomeFragment();
                        loadFragment(fmNew);
                        return  true;
                    case R.id.nav_mnaccount:
                        fmNew = new InfoFragment();
                        loadFragment(fmNew);
                        return  true;
                    case R.id.nav_dssp:
                        fmNew = new DSSPFragment();
                        loadFragment(fmNew);
                        return  true;
                    case R.id.nav_yeuthich:
                        fmNew = new SettingFragment();
                        loadFragment(fmNew);
                        return  true;
                    case R.id.nav_logout:
                        fmNew = new LogOutFragment();
                        loadFragment(fmNew);
                        return  true;
                    case R.id.nav_lsdh:
                        fmNew = new LSDHFragment();
                        loadFragment(fmNew);
                        return  true;
                    case R.id.nav_login: // Assuming that you have a login menu item
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawer(GravityCompat.START); // Close the drawer
                        return true;
                    case R.id.nav_voucher:
                        fmNew = new VoucherFragment();
                        loadFragment(fmNew);
                        return true;
                }
                return true;
            }

        });
    }

    private boolean isLoggedIn() {
        invalidateOptionsMenu();
        SharedPreferences preferences = getSharedPreferences("USER_INFO", MODE_PRIVATE);
        return preferences.contains("username"); // or some other user identifier
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // As this is called every time the menu is displayed, it allows you to update the menu every time it is opened.
        NavigationView navigationView = findViewById(R.id.nav_view);
        updateMenu(navigationView); // Update the visibility of menu items.
        return super.onPrepareOptionsMenu(menu);
    }
    private void updateMenu(NavigationView navigationView) {
        Menu menu = navigationView.getMenu();
        MenuItem logoutItem = menu.findItem(R.id.nav_logout);
        MenuItem loginItem = menu.findItem(R.id.nav_login);
        MenuItem InfoItem = menu.findItem(R.id.nav_mnaccount);
        MenuItem lsdhItem = menu.findItem(R.id.nav_lsdh);
        MenuItem voucher = menu.findItem(R.id.nav_voucher);
        if (isLoggedIn()) {
            logoutItem.setVisible(true);
            loginItem.setVisible(false);
            InfoItem.setVisible(true);
            lsdhItem.setVisible(true);
            voucher.setVisible(true);
        } else {
            logoutItem.setVisible(false);
            loginItem.setVisible(true);
            InfoItem.setVisible(false);
            lsdhItem.setVisible(false);
            voucher.setVisible(false);
        }
    }
    void loadFragment(Fragment fmNew)
    {
        FragmentTransaction fmTran= getSupportFragmentManager().beginTransaction();
        fmTran.replace(R.id.user_fragment, fmNew);
        fmTran.addToBackStack(null);
        fmTran.commit();
        // thu nhỏ drawer
        drawerLayout.closeDrawer(GravityCompat.START);
    }

}
