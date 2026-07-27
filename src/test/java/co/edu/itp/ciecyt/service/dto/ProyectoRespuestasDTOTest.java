package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ProyectoRespuestasDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProyectoRespuestasDTO.class);
        ProyectoRespuestasDTO proyectoRespuestasDTO1 = new ProyectoRespuestasDTO();
        proyectoRespuestasDTO1.setId(1L);
        ProyectoRespuestasDTO proyectoRespuestasDTO2 = new ProyectoRespuestasDTO();
        assertThat(proyectoRespuestasDTO1).isNotEqualTo(proyectoRespuestasDTO2);
        proyectoRespuestasDTO2.setId(proyectoRespuestasDTO1.getId());
        assertThat(proyectoRespuestasDTO1).isEqualTo(proyectoRespuestasDTO2);
        proyectoRespuestasDTO2.setId(2L);
        assertThat(proyectoRespuestasDTO1).isNotEqualTo(proyectoRespuestasDTO2);
        proyectoRespuestasDTO1.setId(null);
        assertThat(proyectoRespuestasDTO1).isNotEqualTo(proyectoRespuestasDTO2);
    }
}
