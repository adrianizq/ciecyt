package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class RolesModalidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RolesModalidad.class);
        RolesModalidad rolesModalidad1 = new RolesModalidad();
        rolesModalidad1.setId(1L);
        RolesModalidad rolesModalidad2 = new RolesModalidad();
        rolesModalidad2.setId(rolesModalidad1.getId());
        assertThat(rolesModalidad1).isEqualTo(rolesModalidad2);
        rolesModalidad2.setId(2L);
        assertThat(rolesModalidad1).isNotEqualTo(rolesModalidad2);
        rolesModalidad1.setId(null);
        assertThat(rolesModalidad1).isNotEqualTo(rolesModalidad2);
    }
}
