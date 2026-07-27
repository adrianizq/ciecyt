package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class PreguntaAuthorityTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreguntaAuthority.class);
        PreguntaAuthority preguntaAuthority1 = new PreguntaAuthority();
        preguntaAuthority1.setId(1L);
        PreguntaAuthority preguntaAuthority2 = new PreguntaAuthority();
        preguntaAuthority2.setId(preguntaAuthority1.getId());
        assertThat(preguntaAuthority1).isEqualTo(preguntaAuthority2);
        preguntaAuthority2.setId(2L);
        assertThat(preguntaAuthority1).isNotEqualTo(preguntaAuthority2);
        preguntaAuthority1.setId(null);
        assertThat(preguntaAuthority1).isNotEqualTo(preguntaAuthority2);
    }
}
