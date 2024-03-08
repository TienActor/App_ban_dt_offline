package com.example.doan_sale.LSDH;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doan_sale.R;
import com.example.doan_sale.model.Order;
import com.example.doan_sale.ui.MainActivity;
import java.text.DecimalFormat;
import java.util.List;

public class LSDHAdapter extends RecyclerView.Adapter<LSDHAdapter.ViewHolder> {
    private Context context;
    private List<Order> orders;
    public LSDHAdapter(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_lsdh_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderID.setText(String.valueOf(order.getOrderId()));
        holder.orderDate.setText(order.getDate());
        // Định dạng số thành dạng tiền tệ
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String formattedTotal = decimalFormat.format(order.getTotal()) + " Đ"; // Giả sử đơn vị tiền là Đồng
        holder.orderTotal.setText(formattedTotal);
        holder.orderAddress.setText(String.valueOf(order.getAddress()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderID, orderDate, orderTotal,orderAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderID = itemView.findViewById(R.id.orderID);
            orderDate = itemView.findViewById(R.id.orderDATE);
            orderTotal = itemView.findViewById(R.id.orderTOTAL);
            orderAddress=itemView.findViewById(R.id.orderADDRESS);
        }
    }

}
