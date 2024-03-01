package com.example.doan_sale;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.doan_sale.ui.MainActivity;

public class ThankYouActivity extends AppCompatActivity {
Button hoanthanh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);
        hoanthanh=(Button) findViewById(R.id.complete);
        hoanthanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(ThankYouActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
