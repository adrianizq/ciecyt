package co.edu.itp.ciecyt.config;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class SpaForwardFilterTest {

    private final SpaForwardFilter filter = new SpaForwardFilter();

    private String forwardUrl(String method, String uri) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest(method, uri);
        MockHttpServletResponse response = new MockHttpServletResponse();
        filter.doFilter(request, response, new MockFilterChain());
        return response.getForwardedUrl();
    }

    @Test
    public void shouldForwardClientRoutes() throws Exception {
        assertThat(forwardUrl("GET", "/login")).isEqualTo("/");
        assertThat(forwardUrl("GET", "/admin/user-management")).isEqualTo("/");
        assertThat(forwardUrl("GET", "/propuesta/informacion-general")).isEqualTo("/");
    }

    @Test
    public void shouldNotForwardRootOrStaticFiles() throws Exception {
        assertThat(forwardUrl("GET", "/")).isNull();
        assertThat(forwardUrl("GET", "/assets/index-abc123.css")).isNull();
        assertThat(forwardUrl("GET", "/content/scss/global.scss")).isNull();
        assertThat(forwardUrl("GET", "/i18n/es.json")).isNull();
        assertThat(forwardUrl("GET", "/favicon.ico")).isNull();
    }

    @Test
    public void shouldNotForwardBackendRequests() throws Exception {
        assertThat(forwardUrl("GET", "/api/menus")).isNull();
        assertThat(forwardUrl("GET", "/management/health")).isNull();
        assertThat(forwardUrl("GET", "/swagger-ui")).isNull();
    }

    @Test
    public void shouldNotForwardNonGetMethods() throws Exception {
        assertThat(forwardUrl("POST", "/admin/user-management")).isNull();
        assertThat(forwardUrl("PUT", "/proyecto/1")).isNull();
    }
}
