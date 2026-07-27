package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ElementoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementoDTO.class);
        ElementoDTO elementoDTO1 = new ElementoDTO();
        elementoDTO1.setId(1L);
        ElementoDTO elementoDTO2 = new ElementoDTO();
        assertThat(elementoDTO1).isNotEqualTo(elementoDTO2);
        elementoDTO2.setId(elementoDTO1.getId());
        assertThat(elementoDTO1).isEqualTo(elementoDTO2);
        elementoDTO2.setId(2L);
        assertThat(elementoDTO1).isNotEqualTo(elementoDTO2);
        elementoDTO1.setId(null);
        assertThat(elementoDTO1).isNotEqualTo(elementoDTO2);
    }
}
