package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class FasesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fases.class);
        Fases fases1 = new Fases();
        fases1.setId(1L);
        Fases fases2 = new Fases();
        fases2.setId(fases1.getId());
        assertThat(fases1).isEqualTo(fases2);
        fases2.setId(2L);
        assertThat(fases1).isNotEqualTo(fases2);
        fases1.setId(null);
        assertThat(fases1).isNotEqualTo(fases2);
    }
}
