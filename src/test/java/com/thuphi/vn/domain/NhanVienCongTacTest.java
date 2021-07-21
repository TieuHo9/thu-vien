package com.thuphi.vn.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.thuphi.vn.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NhanVienCongTacTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhanVienCongTac.class);
        NhanVienCongTac nhanVienCongTac1 = new NhanVienCongTac();
        nhanVienCongTac1.setId(1L);
        NhanVienCongTac nhanVienCongTac2 = new NhanVienCongTac();
        nhanVienCongTac2.setId(nhanVienCongTac1.getId());
        assertThat(nhanVienCongTac1).isEqualTo(nhanVienCongTac2);
        nhanVienCongTac2.setId(2L);
        assertThat(nhanVienCongTac1).isNotEqualTo(nhanVienCongTac2);
        nhanVienCongTac1.setId(null);
        assertThat(nhanVienCongTac1).isNotEqualTo(nhanVienCongTac2);
    }
}
