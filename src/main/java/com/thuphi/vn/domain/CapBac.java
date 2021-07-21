package com.thuphi.vn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A CapBac.
 */
@Entity
@Table(name = "cap_bac")
public class CapBac implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "ten_cap")
    private String tenCap;

    @ManyToMany(mappedBy = "capbacs")
    @JsonIgnoreProperties(value = { "phongbans", "capbacs", "chuyencongtacs", "pbans" }, allowSetters = true)
    private Set<NhanVien> nvs = new HashSet<>();

    @ManyToMany(mappedBy = "cbacs")
    @JsonIgnoreProperties(value = { "cbacs", "dxuattts" }, allowSetters = true)
    private Set<DinhMuc> mucs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CapBac id(Long id) {
        this.id = id;
        return this;
    }

    public String getTenCap() {
        return this.tenCap;
    }

    public CapBac tenCap(String tenCap) {
        this.tenCap = tenCap;
        return this;
    }

    public void setTenCap(String tenCap) {
        this.tenCap = tenCap;
    }

    public Set<NhanVien> getNvs() {
        return this.nvs;
    }

    public CapBac nvs(Set<NhanVien> nhanViens) {
        this.setNvs(nhanViens);
        return this;
    }

    public CapBac addNv(NhanVien nhanVien) {
        this.nvs.add(nhanVien);
        nhanVien.getCapbacs().add(this);
        return this;
    }

    public CapBac removeNv(NhanVien nhanVien) {
        this.nvs.remove(nhanVien);
        nhanVien.getCapbacs().remove(this);
        return this;
    }

    public void setNvs(Set<NhanVien> nhanViens) {
        if (this.nvs != null) {
            this.nvs.forEach(i -> i.removeCapbac(this));
        }
        if (nhanViens != null) {
            nhanViens.forEach(i -> i.addCapbac(this));
        }
        this.nvs = nhanViens;
    }

    public Set<DinhMuc> getMucs() {
        return this.mucs;
    }

    public CapBac mucs(Set<DinhMuc> dinhMucs) {
        this.setMucs(dinhMucs);
        return this;
    }

    public CapBac addMuc(DinhMuc dinhMuc) {
        this.mucs.add(dinhMuc);
        dinhMuc.getCbacs().add(this);
        return this;
    }

    public CapBac removeMuc(DinhMuc dinhMuc) {
        this.mucs.remove(dinhMuc);
        dinhMuc.getCbacs().remove(this);
        return this;
    }

    public void setMucs(Set<DinhMuc> dinhMucs) {
        if (this.mucs != null) {
            this.mucs.forEach(i -> i.removeCbac(this));
        }
        if (dinhMucs != null) {
            dinhMucs.forEach(i -> i.addCbac(this));
        }
        this.mucs = dinhMucs;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CapBac)) {
            return false;
        }
        return id != null && id.equals(((CapBac) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CapBac{" +
            "id=" + getId() +
            ", tenCap='" + getTenCap() + "'" +
            "}";
    }
}
