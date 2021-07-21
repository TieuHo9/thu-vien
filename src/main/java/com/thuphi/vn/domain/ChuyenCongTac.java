package com.thuphi.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ChuyenCongTac.
 */
@Entity
@Table(name = "chuyen_cong_tac")
public class ChuyenCongTac implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ma_chuyen_di")
    private String maChuyenDi;

    @Column(name = "ten_chuyen_di")
    private String tenChuyenDi;

    @Column(name = "thoi_gian_tu")
    private LocalDate thoiGianTu;

    @Column(name = "thoi_gian_den")
    private LocalDate thoiGianDen;

    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ten_nhan_vien")
    private String tenNhanVien;

    @Column(name = "so_dien_thoai")
    private String soDienThoai;

    @Column(name = "dia_diem")
    private String diaDiem;

    @Column(name = "so_tien")
    private String soTien;

    @ManyToMany
    @JoinTable(
        name = "rel_chuyen_cong_tac__nhanvien",
        joinColumns = @JoinColumn(name = "chuyen_cong_tac_id"),
        inverseJoinColumns = @JoinColumn(name = "nhanvien_id")
    )
    @JsonIgnoreProperties(value = { "phongbans", "capbacs", "chuyencongtacs", "pbans" }, allowSetters = true)
    private Set<NhanVien> nhanviens = new HashSet<>();

    @ManyToMany(mappedBy = "chuyencts")
    @JsonIgnoreProperties(value = { "chuyencts" }, allowSetters = true)
    private Set<NhanVienCongTac> nhanviencts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChuyenCongTac id(Long id) {
        this.id = id;
        return this;
    }

    public String getMaChuyenDi() {
        return this.maChuyenDi;
    }

    public ChuyenCongTac maChuyenDi(String maChuyenDi) {
        this.maChuyenDi = maChuyenDi;
        return this;
    }

    public void setMaChuyenDi(String maChuyenDi) {
        this.maChuyenDi = maChuyenDi;
    }

    public String getTenChuyenDi() {
        return this.tenChuyenDi;
    }

    public ChuyenCongTac tenChuyenDi(String tenChuyenDi) {
        this.tenChuyenDi = tenChuyenDi;
        return this;
    }

    public void setTenChuyenDi(String tenChuyenDi) {
        this.tenChuyenDi = tenChuyenDi;
    }

    public LocalDate getThoiGianTu() {
        return this.thoiGianTu;
    }

    public ChuyenCongTac thoiGianTu(LocalDate thoiGianTu) {
        this.thoiGianTu = thoiGianTu;
        return this;
    }

    public void setThoiGianTu(LocalDate thoiGianTu) {
        this.thoiGianTu = thoiGianTu;
    }

    public LocalDate getThoiGianDen() {
        return this.thoiGianDen;
    }

    public ChuyenCongTac thoiGianDen(LocalDate thoiGianDen) {
        this.thoiGianDen = thoiGianDen;
        return this;
    }

    public void setThoiGianDen(LocalDate thoiGianDen) {
        this.thoiGianDen = thoiGianDen;
    }

    public String getMaNhanVien() {
        return this.maNhanVien;
    }

    public ChuyenCongTac maNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        return this;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return this.tenNhanVien;
    }

    public ChuyenCongTac tenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
        return this;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getSoDienThoai() {
        return this.soDienThoai;
    }

    public ChuyenCongTac soDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
        return this;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaDiem() {
        return this.diaDiem;
    }

    public ChuyenCongTac diaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
        return this;
    }

    public void setDiaDiem(String diaDiem) {
        this.diaDiem = diaDiem;
    }

    public String getSoTien() {
        return this.soTien;
    }

    public ChuyenCongTac soTien(String soTien) {
        this.soTien = soTien;
        return this;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public Set<NhanVien> getNhanviens() {
        return this.nhanviens;
    }

    public ChuyenCongTac nhanviens(Set<NhanVien> nhanViens) {
        this.setNhanviens(nhanViens);
        return this;
    }

    public ChuyenCongTac addNhanvien(NhanVien nhanVien) {
        this.nhanviens.add(nhanVien);
        nhanVien.getChuyencongtacs().add(this);
        return this;
    }

    public ChuyenCongTac removeNhanvien(NhanVien nhanVien) {
        this.nhanviens.remove(nhanVien);
        nhanVien.getChuyencongtacs().remove(this);
        return this;
    }

    public void setNhanviens(Set<NhanVien> nhanViens) {
        this.nhanviens = nhanViens;
    }

    public Set<NhanVienCongTac> getNhanviencts() {
        return this.nhanviencts;
    }

    public ChuyenCongTac nhanviencts(Set<NhanVienCongTac> nhanVienCongTacs) {
        this.setNhanviencts(nhanVienCongTacs);
        return this;
    }

    public ChuyenCongTac addNhanvienct(NhanVienCongTac nhanVienCongTac) {
        this.nhanviencts.add(nhanVienCongTac);
        nhanVienCongTac.getChuyencts().add(this);
        return this;
    }

    public ChuyenCongTac removeNhanvienct(NhanVienCongTac nhanVienCongTac) {
        this.nhanviencts.remove(nhanVienCongTac);
        nhanVienCongTac.getChuyencts().remove(this);
        return this;
    }

    public void setNhanviencts(Set<NhanVienCongTac> nhanVienCongTacs) {
        if (this.nhanviencts != null) {
            this.nhanviencts.forEach(i -> i.removeChuyenct(this));
        }
        if (nhanVienCongTacs != null) {
            nhanVienCongTacs.forEach(i -> i.addChuyenct(this));
        }
        this.nhanviencts = nhanVienCongTacs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChuyenCongTac)) {
            return false;
        }
        return id != null && id.equals(((ChuyenCongTac) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChuyenCongTac{" +
            "id=" + getId() +
            ", maChuyenDi='" + getMaChuyenDi() + "'" +
            ", tenChuyenDi='" + getTenChuyenDi() + "'" +
            ", thoiGianTu='" + getThoiGianTu() + "'" +
            ", thoiGianDen='" + getThoiGianDen() + "'" +
            ", maNhanVien='" + getMaNhanVien() + "'" +
            ", tenNhanVien='" + getTenNhanVien() + "'" +
            ", soDienThoai='" + getSoDienThoai() + "'" +
            ", diaDiem='" + getDiaDiem() + "'" +
            ", soTien='" + getSoTien() + "'" +
            "}";
    }
}
