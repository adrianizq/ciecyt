package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class AdjuntoProyectoFaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AdjuntoProyectoFase.class);
        AdjuntoProyectoFase adjuntoProyectoFase1 = new AdjuntoProyectoFase();
        adjuntoProyectoFase1.setId(1L);
        AdjuntoProyectoFase adjuntoProyectoFase2 = new AdjuntoProyectoFase();
        adjuntoProyectoFase2.setId(adjuntoProyectoFase1.getId());
        assertThat(adjuntoProyectoFase1).isEqualTo(adjuntoProyectoFase2);
        adjuntoProyectoFase2.setId(2L);
        assertThat(adjuntoProyectoFase1).isNotEqualTo(adjuntoProyectoFase2);
        adjuntoProyectoFase1.setId(null);
        assertThat(adjuntoProyectoFase1).isNotEqualTo(adjuntoProyectoFase2);
    }
}
