package com.thuphi.vn.domain;

import java.time.LocalDate;

public class DeXuatThanhToanDTO {

    public String getTenChiPhi() {
        return tenChiPhi;
    }

    public void setTenChiPhi(String tenChiPhi) {
        this.tenChiPhi = tenChiPhi;
    }

    public String getTenCapBac() {
        return tenCapBac;
    }

    public void setTenCapBac(String tenCapBac) {
        this.tenCapBac = tenCapBac;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMaDeXuat() {
        return maDeXuat;
    }

    public void setMaDeXuat(String maDeXuat) {
        this.maDeXuat = maDeXuat;
    }

    public String getTenDeXuat() {
        return tenDeXuat;
    }

    public void setTenDeXuat(String tenDeXuat) {
        this.tenDeXuat = tenDeXuat;
    }

    public LocalDate getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(LocalDate tuNgay) {
        this.tuNgay = tuNgay;
    }

    public LocalDate getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(LocalDate denNgay) {
        this.denNgay = denNgay;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getTrangThaiTruongPhong() {
        return trangThaiTruongPhong;
    }

    public void setTrangThaiTruongPhong(String trangThaiTruongPhong) {
        this.trangThaiTruongPhong = trangThaiTruongPhong;
    }

    public String getTrangThaiPhongTaiVu() {
        return trangThaiPhongTaiVu;
    }

    public void setTrangThaiPhongTaiVu(String trangThaiPhongTaiVu) {
        this.trangThaiPhongTaiVu = trangThaiPhongTaiVu;
    }

    public String getTrangThaiBanLanhDao() {
        return trangThaiBanLanhDao;
    }

    public void setTrangThaiBanLanhDao(String trangThaiBanLanhDao) {
        this.trangThaiBanLanhDao = trangThaiBanLanhDao;
    }

    private Long id;
    private String maDeXuat;
    private String tenDeXuat;
    private LocalDate tuNgay;
    private LocalDate denNgay;
    private String thanhToan;
    private String trangThaiTruongPhong;
    private String trangThaiPhongTaiVu;
    private String trangThaiBanLanhDao;
    private String tenChiPhi;
    private String tenCapBac;
}
