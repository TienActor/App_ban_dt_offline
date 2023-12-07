package com.example.doan_sale.model;

public class Voucher {
    private String voucherId;
    private String voucherCode;
    private int discount;
    private long start_Date;
    private long end_Date;
    private int voucherProductId;

    public int getVoucherProductId() {
        return voucherProductId;
    }

    public void setVoucherProductId(int voucherProductId) {
        this.voucherProductId = voucherProductId;
    }

    public void setStart_Date(long start_Date) {
        this.start_Date = start_Date;
    }

    public long getEnd_Date() {
        return end_Date;
    }

    public void setEnd_Date(long end_Date) {
        this.end_Date = end_Date;
    }


    public long getStart_Date() {
        return start_Date;
    }
    public Voucher(String voucherId, String voucherCode, int voucherProductId,int discount, long start_Date, long end_Date) {
        this.voucherId = voucherId;
        this.voucherCode = voucherCode;
        this.discount = discount;
        this.start_Date = start_Date;
        this.end_Date = end_Date;
        this.voucherProductId = voucherProductId;
    }

    public String getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(String voucherId) {
        this.voucherId = voucherId;
    }

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

}


