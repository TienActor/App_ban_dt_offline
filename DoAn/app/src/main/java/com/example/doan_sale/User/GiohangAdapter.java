package com.example.doan_sale.User;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.doan_sale.R;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.MainActivity;
import com.example.doan_sale.ui.Ultils;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangAdapter extends BaseAdapter {
    Context context;
    ArrayList<GioHang> arrayListgiohang;
    ViewHolder viewHolder=null;
    public GiohangAdapter(Context context, ArrayList<GioHang> arrayListgiohang) {
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
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.dong_sanpham_giohang,null);
            viewHolder.txttengiohang=(TextView) view.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagiohang=(TextView) view.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggiohang=(ImageView) view.findViewById(R.id.imgviewgiohang);
            viewHolder.btngiam=(Button) view.findViewById(R.id.btngiam);
            viewHolder.btngiatri=(Button) view.findViewById(R.id.btngiatri);
            viewHolder.btntang=(Button) view.findViewById(R.id.btntang);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) view.getTag();
        }
        GioHang gioHang=(GioHang) getItem(i);
        viewHolder.txttengiohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText("Giá: "+decimalFormat.format(gioHang.getGiasp())+" Đ");
        viewHolder.imggiohang.setImageBitmap(Ultils.convertToBitmapFromAssets(context,gioHang.getHinhsp()));
        viewHolder.btngiatri.setText(gioHang.getSoluong()+"");
        int sl=Integer.parseInt(viewHolder.btngiatri.getText().toString());
        if(sl>=10){
            viewHolder.btntang.setVisibility(View.INVISIBLE);
            viewHolder.btngiam.setVisibility(View.VISIBLE);
            viewHolder.btngiatri.setText("10");
        }else if(sl<=1){
            viewHolder.btngiam.setVisibility(View.INVISIBLE);
        } else if(sl>=1){
            viewHolder.btngiam.setVisibility(View.VISIBLE);
            viewHolder.btntang.setVisibility(View.VISIBLE);
        }
        ViewHolder viewHolder = this.viewHolder;
        this.viewHolder.btntang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi=Integer.parseInt(GiohangAdapter.this.viewHolder.btngiatri.getText().toString())+1;
                int slht= MainActivity.manggiohang.get(i).getSoluong();
                long giatriht=MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluong(slmoi);
                long giamoi=(giatriht * slmoi) / slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoi);
                DecimalFormat format=new DecimalFormat("###,###,###");
                viewHolder.txtgiagiohang.setText(format.format(giamoi)+ " Đ");
                GioHangActivity.EventUtil();
                if(slmoi>9){
                    viewHolder.btntang.setVisibility(View.INVISIBLE);
                    viewHolder.btngiam.setVisibility(View.VISIBLE);
                    viewHolder.btngiatri.setText(String.valueOf(slmoi));
                }else{
                    viewHolder.btntang.setVisibility(View.VISIBLE);
                    viewHolder.btngiam.setVisibility(View.VISIBLE);
                    viewHolder.btngiatri.setText(String.valueOf(slmoi));
                }
            }
        });
        this.viewHolder.btngiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int slmoi=Integer.parseInt(viewHolder.btngiatri.getText().toString())-1;
                int slht= MainActivity.manggiohang.get(i).getSoluong();
                long giatriht=MainActivity.manggiohang.get(i).getGiasp();
                MainActivity.manggiohang.get(i).setSoluong(slmoi);
                long giamoi=(giatriht * slmoi) / slht;
                MainActivity.manggiohang.get(i).setGiasp(giamoi);
                DecimalFormat format=new DecimalFormat("###,###,###");
                viewHolder.txtgiagiohang.setText(format.format(giamoi)+ " Đ");
                GioHangActivity.EventUtil();
                if(slmoi<2){
                    viewHolder.btngiam.setVisibility(View.INVISIBLE);
                    viewHolder.btntang.setVisibility(View.VISIBLE);
                    viewHolder.btngiatri.setText(String.valueOf(slmoi));
                }else{
                    viewHolder.btngiam.setVisibility(View.VISIBLE);
                    viewHolder.btntang.setVisibility(View.VISIBLE);
                    viewHolder.btngiatri.setText(String.valueOf(slmoi));
                }
            }
        });
        return view;
    }
}
