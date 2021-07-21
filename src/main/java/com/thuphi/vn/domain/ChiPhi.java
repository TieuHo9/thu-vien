package com.thuphi.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ChiPhi.
 */
@Entity
@Table(name = "chi_phi")
public class ChiPhi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "loai_chi_phi")
    private String loaiChiPhi;

    @Column(name = "so_tien")
    private String soTien;

    @Column(name = "don_vi_tien_te")
    private String donViTienTe;

    @ManyToMany(mappedBy = "chiphis")
    @JsonIgnoreProperties(value = { "chiphis", "dmucs" }, allowSetters = true)
    private Set<DeXuatThanhToan> dexuats = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChiPhi id(Long id) {
        this.id = id;
        return this;
    }

    public String getLoaiChiPhi() {
        return this.loaiChiPhi;
    }

    public ChiPhi loaiChiPhi(String loaiChiPhi) {
        this.loaiChiPhi = loaiChiPhi;
        return this;
    }

    public void setLoaiChiPhi(String loaiChiPhi) {
        this.loaiChiPhi = loaiChiPhi;
    }

    public String getSoTien() {
        return this.soTien;
    }

    public ChiPhi soTien(String soTien) {
        this.soTien = soTien;
        return this;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public String getDonViTienTe() {
        return this.donViTienTe;
    }

    public ChiPhi donViTienTe(String donViTienTe) {
        this.donViTienTe = donViTienTe;
        return this;
    }

    public void setDonViTienTe(String donViTienTe) {
        this.donViTienTe = donViTienTe;
    }

    public Set<DeXuatThanhToan> getDexuats() {
        return this.dexuats;
    }

    public ChiPhi dexuats(Set<DeXuatThanhToan> deXuatThanhToans) {
        this.setDexuats(deXuatThanhToans);
        return this;
    }

    public ChiPhi addDexuat(DeXuatThanhToan deXuatThanhToan) {
        this.dexuats.add(deXuatThanhToan);
        deXuatThanhToan.getChiphis().add(this);
        return this;
    }

    public ChiPhi removeDexuat(DeXuatThanhToan deXuatThanhToan) {
        this.dexuats.remove(deXuatThanhToan);
        deXuatThanhToan.getChiphis().remove(this);
        return this;
    }

    public void setDexuats(Set<DeXuatThanhToan> deXuatThanhToans) {
        if (this.dexuats != null) {
            this.dexuats.forEach(i -> i.removeChiphi(this));
        }
        if (deXuatThanhToans != null) {
            deXuatThanhToans.forEach(i -> i.addChiphi(this));
        }
        this.dexuats = deXuatThanhToans;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChiPhi)) {
            return false;
        }
        return id != null && id.equals(((ChiPhi) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChiPhi{" +
            "id=" + getId() +
            ", loaiChiPhi='" + getLoaiChiPhi() + "'" +
            ", soTien='" + getSoTien() + "'" +
            ", donViTienTe='" + getDonViTienTe() + "'" +
            "}";
    }
}
