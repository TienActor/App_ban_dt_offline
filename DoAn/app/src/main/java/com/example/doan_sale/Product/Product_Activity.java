package com.example.doan_sale.Product;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.doan_sale.Admin.AdminActivity;
import com.example.doan_sale.R;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.DBHelper;
import java.util.ArrayList;

public class Product_Activity extends AppCompatActivity {

    ArrayList<Product> lstPro;
    TextView tvProImage;
    EditText edProName, edProPrice, edProImage, edProDes,edCate;
    Button btView, btAdd;
    ImageView imagePro;
    ProductAdapter productAdapter;
    ImageButton ib_back;

    DBHelper dbHelper = new DBHelper(Product_Activity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        btAdd = (Button) findViewById(R.id.btn_Add);
        btAdd.setOnClickListener(view -> addSanpham());
        ib_back = findViewById(R.id.ib_Back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(Product_Activity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
//        btAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Product product;
//                String image = edProImage.getText().toString();
//                String name  = edProName.getText().toString();
//                String gia = edProPrice.getText().toString();
//
//                product = new Product(image, name, gia);
//
//                boolean isAdded = dbHelper.addSanpham(product);
//                if(isAdded)
//                    Toast.makeText(Product_Activity.this, "Added: " + product.getProName(), Toast.LENGTH_SHORT).show();
//                else
//                    Toast.makeText(Product_Activity.this, "Error Added!!!!", Toast.LENGTH_SHORT).show();
//            }
//        });
        btView = (Button) findViewById(R.id.btn_ViewAll);
        btView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), View_Pro_List_Activity.class));
            }
        });
    }

    void addSanpham(){
        AlertDialog.Builder alerDialog = new AlertDialog.Builder(Product_Activity.this);
        alerDialog.setTitle("Thêm mới");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_product,null);
        alerDialog.setView(dialogView);
        tvProImage = findViewById(R.id.tv_Image);
        edProImage = findViewById(R.id.edt_Image);
        edProName = findViewById(R.id.edt_proName);
        edProPrice = findViewById(R.id.edt_proPrice);
        edProDes = findViewById(R.id.edt_proDes);
        edCate =findViewById(R.id.edt_cateName);
        //
        String name = edProName.getText().toString();
        String avatar = edProImage.getText().toString();
        Integer price = Integer.valueOf(edProPrice.getText().toString());
        String des = edProDes.getText().toString();
        Integer cate = Integer.valueOf(edCate.getText().toString());
//            if (name.isEmpty()){
//                Toast.makeText(Product_Activity.this, "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
//            }{
            Product pro = new Product(0, avatar, name, price, des,cate);
            long id = ProDataQuery.insert(Product_Activity.this, pro);
            if (id > 0) {
                Toast.makeText(Product_Activity.this, "Thêm sản phẩm thành công.", Toast.LENGTH_LONG).show();
            }
        }
    void resetData(){
        lstPro.clear();
        lstPro.addAll(ProDataQuery.getALL(Product_Activity.this));
        productAdapter.notifyDataSetChanged();
    }
}
