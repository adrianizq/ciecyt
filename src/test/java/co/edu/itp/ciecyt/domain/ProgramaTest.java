package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ProgramaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Programa.class);
        Programa programa1 = new Programa();
        programa1.setId(1L);
        Programa programa2 = new Programa();
        programa2.setId(programa1.getId());
        assertThat(programa1).isEqualTo(programa2);
        programa2.setId(2L);
        assertThat(programa1).isNotEqualTo(programa2);
        programa1.setId(null);
        assertThat(programa1).isNotEqualTo(programa2);
    }
}
