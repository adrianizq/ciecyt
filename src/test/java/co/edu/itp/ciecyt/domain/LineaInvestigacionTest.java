package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class LineaInvestigacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineaInvestigacion.class);
        LineaInvestigacion lineaInvestigacion1 = new LineaInvestigacion();
        lineaInvestigacion1.setId(1L);
        LineaInvestigacion lineaInvestigacion2 = new LineaInvestigacion();
        lineaInvestigacion2.setId(lineaInvestigacion1.getId());
        assertThat(lineaInvestigacion1).isEqualTo(lineaInvestigacion2);
        lineaInvestigacion2.setId(2L);
        assertThat(lineaInvestigacion1).isNotEqualTo(lineaInvestigacion2);
        lineaInvestigacion1.setId(null);
        assertThat(lineaInvestigacion1).isNotEqualTo(lineaInvestigacion2);
    }
}
