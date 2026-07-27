package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CronogramaCiecytFasesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CronogramaCiecytFases.class);
        CronogramaCiecytFases cronogramaCiecytFases1 = new CronogramaCiecytFases();
        cronogramaCiecytFases1.setId(1L);
        CronogramaCiecytFases cronogramaCiecytFases2 = new CronogramaCiecytFases();
        cronogramaCiecytFases2.setId(cronogramaCiecytFases1.getId());
        assertThat(cronogramaCiecytFases1).isEqualTo(cronogramaCiecytFases2);
        cronogramaCiecytFases2.setId(2L);
        assertThat(cronogramaCiecytFases1).isNotEqualTo(cronogramaCiecytFases2);
        cronogramaCiecytFases1.setId(null);
        assertThat(cronogramaCiecytFases1).isNotEqualTo(cronogramaCiecytFases2);
    }
}
