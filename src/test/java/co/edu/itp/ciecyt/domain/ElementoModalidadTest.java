package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ElementoModalidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ElementoModalidad.class);
        ElementoModalidad elementoModalidad1 = new ElementoModalidad();
        elementoModalidad1.setId(1L);
        ElementoModalidad elementoModalidad2 = new ElementoModalidad();
        elementoModalidad2.setId(elementoModalidad1.getId());
        assertThat(elementoModalidad1).isEqualTo(elementoModalidad2);
        elementoModalidad2.setId(2L);
        assertThat(elementoModalidad1).isNotEqualTo(elementoModalidad2);
        elementoModalidad1.setId(null);
        assertThat(elementoModalidad1).isNotEqualTo(elementoModalidad2);
    }
}
