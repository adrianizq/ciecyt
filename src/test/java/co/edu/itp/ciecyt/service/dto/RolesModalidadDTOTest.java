package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class RolesModalidadDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RolesModalidadDTO.class);
        RolesModalidadDTO rolesModalidadDTO1 = new RolesModalidadDTO();
        rolesModalidadDTO1.setId(1L);
        RolesModalidadDTO rolesModalidadDTO2 = new RolesModalidadDTO();
        assertThat(rolesModalidadDTO1).isNotEqualTo(rolesModalidadDTO2);
        rolesModalidadDTO2.setId(rolesModalidadDTO1.getId());
        assertThat(rolesModalidadDTO1).isEqualTo(rolesModalidadDTO2);
        rolesModalidadDTO2.setId(2L);
        assertThat(rolesModalidadDTO1).isNotEqualTo(rolesModalidadDTO2);
        rolesModalidadDTO1.setId(null);
        assertThat(rolesModalidadDTO1).isNotEqualTo(rolesModalidadDTO2);
    }
}
