package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class FacultadDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FacultadDTO.class);
        FacultadDTO facultadDTO1 = new FacultadDTO();
        facultadDTO1.setId(1L);
        FacultadDTO facultadDTO2 = new FacultadDTO();
        assertThat(facultadDTO1).isNotEqualTo(facultadDTO2);
        facultadDTO2.setId(facultadDTO1.getId());
        assertThat(facultadDTO1).isEqualTo(facultadDTO2);
        facultadDTO2.setId(2L);
        assertThat(facultadDTO1).isNotEqualTo(facultadDTO2);
        facultadDTO1.setId(null);
        assertThat(facultadDTO1).isNotEqualTo(facultadDTO2);
    }
}
