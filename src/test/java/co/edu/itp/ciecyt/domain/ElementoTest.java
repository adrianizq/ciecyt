package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ElementoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Elemento.class);
        Elemento elemento1 = new Elemento();
        elemento1.setId(1L);
        Elemento elemento2 = new Elemento();
        elemento2.setId(elemento1.getId());
        assertThat(elemento1).isEqualTo(elemento2);
        elemento2.setId(2L);
        assertThat(elemento1).isNotEqualTo(elemento2);
        elemento1.setId(null);
        assertThat(elemento1).isNotEqualTo(elemento2);
    }
}
