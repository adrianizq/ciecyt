package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CronogramaCiecytFasesDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CronogramaCiecytFasesDTO.class);
        CronogramaCiecytFasesDTO cronogramaCiecytFasesDTO1 = new CronogramaCiecytFasesDTO();
        cronogramaCiecytFasesDTO1.setId(1L);
        CronogramaCiecytFasesDTO cronogramaCiecytFasesDTO2 = new CronogramaCiecytFasesDTO();
        assertThat(cronogramaCiecytFasesDTO1).isNotEqualTo(cronogramaCiecytFasesDTO2);
        cronogramaCiecytFasesDTO2.setId(cronogramaCiecytFasesDTO1.getId());
        assertThat(cronogramaCiecytFasesDTO1).isEqualTo(cronogramaCiecytFasesDTO2);
        cronogramaCiecytFasesDTO2.setId(2L);
        assertThat(cronogramaCiecytFasesDTO1).isNotEqualTo(cronogramaCiecytFasesDTO2);
        cronogramaCiecytFasesDTO1.setId(null);
        assertThat(cronogramaCiecytFasesDTO1).isNotEqualTo(cronogramaCiecytFasesDTO2);
    }
}
