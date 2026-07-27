package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class PreguntaModalidadDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreguntaModalidadDTO.class);
        PreguntaModalidadDTO preguntaModalidadDTO1 = new PreguntaModalidadDTO();
        preguntaModalidadDTO1.setId(1L);
        PreguntaModalidadDTO preguntaModalidadDTO2 = new PreguntaModalidadDTO();
        assertThat(preguntaModalidadDTO1).isNotEqualTo(preguntaModalidadDTO2);
        preguntaModalidadDTO2.setId(preguntaModalidadDTO1.getId());
        assertThat(preguntaModalidadDTO1).isEqualTo(preguntaModalidadDTO2);
        preguntaModalidadDTO2.setId(2L);
        assertThat(preguntaModalidadDTO1).isNotEqualTo(preguntaModalidadDTO2);
        preguntaModalidadDTO1.setId(null);
        assertThat(preguntaModalidadDTO1).isNotEqualTo(preguntaModalidadDTO2);
    }
}
