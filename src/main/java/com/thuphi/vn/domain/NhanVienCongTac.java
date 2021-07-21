package com.thuphi.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A NhanVienCongTac.
 */
@Entity
@Table(name = "nhan_vien_cong_tac")
public class NhanVienCongTac implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ma_nhan_vien")
    private String maNhanVien;

    @Column(name = "ma_chuyen_di")
    private String maChuyenDi;

    @Column(name = "so_tien")
    private String soTien;

    @ManyToMany
    @JoinTable(
        name = "rel_nhan_vien_cong_tac__chuyenct",
        joinColumns = @JoinColumn(name = "nhan_vien_cong_tac_id"),
        inverseJoinColumns = @JoinColumn(name = "chuyenct_id")
    )
    @JsonIgnoreProperties(value = { "nhanviens", "nhanviencts" }, allowSetters = true)
    private Set<ChuyenCongTac> chuyencts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NhanVienCongTac id(Long id) {
        this.id = id;
        return this;
    }

    public String getMaNhanVien() {
        return this.maNhanVien;
    }

    public NhanVienCongTac maNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
        return this;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaChuyenDi() {
        return this.maChuyenDi;
    }

    public NhanVienCongTac maChuyenDi(String maChuyenDi) {
        this.maChuyenDi = maChuyenDi;
        return this;
    }

    public void setMaChuyenDi(String maChuyenDi) {
        this.maChuyenDi = maChuyenDi;
    }

    public String getSoTien() {
        return this.soTien;
    }

    public NhanVienCongTac soTien(String soTien) {
        this.soTien = soTien;
        return this;
    }

    public void setSoTien(String soTien) {
        this.soTien = soTien;
    }

    public Set<ChuyenCongTac> getChuyencts() {
        return this.chuyencts;
    }

    public NhanVienCongTac chuyencts(Set<ChuyenCongTac> chuyenCongTacs) {
        this.setChuyencts(chuyenCongTacs);
        return this;
    }

    public NhanVienCongTac addChuyenct(ChuyenCongTac chuyenCongTac) {
        this.chuyencts.add(chuyenCongTac);
        chuyenCongTac.getNhanviencts().add(this);
        return this;
    }

    public NhanVienCongTac removeChuyenct(ChuyenCongTac chuyenCongTac) {
        this.chuyencts.remove(chuyenCongTac);
        chuyenCongTac.getNhanviencts().remove(this);
        return this;
    }

    public void setChuyencts(Set<ChuyenCongTac> chuyenCongTacs) {
        this.chuyencts = chuyenCongTacs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NhanVienCongTac)) {
            return false;
        }
        return id != null && id.equals(((NhanVienCongTac) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NhanVienCongTac{" +
            "id=" + getId() +
            ", maNhanVien='" + getMaNhanVien() + "'" +
            ", maChuyenDi='" + getMaChuyenDi() + "'" +
            ", soTien='" + getSoTien() + "'" +
            "}";
    }
}
