package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CronogramaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CronogramaDTO.class);
        CronogramaDTO cronogramaDTO1 = new CronogramaDTO();
        cronogramaDTO1.setId(1L);
        CronogramaDTO cronogramaDTO2 = new CronogramaDTO();
        assertThat(cronogramaDTO1).isNotEqualTo(cronogramaDTO2);
        cronogramaDTO2.setId(cronogramaDTO1.getId());
        assertThat(cronogramaDTO1).isEqualTo(cronogramaDTO2);
        cronogramaDTO2.setId(2L);
        assertThat(cronogramaDTO1).isNotEqualTo(cronogramaDTO2);
        cronogramaDTO1.setId(null);
        assertThat(cronogramaDTO1).isNotEqualTo(cronogramaDTO2);
    }
}
