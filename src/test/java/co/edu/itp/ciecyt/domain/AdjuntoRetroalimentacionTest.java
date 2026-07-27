package co.edu.itp.ciecyt.domain;

import static org.assertj.core.api.Assertions.assertThat;

import co.edu.itp.ciecyt.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AdjuntoRetroalimentacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjuntoRetroalimentacion.class);
        AdjuntoRetroalimentacion adjuntoRetroalimentacion1 = new AdjuntoRetroalimentacion();
        adjuntoRetroalimentacion1.setId(1L);
        AdjuntoRetroalimentacion adjuntoRetroalimentacion2 = new AdjuntoRetroalimentacion();
        adjuntoRetroalimentacion2.setId(adjuntoRetroalimentacion1.getId());
        assertThat(adjuntoRetroalimentacion1).isEqualTo(adjuntoRetroalimentacion2);
        adjuntoRetroalimentacion2.setId(2L);
        assertThat(adjuntoRetroalimentacion1).isNotEqualTo(adjuntoRetroalimentacion2);
        adjuntoRetroalimentacion1.setId(null);
        assertThat(adjuntoRetroalimentacion1).isNotEqualTo(adjuntoRetroalimentacion2);
    }
}
