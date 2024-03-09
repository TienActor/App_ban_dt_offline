package com.example.doan_sale.Admin;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.doan_sale.R;
import com.example.doan_sale.ui.DBHelper;
import com.example.doan_sale.ui.Ultils;
public class AddVoucherActivity extends AppCompatActivity {
    private EditText editTextVoucherCode, editTextVoucherDiscount, editTextVoucherProductId,
            editTextVoucherStartDate, editTextVoucherEndDate;
    private Button buttonAddVoucher,btView;
    private DBHelper dbHelper;
   ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_voucher);
        dbHelper = new DBHelper(this);
        editTextVoucherCode = findViewById(R.id.edit_text_voucher_code);
        editTextVoucherDiscount = findViewById(R.id.edit_text_voucher_discount);
        editTextVoucherProductId = findViewById(R.id.edit_text_voucher_product_id);
        editTextVoucherStartDate = findViewById(R.id.edit_text_voucher_start_date);
        editTextVoucherEndDate = findViewById(R.id.edit_text_voucher_end_date);
        btView = findViewById(R.id.btn_View);
        btView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddVoucherActivity.this,VoucherListActivity.class);
                startActivity(intent);
            }
        });
        back=findViewById(R.id.ib_Back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddVoucherActivity.this,AdminActivity.class);
                startActivity(i);
            }
        });
        buttonAddVoucher = findViewById(R.id.button_add_voucher);
        buttonAddVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String voucherCode = editTextVoucherCode.getText().toString().trim();
                String voucherDiscount = editTextVoucherDiscount.getText().toString().trim();
                String voucherProductId = editTextVoucherProductId.getText().toString().trim();
                String voucherStartDate = editTextVoucherStartDate.getText().toString().trim();
                String voucherEndDate = editTextVoucherEndDate.getText().toString().trim();
                if (!voucherCode.isEmpty() && !voucherDiscount.isEmpty() && !voucherProductId.isEmpty()
                        && !voucherStartDate.isEmpty() && !voucherEndDate.isEmpty()) {
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(Ultils.VOUCHER_CODE, voucherCode);
                    values.put(Ultils.VOUCHER_DISCOUNT, voucherDiscount);
                    values.put(Ultils.VOUCHER_PRODUCT_ID, voucherProductId);
                    values.put(Ultils.VOUCHER_START_DATE, voucherStartDate);
                    values.put(Ultils.VOUCHER_END_DATE, voucherEndDate);
                    long newRowId = db.insert(Ultils.TABLE_VOUCHER, null, values);
                    if (newRowId != -1) {
                        Toast.makeText(AddVoucherActivity.this, "Voucher added successfully", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(AddVoucherActivity.this, AdminActivity.class);
//                        startActivity(intent);
                    } else {
                        Toast.makeText(AddVoucherActivity.this, "Error adding voucher", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}



