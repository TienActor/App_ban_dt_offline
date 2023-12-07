package com.example.doan_sale.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doan_sale.R;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private List<Product> lstPro;
    private Context mcontext;
    private MainCallBack mainCallBack;
    private ItemClickListener itemClickListener;
    int Id=0;

    int Giachitiet=0;

    // ... Các thuộc tính và constructor đã có
    private ArrayList<Product> originalList; // Danh sách ban đầu

    // Khởi tạo với danh sách ban đầu
    // Khởi tạo với danh sách và context
    public MainAdapter(Context context, ArrayList<Product> lstPro, ItemClickListener itemClickListener) {
        this.mcontext = context;
        this.lstPro = lstPro;
        this.itemClickListener = itemClickListener;
        this.originalList = new ArrayList<>(lstPro);
    }


    public void setMainCallBack(MainCallBack mainCallBack) {
        this.mainCallBack = mainCallBack;
    }

    public MainAdapter(ArrayList<Product> lstPro) {
        this.lstPro = lstPro;
    }


    @NonNull
    @Override
    public MainViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mcontext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        //nạp layout cho View biểu diễn phần tử user
        View proView= inflater.inflate(R.layout.layoutitem_main, parent, false);
        //
        MainViewHolder mainViewHolder = new MainViewHolder(proView);
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
        Product item = lstPro.get(position);
        // Gán vào item của View
        holder.imPro.setImageBitmap(Ultils.convertToBitmapFromAssets(mcontext, item.getProImage()));
        holder.proName.setText(item.getProName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.proPrice.setText("Giá " + decimalFormat.format(item.getProPrice()) + " Đ");
        // Lay su kien
        holder.itemView.setOnClickListener(view -> {
            if (mainCallBack != null) {
                mainCallBack.onItemClick(item.getProID());
            }
        });
        holder.itemView.setOnClickListener(view -> {
            if (itemClickListener != null) {
                itemClickListener.onItemCick(item);
            }
        });
        holder.ivthich.setOnClickListener(view -> {
            boolean exists = false;
            // Kiểm tra sản phẩm có tồn tại trong danh sách yêu thích chưa
            for (Product p : MainActivity.mangyeuthich) {
                if (p.getProID() == item.getProID()) {
                    exists = true;
                    break;
                }
            }
            if (exists) {
                Toast.makeText(view.getContext(), "Sản phẩm đã có trong yêu thích", Toast.LENGTH_SHORT).show();
            } else {
                MainActivity.mangyeuthich.add(new Product(item.getProID(), item.getProImage(), item.getProName(), item.getProPrice(), item.getProDes()));
                Toast.makeText(view.getContext(), "Thêm sản phẩm vào yêu thích thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void search(String keyword) {
        lstPro.clear();
        if (keyword.isEmpty()) {
            lstPro.addAll(originalList); // Nếu không có từ khóa, sử dụng danh sách gốc
        } else {
            for (Product product : originalList) {
                if (product.getProName().toLowerCase().contains(keyword.toLowerCase())) {
                    lstPro.add(product); // Thêm sản phẩm phù hợp với từ khóa tìm kiếm
                }
            }
        }
        notifyDataSetChanged(); // Thông báo rằng dữ liệu đã thay đổi để cập nhật UI
    }

    // Reset danh sách về ban đầu
    public void reset() {
        lstPro.clear();
        lstPro.addAll(originalList);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return lstPro.size();
    }
    public interface ItemClickListener{
        void onItemCick(Product product);
    }
    public class MainViewHolder extends RecyclerView.ViewHolder{
        ImageView imPro;
        TextView proName;
        TextView proPrice;
        ImageView ivthich;


        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            imPro = itemView.findViewById(R.id.img_phone);
            proName = itemView.findViewById(R.id.tv_phone);
            proPrice = itemView.findViewById(R.id.tv_Count);
            ivthich=itemView.findViewById(R.id.yeuthich);

        }
    }
    public interface MainCallBack{
        void onItemClick(int id);
    }

}