package com.thuphi.vn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thuphi.vn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChiPhiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChiPhi.class);
        ChiPhi chiPhi1 = new ChiPhi();
        chiPhi1.setId(1L);
        ChiPhi chiPhi2 = new ChiPhi();
        chiPhi2.setId(chiPhi1.getId());
        assertThat(chiPhi1).isEqualTo(chiPhi2);
        chiPhi2.setId(2L);
        assertThat(chiPhi1).isNotEqualTo(chiPhi2);
        chiPhi1.setId(null);
        assertThat(chiPhi1).isNotEqualTo(chiPhi2);
    }
}
