package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ProyectoRespuestasTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProyectoRespuestas.class);
        ProyectoRespuestas proyectoRespuestas1 = new ProyectoRespuestas();
        proyectoRespuestas1.setId(1L);
        ProyectoRespuestas proyectoRespuestas2 = new ProyectoRespuestas();
        proyectoRespuestas2.setId(proyectoRespuestas1.getId());
        assertThat(proyectoRespuestas1).isEqualTo(proyectoRespuestas2);
        proyectoRespuestas2.setId(2L);
        assertThat(proyectoRespuestas1).isNotEqualTo(proyectoRespuestas2);
        proyectoRespuestas1.setId(null);
        assertThat(proyectoRespuestas1).isNotEqualTo(proyectoRespuestas2);
    }
}
