package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ModalidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Modalidad.class);
        Modalidad modalidad1 = new Modalidad();
        modalidad1.setId(1L);
        Modalidad modalidad2 = new Modalidad();
        modalidad2.setId(modalidad1.getId());
        assertThat(modalidad1).isEqualTo(modalidad2);
        modalidad2.setId(2L);
        assertThat(modalidad1).isNotEqualTo(modalidad2);
        modalidad1.setId(null);
        assertThat(modalidad1).isNotEqualTo(modalidad2);
    }
}
