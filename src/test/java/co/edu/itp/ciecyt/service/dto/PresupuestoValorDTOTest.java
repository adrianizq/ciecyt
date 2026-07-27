package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class PresupuestoValorDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PresupuestoValorDTO.class);
        PresupuestoValorDTO presupuestoValorDTO1 = new PresupuestoValorDTO();
        presupuestoValorDTO1.setId(1L);
        PresupuestoValorDTO presupuestoValorDTO2 = new PresupuestoValorDTO();
        assertThat(presupuestoValorDTO1).isNotEqualTo(presupuestoValorDTO2);
        presupuestoValorDTO2.setId(presupuestoValorDTO1.getId());
        assertThat(presupuestoValorDTO1).isEqualTo(presupuestoValorDTO2);
        presupuestoValorDTO2.setId(2L);
        assertThat(presupuestoValorDTO1).isNotEqualTo(presupuestoValorDTO2);
        presupuestoValorDTO1.setId(null);
        assertThat(presupuestoValorDTO1).isNotEqualTo(presupuestoValorDTO2);
    }
}
