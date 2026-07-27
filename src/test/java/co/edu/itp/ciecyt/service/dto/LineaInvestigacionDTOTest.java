package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class LineaInvestigacionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineaInvestigacionDTO.class);
        LineaInvestigacionDTO lineaInvestigacionDTO1 = new LineaInvestigacionDTO();
        lineaInvestigacionDTO1.setId(1L);
        LineaInvestigacionDTO lineaInvestigacionDTO2 = new LineaInvestigacionDTO();
        assertThat(lineaInvestigacionDTO1).isNotEqualTo(lineaInvestigacionDTO2);
        lineaInvestigacionDTO2.setId(lineaInvestigacionDTO1.getId());
        assertThat(lineaInvestigacionDTO1).isEqualTo(lineaInvestigacionDTO2);
        lineaInvestigacionDTO2.setId(2L);
        assertThat(lineaInvestigacionDTO1).isNotEqualTo(lineaInvestigacionDTO2);
        lineaInvestigacionDTO1.setId(null);
        assertThat(lineaInvestigacionDTO1).isNotEqualTo(lineaInvestigacionDTO2);
    }
}
