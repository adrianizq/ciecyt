package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class CicloPropedeuticoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CicloPropedeuticoDTO.class);
        CicloPropedeuticoDTO cicloPropedeuticoDTO1 = new CicloPropedeuticoDTO();
        cicloPropedeuticoDTO1.setId(1L);
        CicloPropedeuticoDTO cicloPropedeuticoDTO2 = new CicloPropedeuticoDTO();
        assertThat(cicloPropedeuticoDTO1).isNotEqualTo(cicloPropedeuticoDTO2);
        cicloPropedeuticoDTO2.setId(cicloPropedeuticoDTO1.getId());
        assertThat(cicloPropedeuticoDTO1).isEqualTo(cicloPropedeuticoDTO2);
        cicloPropedeuticoDTO2.setId(2L);
        assertThat(cicloPropedeuticoDTO1).isNotEqualTo(cicloPropedeuticoDTO2);
        cicloPropedeuticoDTO1.setId(null);
        assertThat(cicloPropedeuticoDTO1).isNotEqualTo(cicloPropedeuticoDTO2);
    }
}
