package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CicloPropedeuticoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CicloPropedeutico.class);
        CicloPropedeutico cicloPropedeutico1 = new CicloPropedeutico();
        cicloPropedeutico1.setId(1L);
        CicloPropedeutico cicloPropedeutico2 = new CicloPropedeutico();
        cicloPropedeutico2.setId(cicloPropedeutico1.getId());
        assertThat(cicloPropedeutico1).isEqualTo(cicloPropedeutico2);
        cicloPropedeutico2.setId(2L);
        assertThat(cicloPropedeutico1).isNotEqualTo(cicloPropedeutico2);
        cicloPropedeutico1.setId(null);
        assertThat(cicloPropedeutico1).isNotEqualTo(cicloPropedeutico2);
    }
}
