package com.example.doan_sale.Admin;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.doan_sale.R;
import com.example.doan_sale.model.Voucher;
import com.example.doan_sale.ui.DBHelper;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {

    private List<Voucher> voucherList;

    public VoucherAdapter(List<Voucher> voucherList) {
        this.voucherList = voucherList;
    }
    private DBHelper dbHelper;
    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_item, parent, false);
        return new VoucherViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Voucher voucher = voucherList.get(position);
        // Cập nhật TextView với ngày tháng năm được định dạng
        holder.textViewVoucherCode.setText(voucher.getVoucherCode());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.textViewVoucherDiscount.setText(String.valueOf(voucher.getDiscount()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd//MM/yyyy");
        String startDate = dateFormat.format(new Date(voucher.getStart_Date()));
        String endDate = dateFormat.format(new Date(voucher.getEnd_Date()));
        holder.textViewVoucherStart.setText(String.valueOf(voucher.getStart_Date()));
        holder.textViewVoucherEnd.setText(String.valueOf(voucher.getEnd_Date()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Xóa Voucher");
                builder.setMessage("Bạn có chắc là muốn xóa không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        VoucherListActivity activity = (VoucherListActivity) view.getContext();
                        activity.deleteVoucher(voucher.getVoucherId());
                    }
                });
                builder.setNegativeButton("Không", null);
                builder.show();
                return true;
            }
        });
    }
    @Override
    public int getItemCount() {
        return voucherList.size();
    }
    public class VoucherViewHolder extends RecyclerView.ViewHolder {
        TextView textViewVoucherCode;
        TextView textViewVoucherDiscount;
        TextView textViewVoucherStart;
        TextView textViewVoucherEnd;
        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewVoucherCode = itemView.findViewById(R.id.text_view_voucher_code);
            textViewVoucherDiscount = itemView.findViewById(R.id.text_view_voucher_discount);
            textViewVoucherStart = itemView.findViewById(R.id.text_view_voucher_start_date);
            textViewVoucherEnd = itemView.findViewById(R.id.text_view_voucher_end_date);
        }
    }
}

