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
import com.example.doan_sale.ui.Ultils;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    ArrayList<Product> lstPro;
    Context context;
    ProCallBack proCallBack;
    public void setProCallBack(ProCallBack proCallBack) {
        this.proCallBack = proCallBack;
    }

    public ProductAdapter(ArrayList<Product> lstPro) {
        this.lstPro = lstPro;
    }

    public RecyclerView.Adapter getAdapter() {
        return this;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nạp layout cho View biểu diễn phần tử user
        View proView= inflater.inflate(R.layout.layoutitem, parent, false);
        //
        ProductViewHolder productViewHolder = new ProductViewHolder(proView);
        return productViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        //Lấy từng item của dữ liệu
        Product item = lstPro.get(position);
        //Gán vào item của View
        holder.imPro.setImageBitmap(Ultils.convertToBitmapFromAssets(context,item.getProImage()));
        holder.proName.setText("Tên: "+item.getProName());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.proPrice.setText("Giá: "+decimalFormat.format(item.getProPrice())+" Đ");
        holder.proDes.setText("Mô tả: "+ item.getProDes());
        holder.IDcate.setText("ID sản phẩm: "+ String.valueOf(item.getCategoryID()));
        //Lay su kien
        holder.itemView.setOnClickListener(view -> proCallBack.onItemClick(item.getProID()));
        holder.ivDelete.setOnClickListener(view -> proCallBack.onitemDeleteClicked(item,position));
        holder.ivEdit.setOnClickListener(view -> proCallBack.onitemEditClicked(item,position));
    }
    @Override
    public int getItemCount() {
        return lstPro.size();
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder{
        ImageView imPro;
        TextView proName;
        TextView proPrice;
        TextView proDes;
        TextView IDcate;
        ImageView ivEdit;
        ImageView ivDelete;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imPro = itemView.findViewById(R.id.ivAvatar);
            proName = itemView.findViewById(R.id.tvName2);
            proPrice = itemView.findViewById(R.id.tvPrice);
            proDes=itemView.findViewById(R.id.tvdes);
            IDcate=itemView.findViewById(R.id.tvID);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
    public interface ProCallBack{
        void onItemClick(int id);
        void onitemDeleteClicked(Product product, int position);
        void onitemEditClicked(Product product, int position);
    }

}
