package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class AcuerdoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AcuerdoDTO.class);
        AcuerdoDTO acuerdoDTO1 = new AcuerdoDTO();
        acuerdoDTO1.setId(1L);
        AcuerdoDTO acuerdoDTO2 = new AcuerdoDTO();
        assertThat(acuerdoDTO1).isNotEqualTo(acuerdoDTO2);
        acuerdoDTO2.setId(acuerdoDTO1.getId());
        assertThat(acuerdoDTO1).isEqualTo(acuerdoDTO2);
        acuerdoDTO2.setId(2L);
        assertThat(acuerdoDTO1).isNotEqualTo(acuerdoDTO2);
        acuerdoDTO1.setId(null);
        assertThat(acuerdoDTO1).isNotEqualTo(acuerdoDTO2);
    }
}
