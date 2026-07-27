package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class RolMenuDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RolMenuDTO.class);
        RolMenuDTO rolMenuDTO1 = new RolMenuDTO();
        rolMenuDTO1.setId(1L);
        RolMenuDTO rolMenuDTO2 = new RolMenuDTO();
        assertThat(rolMenuDTO1).isNotEqualTo(rolMenuDTO2);
        rolMenuDTO2.setId(rolMenuDTO1.getId());
        assertThat(rolMenuDTO1).isEqualTo(rolMenuDTO2);
        rolMenuDTO2.setId(2L);
        assertThat(rolMenuDTO1).isNotEqualTo(rolMenuDTO2);
        rolMenuDTO1.setId(null);
        assertThat(rolMenuDTO1).isNotEqualTo(rolMenuDTO2);
    }
}
