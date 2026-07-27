package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ProyectoFaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProyectoFase.class);
        ProyectoFase proyectoFase1 = new ProyectoFase();
        proyectoFase1.setId(1L);
        ProyectoFase proyectoFase2 = new ProyectoFase();
        proyectoFase2.setId(proyectoFase1.getId());
        assertThat(proyectoFase1).isEqualTo(proyectoFase2);
        proyectoFase2.setId(2L);
        assertThat(proyectoFase1).isNotEqualTo(proyectoFase2);
        proyectoFase1.setId(null);
        assertThat(proyectoFase1).isNotEqualTo(proyectoFase2);
    }
}
