package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ElementoModalidadDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementoModalidadDTO.class);
        ElementoModalidadDTO elementoModalidadDTO1 = new ElementoModalidadDTO();
        elementoModalidadDTO1.setId(1L);
        ElementoModalidadDTO elementoModalidadDTO2 = new ElementoModalidadDTO();
        assertThat(elementoModalidadDTO1).isNotEqualTo(elementoModalidadDTO2);
        elementoModalidadDTO2.setId(elementoModalidadDTO1.getId());
        assertThat(elementoModalidadDTO1).isEqualTo(elementoModalidadDTO2);
        elementoModalidadDTO2.setId(2L);
        assertThat(elementoModalidadDTO1).isNotEqualTo(elementoModalidadDTO2);
        elementoModalidadDTO1.setId(null);
        assertThat(elementoModalidadDTO1).isNotEqualTo(elementoModalidadDTO2);
    }
}
