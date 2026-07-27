package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ModalidadDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ModalidadDTO.class);
        ModalidadDTO modalidadDTO1 = new ModalidadDTO();
        modalidadDTO1.setId(1L);
        ModalidadDTO modalidadDTO2 = new ModalidadDTO();
        assertThat(modalidadDTO1).isNotEqualTo(modalidadDTO2);
        modalidadDTO2.setId(modalidadDTO1.getId());
        assertThat(modalidadDTO1).isEqualTo(modalidadDTO2);
        modalidadDTO2.setId(2L);
        assertThat(modalidadDTO1).isNotEqualTo(modalidadDTO2);
        modalidadDTO1.setId(null);
        assertThat(modalidadDTO1).isNotEqualTo(modalidadDTO2);
    }
}
