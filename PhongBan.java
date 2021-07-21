package com.thuphi.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "phong_ban")
public class PhongBan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ma_phong")
    private String maPhong;

    @Column(name = "ten_phong")
    private String tenPhong;

    @ManyToMany(mappedBy = "phongbans")
    @JsonIgnoreProperties(value = { "phongbans", "capbacs", "chuyencongtacs", "pbans" }, allowSetters = true)
    private Set<NhanVien> nhanvs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PhongBan id(Long id) {
        this.id = id;
        return this;
    }

    public String getMaPhong() {
        return this.maPhong;
    }

    public PhongBan maPhong(String maPhong) {
        this.maPhong = maPhong;
        return this;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getTenPhong() {
        return this.tenPhong;
    }

    public PhongBan tenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
        return this;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public Set<NhanVien> getNhanvs() {
        return this.nhanvs;
    }

    public PhongBan nhanvs(Set<NhanVien> nhanViens) {
        this.setNhanvs(nhanViens);
        return this;
    }

    public PhongBan addNhanv(NhanVien nhanVien) {
        this.nhanvs.add(nhanVien);
        nhanVien.getPhongbans().add(this);
        return this;
    }

    public PhongBan removeNhanv(NhanVien nhanVien) {
        this.nhanvs.remove(nhanVien);
        nhanVien.getPhongbans().remove(this);
        return this;
    }

    public void setNhanvs(Set<NhanVien> nhanViens) {
        if (this.nhanvs != null) {
            this.nhanvs.forEach(i -> i.removePhongban(this));
        }
        if (nhanViens != null) {
            nhanViens.forEach(i -> i.addPhongban(this));
        }
        this.nhanvs = nhanViens;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhongBan)) {
            return false;
        }
        return id != null && id.equals(((PhongBan) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhongBan{" +
            "id=" + getId() +
            ", maPhong='" + getMaPhong() + "'" +
            ", tenPhong='" + getTenPhong() + "'" +
            "}";
    }
}
