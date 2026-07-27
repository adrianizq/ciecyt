package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ElementoProyectoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementoProyectoDTO.class);
        ElementoProyectoDTO elementoProyectoDTO1 = new ElementoProyectoDTO();
        elementoProyectoDTO1.setId(1L);
        ElementoProyectoDTO elementoProyectoDTO2 = new ElementoProyectoDTO();
        assertThat(elementoProyectoDTO1).isNotEqualTo(elementoProyectoDTO2);
        elementoProyectoDTO2.setId(elementoProyectoDTO1.getId());
        assertThat(elementoProyectoDTO1).isEqualTo(elementoProyectoDTO2);
        elementoProyectoDTO2.setId(2L);
        assertThat(elementoProyectoDTO1).isNotEqualTo(elementoProyectoDTO2);
        elementoProyectoDTO1.setId(null);
        assertThat(elementoProyectoDTO1).isNotEqualTo(elementoProyectoDTO2);
    }
}
