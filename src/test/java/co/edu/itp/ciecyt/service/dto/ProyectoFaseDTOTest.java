package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ProyectoFaseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProyectoFaseDTO.class);
        ProyectoFaseDTO proyectoFaseDTO1 = new ProyectoFaseDTO();
        proyectoFaseDTO1.setId(1L);
        ProyectoFaseDTO proyectoFaseDTO2 = new ProyectoFaseDTO();
        assertThat(proyectoFaseDTO1).isNotEqualTo(proyectoFaseDTO2);
        proyectoFaseDTO2.setId(proyectoFaseDTO1.getId());
        assertThat(proyectoFaseDTO1).isEqualTo(proyectoFaseDTO2);
        proyectoFaseDTO2.setId(2L);
        assertThat(proyectoFaseDTO1).isNotEqualTo(proyectoFaseDTO2);
        proyectoFaseDTO1.setId(null);
        assertThat(proyectoFaseDTO1).isNotEqualTo(proyectoFaseDTO2);
    }
}
