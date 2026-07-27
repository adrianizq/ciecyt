package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CategorizacionDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CategorizacionDTO.class);
        CategorizacionDTO categorizacionDTO1 = new CategorizacionDTO();
        categorizacionDTO1.setId(1L);
        CategorizacionDTO categorizacionDTO2 = new CategorizacionDTO();
        assertThat(categorizacionDTO1).isNotEqualTo(categorizacionDTO2);
        categorizacionDTO2.setId(categorizacionDTO1.getId());
        assertThat(categorizacionDTO1).isEqualTo(categorizacionDTO2);
        categorizacionDTO2.setId(2L);
        assertThat(categorizacionDTO1).isNotEqualTo(categorizacionDTO2);
        categorizacionDTO1.setId(null);
        assertThat(categorizacionDTO1).isNotEqualTo(categorizacionDTO2);
    }
}
