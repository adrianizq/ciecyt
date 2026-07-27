package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class IntegranteProyectoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntegranteProyecto.class);
        IntegranteProyecto integranteProyecto1 = new IntegranteProyecto();
        integranteProyecto1.setId(1L);
        IntegranteProyecto integranteProyecto2 = new IntegranteProyecto();
        integranteProyecto2.setId(integranteProyecto1.getId());
        assertThat(integranteProyecto1).isEqualTo(integranteProyecto2);
        integranteProyecto2.setId(2L);
        assertThat(integranteProyecto1).isNotEqualTo(integranteProyecto2);
        integranteProyecto1.setId(null);
        assertThat(integranteProyecto1).isNotEqualTo(integranteProyecto2);
    }
}
