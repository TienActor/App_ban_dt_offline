package com.example.doan_sale.fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.doan_sale.Product.ChiTietSanPhamActivity;
import com.example.doan_sale.Product.Yeuthichadapter;
import com.example.doan_sale.R;
import com.example.doan_sale.User.GioHangActivity;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.MainActivity;
import java.text.DecimalFormat;

public class SettingFragment extends Fragment {
    TextView txtThongbao;
    static TextView txtTongtien;
    ListView listViewgiohang;
    Button btnthemgiohang, btntieptucmua;
    Yeuthichadapter giohangAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);
        Anhxa(view);
        CheckData();
        CacthOnItem();
        EventButton();
        return view;
    }
    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckData();
                Intent intent=new Intent(getActivity(), MainActivity.class);
                giohangAdapter.notifyDataSetChanged();
                startActivity(intent);
            }
        });

    }
    private void CacthOnItem() {
        listViewgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc muốn xóa sản phẩm này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(MainActivity.mangyeuthich.size()<=0){
                            txtThongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.mangyeuthich.remove(position);
                            giohangAdapter.notifyDataSetChanged();

                            if(MainActivity.mangyeuthich.size()<=0) {
                                txtThongbao.setVisibility(View.VISIBLE);
                            }else{
                                txtThongbao.setVisibility(View.INVISIBLE);
                                giohangAdapter.notifyDataSetChanged();

                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        giohangAdapter.notifyDataSetChanged();
//                        EventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }
    private void CheckData() {
        if(MainActivity.mangyeuthich.size()<=0)
        {
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.VISIBLE);
            listViewgiohang.setVisibility(View.INVISIBLE);
        }else{
            giohangAdapter.notifyDataSetChanged();
            txtThongbao.setVisibility(View.INVISIBLE);
            listViewgiohang.setVisibility(View.VISIBLE);
        }
    }
    private void Anhxa(View view) {
        txtThongbao = view.findViewById(R.id.textviewthongbao2);
        txtTongtien = view.findViewById(R.id.textviewtongtien);
        listViewgiohang = view.findViewById(R.id.listviewgiohang2);
        btntieptucmua = view.findViewById(R.id.btntieptucmuahang2);
        giohangAdapter = new Yeuthichadapter(getContext(), MainActivity.mangyeuthich);
        listViewgiohang.setAdapter(giohangAdapter);
        listViewgiohang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Product gioHang = (Product) adapterView.getItemAtPosition(position);
                // Create an intent to start the ChiTietSanPhamActivity
                Intent intent = new Intent(getActivity(), ChiTietSanPhamActivity.class);
                // Put the selected GioHang object as extra data to the intent
                intent.putExtra("thongtinsanpham", gioHang);
                // Start the activity
                startActivity(intent);
            }
        });

    }
    }

