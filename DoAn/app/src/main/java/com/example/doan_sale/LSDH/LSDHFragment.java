package com.example.doan_sale.LSDH;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doan_sale.R;
import com.example.doan_sale.model.Order;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.MainActivity;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
public class LSDHFragment extends Fragment {
    private RecyclerView recyclerView;
    private LSDHAdapter adapter;
    private List<Order> orderList;
    private DBHelper dbHelper;
    private ImageButton gobackbtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_l_s_d_h, container, false);
        gobackbtn = view.findViewById(R.id.gobackbtn);
        recyclerView = view.findViewById(R.id.fragmentLSDH);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dbHelper = new DBHelper(getActivity());
        gobackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        loadOrderData();
        adapter = new LSDHAdapter(getActivity(), orderList);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void loadOrderData() {
        String username = getUsernameFromSharedPreferences();
        if (username != null) {
            orderList = dbHelper.getOrdersByUsername(username);
            // Sắp xếp danh sách đơn hàng từ mới nhất đến cũ nhất
            Collections.sort(orderList, new Comparator<Order>() {
                @Override
                public int compare(Order o1, Order o2) {
                    return o2.getDate().compareTo(o1.getDate()); // Giả sử getDate() trả về String có định dạng có thể so sánh được
                }
            });
        }
    }

    private String getUsernameFromSharedPreferences() {
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_INFO", Context.MODE_PRIVATE);
        return preferences.getString("username", "");
    }
}
