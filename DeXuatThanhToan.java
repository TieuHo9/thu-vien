package com.thuphi.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A DeXuatThanhToan.
 */
@Entity
@Table(name = "de_xuat_thanh_toan")
public class DeXuatThanhToan implements Serializable {

    public String getTenChiPhi() {
        return tenChiPhi;
    }

    public void setTenChiPhi(String tenChiPhi) {
        this.tenChiPhi = tenChiPhi;
    }

    public String getTienDinhMuc() {
        return tienDinhMuc;
    }

    public void setTienDinhMuc(String tienDinhMuc) {
        this.tienDinhMuc = tienDinhMuc;
    }

    public String getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(String thanhToan) {
        this.thanhToan = thanhToan;
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ma_de_xuat")
    private String maDeXuat;

    @Column(name = "ten_de_xuat")
    private String tenDeXuat;

    @Column(name = "tu_ngay")
    private LocalDate tuNgay;

    @Column(name = "den_ngay")
    private LocalDate denNgay;

    @Column(name = "trang_thai_thanh_toan")
    private String thanhToan;

    @Column(name = "trang_thai_truong_phong")
    private String trangThaiTruongPhong;

    @Column(name = "trang_thai_phong_tai_vu")
    private String trangThaiPhongTaiVu;

    @Column(name = "trang_thai_ban_lanh_dao")
    private String trangThaiBanLanhDao;

    @Column(name = "ten_chi_phi")
    private String tenChiPhi;

    @Column(name = "tien_dinh_muc")
    private String tienDinhMuc;

    @ManyToMany
    @JoinTable(
        name = "rel_de_xuat_thanh_toan__chiphi",
        joinColumns = @JoinColumn(name = "de_xuat_thanh_toan_id"),
        inverseJoinColumns = @JoinColumn(name = "chiphi_id")
    )
    @JsonIgnoreProperties(value = { "dexuats" }, allowSetters = true)
    private Set<ChiPhi> chiphis = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_de_xuat_thanh_toan__dmuc",
        joinColumns = @JoinColumn(name = "de_xuat_thanh_toan_id"),
        inverseJoinColumns = @JoinColumn(name = "dmuc_id")
    )
    @JsonIgnoreProperties(value = { "cbacs", "dxuattts" }, allowSetters = true)
    private Set<DinhMuc> dmucs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DeXuatThanhToan id(Long id) {
        this.id = id;
        return this;
    }

    public String getMaDeXuat() {
        return this.maDeXuat;
    }

    public DeXuatThanhToan maDeXuat(String maDeXuat) {
        this.maDeXuat = maDeXuat;
        return this;
    }

    public void setMaDeXuat(String maDeXuat) {
        this.maDeXuat = maDeXuat;
    }

    public String getTenDeXuat() {
        return this.tenDeXuat;
    }

    public DeXuatThanhToan tenDeXuat(String tenDeXuat) {
        this.tenDeXuat = tenDeXuat;
        return this;
    }

    public void setTenDeXuat(String tenDeXuat) {
        this.tenDeXuat = tenDeXuat;
    }

    public LocalDate getTuNgay() {
        return this.tuNgay;
    }

    public DeXuatThanhToan tuNgay(LocalDate tuNgay) {
        this.tuNgay = tuNgay;
        return this;
    }

    public void setTuNgay(LocalDate tuNgay) {
        this.tuNgay = tuNgay;
    }

    public LocalDate getDenNgay() {
        return this.denNgay;
    }

    public DeXuatThanhToan denNgay(LocalDate denNgay) {
        this.denNgay = denNgay;
        return this;
    }

    public void setDenNgay(LocalDate denNgay) {
        this.denNgay = denNgay;
    }

    public String getTrangThaiTruongPhong() {
        return this.trangThaiTruongPhong;
    }

    public DeXuatThanhToan trangThaiTruongPhong(String trangThaiTruongPhong) {
        this.trangThaiTruongPhong = trangThaiTruongPhong;
        return this;
    }

    public void setTrangThaiTruongPhong(String trangThaiTruongPhong) {
        this.trangThaiTruongPhong = trangThaiTruongPhong;
    }

    public String getTrangThaiPhongTaiVu() {
        return this.trangThaiPhongTaiVu;
    }

    public DeXuatThanhToan trangThaiPhongTaiVu(String trangThaiPhongTaiVu) {
        this.trangThaiPhongTaiVu = trangThaiPhongTaiVu;
        return this;
    }

    public void setTrangThaiPhongTaiVu(String trangThaiPhongTaiVu) {
        this.trangThaiPhongTaiVu = trangThaiPhongTaiVu;
    }

    public String getTrangThaiBanLanhDao() {
        return this.trangThaiBanLanhDao;
    }

    public DeXuatThanhToan trangThaiBanLanhDao(String trangThaiBanLanhDao) {
        this.trangThaiBanLanhDao = trangThaiBanLanhDao;
        return this;
    }

    public void setTrangThaiBanLanhDao(String trangThaiBanLanhDao) {
        this.trangThaiBanLanhDao = trangThaiBanLanhDao;
    }

    public Set<ChiPhi> getChiphis() {
        return this.chiphis;
    }

    public DeXuatThanhToan chiphis(Set<ChiPhi> chiPhis) {
        this.setChiphis(chiPhis);
        return this;
    }

    public DeXuatThanhToan addChiphi(ChiPhi chiPhi) {
        this.chiphis.add(chiPhi);
        chiPhi.getDexuats().add(this);
        return this;
    }

    public DeXuatThanhToan removeChiphi(ChiPhi chiPhi) {
        this.chiphis.remove(chiPhi);
        chiPhi.getDexuats().remove(this);
        return this;
    }

    public void setChiphis(Set<ChiPhi> chiPhis) {
        this.chiphis = chiPhis;
    }

    public Set<DinhMuc> getDmucs() {
        return this.dmucs;
    }

    public DeXuatThanhToan dmucs(Set<DinhMuc> dinhMucs) {
        this.setDmucs(dinhMucs);
        return this;
    }

    public DeXuatThanhToan addDmuc(DinhMuc dinhMuc) {
        this.dmucs.add(dinhMuc);
        dinhMuc.getDxuattts().add(this);
        return this;
    }

    public DeXuatThanhToan removeDmuc(DinhMuc dinhMuc) {
        this.dmucs.remove(dinhMuc);
        dinhMuc.getDxuattts().remove(this);
        return this;
    }

    public void setDmucs(Set<DinhMuc> dinhMucs) {
        this.dmucs = dinhMucs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DeXuatThanhToan)) {
            return false;
        }
        return id != null && id.equals(((DeXuatThanhToan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DeXuatThanhToan{" +
            "id=" + getId() +
            ", maDeXuat='" + getMaDeXuat() + "'" +
            ", tenDeXuat='" + getTenDeXuat() + "'" +
            ", tuNgay='" + getTuNgay() + "'" +
            ", denNgay='" + getDenNgay() + "'" +
            ", trangThaiTruongPhong='" + getTrangThaiTruongPhong() + "'" +
            ", trangThaiPhongTaiVu='" + getTrangThaiPhongTaiVu() + "'" +
            ", trangThaiBanLanhDao='" + getTrangThaiBanLanhDao() + "'" +
            "}";
    }
}
