package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class FormatoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormatoDTO.class);
        FormatoDTO formatoDTO1 = new FormatoDTO();
        formatoDTO1.setId(1L);
        FormatoDTO formatoDTO2 = new FormatoDTO();
        assertThat(formatoDTO1).isNotEqualTo(formatoDTO2);
        formatoDTO2.setId(formatoDTO1.getId());
        assertThat(formatoDTO1).isEqualTo(formatoDTO2);
        formatoDTO2.setId(2L);
        assertThat(formatoDTO1).isNotEqualTo(formatoDTO2);
        formatoDTO1.setId(null);
        assertThat(formatoDTO1).isNotEqualTo(formatoDTO2);
    }
}
