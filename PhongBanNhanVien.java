package com.thuphi.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A PhongBanNhanVien.
 */
@Entity
@Table(name = "phong_ban_nhan_vien")
public class PhongBanNhanVien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ma_phong")
    private String maPhong;

    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @ManyToMany
    @JoinTable(
        name = "rel_phong_ban_nhan_vien__nvien",
        joinColumns = @JoinColumn(name = "phong_ban_nhan_vien_id"),
        inverseJoinColumns = @JoinColumn(name = "nvien_id")
    )
    @JsonIgnoreProperties(value = { "phongbans", "capbacs", "chuyencongtacs", "pbans" }, allowSetters = true)
    private Set<NhanVien> nviens = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhongBanNhanVien id(Long id) {
        this.id = id;
        return this;
    }

    public String getMaPhong() {
        return this.maPhong;
    }

    public PhongBanNhanVien maPhong(String maPhong) {
        this.maPhong = maPhong;
        return this;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaNhanVien() {
        return this.maNhanVien;
    }

    public PhongBanNhanVien maNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        return this;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public Set<NhanVien> getNviens() {
        return this.nviens;
    }

    public PhongBanNhanVien nviens(Set<NhanVien> nhanViens) {
        this.setNviens(nhanViens);
        return this;
    }

    public PhongBanNhanVien addNvien(NhanVien nhanVien) {
        this.nviens.add(nhanVien);
        nhanVien.getPbans().add(this);
        return this;
    }

    public PhongBanNhanVien removeNvien(NhanVien nhanVien) {
        this.nviens.remove(nhanVien);
        nhanVien.getPbans().remove(this);
        return this;
    }

    public void setNviens(Set<NhanVien> nhanViens) {
        this.nviens = nhanViens;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhongBanNhanVien)) {
            return false;
        }
        return id != null && id.equals(((PhongBanNhanVien) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhongBanNhanVien{" +
            "id=" + getId() +
            ", maPhong='" + getMaPhong() + "'" +
            ", maNhanVien='" + getMaNhanVien() + "'" +
            "}";
    }
}
