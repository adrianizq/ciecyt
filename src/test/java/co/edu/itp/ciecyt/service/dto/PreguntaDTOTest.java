package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class PreguntaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreguntaDTO.class);
        PreguntaDTO preguntaDTO1 = new PreguntaDTO();
        preguntaDTO1.setId(1L);
        PreguntaDTO preguntaDTO2 = new PreguntaDTO();
        assertThat(preguntaDTO1).isNotEqualTo(preguntaDTO2);
        preguntaDTO2.setId(preguntaDTO1.getId());
        assertThat(preguntaDTO1).isEqualTo(preguntaDTO2);
        preguntaDTO2.setId(2L);
        assertThat(preguntaDTO1).isNotEqualTo(preguntaDTO2);
        preguntaDTO1.setId(null);
        assertThat(preguntaDTO1).isNotEqualTo(preguntaDTO2);
    }
}
