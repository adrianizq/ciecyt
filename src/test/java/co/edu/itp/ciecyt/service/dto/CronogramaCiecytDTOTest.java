package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CronogramaCiecytDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CronogramaCiecytDTO.class);
        CronogramaCiecytDTO cronogramaCiecytDTO1 = new CronogramaCiecytDTO();
        cronogramaCiecytDTO1.setId(1L);
        CronogramaCiecytDTO cronogramaCiecytDTO2 = new CronogramaCiecytDTO();
        assertThat(cronogramaCiecytDTO1).isNotEqualTo(cronogramaCiecytDTO2);
        cronogramaCiecytDTO2.setId(cronogramaCiecytDTO1.getId());
        assertThat(cronogramaCiecytDTO1).isEqualTo(cronogramaCiecytDTO2);
        cronogramaCiecytDTO2.setId(2L);
        assertThat(cronogramaCiecytDTO1).isNotEqualTo(cronogramaCiecytDTO2);
        cronogramaCiecytDTO1.setId(null);
        assertThat(cronogramaCiecytDTO1).isNotEqualTo(cronogramaCiecytDTO2);
    }
}
