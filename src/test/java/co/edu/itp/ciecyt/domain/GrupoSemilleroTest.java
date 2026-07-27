package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class GrupoSemilleroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoSemillero.class);
        GrupoSemillero grupoSemillero1 = new GrupoSemillero();
        grupoSemillero1.setId(1L);
        GrupoSemillero grupoSemillero2 = new GrupoSemillero();
        grupoSemillero2.setId(grupoSemillero1.getId());
        assertThat(grupoSemillero1).isEqualTo(grupoSemillero2);
        grupoSemillero2.setId(2L);
        assertThat(grupoSemillero1).isNotEqualTo(grupoSemillero2);
        grupoSemillero1.setId(null);
        assertThat(grupoSemillero1).isNotEqualTo(grupoSemillero2);
    }
}
