package co.edu.itp.ciecyt.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ProgramaDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProgramaDTO.class);
        ProgramaDTO programaDTO1 = new ProgramaDTO();
        programaDTO1.setId(1L);
        ProgramaDTO programaDTO2 = new ProgramaDTO();
        assertThat(programaDTO1).isNotEqualTo(programaDTO2);
        programaDTO2.setId(programaDTO1.getId());
        assertThat(programaDTO1).isEqualTo(programaDTO2);
        programaDTO2.setId(2L);
        assertThat(programaDTO1).isNotEqualTo(programaDTO2);
        programaDTO1.setId(null);
        assertThat(programaDTO1).isNotEqualTo(programaDTO2);
    }
}
