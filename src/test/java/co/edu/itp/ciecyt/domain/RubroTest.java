package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class RubroTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rubro.class);
        Rubro rubro1 = new Rubro();
        rubro1.setId(1L);
        Rubro rubro2 = new Rubro();
        rubro2.setId(rubro1.getId());
        assertThat(rubro1).isEqualTo(rubro2);
        rubro2.setId(2L);
        assertThat(rubro1).isNotEqualTo(rubro2);
        rubro1.setId(null);
        assertThat(rubro1).isNotEqualTo(rubro2);
    }
}
