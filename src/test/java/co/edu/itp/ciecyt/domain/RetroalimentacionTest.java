package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class RetroalimentacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Retroalimentacion.class);
        Retroalimentacion retroalimentacion1 = new Retroalimentacion();
        retroalimentacion1.setId(1L);
        Retroalimentacion retroalimentacion2 = new Retroalimentacion();
        retroalimentacion2.setId(retroalimentacion1.getId());
        assertThat(retroalimentacion1).isEqualTo(retroalimentacion2);
        retroalimentacion2.setId(2L);
        assertThat(retroalimentacion1).isNotEqualTo(retroalimentacion2);
        retroalimentacion1.setId(null);
        assertThat(retroalimentacion1).isNotEqualTo(retroalimentacion2);
    }
}
