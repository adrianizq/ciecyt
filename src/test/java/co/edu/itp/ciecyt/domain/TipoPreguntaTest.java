package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class TipoPreguntaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPregunta.class);
        TipoPregunta tipoPregunta1 = new TipoPregunta();
        tipoPregunta1.setId(1L);
        TipoPregunta tipoPregunta2 = new TipoPregunta();
        tipoPregunta2.setId(tipoPregunta1.getId());
        assertThat(tipoPregunta1).isEqualTo(tipoPregunta2);
        tipoPregunta2.setId(2L);
        assertThat(tipoPregunta1).isNotEqualTo(tipoPregunta2);
        tipoPregunta1.setId(null);
        assertThat(tipoPregunta1).isNotEqualTo(tipoPregunta2);
    }
}
