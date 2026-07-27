package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class InvestigacionTipoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvestigacionTipo.class);
        InvestigacionTipo investigacionTipo1 = new InvestigacionTipo();
        investigacionTipo1.setId(1L);
        InvestigacionTipo investigacionTipo2 = new InvestigacionTipo();
        investigacionTipo2.setId(investigacionTipo1.getId());
        assertThat(investigacionTipo1).isEqualTo(investigacionTipo2);
        investigacionTipo2.setId(2L);
        assertThat(investigacionTipo1).isNotEqualTo(investigacionTipo2);
        investigacionTipo1.setId(null);
        assertThat(investigacionTipo1).isNotEqualTo(investigacionTipo2);
    }
}
