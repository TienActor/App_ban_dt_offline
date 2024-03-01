package com.example.doan_sale.ui;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.example.doan_sale.Product.ChiTietSanPhamActivity;
import com.example.doan_sale.R;
import com.example.doan_sale.model.Product;
import java.util.ArrayList;
import java.util.List;

public class SearchResults extends AppCompatActivity  implements MainAdapter.MainCallBack {
    private RecyclerView rvSearchResults;
    private MainAdapter mainAdapter;
    private ArrayList<Product> searchResultsList;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        rvSearchResults = findViewById(R.id.rv_search_results);
        rvSearchResults.setLayoutManager(new GridLayoutManager(this,2));
        back = findViewById(R.id.backtt);
        searchResultsList = new ArrayList<>();
        mainAdapter = new MainAdapter(SearchResults.this, searchResultsList, new MainAdapter.ItemClickListener() {
            // Xử lý khi một sản phẩm được click, ví dụ mở chi tiết sản phẩm
            @Override
            public void onItemCick(Product product) {
                onClickrecyclerview(product);
            }
        });
        rvSearchResults.setAdapter(mainAdapter);
        // Lấy từ khóa từ Intent
        String query = getIntent().getStringExtra("SEARCH_QUERY");
        performSearch(query);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void performSearch(String query) {
        // Giả sử bạn có một phương thức để tìm kiếm sản phẩm dựa trên từ khóa
        List<Product> results = searchInDatabase(query); // Thay thế bằng phương thức tìm kiếm của bạn
        // Cập nhật kết quả tìm kiếm
        searchResultsList.clear();
        searchResultsList.addAll(results);
        mainAdapter.notifyDataSetChanged();
    }
    private List<Product> searchInDatabase(String query) {
        // Thay thế phần này với logic tìm kiếm thực tế của bạn
        // Ví dụ: truy vấn cơ sở dữ liệu SQLite, hoặc sử dụng một lớp quản lý dữ liệu, v.v.
        DBHelper dbHelper = new DBHelper(this);
        return dbHelper.searchProducts(query);
    }
    @Override
    public void onItemClick(int id) {
    }
    private void onClickrecyclerview(Product product){
        Intent intent=new Intent(SearchResults.this, ChiTietSanPhamActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("thongtinsanpham", product);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
