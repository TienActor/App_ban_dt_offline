package com.example.doan_sale.fragment;

import android.annotation.SuppressLint;
import android.app.appsearch.SearchResults;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doan_sale.BannerAdapter;
import com.example.doan_sale.Product.ChiTietSanPhamActivity;
import com.example.doan_sale.Product.ProDataQuery;
import com.example.doan_sale.Product.ProductAdapter;
import com.example.doan_sale.R;
import com.example.doan_sale.model.GioHang;
import com.example.doan_sale.model.Product;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.MainActivity;
import com.example.doan_sale.ui.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment implements MainAdapter.MainCallBack {
    Button backl;
    RecyclerView rvListCode;
    ArrayList<Product> lstPro, lstProOriginal;
    private EditText searchEditText;
    private Button searchButton;
    MainAdapter mainAdapter;

    ImageView ivthich;
    private ViewPager bannerViewPager;
    private BannerAdapter bannerAdapter;
    BannerAdapter photoAdapter;
    CircleIndicator circleIndicator;
    List<Integer> listPhoto;
    ViewPager viewPager;
    private Handler handler = new Handler();
    private int currentPage = 0;
    public static ArrayList<GioHang> manggiohang;
    ArrayList<Product> originalList = new ArrayList<>();
    private RecyclerView rcvHotProducts;

    private ArrayList<Product> hotProductList;

    @Override
    public void onItemClick(int id) {
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (bannerViewPager.getAdapter().getCount() != 0) {
                currentPage = (currentPage + 1) % bannerViewPager.getAdapter().getCount();
                bannerViewPager.setCurrentItem(currentPage, true);
                handler.postDelayed(runnable, 3000);
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvListCode = view.findViewById(R.id.rcv_Phone);
        // Lưu trữ danh sách sản phẩm ban đầu
        lstProOriginal = ProDataQuery.getALL(getActivity());
        //lay du lieu
        lstPro = ProDataQuery.getALL(getActivity());
        mainAdapter = new MainAdapter(lstPro);
        mainAdapter.setMainCallBack(this);
        mainAdapter = new MainAdapter(getContext(), lstPro, new MainAdapter.ItemClickListener() {
            @Override
            public void onItemCick(Product product) {
                onClickrecyclerview(product);
            }
        });


        //gan Adapter
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rvListCode.setAdapter(mainAdapter);
        rvListCode.setLayoutManager(gridLayoutManager);
        // Khởi tạo RecyclerView và Adapter cho sản phẩm đang hot
        rcvHotProducts = view.findViewById(R.id.rcv_hot_products);
        hotProductList = new ArrayList<>();
        mainAdapter =new MainAdapter(getContext(), hotProductList, new MainAdapter.ItemClickListener(){
            @Override
            public void onItemCick(Product product) {
                onClickrecyclerview(product);
            }
        });
        rcvHotProducts.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rcvHotProducts.setAdapter(mainAdapter);

        // Load dữ liệu sản phẩm đang hot
        loadHotProducts();


        if(manggiohang!=null){

        }else{
            manggiohang=new ArrayList<>();
        }
        // ... Khởi tạo RecyclerView, lấy dữ liệu và gán Adapter đã có

        searchEditText = view.findViewById(R.id.search_text);
        searchButton = view.findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = searchEditText.getText().toString();
                if (!keyword.isEmpty()) {
                    Intent intent = new Intent(getActivity(), SearchResults.class);
                    intent.putExtra("SEARCH_QUERY", keyword);
                    startActivity(intent);
                }
            }
        });


//        searchEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String keyword = s.toString();
//                if (!keyword.isEmpty()) {
//                    rvListCode.setVisibility(View.GONE); // Ẩn RecyclerView chính
//                    rcvHotProducts.setVisibility(View.GONE); // Ẩn RecyclerView sản phẩm hot
//                    // Tìm kiếm theo từ khóa
//                    mainAdapter.search(keyword);
//                } else {
//                    rvListCode.setVisibility(View.VISIBLE); // Hiển thị RecyclerView chính
//                    rcvHotProducts.setVisibility(View.VISIBLE); // Hiển thị RecyclerView sản phẩm hot
//                    // Trả về danh sách ban đầu
//                    mainAdapter.search("");
//                }
//            }
//        });

        anhXa(view);
        bannerAdapter = new BannerAdapter(getContext(), listPhoto);
        bannerViewPager.setAdapter(bannerAdapter);
        // Bắt đầu chạy banner tự động
        handler.postDelayed(runnable, 3000);
        // Load products
        listPhoto = listBanner();
        //set Adapter
        photoAdapter = new BannerAdapter(getContext(), listPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        //gan Adapter

        // Inflate the layout for this fragment
        return view;
    }
    private void onClickrecyclerview(Product product){
        Intent intent=new Intent(getContext(), ChiTietSanPhamActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("thongtinsanpham", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    private void anhXa(View view){

        circleIndicator = view.findViewById(R.id.circleIndicator);
        viewPager = view.findViewById(R.id.banner_viewpager);
        bannerViewPager = view.findViewById(R.id.banner_viewpager);

    }
    private List<Integer> listBanner() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.banner_01);
        list.add(R.drawable.banner_02);
        list.add(R.drawable.banner_03);
        return list;
    }
    private void loadHotProducts() {
        // Gọi đến phương thức getHotProducts từ lớp quản lý cơ sở dữ liệu và cập nhật dữ liệu
        DBHelper db = new DBHelper(getContext());
        hotProductList.clear();
        hotProductList.addAll(db.getHotProducts());
        mainAdapter.notifyDataSetChanged();
    }

}