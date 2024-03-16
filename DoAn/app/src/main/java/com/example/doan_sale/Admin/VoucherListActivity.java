package com.example.doan_sale.Admin;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doan_sale.R;
import com.example.doan_sale.model.Voucher;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.Ultils;
import java.util.ArrayList;
import java.util.List;

public class VoucherListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private VoucherAdapter adapter;
    private List<Voucher> voucherList;
    private DBHelper dbHelper;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_list);

        dbHelper = new DBHelper(this);
        voucherList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler_view_vouchers);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new VoucherAdapter(voucherList);
        recyclerView.setAdapter(adapter);
        back=(Button) findViewById(R.id.ve);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(VoucherListActivity.this,AdminActivity.class);
                startActivity(i);
            }
        });
        loadVoucherList();
    }


    private void loadVoucherList() {
        voucherList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                Ultils.VOUCHER_ID,
                Ultils.VOUCHER_CODE,
                Ultils.VOUCHER_DISCOUNT,
                Ultils.VOUCHER_START_DATE,
                Ultils.VOUCHER_END_DATE,
                Ultils.VOUCHER_PRODUCT_ID
        };
        Cursor cursor = db.query(
                Ultils.TABLE_VOUCHER,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext()) {
            String voucherId = cursor.getString(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_ID));
            String voucherCode = cursor.getString(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_CODE));
            int voucherProductId = cursor.getInt(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_PRODUCT_ID));
            int voucherDiscount = cursor.getInt(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_DISCOUNT));
            long voucherStart = cursor.getLong(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_START_DATE));
            long voucherEnd = cursor.getLong(cursor.getColumnIndexOrThrow(Ultils.VOUCHER_END_DATE));
            Voucher voucher = new Voucher(voucherId, voucherCode,voucherProductId,voucherDiscount, voucherStart, voucherEnd);
            voucherList.add(voucher);
        }
        cursor.close();
        VoucherAdapter adapter = new VoucherAdapter(voucherList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
    public void deleteVoucher(String voucherId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = Ultils.VOUCHER_ID + " LIKE ?";
        String[] selectionArgs = { voucherId };
        int deletedRows = db.delete(Ultils.TABLE_VOUCHER, selection, selectionArgs);
        if (deletedRows > 0) {
            Toast.makeText(this, "Voucher deleted successfully", Toast.LENGTH_SHORT).show();
            loadVoucherList();
        } else {
            Toast.makeText(this, "Error deleting voucher", Toast.LENGTH_SHORT).show();
        }
    }
}
