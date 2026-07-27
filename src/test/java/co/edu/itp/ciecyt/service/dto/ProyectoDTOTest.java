package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ProyectoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProyectoDTO.class);
        ProyectoDTO proyectoDTO1 = new ProyectoDTO();
        proyectoDTO1.setId(1L);
        ProyectoDTO proyectoDTO2 = new ProyectoDTO();
        assertThat(proyectoDTO1).isNotEqualTo(proyectoDTO2);
        proyectoDTO2.setId(proyectoDTO1.getId());
        assertThat(proyectoDTO1).isEqualTo(proyectoDTO2);
        proyectoDTO2.setId(2L);
        assertThat(proyectoDTO1).isNotEqualTo(proyectoDTO2);
        proyectoDTO1.setId(null);
        assertThat(proyectoDTO1).isNotEqualTo(proyectoDTO2);
    }
}
