package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ElementoProyectoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementoProyecto.class);
        ElementoProyecto elementoProyecto1 = new ElementoProyecto();
        elementoProyecto1.setId(1L);
        ElementoProyecto elementoProyecto2 = new ElementoProyecto();
        elementoProyecto2.setId(elementoProyecto1.getId());
        assertThat(elementoProyecto1).isEqualTo(elementoProyecto2);
        elementoProyecto2.setId(2L);
        assertThat(elementoProyecto1).isNotEqualTo(elementoProyecto2);
        elementoProyecto1.setId(null);
        assertThat(elementoProyecto1).isNotEqualTo(elementoProyecto2);
    }
}
