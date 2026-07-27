package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class EntidadFinanciadoraTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadFinanciadora.class);
        EntidadFinanciadora entidadFinanciadora1 = new EntidadFinanciadora();
        entidadFinanciadora1.setId(1L);
        EntidadFinanciadora entidadFinanciadora2 = new EntidadFinanciadora();
        entidadFinanciadora2.setId(entidadFinanciadora1.getId());
        assertThat(entidadFinanciadora1).isEqualTo(entidadFinanciadora2);
        entidadFinanciadora2.setId(2L);
        assertThat(entidadFinanciadora1).isNotEqualTo(entidadFinanciadora2);
        entidadFinanciadora1.setId(null);
        assertThat(entidadFinanciadora1).isNotEqualTo(entidadFinanciadora2);
    }
}
