package com.example.doan_sale.User;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doan_sale.R;
import com.example.doan_sale.model.user;
import java.util.ArrayList;

public class UserAccountAdapter extends RecyclerView.Adapter<UserAccountAdapter.UserAccountViewHolder> {
    private ArrayList<user> lstuser;
    private Context context;
    private UserAccountCallBack userCallBack;
    private user currentUser; // Thêm thuộc tính currentUser
    public void setUser(user currentUser) {
        this.currentUser = currentUser;
        notifyDataSetChanged(); // Cập nhật lại giao diện sau khi thay đổi dữ liệu
    }

    // Thêm constructor
    public UserAccountAdapter(ArrayList<user> lstuser) {
        this.lstuser = lstuser;
        this.currentUser = null;
    }
    public UserAccountAdapter(ArrayList<user> lstuser, user currentUser) {
        this.lstuser = lstuser;
        this.currentUser = currentUser;
    }
    public UserAccountAdapter() {
        this.lstuser = new ArrayList<>();
    }
    public void setUserAccountCallBack(UserAccountCallBack userCallBack) {
        this.userCallBack = userCallBack;
    }
    @NonNull
    @Override
    public UserAccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nạp layout cho View biểu diễn phần tử user
        View proView= inflater.inflate(R.layout.account_user_item, parent, false);
        //
        UserAccountViewHolder userViewHolder = new UserAccountViewHolder(proView);
        return userViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull UserAccountViewHolder holder, int position) {
        user item = lstuser.get(position);
        Log.d("Adapter", "Position: " + position + ", User: " + item.getUserName());
        //Gán vào item của View
        if (item != null) {
            holder.userName.setText("Tên: "+item.getUserName());
            holder.userPass.setText("Mật khẩu: "+item.getPassword());
            holder.userEmail.setText("Email: "+item.getEmail());
            holder.userPhone.setText("Số điện thoại: "+item.getPhoneNumber());
            holder.avatarUser.setImageResource(item.getAvatar());
        }
        //Lay su kien
        holder.itemView.setOnClickListener(view -> {
            Log.d("AdapterClick", "Item Clicked ID: " + item.getUserID());
            userCallBack.onItemClick(item.getUserID());
        });
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userCallBack != null) {
                    int currentPosition = holder.getAdapterPosition();
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        userCallBack.onitemEditClicked(lstuser.get(currentPosition), currentPosition);
                    }
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return lstuser.size();
    }
    public class UserAccountViewHolder extends RecyclerView.ViewHolder{
        ImageView avatarUser;
        TextView userName;
        TextView userPass;
        TextView userEmail;
        TextView userPhone;
        ImageView ivEdit;
        public UserAccountViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarUser=itemView.findViewById(R.id.ivAvatar2);
            userName=itemView.findViewById(R.id.tvName3);
            userPass=itemView.findViewById(R.id.tvPassword1);
            userEmail=itemView.findViewById(R.id.tvEmail1);
            userPhone=itemView.findViewById(R.id.tvPhone1);
            ivEdit = itemView.findViewById(R.id.ivEdit);

        }
    }
    public interface UserAccountCallBack {
        void onItemClick(int id);
        void onitemEditClicked(user us, int position);
    }

}
