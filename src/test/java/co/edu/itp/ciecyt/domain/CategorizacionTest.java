package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CategorizacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categorizacion.class);
        Categorizacion categorizacion1 = new Categorizacion();
        categorizacion1.setId(1L);
        Categorizacion categorizacion2 = new Categorizacion();
        categorizacion2.setId(categorizacion1.getId());
        assertThat(categorizacion1).isEqualTo(categorizacion2);
        categorizacion2.setId(2L);
        assertThat(categorizacion1).isNotEqualTo(categorizacion2);
        categorizacion1.setId(null);
        assertThat(categorizacion1).isNotEqualTo(categorizacion2);
    }
}
