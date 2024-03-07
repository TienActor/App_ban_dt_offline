package com.example.doan_sale.Product;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.doan_sale.R;
import com.example.doan_sale.User.GioHangActivity;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.MainActivity;
import com.example.doan_sale.ui.MainAdapter;
import com.example.doan_sale.ui.Ultils;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPhamActivity extends AppCompatActivity implements MainAdapter.MainCallBack {
    ImageView imgchitietsp;
    TextView txttensp, txtgiasp, txtmota;
    Spinner spinner;
    Button btnthemgohang;
    Toolbar toolbarchitiet;
    int Id=0;
    String Tenchitiet="";
    private boolean isProductAdded = false;
    int Giachitiet=0;
    String Hinhanhchitiet="";
    String Motachitiet="";
    int Idsanpham=0;
    ArrayList<Product> lstPro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menugiohang,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void EventButton() {
        btnthemgohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                boolean exists = false;
                for (int i = 0; i < MainActivity.manggiohang.size(); i++) {
                    if (MainActivity.manggiohang.get(i).getIdsp() == Id) {
                        // Tính toán số lượng mới dựa trên số lượng hiện tại và số lượng bạn muốn thêm.
                        int newQuantity = MainActivity.manggiohang.get(i).getSoluong() + sl;

                        // Kiểm tra nếu số lượng mới vượt quá 10, chỉ đặt nó thành 10.
                        if (newQuantity > 10) {
                            MainActivity.manggiohang.get(i).setSoluong(10);
                        } else {
                            MainActivity.manggiohang.get(i).setSoluong(newQuantity);
                        }

                        // Cập nhật giá dựa trên số lượng mới.
                        int newPrice = MainActivity.manggiohang.get(i).getSoluong() * Giachitiet;
                        MainActivity.manggiohang.get(i).setGiasp(newPrice);

                        exists = true;
                        break; // Thêm break để thoát khỏi vòng lặp sau khi cập nhật
                    }
                }

                if (!exists) {
                    int Giamoi = sl * Giachitiet;
                    MainActivity.manggiohang.add(new GioHang(Id, Tenchitiet, Giamoi, Hinhanhchitiet, sl));
                }

                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
            }
        });
    }
    private void CatchEventSpinner(){
        Integer[] soluong= new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> integerArrayAdapter=new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(integerArrayAdapter);
    }
    private void GetInformation() {
        Product product= (Product) getIntent().getSerializableExtra("thongtinsanpham");
        Id=product.getProID();
        Tenchitiet=product.getProName();
        Giachitiet=product.getProPrice();
        Hinhanhchitiet=product.getProImage();
        imgchitietsp.setImageBitmap(Ultils.convertToBitmapFromAssets(this,product.getProImage()));
        Motachitiet=product.getProDes();
        txttensp.setText(Tenchitiet);
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtgiasp.setText("Giá: "+decimalFormat.format(Giachitiet)+" Đ");
        txtmota.setText(Motachitiet);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void Anhxa() {
        imgchitietsp=(ImageView) findViewById(R.id.imgchitietsp);
        txttensp=(TextView) findViewById(R.id.txttensp);
        txtgiasp=(TextView) findViewById(R.id.txtgiachitietsp);
        txtmota=(TextView) findViewById(R.id.txtmotachitietsp);
        spinner=(Spinner) findViewById(R.id.spinnerchitietsp);
        btnthemgohang=(Button) findViewById(R.id.btnthemgiohang);
        toolbarchitiet=(Toolbar) findViewById(R.id.toolbarchitietsp);
    }
    @Override
    public void onItemClick(int id) {
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Cập nhật spinner dựa trên số lượng sản phẩm trong giỏ hàng
        for (GioHang gh : MainActivity.manggiohang) {
            if (gh.getIdsp() == Id) {
                int index = gh.getSoluong() - 1; // Vì mảng bắt đầu từ 0
                spinner.setSelection(index);
                break;
            }
        }
    }


}
