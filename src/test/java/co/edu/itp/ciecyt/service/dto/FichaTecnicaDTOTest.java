package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class FichaTecnicaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FichaTecnicaDTO.class);
        FichaTecnicaDTO fichaTecnicaDTO1 = new FichaTecnicaDTO();
        fichaTecnicaDTO1.setId(1L);
        FichaTecnicaDTO fichaTecnicaDTO2 = new FichaTecnicaDTO();
        assertThat(fichaTecnicaDTO1).isNotEqualTo(fichaTecnicaDTO2);
        fichaTecnicaDTO2.setId(fichaTecnicaDTO1.getId());
        assertThat(fichaTecnicaDTO1).isEqualTo(fichaTecnicaDTO2);
        fichaTecnicaDTO2.setId(2L);
        assertThat(fichaTecnicaDTO1).isNotEqualTo(fichaTecnicaDTO2);
        fichaTecnicaDTO1.setId(null);
        assertThat(fichaTecnicaDTO1).isNotEqualTo(fichaTecnicaDTO2);
    }
}
