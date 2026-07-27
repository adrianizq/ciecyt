package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class FasesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FasesDTO.class);
        FasesDTO fasesDTO1 = new FasesDTO();
        fasesDTO1.setId(1L);
        FasesDTO fasesDTO2 = new FasesDTO();
        assertThat(fasesDTO1).isNotEqualTo(fasesDTO2);
        fasesDTO2.setId(fasesDTO1.getId());
        assertThat(fasesDTO1).isEqualTo(fasesDTO2);
        fasesDTO2.setId(2L);
        assertThat(fasesDTO1).isNotEqualTo(fasesDTO2);
        fasesDTO1.setId(null);
        assertThat(fasesDTO1).isNotEqualTo(fasesDTO2);
    }
}
