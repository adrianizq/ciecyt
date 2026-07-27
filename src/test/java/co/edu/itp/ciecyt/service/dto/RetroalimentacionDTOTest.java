package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class RetroalimentacionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RetroalimentacionDTO.class);
        RetroalimentacionDTO retroalimentacionDTO1 = new RetroalimentacionDTO();
        retroalimentacionDTO1.setId(1L);
        RetroalimentacionDTO retroalimentacionDTO2 = new RetroalimentacionDTO();
        assertThat(retroalimentacionDTO1).isNotEqualTo(retroalimentacionDTO2);
        retroalimentacionDTO2.setId(retroalimentacionDTO1.getId());
        assertThat(retroalimentacionDTO1).isEqualTo(retroalimentacionDTO2);
        retroalimentacionDTO2.setId(2L);
        assertThat(retroalimentacionDTO1).isNotEqualTo(retroalimentacionDTO2);
        retroalimentacionDTO1.setId(null);
        assertThat(retroalimentacionDTO1).isNotEqualTo(retroalimentacionDTO2);
    }
}
