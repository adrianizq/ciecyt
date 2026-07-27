package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class SolicitudTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Solicitud.class);
        Solicitud solicitud1 = new Solicitud();
        solicitud1.setId(1L);
        Solicitud solicitud2 = new Solicitud();
        solicitud2.setId(solicitud1.getId());
        assertThat(solicitud1).isEqualTo(solicitud2);
        solicitud2.setId(2L);
        assertThat(solicitud1).isNotEqualTo(solicitud2);
        solicitud1.setId(null);
        assertThat(solicitud1).isNotEqualTo(solicitud2);
    }
}
