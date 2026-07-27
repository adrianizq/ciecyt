package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class FormatoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formato.class);
        Formato formato1 = new Formato();
        formato1.setId(1L);
        Formato formato2 = new Formato();
        formato2.setId(formato1.getId());
        assertThat(formato1).isEqualTo(formato2);
        formato2.setId(2L);
        assertThat(formato1).isNotEqualTo(formato2);
        formato1.setId(null);
        assertThat(formato1).isNotEqualTo(formato2);
    }
}
