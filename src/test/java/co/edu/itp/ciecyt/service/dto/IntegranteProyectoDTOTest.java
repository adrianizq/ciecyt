package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class IntegranteProyectoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegranteProyectoDTO.class);
        IntegranteProyectoDTO integranteProyectoDTO1 = new IntegranteProyectoDTO();
        integranteProyectoDTO1.setId(1L);
        IntegranteProyectoDTO integranteProyectoDTO2 = new IntegranteProyectoDTO();
        assertThat(integranteProyectoDTO1).isNotEqualTo(integranteProyectoDTO2);
        integranteProyectoDTO2.setId(integranteProyectoDTO1.getId());
        assertThat(integranteProyectoDTO1).isEqualTo(integranteProyectoDTO2);
        integranteProyectoDTO2.setId(2L);
        assertThat(integranteProyectoDTO1).isNotEqualTo(integranteProyectoDTO2);
        integranteProyectoDTO1.setId(null);
        assertThat(integranteProyectoDTO1).isNotEqualTo(integranteProyectoDTO2);
    }
}
