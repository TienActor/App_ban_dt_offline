package com.example.doan_sale.model;
import java.io.Serializable;

public class GioHang implements Serializable {
    public int idsp;
    public String tensp;
    public long Giasp;
    public String Hinhsp;
    public int Soluong;
    private boolean isDiscountApplied;
    public GioHang(int idsp, String tensp, long giasp, String hinhsp, int soluong) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.Giasp = giasp;
        this.Hinhsp = hinhsp;
        this.Soluong = soluong;
    }
    public GioHang(int idsp, String tensp, String hinhsp,long giasp) {
        this.idsp = idsp;
        this.tensp = tensp;
        this.Hinhsp = hinhsp;
        this.Giasp= giasp;
    }
    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }
    public long getGiasp() {
        return Giasp;
    }

    public void setGiasp(long giasp) {
        Giasp = giasp;
    }

    public String getHinhsp() {
        return Hinhsp;
    }

    public void setHinhsp(String hinhsp) {
        Hinhsp = hinhsp;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int soluong) {
        Soluong = soluong;
    }

    public boolean isDiscountApplied() {
        return isDiscountApplied;
    }

    public void setDiscountApplied(boolean discountApplied) {
        isDiscountApplied = discountApplied;
    }
}
