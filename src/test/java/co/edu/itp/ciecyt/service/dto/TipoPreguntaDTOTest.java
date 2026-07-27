package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class TipoPreguntaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPreguntaDTO.class);
        TipoPreguntaDTO tipoPreguntaDTO1 = new TipoPreguntaDTO();
        tipoPreguntaDTO1.setId(1L);
        TipoPreguntaDTO tipoPreguntaDTO2 = new TipoPreguntaDTO();
        assertThat(tipoPreguntaDTO1).isNotEqualTo(tipoPreguntaDTO2);
        tipoPreguntaDTO2.setId(tipoPreguntaDTO1.getId());
        assertThat(tipoPreguntaDTO1).isEqualTo(tipoPreguntaDTO2);
        tipoPreguntaDTO2.setId(2L);
        assertThat(tipoPreguntaDTO1).isNotEqualTo(tipoPreguntaDTO2);
        tipoPreguntaDTO1.setId(null);
        assertThat(tipoPreguntaDTO1).isNotEqualTo(tipoPreguntaDTO2);
    }
}
