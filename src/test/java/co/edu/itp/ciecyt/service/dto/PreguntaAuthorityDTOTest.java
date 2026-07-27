package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class PreguntaAuthorityDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreguntaAuthorityDTO.class);
        PreguntaAuthorityDTO preguntaAuthorityDTO1 = new PreguntaAuthorityDTO();
        preguntaAuthorityDTO1.setId(1L);
        PreguntaAuthorityDTO preguntaAuthorityDTO2 = new PreguntaAuthorityDTO();
        assertThat(preguntaAuthorityDTO1).isNotEqualTo(preguntaAuthorityDTO2);
        preguntaAuthorityDTO2.setId(preguntaAuthorityDTO1.getId());
        assertThat(preguntaAuthorityDTO1).isEqualTo(preguntaAuthorityDTO2);
        preguntaAuthorityDTO2.setId(2L);
        assertThat(preguntaAuthorityDTO1).isNotEqualTo(preguntaAuthorityDTO2);
        preguntaAuthorityDTO1.setId(null);
        assertThat(preguntaAuthorityDTO1).isNotEqualTo(preguntaAuthorityDTO2);
    }
}
