package com.example.doan_sale.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.doan_sale.Product.CateAdapter;
import com.example.doan_sale.Product.ChiTietSanPhamActivity;
import com.example.doan_sale.Product.ProDataQuery;
import com.example.doan_sale.Product.ProductAdapter;
import com.example.doan_sale.R;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.MainAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DSSPFragment extends Fragment implements CateAdapter.CateCallBack {

    private ArrayAdapter<String> categoryAdapter;
    private List<String> categoryNames;

    private RecyclerView phoneList;

    private List<Product> phoneProducts;
    private List<Product> headphoneProducts;
    private List<Product> chargerProducts;

    private CateAdapter phoneAdapter;
    private CateAdapter headphoneAdapter;
    private CateAdapter chargerAdapter;

    private DBHelper dbHelper;

    //code moi
    private TabLayout tabLayoutOrderStatus;
    private TextView tvStatusMessage;
    private ProgressBar progressBar;
    private CateAdapter currentAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_s_s_p, container, false);
        // Initialize the Spinner


        tabLayoutOrderStatus = view.findViewById(R.id.tabCategory_11);
        tabLayoutOrderStatus.addTab(tabLayoutOrderStatus.newTab().setText("Điện thoại"));
        tabLayoutOrderStatus.addTab(tabLayoutOrderStatus.newTab().setText( "Tai nghe"));
        tabLayoutOrderStatus.addTab(tabLayoutOrderStatus.newTab().setText("Sạc điện thoại"));

        TabLayout.Tab defaultTab = tabLayoutOrderStatus.getTabAt(0);
        if (defaultTab != null) {
            defaultTab.select();

        }


        tabLayoutOrderStatus.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        // Tab "Đã giao"
                        loadPhoneProducts();
                        break;
                    case 1:
                        // Tab "Đã hủy"
                        loadHeadphoneProducts();
                        break;
                    case 2:
                        // Tab "Tất cả"
                        loadChargerProducts();
//                        loadAllOrders();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Handle tab unselected state if needed
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Handle tab reselected state if needed
            }
        });
        loadPhoneProducts();
        //rvOrderList.setLayoutManager(new GridLayoutManager(getContext(), 1));
        //loadDeliveredOrders(()->filterOrdersAndUpdateUI("Đã giao"));


        // Populate the Spinner with category names
        categoryNames = Arrays.asList("Điện thoại", "Tai nghe", "Sạc điện thoại");
        categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categoryNames);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // Set a listener for category selection

        phoneList = view.findViewById(R.id.phone_list);
        tvStatusMessage = view.findViewById(R.id.tvStatusMessage);
        progressBar = view.findViewById(R.id.progressBar);


        // lay database
        dbHelper = new DBHelper(getActivity());
        phoneProducts = dbHelper.getProductsByCategoryId(1);
        headphoneProducts = dbHelper.getProductsByCategoryId(2);
        chargerProducts = dbHelper.getProductsByCategoryId(3);


        phoneAdapter = new CateAdapter(new ArrayList<>(phoneProducts));
        headphoneAdapter = new CateAdapter(new ArrayList<>(headphoneProducts));
        chargerAdapter = new CateAdapter(new ArrayList<>(chargerProducts));

        phoneList.setLayoutManager(new LinearLayoutManager(getActivity()));
        phoneAdapter = new CateAdapter(new ArrayList<>(phoneProducts));
        phoneAdapter.setCateCallBack(this);
        currentAdapter = phoneAdapter; // Đặt adapter mặc định là phoneAdapter
        phoneList.setAdapter(currentAdapter);


        return view;
    }

    // code moi


    private void loadPhoneProducts() {
        phoneProducts = dbHelper.getProductsByCategoryId(1);
        phoneAdapter.updateData(new ArrayList<>(phoneProducts));
//        currentAdapter.notifyDataSetChanged(); // Thông báo dữ liệu đã thay đổi
//        toggleRecyclerViewVisibility(true); // Hiển thị RecyclerView
        switchAdapter(phoneAdapter);
    }

    private void loadHeadphoneProducts() {
        headphoneProducts = dbHelper.getProductsByCategoryId(2);
        headphoneAdapter.updateData(new ArrayList<>(headphoneProducts));
        switchAdapter(headphoneAdapter); // Chuyển Adapter
    }

    private void loadChargerProducts() {
        chargerProducts = dbHelper.getProductsByCategoryId(3);
        chargerAdapter.updateData(new ArrayList<>(chargerProducts));
        switchAdapter(chargerAdapter); // Chuyển Adapter
    }

    private void switchAdapter(CateAdapter newAdapter) {
        if (currentAdapter != newAdapter) {
            currentAdapter = newAdapter;
            phoneList.setAdapter(currentAdapter);
        }
        currentAdapter.notifyDataSetChanged();
        toggleRecyclerViewVisibility(true); // Hiển thị RecyclerView
    }

    private void toggleRecyclerViewVisibility(boolean showList) {
        phoneList.setVisibility(showList ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(showList ? View.GONE : View.VISIBLE); // Ẩn hiển thị progressBar khi có dữ liệu
        tvStatusMessage.setVisibility(showList ? View.GONE : View.VISIBLE);
        tvStatusMessage.setText(showList ? "" : "Không có sản phẩm nào."); // Cập nhật thông báo khi không có dữ liệu
    }

    // code moi

    @Override
    public void onItemClick(int id) {
        Product product = dbHelper.getProductById(id);
        Intent intent = new Intent(getActivity(), ChiTietSanPhamActivity.class);
        intent.putExtra("thongtinsanpham", product);
        startActivity(intent);
    }

    private void updateRecyclerView(String selectedCategory) {
        phoneList.setVisibility(View.GONE);

        if (selectedCategory.equals("Điện thoại")) {
            phoneList.setVisibility(View.VISIBLE);
        } else if (selectedCategory.equals("Tai nghe")) {
        } else if (selectedCategory.equals("Sạc điện thoại")) {
        }
    }


}