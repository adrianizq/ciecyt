package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ResultadosEsperadosDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultadosEsperadosDTO.class);
        ResultadosEsperadosDTO resultadosEsperadosDTO1 = new ResultadosEsperadosDTO();
        resultadosEsperadosDTO1.setId(1L);
        ResultadosEsperadosDTO resultadosEsperadosDTO2 = new ResultadosEsperadosDTO();
        assertThat(resultadosEsperadosDTO1).isNotEqualTo(resultadosEsperadosDTO2);
        resultadosEsperadosDTO2.setId(resultadosEsperadosDTO1.getId());
        assertThat(resultadosEsperadosDTO1).isEqualTo(resultadosEsperadosDTO2);
        resultadosEsperadosDTO2.setId(2L);
        assertThat(resultadosEsperadosDTO1).isNotEqualTo(resultadosEsperadosDTO2);
        resultadosEsperadosDTO1.setId(null);
        assertThat(resultadosEsperadosDTO1).isNotEqualTo(resultadosEsperadosDTO2);
    }
}
