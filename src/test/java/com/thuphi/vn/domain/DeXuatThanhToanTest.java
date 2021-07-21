package com.thuphi.vn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thuphi.vn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeXuatThanhToanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DeXuatThanhToan.class);
        DeXuatThanhToan deXuatThanhToan1 = new DeXuatThanhToan();
        deXuatThanhToan1.setId(1L);
        DeXuatThanhToan deXuatThanhToan2 = new DeXuatThanhToan();
        deXuatThanhToan2.setId(deXuatThanhToan1.getId());
        assertThat(deXuatThanhToan1).isEqualTo(deXuatThanhToan2);
        deXuatThanhToan2.setId(2L);
        assertThat(deXuatThanhToan1).isNotEqualTo(deXuatThanhToan2);
        deXuatThanhToan1.setId(null);
        assertThat(deXuatThanhToan1).isNotEqualTo(deXuatThanhToan2);
    }
}
