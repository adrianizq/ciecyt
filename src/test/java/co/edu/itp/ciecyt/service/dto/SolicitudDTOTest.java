package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class SolicitudDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SolicitudDTO.class);
        SolicitudDTO solicitudDTO1 = new SolicitudDTO();
        solicitudDTO1.setId(1L);
        SolicitudDTO solicitudDTO2 = new SolicitudDTO();
        assertThat(solicitudDTO1).isNotEqualTo(solicitudDTO2);
        solicitudDTO2.setId(solicitudDTO1.getId());
        assertThat(solicitudDTO1).isEqualTo(solicitudDTO2);
        solicitudDTO2.setId(2L);
        assertThat(solicitudDTO1).isNotEqualTo(solicitudDTO2);
        solicitudDTO1.setId(null);
        assertThat(solicitudDTO1).isNotEqualTo(solicitudDTO2);
    }
}
