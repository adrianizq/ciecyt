package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ResultadosEsperadosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultadosEsperados.class);
        ResultadosEsperados resultadosEsperados1 = new ResultadosEsperados();
        resultadosEsperados1.setId(1L);
        ResultadosEsperados resultadosEsperados2 = new ResultadosEsperados();
        resultadosEsperados2.setId(resultadosEsperados1.getId());
        assertThat(resultadosEsperados1).isEqualTo(resultadosEsperados2);
        resultadosEsperados2.setId(2L);
        assertThat(resultadosEsperados1).isNotEqualTo(resultadosEsperados2);
        resultadosEsperados1.setId(null);
        assertThat(resultadosEsperados1).isNotEqualTo(resultadosEsperados2);
    }
}
