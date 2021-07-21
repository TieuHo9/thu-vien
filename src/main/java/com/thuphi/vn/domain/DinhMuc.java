package com.thuphi.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Task entity.\n@author The JHipster team.
 */
@ApiModel(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "dinh_muc")
public class DinhMuc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ma_muc")
    private String maMuc;

    @Column(name = "loai_phi")
    private String loaiPhi;

    //    @Column(name = "so_tien")
    //    private String soTien;

    @Column(name = "cap_bac")
    private String capBac;

    @ManyToMany
    @JoinTable(
        name = "rel_dinh_muc__cbac",
        joinColumns = @JoinColumn(name = "dinh_muc_id"),
        inverseJoinColumns = @JoinColumn(name = "cbac_id")
    )
    @JsonIgnoreProperties(value = { "nvs", "mucs" }, allowSetters = true)
    private Set<CapBac> cbacs = new HashSet<>();

    @ManyToMany(mappedBy = "dmucs")
    @JsonIgnoreProperties(value = { "chiphis", "dmucs" }, allowSetters = true)
    private Set<DeXuatThanhToan> dxuattts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DinhMuc id(Long id) {
        this.id = id;
        return this;
    }

    public String getMaMuc() {
        return this.maMuc;
    }

    public DinhMuc maMuc(String maMuc) {
        this.maMuc = maMuc;
        return this;
    }

    public void setMaMuc(String maMuc) {
        this.maMuc = maMuc;
    }

    public String getLoaiPhi() {
        return this.loaiPhi;
    }

    public DinhMuc loaiPhi(String loaiPhi) {
        this.loaiPhi = loaiPhi;
        return this;
    }

    public void setLoaiPhi(String loaiPhi) {
        this.loaiPhi = loaiPhi;
    }

    //    public String getSoTien() {
    //        return this.soTien;
    //    }
    //
    //    public DinhMuc soTien(String soTien) {
    //        this.soTien = soTien;
    //        return this;
    //    }
    //
    //    public void setSoTien(String soTien) {
    //        this.soTien = soTien;
    //    }

    public String getCapBac() {
        return this.capBac;
    }

    public DinhMuc capBac(String capBac) {
        this.capBac = capBac;
        return this;
    }

    public void setCapBac(String capBac) {
        this.capBac = capBac;
    }

    public Set<CapBac> getCbacs() {
        return this.cbacs;
    }

    public DinhMuc cbacs(Set<CapBac> capBacs) {
        this.setCbacs(capBacs);
        return this;
    }

    public DinhMuc addCbac(CapBac capBac) {
        this.cbacs.add(capBac);
        capBac.getMucs().add(this);
        return this;
    }

    public DinhMuc removeCbac(CapBac capBac) {
        this.cbacs.remove(capBac);
        capBac.getMucs().remove(this);
        return this;
    }

    public void setCbacs(Set<CapBac> capBacs) {
        this.cbacs = capBacs;
    }

    public Set<DeXuatThanhToan> getDxuattts() {
        return this.dxuattts;
    }

    public DinhMuc dxuattts(Set<DeXuatThanhToan> deXuatThanhToans) {
        this.setDxuattts(deXuatThanhToans);
        return this;
    }

    public DinhMuc addDxuattt(DeXuatThanhToan deXuatThanhToan) {
        this.dxuattts.add(deXuatThanhToan);
        deXuatThanhToan.getDmucs().add(this);
        return this;
    }

    public DinhMuc removeDxuattt(DeXuatThanhToan deXuatThanhToan) {
        this.dxuattts.remove(deXuatThanhToan);
        deXuatThanhToan.getDmucs().remove(this);
        return this;
    }

    public void setDxuattts(Set<DeXuatThanhToan> deXuatThanhToans) {
        if (this.dxuattts != null) {
            this.dxuattts.forEach(i -> i.removeDmuc(this));
        }
        if (deXuatThanhToans != null) {
            deXuatThanhToans.forEach(i -> i.addDmuc(this));
        }
        this.dxuattts = deXuatThanhToans;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DinhMuc)) {
            return false;
        }
        return id != null && id.equals(((DinhMuc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DinhMuc{" +
            "id=" + getId() +
            ", maMuc='" + getMaMuc() + "'" +
            ", loaiPhi='" + getLoaiPhi() + "'" +
            ", capBac='" + getCapBac() + "'" +
            "}";
    }
}
