package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class FacultadTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Facultad.class);
        Facultad facultad1 = new Facultad();
        facultad1.setId(1L);
        Facultad facultad2 = new Facultad();
        facultad2.setId(facultad1.getId());
        assertThat(facultad1).isEqualTo(facultad2);
        facultad2.setId(2L);
        assertThat(facultad1).isNotEqualTo(facultad2);
        facultad1.setId(null);
        assertThat(facultad1).isNotEqualTo(facultad2);
    }
}
