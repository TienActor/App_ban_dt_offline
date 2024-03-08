package com.example.doan_sale.Product;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.doan_sale.R;
import com.example.doan_sale.User.GioHangActivity;
import com.example.doan_sale.User.GiohangAdapter;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.MainActivity;
import com.example.doan_sale.ui.MainAdapter;
import com.example.doan_sale.ui.Ultils;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Yeuthichadapter extends BaseAdapter {
    Context context;
    ArrayList<Product> arrayListgiohang;
    Yeuthichadapter.ViewHolder viewHolder=null;
    public Yeuthichadapter(Context context, ArrayList<Product> arrayListgiohang) {
        this.context = context;
        this.arrayListgiohang = arrayListgiohang;
    }
    @Override
    public int getCount() {
        return arrayListgiohang.size();
    }
    @Override
    public Object getItem(int i) {
        return arrayListgiohang.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    public static class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btngiam,btngiatri,btntang;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view==null){
            viewHolder=new Yeuthichadapter.ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_sanpham_yeuthich,null);
            viewHolder.txttengiohang=(TextView) view.findViewById(R.id.textviewtengiohang1);
            viewHolder.txtgiagiohang=(TextView) view.findViewById(R.id.textviewgiagiohang1);
            viewHolder.imggiohang=(ImageView) view.findViewById(R.id.imgviewgiohang1);
            view.setTag(viewHolder);
        }else {
            viewHolder=(Yeuthichadapter.ViewHolder) view.getTag();
        }
        Product gioHang=(Product) getItem(i);
        viewHolder.txttengiohang.setText(gioHang.getProName());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText("Giá "+decimalFormat.format(gioHang.getProPrice())+" Đ");
        viewHolder.imggiohang.setImageBitmap(Ultils.convertToBitmapFromAssets(context,gioHang.getProImage()));

        return view;
    }


}
