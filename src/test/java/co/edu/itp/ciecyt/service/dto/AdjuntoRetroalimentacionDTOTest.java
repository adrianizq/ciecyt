package co.edu.itp.ciecyt.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.ciecyt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AdjuntoRetroalimentacionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjuntoRetroalimentacionDTO.class);
        AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO1 = new AdjuntoRetroalimentacionDTO();
        adjuntoRetroalimentacionDTO1.setId(1L);
        AdjuntoRetroalimentacionDTO adjuntoRetroalimentacionDTO2 = new AdjuntoRetroalimentacionDTO();
        assertThat(adjuntoRetroalimentacionDTO1).isNotEqualTo(adjuntoRetroalimentacionDTO2);
        adjuntoRetroalimentacionDTO2.setId(adjuntoRetroalimentacionDTO1.getId());
        assertThat(adjuntoRetroalimentacionDTO1).isEqualTo(adjuntoRetroalimentacionDTO2);
        adjuntoRetroalimentacionDTO2.setId(2L);
        assertThat(adjuntoRetroalimentacionDTO1).isNotEqualTo(adjuntoRetroalimentacionDTO2);
        adjuntoRetroalimentacionDTO1.setId(null);
        assertThat(adjuntoRetroalimentacionDTO1).isNotEqualTo(adjuntoRetroalimentacionDTO2);
    }
}
