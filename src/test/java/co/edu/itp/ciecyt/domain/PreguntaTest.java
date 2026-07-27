package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class PreguntaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pregunta.class);
        Pregunta pregunta1 = new Pregunta();
        pregunta1.setId(1L);
        Pregunta pregunta2 = new Pregunta();
        pregunta2.setId(pregunta1.getId());
        assertThat(pregunta1).isEqualTo(pregunta2);
        pregunta2.setId(2L);
        assertThat(pregunta1).isNotEqualTo(pregunta2);
        pregunta1.setId(null);
        assertThat(pregunta1).isNotEqualTo(pregunta2);
    }
}
