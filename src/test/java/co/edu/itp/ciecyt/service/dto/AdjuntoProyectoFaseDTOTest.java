package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class AdjuntoProyectoFaseDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjuntoProyectoFaseDTO.class);
        AdjuntoProyectoFaseDTO adjuntoProyectoFaseDTO1 = new AdjuntoProyectoFaseDTO();
        adjuntoProyectoFaseDTO1.setId(1L);
        AdjuntoProyectoFaseDTO adjuntoProyectoFaseDTO2 = new AdjuntoProyectoFaseDTO();
        assertThat(adjuntoProyectoFaseDTO1).isNotEqualTo(adjuntoProyectoFaseDTO2);
        adjuntoProyectoFaseDTO2.setId(adjuntoProyectoFaseDTO1.getId());
        assertThat(adjuntoProyectoFaseDTO1).isEqualTo(adjuntoProyectoFaseDTO2);
        adjuntoProyectoFaseDTO2.setId(2L);
        assertThat(adjuntoProyectoFaseDTO1).isNotEqualTo(adjuntoProyectoFaseDTO2);
        adjuntoProyectoFaseDTO1.setId(null);
        assertThat(adjuntoProyectoFaseDTO1).isNotEqualTo(adjuntoProyectoFaseDTO2);
    }
}
