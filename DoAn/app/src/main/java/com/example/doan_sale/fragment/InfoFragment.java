    package com.example.doan_sale.fragment;

    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.SharedPreferences;
    import android.content.res.Resources;
    import android.os.Bundle;

    import androidx.appcompat.app.AlertDialog;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import android.support.annotation.NonNull;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.AdapterView;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.GridView;
    import android.widget.ImageView;
    import android.widget.TextView;
    import android.widget.Toast;

    import com.example.doan_sale.Admin.Admin_User_Activity;
    import com.example.doan_sale.Admin.AvatarAdapter;
    import com.example.doan_sale.Admin.UserDataQuery;
    import com.example.doan_sale.Product.ProDataQuery;
    import com.example.doan_sale.R;
    import com.example.doan_sale.User.UserAccountAdapter;
    import com.example.doan_sale.model.user;
    import com.example.doan_sale.ui.DBHelper;
    import com.example.doan_sale.ui.MainAdapter;

    import java.util.ArrayList;


    public class InfoFragment extends Fragment implements UserAccountAdapter.UserAccountCallBack {
        ArrayList<user> lstuser;
        private boolean hasSelectedAvatar = false;

        RecyclerView rvListCode;
        public static int selectedAvatar;
        TextView tvAvatar;
        GridView gridView;
        Button btAvatar;
        ImageView ivAvatar, ivSelectedAvatar,tAvatar;
        private SharedPreferences sharedPreferences;
        private UserAccountAdapter adapter;
        private String username;

        @Override
        public void onAttach(@NonNull Context context) {
            super.onAttach(context);
            sharedPreferences = context.getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
            username = sharedPreferences.getString("username", null);
            user currentUser = UserDataQuery.getUserByUsername(context, username);


            lstuser = new ArrayList<>();
            if (currentUser != null) {
                lstuser.add(currentUser);
            }

            adapter = new UserAccountAdapter(lstuser);
            adapter.setUser(currentUser);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_info, container, false);
            rvListCode = view.findViewById(R.id.rvListUser1);

            adapter.setUserAccountCallBack(this);
            rvListCode.setAdapter(adapter);
            // set layout manager for the RecyclerView
            rvListCode.setLayoutManager(new LinearLayoutManager(getContext()));
            return view;

        }
        void updateUserDialog(user us) {
            // Khoi tao dialog de cap nhat nguoi dung
            androidx.appcompat.app.AlertDialog.Builder alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getContext());
            alertDialog.setTitle("Cập nhật");

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View dialogView = inflater.inflate(R.layout.user_dialog, null);
            alertDialog.setView(dialogView);

            EditText edName = (EditText) dialogView.findViewById(R.id.ed_User_Name);
            EditText edPass = (EditText) dialogView.findViewById(R.id.ed_User_Pass);
            EditText edEmail = (EditText) dialogView.findViewById(R.id.ed_User_Email);
            EditText edPhone = (EditText) dialogView.findViewById(R.id.ed_User_Phone);
            tAvatar =(ImageView) dialogView.findViewById(R.id.ivAvatar1);
            // Set gia tri hien tai cua nguoi dung vao cac EditText
            edName.setText(us.getUserName());
            edPass.setText(us.getPassword());
            edEmail.setText(us.getEmail());
            edPhone.setText(us.getPhoneNumber());
            tAvatar.setImageResource(us.getAvatar());

            // Gan du lieu
            alertDialog.setPositiveButton("Chỉnh sửa", (dialog, which) -> {
                us.setUserName(edName.getText().toString());
                us.setPassword(edPass.getText().toString());
                us.setEmail(edEmail.getText().toString());
                us.setPhoneNumber(edPhone.getText().toString());
                us.setAvatar(selectedAvatar);
                if (!hasSelectedAvatar) {
                    hinhanh();
                }

                if (us.getUserName().isEmpty()) {
                    Toast.makeText(getContext(), "Nhập dữ liệu không hợp lệ", Toast.LENGTH_LONG).show();
                } else {
                    int id = UserDataQuery.update(getContext(),us);
                    if (id > 0) {
                        Toast.makeText(getContext(), "Cập nhật người dùng thành công", Toast.LENGTH_LONG).show();
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
                    hasSelectedAvatar = true;
                }
            });
        }

        void hinhanh() {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getContext());

            LayoutInflater inflater = LayoutInflater.from(getActivity());

            View dialogView = inflater.inflate(R.layout.avatar_dialog, null);
            builder.setView(dialogView);

            ivSelectedAvatar = dialogView.findViewById(R.id.ivSelectedAvatar);
            tvAvatar = dialogView.findViewById(R.id.tvAvatar);
            gridView = dialogView.findViewById(R.id.gridview);

            AvatarAdapter adapter = new AvatarAdapter(getContext(), new int[]{R.drawable.meo1, R.drawable.meo2, R.drawable.meo3});
            gridView.setAdapter(adapter);

            Button btnChoose = dialogView.findViewById(R.id.btnChoose);
            final AlertDialog dialog = builder.create();

            // Khởi tạo giá trị selectedAvatar
            selectedAvatar = -1;

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
            btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Kiểm tra xem có hình ảnh nào đã được chọn hay không bằng cách kiểm tra giá trị của biến selectedAvatar
                    if (selectedAvatar != -1) {
                        // Nếu có, cập nhật hình ảnh đã chọn vào ImageView tAvatar trong hộp thoại cập nhật thông tin người dùng
                        tAvatar.setImageResource(selectedAvatar);

                        // Sau khi cập nhật hình ảnh, đóng hộp thoại dialog để người dùng tiếp tục thao tác khác
                        dialog.dismiss(); // Đảm bảo dialog được đóng ở đây
                    }
                }
            });

            dialog.show();
        }


        void resetData(){
            lstuser.clear();
            lstuser.add(UserDataQuery.getUserByUsername(getContext(),username));
            adapter.notifyDataSetChanged();
        }
        @Override
        public void onItemClick(int id) {

        }

        @Override
        public void onitemEditClicked(user us, int position) {
            updateUserDialog(us);
        }
    }
