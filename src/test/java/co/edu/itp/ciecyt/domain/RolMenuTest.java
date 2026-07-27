package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class RolMenuTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RolMenu.class);
        RolMenu rolMenu1 = new RolMenu();
        rolMenu1.setId(1L);
        RolMenu rolMenu2 = new RolMenu();
        rolMenu2.setId(rolMenu1.getId());
        assertThat(rolMenu1).isEqualTo(rolMenu2);
        rolMenu2.setId(2L);
        assertThat(rolMenu1).isNotEqualTo(rolMenu2);
        rolMenu1.setId(null);
        assertThat(rolMenu1).isNotEqualTo(rolMenu2);
    }
}
