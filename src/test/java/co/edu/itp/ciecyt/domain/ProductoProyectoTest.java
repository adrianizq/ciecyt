package co.edu.itp.ciecyt.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.edu.itp.ciecyt.web.rest.TestUtil;

public class ProductoProyectoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductoProyecto.class);
        ProductoProyecto productoProyecto1 = new ProductoProyecto();
        productoProyecto1.setId(1L);
        ProductoProyecto productoProyecto2 = new ProductoProyecto();
        productoProyecto2.setId(productoProyecto1.getId());
        assertThat(productoProyecto1).isEqualTo(productoProyecto2);
        productoProyecto2.setId(2L);
        assertThat(productoProyecto1).isNotEqualTo(productoProyecto2);
        productoProyecto1.setId(null);
        assertThat(productoProyecto1).isNotEqualTo(productoProyecto2);
    }
}
