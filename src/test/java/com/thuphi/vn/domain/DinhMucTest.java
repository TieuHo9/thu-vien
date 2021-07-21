package com.thuphi.vn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thuphi.vn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DinhMucTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DinhMuc.class);
        DinhMuc dinhMuc1 = new DinhMuc();
        dinhMuc1.setId(1L);
        DinhMuc dinhMuc2 = new DinhMuc();
        dinhMuc2.setId(dinhMuc1.getId());
        assertThat(dinhMuc1).isEqualTo(dinhMuc2);
        dinhMuc2.setId(2L);
        assertThat(dinhMuc1).isNotEqualTo(dinhMuc2);
        dinhMuc1.setId(null);
        assertThat(dinhMuc1).isNotEqualTo(dinhMuc2);
    }
}
