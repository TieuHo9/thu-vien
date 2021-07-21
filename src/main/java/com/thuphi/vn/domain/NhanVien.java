package com.thuphi.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A NhanVien.
 */
@Entity
@Table(name = "nhan_vien")
public class NhanVien implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ten_nhan_vien")
    private String tenNhanVien;

    @Column(name = "phong_ban")
    private String phongBan;

    @Column(name = "cap_bac")
    private String capBac;

    @ManyToMany
    @JoinTable(
        name = "rel_nhan_vien__phongban",
        joinColumns = @JoinColumn(name = "nhan_vien_id"),
        inverseJoinColumns = @JoinColumn(name = "phongban_id")
    )
    @JsonIgnoreProperties(value = { "nhanvs" }, allowSetters = true)
    private Set<PhongBan> phongbans = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "rel_nhan_vien__capbac",
        joinColumns = @JoinColumn(name = "nhan_vien_id"),
        inverseJoinColumns = @JoinColumn(name = "capbac_id")
    )
    @JsonIgnoreProperties(value = { "nvs", "mucs" }, allowSetters = true)
    private Set<CapBac> capbacs = new HashSet<>();

    @ManyToMany(mappedBy = "nhanviens")
    @JsonIgnoreProperties(value = { "nhanviens", "nhanviencts" }, allowSetters = true)
    private Set<ChuyenCongTac> chuyencongtacs = new HashSet<>();

    @ManyToMany(mappedBy = "nviens")
    @JsonIgnoreProperties(value = { "nviens" }, allowSetters = true)
    private Set<PhongBanNhanVien> pbans = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NhanVien id(Long id) {
        this.id = id;
        return this;
    }

    public String getMaNhanVien() {
        return this.maNhanVien;
    }

    public NhanVien maNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        return this;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getTenNhanVien() {
        return this.tenNhanVien;
    }

    public NhanVien tenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
        return this;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public String getPhongBan() {
        return this.phongBan;
    }

    public NhanVien phongBan(String phongBan) {
        this.phongBan = phongBan;
        return this;
    }

    public void setPhongBan(String phongBan) {
        this.phongBan = phongBan;
    }

    public String getCapBac() {
        return this.capBac;
    }

    public NhanVien capBac(String capBac) {
        this.capBac = capBac;
        return this;
    }

    public void setCapBac(String capBac) {
        this.capBac = capBac;
    }

    public Set<PhongBan> getPhongbans() {
        return this.phongbans;
    }

    public NhanVien phongbans(Set<PhongBan> phongBans) {
        this.setPhongbans(phongBans);
        return this;
    }

    public NhanVien addPhongban(PhongBan phongBan) {
        this.phongbans.add(phongBan);
        phongBan.getNhanvs().add(this);
        return this;
    }

    public NhanVien removePhongban(PhongBan phongBan) {
        this.phongbans.remove(phongBan);
        phongBan.getNhanvs().remove(this);
        return this;
    }

    public void setPhongbans(Set<PhongBan> phongBans) {
        this.phongbans = phongBans;
    }

    public Set<CapBac> getCapbacs() {
        return this.capbacs;
    }

    public NhanVien capbacs(Set<CapBac> capBacs) {
        this.setCapbacs(capBacs);
        return this;
    }

    public NhanVien addCapbac(CapBac capBac) {
        this.capbacs.add(capBac);
        capBac.getNvs().add(this);
        return this;
    }

    public NhanVien removeCapbac(CapBac capBac) {
        this.capbacs.remove(capBac);
        capBac.getNvs().remove(this);
        return this;
    }

    public void setCapbacs(Set<CapBac> capBacs) {
        this.capbacs = capBacs;
    }

    public Set<ChuyenCongTac> getChuyencongtacs() {
        return this.chuyencongtacs;
    }

    public NhanVien chuyencongtacs(Set<ChuyenCongTac> chuyenCongTacs) {
        this.setChuyencongtacs(chuyenCongTacs);
        return this;
    }

    public NhanVien addChuyencongtac(ChuyenCongTac chuyenCongTac) {
        this.chuyencongtacs.add(chuyenCongTac);
        chuyenCongTac.getNhanviens().add(this);
        return this;
    }

    public NhanVien removeChuyencongtac(ChuyenCongTac chuyenCongTac) {
        this.chuyencongtacs.remove(chuyenCongTac);
        chuyenCongTac.getNhanviens().remove(this);
        return this;
    }

    public void setChuyencongtacs(Set<ChuyenCongTac> chuyenCongTacs) {
        if (this.chuyencongtacs != null) {
            this.chuyencongtacs.forEach(i -> i.removeNhanvien(this));
        }
        if (chuyenCongTacs != null) {
            chuyenCongTacs.forEach(i -> i.addNhanvien(this));
        }
        this.chuyencongtacs = chuyenCongTacs;
    }

    public Set<PhongBanNhanVien> getPbans() {
        return this.pbans;
    }

    public NhanVien pbans(Set<PhongBanNhanVien> phongBanNhanViens) {
        this.setPbans(phongBanNhanViens);
        return this;
    }

    public NhanVien addPban(PhongBanNhanVien phongBanNhanVien) {
        this.pbans.add(phongBanNhanVien);
        phongBanNhanVien.getNviens().add(this);
        return this;
    }

    public NhanVien removePban(PhongBanNhanVien phongBanNhanVien) {
        this.pbans.remove(phongBanNhanVien);
        phongBanNhanVien.getNviens().remove(this);
        return this;
    }

    public void setPbans(Set<PhongBanNhanVien> phongBanNhanViens) {
        if (this.pbans != null) {
            this.pbans.forEach(i -> i.removeNvien(this));
        }
        if (phongBanNhanViens != null) {
            phongBanNhanViens.forEach(i -> i.addNvien(this));
        }
        this.pbans = phongBanNhanViens;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhanVien)) {
            return false;
        }
        return id != null && id.equals(((NhanVien) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhanVien{" +
            "id=" + getId() +
            ", maNhanVien='" + getMaNhanVien() + "'" +
            ", tenNhanVien='" + getTenNhanVien() + "'" +
            ", phongBan='" + getPhongBan() + "'" +
            ", capBac='" + getCapBac() + "'" +
            "}";
    }
}
