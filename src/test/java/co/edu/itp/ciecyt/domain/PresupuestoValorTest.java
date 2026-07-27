package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class PresupuestoValorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PresupuestoValor.class);
        PresupuestoValor presupuestoValor1 = new PresupuestoValor();
        presupuestoValor1.setId(1L);
        PresupuestoValor presupuestoValor2 = new PresupuestoValor();
        presupuestoValor2.setId(presupuestoValor1.getId());
        assertThat(presupuestoValor1).isEqualTo(presupuestoValor2);
        presupuestoValor2.setId(2L);
        assertThat(presupuestoValor1).isNotEqualTo(presupuestoValor2);
        presupuestoValor1.setId(null);
        assertThat(presupuestoValor1).isNotEqualTo(presupuestoValor2);
    }
}
