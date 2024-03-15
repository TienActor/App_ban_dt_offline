package com.example.doan_sale.Admin;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doan_sale.Account.RegisterActivity;
import com.example.doan_sale.R;
import com.example.doan_sale.model.user;
import com.example.doan_sale.ui.MainActivity;
import com.example.doan_sale.ui.Ultils;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    ArrayList<user> lstuser;
    Context context;
    UserCallBack userCallBack;

    public UserAdapter(ArrayList<user> lstuser) {
        this.lstuser = lstuser;
    }

    public void setUserCallBack(UserCallBack userCallBack) {
        this.userCallBack = userCallBack;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nạp layout cho View biểu diễn phần tử user
        View proView= inflater.inflate(R.layout.layoutuseritem, parent, false);
        //
        UserViewHolder userViewHolder = new UserViewHolder(proView);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        //Lấy từng item của dữ liệu
        user item = lstuser.get(position);
        //Gán vào item của View
        holder.userName.setText("Tên: "+item.getUserName());
        holder.userPass.setText("Mật khẩu: "+item.getPassword());
        holder.userEmail.setText("Email: "+item.getEmail());
        holder.userPhone.setText("Số điện thoại: "+item.getPhoneNumber());
        holder.avatarUser.setImageResource(item.getAvatar());
        //Lay su kien
        holder.itemView.setOnClickListener(view -> userCallBack.onItemClick(item.getUserID()));
        holder.ivDelete.setOnClickListener(view -> userCallBack.onitemDeleteClicked(item,position));

    }
    @Override
    public int getItemCount() {
        return lstuser.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder{
        ImageView avatarUser;
        TextView userName;
        TextView userPass;
        TextView userEmail;
        TextView userPhone;

        ImageView ivDelete;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarUser=itemView.findViewById(R.id.ivAvatar);
            userName=itemView.findViewById(R.id.tvName2);
            userPass=itemView.findViewById(R.id.tvPassword);
            userEmail=itemView.findViewById(R.id.tvEmail);
            userPhone=itemView.findViewById(R.id.tvPhone);

            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
    public interface UserCallBack{
        void onItemClick(int id);
        void onitemDeleteClicked(user us, int position);
        void onitemEditClicked(user us, int position);
    }
}
