package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ImpactosEsperadosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImpactosEsperados.class);
        ImpactosEsperados impactosEsperados1 = new ImpactosEsperados();
        impactosEsperados1.setId(1L);
        ImpactosEsperados impactosEsperados2 = new ImpactosEsperados();
        impactosEsperados2.setId(impactosEsperados1.getId());
        assertThat(impactosEsperados1).isEqualTo(impactosEsperados2);
        impactosEsperados2.setId(2L);
        assertThat(impactosEsperados1).isNotEqualTo(impactosEsperados2);
        impactosEsperados1.setId(null);
        assertThat(impactosEsperados1).isNotEqualTo(impactosEsperados2);
    }
}
