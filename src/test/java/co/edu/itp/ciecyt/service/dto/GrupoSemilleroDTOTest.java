package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class GrupoSemilleroDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GrupoSemilleroDTO.class);
        GrupoSemilleroDTO grupoSemilleroDTO1 = new GrupoSemilleroDTO();
        grupoSemilleroDTO1.setId(1L);
        GrupoSemilleroDTO grupoSemilleroDTO2 = new GrupoSemilleroDTO();
        assertThat(grupoSemilleroDTO1).isNotEqualTo(grupoSemilleroDTO2);
        grupoSemilleroDTO2.setId(grupoSemilleroDTO1.getId());
        assertThat(grupoSemilleroDTO1).isEqualTo(grupoSemilleroDTO2);
        grupoSemilleroDTO2.setId(2L);
        assertThat(grupoSemilleroDTO1).isNotEqualTo(grupoSemilleroDTO2);
        grupoSemilleroDTO1.setId(null);
        assertThat(grupoSemilleroDTO1).isNotEqualTo(grupoSemilleroDTO2);
    }
}
