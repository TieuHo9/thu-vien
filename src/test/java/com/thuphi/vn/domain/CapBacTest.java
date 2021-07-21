package com.thuphi.vn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thuphi.vn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CapBacTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CapBac.class);
        CapBac capBac1 = new CapBac();
        capBac1.setId(1L);
        CapBac capBac2 = new CapBac();
        capBac2.setId(capBac1.getId());
        assertThat(capBac1).isEqualTo(capBac2);
        capBac2.setId(2L);
        assertThat(capBac1).isNotEqualTo(capBac2);
        capBac1.setId(null);
        assertThat(capBac1).isNotEqualTo(capBac2);
    }
}
