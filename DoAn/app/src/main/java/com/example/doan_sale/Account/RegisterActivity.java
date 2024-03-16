package com.example.doan_sale.Account;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.doan_sale.Admin.AvatarAdapter;
import com.example.doan_sale.Admin.UserDataQuery;
import com.example.doan_sale.R;
import com.example.doan_sale.model.user;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.Ultils;

public class RegisterActivity extends AppCompatActivity {
    Button register, btAvatar;
    EditText eduserName, edpassword, edphone, edrepassword, edemail;
    ImageView ivAvatar, ivSelectedAvatar,ivSelectAv;
    GridView gridView;
    TextView tvAvatar;
    ImageButton regback;

    DBHelper db = new DBHelper(RegisterActivity.this);
    private AlertDialog.Builder builder;
    private AlertDialog dialog; // Khai báo biến dialog là biến toàn cục
    public static int selectedAvatar; // lưu trữ trạng thái ImageView đã chọn, ban đầu chưa có gì được chọn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.btRegister);
        eduserName = findViewById(R.id.edUserNameRe);
        edpassword = findViewById(R.id.edPassword);
        edphone = findViewById(R.id.edPhone);
        edrepassword = findViewById(R.id.edRePassword);
        edemail = findViewById(R.id.edEmail);
        btAvatar = findViewById(R.id.btAvatar);
        regback=findViewById(R.id.ibReg);
        regback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        btAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Khai báo biến dialog ở đây
                // Hiển thị dialog chọn avatar
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                LayoutInflater inflater = RegisterActivity.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.avatar_dialog, null);
                builder.setView(dialogView);

                ivSelectedAvatar = dialogView.findViewById(R.id.ivSelectedAvatar);
                tvAvatar = dialogView.findViewById(R.id.tvAvatar);
                gridView = dialogView.findViewById(R.id.gridview);
                // Tạo adapter và gán vào GridView trong dialog
                AvatarAdapter adapter = new AvatarAdapter(RegisterActivity.this, new int[]{R.drawable.meo1, R.drawable.meo2, R.drawable.meo3});
                gridView.setAdapter(adapter);

                // Tạo button chọn ảnh và xử lý sự kiện khi click
                Button btnChoose = dialogView.findViewById(R.id.btnChoose);
                btnChoose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (selectedAvatar != -1) {
                            // Lưu đường dẫn của hình ảnh đã chọn
                            String avatarName = getResources().getResourceEntryName(selectedAvatar);
                            // Lấy ID của ImageView trong layout Register
                            int ivAvatarId = getResources().getIdentifier("ivAvatar", "id", getPackageName());
                            // Đặt hình ảnh vào ImageView trong layout Register
                            ivAvatar = findViewById(R.id.ivAvatar);
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

                dialog = builder.create();
                // Khởi tạo giá trị selectedAvatar
                selectedAvatar = -1;

                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        // Lấy hình ảnh đã chọn từ adapter
                        Integer item = (Integer) adapterView.getItemAtPosition(i);
                        if (item != null) {
                            selectedAvatar = item.intValue();
                            // Gán hình ảnh vào ImageView ivSelectedAvatar
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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameR = eduserName.getText().toString().trim();
                if (isUsernameTaken(usernameR)) {  // Sử dụng phương thức kiểm tra tên đăng nhập
                    eduserName.setError("Tên đăng nhập này đã được sử dụng. Vui lòng chọn một tên đăng nhập khác.");
                    return;
                }
                String passwordR = edpassword.getText().toString().trim();
                String edphoneR = edphone.getText().toString().trim();
                String repassR = edrepassword.getText().toString().trim();
                String email = edemail.getText().toString().trim();
                if (isEmailTaken(email)) {
                    edemail.setError("Email đã được đăng ký,hãy sử dụng email khác");
                    return;
                }
                int avatarPath = selectedAvatar;
                user user = new user(0, usernameR, passwordR, email, edphoneR, avatarPath);


                //checklogin
                boolean isValid = (checkUsernameR(usernameR) && CheckPass(passwordR, repassR) && checkEmailR(email) && checkPhoneR(edphoneR));
                if (isValid) {
                    long insertedRowId = UserDataQuery.insert(RegisterActivity.this, user);
                    if (insertedRowId > 0) {
                        Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Đăng ký không thành công", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    boolean checkUsernameR(String usernameR){
        if (usernameR.isEmpty()) {
            eduserName.setError("Vui lòng nhập tên đăng nhập");
            return false;
        }
        return true;
    }
    boolean CheckPass(String passwordR, String repassword){
        if(passwordR.isEmpty()){
            edpassword.setError("Vui lòng nhập mật khẩu");
            return false;
        }
        if(passwordR.length()<=5)
        {
            edpassword.setError("Vui lòng nhập trên 6 ký tự");
        }
        if(repassword.isEmpty()){
            edrepassword.setError("Vui lòng nhập lại mật khẩu");
            return false;
        }

        if(!passwordR.equals(repassword)) {
            edrepassword.setError("xác nhận mật khẩu không khớp");
            return false;
        }
        return true;
    }
    boolean checkEmailR(String emailR){
        if(emailR.isEmpty()){
            edemail.setError("Vui lòng nhập email");
            return false;
        }

        // Sử dụng biểu thức chính quy để kiểm tra định dạng email
        String emailRegex = "^[A-Za-z0-9+_.-]+@gmail.com$";
        if (!emailR.matches(emailRegex)) {
            edemail.setError("Email không hợp lệ");
            return false;
        }

        return true;
    }
    boolean checkPhoneR(String phoneR){
        if(phoneR.isEmpty()){
            edphone.setError("Vui lòng nhập số điện thoại");
            return false;
        }
        return true;
    }

    public boolean isUsernameTaken(String username) {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Ultils.TABLE_USER + " WHERE " + Ultils.USER_NAME + " = ?", new String[]{username});
        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count > 0;
    }
    public boolean isEmailTaken(String email) {
        SQLiteDatabase database = db.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Ultils.TABLE_USER + " WHERE " + Ultils.USER_EMAIL + " = ?", new String[]{email});
        int count = cursor.getCount();
        cursor.close();
        database.close();
        return count > 0;
    }

}
