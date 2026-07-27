package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CronogramaCiecytTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CronogramaCiecyt.class);
        CronogramaCiecyt cronogramaCiecyt1 = new CronogramaCiecyt();
        cronogramaCiecyt1.setId(1L);
        CronogramaCiecyt cronogramaCiecyt2 = new CronogramaCiecyt();
        cronogramaCiecyt2.setId(cronogramaCiecyt1.getId());
        assertThat(cronogramaCiecyt1).isEqualTo(cronogramaCiecyt2);
        cronogramaCiecyt2.setId(2L);
        assertThat(cronogramaCiecyt1).isNotEqualTo(cronogramaCiecyt2);
        cronogramaCiecyt1.setId(null);
        assertThat(cronogramaCiecyt1).isNotEqualTo(cronogramaCiecyt2);
    }
}
