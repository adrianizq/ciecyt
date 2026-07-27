package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class AcuerdoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acuerdo.class);
        Acuerdo acuerdo1 = new Acuerdo();
        acuerdo1.setId(1L);
        Acuerdo acuerdo2 = new Acuerdo();
        acuerdo2.setId(acuerdo1.getId());
        assertThat(acuerdo1).isEqualTo(acuerdo2);
        acuerdo2.setId(2L);
        assertThat(acuerdo1).isNotEqualTo(acuerdo2);
        acuerdo1.setId(null);
        assertThat(acuerdo1).isNotEqualTo(acuerdo2);
    }
}
