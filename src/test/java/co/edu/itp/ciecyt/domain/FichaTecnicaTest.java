package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class FichaTecnicaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaTecnica.class);
        FichaTecnica fichaTecnica1 = new FichaTecnica();
        fichaTecnica1.setId(1L);
        FichaTecnica fichaTecnica2 = new FichaTecnica();
        fichaTecnica2.setId(fichaTecnica1.getId());
        assertThat(fichaTecnica1).isEqualTo(fichaTecnica2);
        fichaTecnica2.setId(2L);
        assertThat(fichaTecnica1).isNotEqualTo(fichaTecnica2);
        fichaTecnica1.setId(null);
        assertThat(fichaTecnica1).isNotEqualTo(fichaTecnica2);
    }
}
