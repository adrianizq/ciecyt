package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CronogramaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cronograma.class);
        Cronograma cronograma1 = new Cronograma();
        cronograma1.setId(1L);
        Cronograma cronograma2 = new Cronograma();
        cronograma2.setId(cronograma1.getId());
        assertThat(cronograma1).isEqualTo(cronograma2);
        cronograma2.setId(2L);
        assertThat(cronograma1).isNotEqualTo(cronograma2);
        cronograma1.setId(null);
        assertThat(cronograma1).isNotEqualTo(cronograma2);
    }
}
