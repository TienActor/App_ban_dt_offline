package com.example.doan_sale.Product;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doan_sale.R;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.MainAdapter;
import com.example.doan_sale.ui.Ultils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CateAdapter  extends RecyclerView.Adapter<CateAdapter.CateViewHolder>{
    ArrayList<Product> lstPro;
    Context context;
    CateCallBack cateCallBack;
    public void setCateCallBack(CateCallBack cateCallBack) {
        this.cateCallBack = cateCallBack;
    }
    public  CateAdapter(Context context, ArrayList<Product> lstPro) {
        this.context = context;
        this.lstPro = lstPro;

    }
    public void updateData(List<Product> newProducts) {
        lstPro.clear(); // Xóa danh sách sản phẩm hiện tại
        lstPro.addAll(newProducts); // Thêm danh sách sản phẩm mới vào
        notifyDataSetChanged(); // Cập nhật giao diện người dùng
    }

    public CateAdapter(ArrayList<Product> lstPro) {
        this.lstPro = lstPro;
    }
    @NonNull
    @Override
    public CateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nạp layout cho View biểu diễn phần tử user
        View proView= inflater.inflate(R.layout.layout_cate, parent, false);
        //
        CateViewHolder cateViewHolder = new CateViewHolder(proView);
        return cateViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CateViewHolder holder, int position) {
        Product item = lstPro.get(position);
        //Gán vào item của View
        holder.imPro.setImageBitmap(Ultils.convertToBitmapFromAssets(context,item.getProImage()));
        holder.proName.setText(item.getProName());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.proPrice.setText("Giá: "+decimalFormat.format(item.getProPrice())+" Đ");
        //Lay su kien
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cateCallBack != null) {
                    cateCallBack.onItemClick(item.getProID());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstPro.size();
    }

    public class CateViewHolder extends RecyclerView.ViewHolder{
        ImageView imPro;
        TextView proName;
        TextView proPrice;


        public CateViewHolder(@NonNull View itemView) {
            super(itemView);
            imPro = itemView.findViewById(R.id.ivAvatarcate);
            proName = itemView.findViewById(R.id.tvNamecate);
            proPrice = itemView.findViewById(R.id.tvPricecate);

        }
    }
    public interface CateCallBack{
        void onItemClick(int id);

    }
    public interface ItemClickListener{
        void onItemCick(Product product);
    }
}
