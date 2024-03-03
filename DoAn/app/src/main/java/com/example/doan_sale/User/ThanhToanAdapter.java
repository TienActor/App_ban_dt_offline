package com.example.doan_sale.User;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doan_sale.Product.ProductAdapter;
import com.example.doan_sale.R;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.Ultils;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.ThanhToanViewHolder>{
    ArrayList<GioHang> lstgio;
    Context context;
    ThanhToanCallBack proCallBack;
    public void updateData(ArrayList<GioHang> lstgio) {
        this.lstgio = lstgio;
        notifyDataSetChanged();
    }
    public void setProCallBack(ThanhToanCallBack proCallBack) {
        this.proCallBack = proCallBack;
    }
    public ThanhToanAdapter(ArrayList<GioHang> lstgio) {
        this.lstgio = lstgio;
    }
    public ThanhToanAdapter(Context context, ArrayList<GioHang> lstPro, ThanhToanCallBack callBack) {
        this.context = context;
        this.lstgio= lstPro;
        this.proCallBack = callBack;
    }
    @NonNull
    @Override
    public ThanhToanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //nạp layout cho View biểu diễn phần tử user
        View proView= inflater.inflate(R.layout.thanh_toan_item, parent, false);
        //
       ThanhToanViewHolder productViewHolder = new ThanhToanViewHolder(proView);
        return productViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ThanhToanViewHolder holder, int position) {
        GioHang item = lstgio.get(position);
        //Gán vào item của View
        holder.imPro.setImageBitmap(Ultils.convertToBitmapFromAssets(context,item.getHinhsp()));
        holder.proName.setText("Tên: "+item.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.proPrice.setText("Giá: "+decimalFormat.format(item.getGiasp())+" Đ");
        holder.proQuantity.setText("Số lượng: "+item.getSoluong());
        //Lay su kien
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (proCallBack != null) {
                    proCallBack.onItemLongClicked(item);
                }
                return true;
            }
        });

    }
    public void removeItem(int position) {
        lstgio.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return lstgio.size();
    }
    public class ThanhToanViewHolder extends RecyclerView.ViewHolder{
        ImageView imPro;
        TextView proName;
        TextView proPrice;
        TextView proQuantity;

        public ThanhToanViewHolder(@NonNull View itemView) {
            super(itemView);
            imPro = itemView.findViewById(R.id.ivProductImage);
            proName = itemView.findViewById(R.id.tvName4);
            proPrice = itemView.findViewById(R.id.tvPrice2);
            proQuantity=itemView.findViewById(R.id.tvQuantity);
        }
    }
    public interface ThanhToanCallBack{

        void onItemLongClicked(GioHang gioHang);
    }


}
