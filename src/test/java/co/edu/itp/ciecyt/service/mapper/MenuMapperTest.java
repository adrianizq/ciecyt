package co.edu.itp.ciecyt.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class MenuMapperTest {

    private MenuMapper menuMapper;

    @BeforeEach
    public void setUp() {
        menuMapper = new MenuMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(menuMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(menuMapper.fromId(null)).isNull();
    }
}
