package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ImpactosEsperadosDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImpactosEsperadosDTO.class);
        ImpactosEsperadosDTO impactosEsperadosDTO1 = new ImpactosEsperadosDTO();
        impactosEsperadosDTO1.setId(1L);
        ImpactosEsperadosDTO impactosEsperadosDTO2 = new ImpactosEsperadosDTO();
        assertThat(impactosEsperadosDTO1).isNotEqualTo(impactosEsperadosDTO2);
        impactosEsperadosDTO2.setId(impactosEsperadosDTO1.getId());
        assertThat(impactosEsperadosDTO1).isEqualTo(impactosEsperadosDTO2);
        impactosEsperadosDTO2.setId(2L);
        assertThat(impactosEsperadosDTO1).isNotEqualTo(impactosEsperadosDTO2);
        impactosEsperadosDTO1.setId(null);
        assertThat(impactosEsperadosDTO1).isNotEqualTo(impactosEsperadosDTO2);
    }
}
