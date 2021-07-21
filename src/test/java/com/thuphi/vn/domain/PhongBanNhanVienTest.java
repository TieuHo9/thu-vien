package com.thuphi.vn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thuphi.vn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PhongBanNhanVienTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhongBanNhanVien.class);
        PhongBanNhanVien phongBanNhanVien1 = new PhongBanNhanVien();
        phongBanNhanVien1.setId(1L);
        PhongBanNhanVien phongBanNhanVien2 = new PhongBanNhanVien();
        phongBanNhanVien2.setId(phongBanNhanVien1.getId());
        assertThat(phongBanNhanVien1).isEqualTo(phongBanNhanVien2);
        phongBanNhanVien2.setId(2L);
        assertThat(phongBanNhanVien1).isNotEqualTo(phongBanNhanVien2);
        phongBanNhanVien1.setId(null);
        assertThat(phongBanNhanVien1).isNotEqualTo(phongBanNhanVien2);
    }
}
