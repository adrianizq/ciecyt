package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class EntidadFinanciadoraDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadFinanciadoraDTO.class);
        EntidadFinanciadoraDTO entidadFinanciadoraDTO1 = new EntidadFinanciadoraDTO();
        entidadFinanciadoraDTO1.setId(1L);
        EntidadFinanciadoraDTO entidadFinanciadoraDTO2 = new EntidadFinanciadoraDTO();
        assertThat(entidadFinanciadoraDTO1).isNotEqualTo(entidadFinanciadoraDTO2);
        entidadFinanciadoraDTO2.setId(entidadFinanciadoraDTO1.getId());
        assertThat(entidadFinanciadoraDTO1).isEqualTo(entidadFinanciadoraDTO2);
        entidadFinanciadoraDTO2.setId(2L);
        assertThat(entidadFinanciadoraDTO1).isNotEqualTo(entidadFinanciadoraDTO2);
        entidadFinanciadoraDTO1.setId(null);
        assertThat(entidadFinanciadoraDTO1).isNotEqualTo(entidadFinanciadoraDTO2);
    }
}
