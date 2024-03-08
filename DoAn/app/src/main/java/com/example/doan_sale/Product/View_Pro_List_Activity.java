package com.example.doan_sale.Product;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.doan_sale.Admin.AdminActivity;
import com.example.doan_sale.R;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.DBHelper;
import java.util.ArrayList;

public class View_Pro_List_Activity extends AppCompatActivity implements ProductAdapter.ProCallBack{
    Dialog myDialog;
    RecyclerView rvListCode;
    ArrayList<Product> lstPro;
    ProductAdapter productAdapter;
    ImageButton back;
    DBHelper dbHelper = new DBHelper(View_Pro_List_Activity.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pro_list);
        rvListCode = findViewById(R.id.rvList);
        //lay du lieu
        lstPro = ProDataQuery.getALL(this);
        productAdapter = new ProductAdapter(lstPro);
        productAdapter.setProCallBack(this);
        //gan Adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListCode.setAdapter(productAdapter);
        rvListCode.setLayoutManager(linearLayoutManager);
        back=findViewById(R.id.imbBack2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(View_Pro_List_Activity.this, AdminActivity.class);
                startActivity(i);
            }
        });

    }

    void updateProDialog(Product product){
        //KHoi tai dialog de cap nhat nguoi dung
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(View_Pro_List_Activity.this);
        alertDialog.setTitle("Cập nhật");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.product_diaglog,null);
        alertDialog.setView(dialogView);
        // Đặt giá trị cho các EditText dựa trên dữ liệu của sản phẩm
        EditText edImage = (EditText) dialogView.findViewById(R.id.edImage);
        EditText edName = (EditText) dialogView.findViewById(R.id.edproName);
        EditText edPrice = (EditText) dialogView.findViewById(R.id.edproPrice);
        EditText edDes = (EditText) dialogView.findViewById(R.id.edproDes);
        EditText edCate=(EditText) dialogView.findViewById(R.id.edcateID);
        edImage.setText(product.getProImage());
        edName.setText(product.getProName());
        edPrice.setText(String.valueOf(product.getProPrice()));
        edDes.setText(product.getProDes());
        edCate.setText(String.valueOf(product.getCategoryID()));
        //gan du lieu
        alertDialog.setPositiveButton("Chỉnh sửa", (dialog, which) -> {
            product.setProImage(edImage.getText().toString());
            product.setProName(edName.getText().toString());
            product.setProPrice(Integer.valueOf(edPrice.getText().toString()));
            product.setProDes(edDes.getText().toString());
            product.setCategoryID(Integer.valueOf(edCate.getText().toString()));
            if (product.getProName().isEmpty()){
                Toast.makeText(View_Pro_List_Activity.this, "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
            }else {
                int id = ProDataQuery.update(View_Pro_List_Activity.this,product);
                if (id > 0){
                    Toast.makeText(View_Pro_List_Activity.this, "Cập nhật sản phẩm thành công", Toast.LENGTH_LONG).show();
                    resetData();
                    dialog.dismiss();
                }
            }
        });
        alertDialog.setNegativeButton("Hủy", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }
    @Override
    public void onItemClick(int id) {

    }

    @Override
    public void onitemDeleteClicked(Product product, int position) {

        boolean rs = ProDataQuery.delete(View_Pro_List_Activity.this,product.getProID());
        if(rs) {
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_LONG).show();
            resetData();
        }else {
            Toast.makeText(this,"Xóa thất bại", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onitemEditClicked(Product product, int position) {
        updateProDialog(product);
    }
    void resetData(){
        lstPro.clear();
        lstPro.addAll(ProDataQuery.getALL(View_Pro_List_Activity.this));
        productAdapter.notifyDataSetChanged();
    }
}
