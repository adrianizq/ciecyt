package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class InformacionPasantiaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(InformacionPasantia.class);
        InformacionPasantia informacionPasantia1 = new InformacionPasantia();
        informacionPasantia1.setId(1L);
        InformacionPasantia informacionPasantia2 = new InformacionPasantia();
        informacionPasantia2.setId(informacionPasantia1.getId());
        assertThat(informacionPasantia1).isEqualTo(informacionPasantia2);
        informacionPasantia2.setId(2L);
        assertThat(informacionPasantia1).isNotEqualTo(informacionPasantia2);
        informacionPasantia1.setId(null);
        assertThat(informacionPasantia1).isNotEqualTo(informacionPasantia2);
    }
}
