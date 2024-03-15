package com.example.doan_sale.Admin;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.res.Resources;
import android.widget.GridView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.doan_sale.Account.RegisterActivity;
import com.example.doan_sale.Product.ProDataQuery;
import com.example.doan_sale.R;
import com.example.doan_sale.model.user;
import java.util.ArrayList;

public class Admin_User_Activity extends AppCompatActivity implements UserAdapter.UserCallBack{

    ImageButton back_User;
    RecyclerView rvListCode;
    ArrayList<user> lstUser;
    UserAdapter userAdapter;
    GridView gridView;
    ImageView ivAvatar, ivSelectedAvatar;
    Button btAvatar;
    user us ;
    TextView tvAvatar;
    private AlertDialog.Builder builder;
    private AlertDialog dialog; // Khai báo biến dialog là biến toàn cục
    public static int selectedAvatar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);
        back_User=findViewById(R.id.imbBack);
        rvListCode=findViewById(R.id.rvListUser);
        lstUser = UserDataQuery.getAll(this);
        userAdapter = new UserAdapter(lstUser);
        userAdapter.setUserCallBack(this);
        //gan Adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListCode.setAdapter(userAdapter);
        rvListCode.setLayoutManager(linearLayoutManager);
        back_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Admin_User_Activity.this, AdminActivity.class);
                startActivity(i);
            }
        });

    }
    void updateUserDialog(user us) {
        // Khoi tao dialog de cap nhat nguoi dung
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Admin_User_Activity.this);
        alertDialog.setTitle("Cập nhật");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.user_dialog,null);
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.ed_User_Name);
        EditText edPass = (EditText) dialogView.findViewById(R.id.ed_User_Pass);
        EditText edEmail = (EditText) dialogView.findViewById(R.id.ed_User_Email);
        EditText edPhone = (EditText) dialogView.findViewById(R.id.ed_User_Phone);

        edPass.setText(us.getPassword());
        edName.setText(us.getUserName());
        edEmail.setText(us.getEmail());
        edPhone.setText(us.getPhoneNumber());

        // Gan du lieu
        alertDialog.setPositiveButton("Chỉnh sửa", (dialog, which) -> {
            us.setUserName(edName.getText().toString());
            us.setPassword(edPass.getText().toString());
            us.setEmail(edEmail.getText().toString());
            us.setPhoneNumber(edPhone.getText().toString());
            us.setAvatar(selectedAvatar);
            hinhanh();
            if (us.getUserName().isEmpty()) {
                Toast.makeText(Admin_User_Activity.this, "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
            } else {
                int id = UserDataQuery.update(Admin_User_Activity.this,us);
                if (id > 0) {
                    Toast.makeText(Admin_User_Activity.this, "Cập nhật người dùng thành công", Toast.LENGTH_LONG).show();
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
        // Gan su kien click cho button chon hinh anh
        btAvatar = dialogView.findViewById(R.id.btAvatar1);
        btAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hinhanh();
            }
        });
    }
    void hinhanh() {
        btAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Admin_User_Activity.this);
                LayoutInflater inflater = Admin_User_Activity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.avatar_dialog, null);
                builder.setView(dialogView);

                ivSelectedAvatar = dialogView.findViewById(R.id.ivSelectedAvatar);
                tvAvatar = dialogView.findViewById(R.id.tvAvatar);
                gridView = dialogView.findViewById(R.id.gridview);

                AvatarAdapter adapter = new AvatarAdapter(Admin_User_Activity.this, new int[]{R.drawable.meo1, R.drawable.meo2, R.drawable.meo3});
                gridView.setAdapter(adapter);

                Button btnChoose = dialogView.findViewById(R.id.btnChoose);
                final AlertDialog dialog = builder.create();

                // Khởi tạo giá trị selectedAvatar
                selectedAvatar = -1;
                btnChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedAvatar != -1) {
                            // Lưu đường dẫn của hình ảnh đã chọn
                            String avatarName = getResources().getResourceEntryName(selectedAvatar);
                            // Lấy ID của ImageView trong layout Register
                            int ivAvatarId = getResources().getIdentifier("ivAvatar1", "id", getPackageName());
                            // Đặt hình ảnh vào ImageView trong layout Register
                            ivAvatar = dialogView.findViewById(R.id.ivSelectedAvatar);
                            ivAvatar.setImageResource(selectedAvatar);
                            // Lưu đường dẫn tuyệt đối của hình ảnh đã chọn vào biến selectedAvatar
                            Resources res = getResources();
                            String packageName = getPackageName();
                            int resId = res.getIdentifier(avatarName, "drawable", packageName);
                            selectedAvatar = resId;
                        }
                        // Ẩn ImageView trong dialog
                        ivSelectedAvatar.setVisibility(View.GONE);
                        // Đóng dialog
                        dialog.dismiss();
                    }
                });
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // Lấy hình ảnh đã chọn từ adapter
                        Integer item = (Integer) adapterView.getItemAtPosition(i);
                        if (item != null) {
                            selectedAvatar = item.intValue();
                            // Gán hình ảnh đã chọn cho ImageView
                            ivSelectedAvatar.setImageResource(selectedAvatar);
                            // Hiển thị ImageView ivSelectedAvatar
                            ivSelectedAvatar.setVisibility(View.VISIBLE);
                        } else {
                            // In ra màn hình nếu item không có giá trị
                            Log.e("AvatarAdapter", "Item is null");
                        }
                    }
                });
                dialog.show();
            }
        });

    }
        @Override
    public void onItemClick(int id) {
    }


    @Override
    public void onitemDeleteClicked(user us, int position) {

        boolean rs = UserDataQuery.delete(Admin_User_Activity.this,us.getUserID());
        if(rs) {
            Toast.makeText(this, "Xóa thành công", Toast.LENGTH_LONG).show();
            resetData();
        }else {
            Toast.makeText(this,"Xóa thất bại", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onitemEditClicked(user us, int position) {
        updateUserDialog(us);
    }
    void resetData(){
        lstUser.clear();
        lstUser.addAll(UserDataQuery.getAll(Admin_User_Activity.this));
        userAdapter.notifyDataSetChanged();
    }
}
