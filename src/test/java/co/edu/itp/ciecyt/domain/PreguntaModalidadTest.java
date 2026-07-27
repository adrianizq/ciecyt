package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class PreguntaModalidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreguntaModalidad.class);
        PreguntaModalidad preguntaModalidad1 = new PreguntaModalidad();
        preguntaModalidad1.setId(1L);
        PreguntaModalidad preguntaModalidad2 = new PreguntaModalidad();
        preguntaModalidad2.setId(preguntaModalidad1.getId());
        assertThat(preguntaModalidad1).isEqualTo(preguntaModalidad2);
        preguntaModalidad2.setId(2L);
        assertThat(preguntaModalidad1).isNotEqualTo(preguntaModalidad2);
        preguntaModalidad1.setId(null);
        assertThat(preguntaModalidad1).isNotEqualTo(preguntaModalidad2);
    }
}
