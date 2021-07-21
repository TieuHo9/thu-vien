package com.thuphi.vn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thuphi.vn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChuyenCongTacTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ChuyenCongTac.class);
        ChuyenCongTac chuyenCongTac1 = new ChuyenCongTac();
        chuyenCongTac1.setId(1L);
        ChuyenCongTac chuyenCongTac2 = new ChuyenCongTac();
        chuyenCongTac2.setId(chuyenCongTac1.getId());
        assertThat(chuyenCongTac1).isEqualTo(chuyenCongTac2);
        chuyenCongTac2.setId(2L);
        assertThat(chuyenCongTac1).isNotEqualTo(chuyenCongTac2);
        chuyenCongTac1.setId(null);
        assertThat(chuyenCongTac1).isNotEqualTo(chuyenCongTac2);
    }
}
