package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class InvestigacionTipoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InvestigacionTipoDTO.class);
        InvestigacionTipoDTO investigacionTipoDTO1 = new InvestigacionTipoDTO();
        investigacionTipoDTO1.setId(1L);
        InvestigacionTipoDTO investigacionTipoDTO2 = new InvestigacionTipoDTO();
        assertThat(investigacionTipoDTO1).isNotEqualTo(investigacionTipoDTO2);
        investigacionTipoDTO2.setId(investigacionTipoDTO1.getId());
        assertThat(investigacionTipoDTO1).isEqualTo(investigacionTipoDTO2);
        investigacionTipoDTO2.setId(2L);
        assertThat(investigacionTipoDTO1).isNotEqualTo(investigacionTipoDTO2);
        investigacionTipoDTO1.setId(null);
        assertThat(investigacionTipoDTO1).isNotEqualTo(investigacionTipoDTO2);
    }
}
