package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class RolesModalidadMapperTest {

    private RolesModalidadMapper rolesModalidadMapper;

    @BeforeEach
    public void setUp() {
        rolesModalidadMapper = new RolesModalidadMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(rolesModalidadMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(rolesModalidadMapper.fromId(null)).isNull();
    }
}
