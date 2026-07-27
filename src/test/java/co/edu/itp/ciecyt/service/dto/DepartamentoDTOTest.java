package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class DepartamentoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepartamentoDTO.class);
        DepartamentoDTO departamentoDTO1 = new DepartamentoDTO();
        departamentoDTO1.setId(1L);
        DepartamentoDTO departamentoDTO2 = new DepartamentoDTO();
        assertThat(departamentoDTO1).isNotEqualTo(departamentoDTO2);
        departamentoDTO2.setId(departamentoDTO1.getId());
        assertThat(departamentoDTO1).isEqualTo(departamentoDTO2);
        departamentoDTO2.setId(2L);
        assertThat(departamentoDTO1).isNotEqualTo(departamentoDTO2);
        departamentoDTO1.setId(null);
        assertThat(departamentoDTO1).isNotEqualTo(departamentoDTO2);
    }
}
