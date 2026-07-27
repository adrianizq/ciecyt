package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class EntidadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entidad.class);
        Entidad entidad1 = new Entidad();
        entidad1.setId(1L);
        Entidad entidad2 = new Entidad();
        entidad2.setId(entidad1.getId());
        assertThat(entidad1).isEqualTo(entidad2);
        entidad2.setId(2L);
        assertThat(entidad1).isNotEqualTo(entidad2);
        entidad1.setId(null);
        assertThat(entidad1).isNotEqualTo(entidad2);
    }
}
