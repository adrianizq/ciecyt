package co.edu.itp.ciecyt.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Serves the single page app for any GET/HEAD request without a file extension,
 * so deep links and browser refresh work when Spring Boot serves the frontend.
 * Requests with an extension and backend prefixes are left untouched, so the
 * resource handler can serve static files and Spring Security still guards the API.
 */
public class SpaForwardFilter extends OncePerRequestFilter {

    private static final List<String> BACKEND_PREFIXES = List.of(
        "/api",
        "/management",
        "/app",
        "/content",
        "/i18n",
        "/assets",
        "/swagger-ui",
        "/v3",
        "/test",
        "/h2-console",
        "/actuator",
        "/error",
        "/webjars"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        if (shouldForward(request)) {
            request.getRequestDispatcher("/").forward(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean shouldForward(HttpServletRequest request) {
        String method = request.getMethod();
        if (!"GET".equals(method) && !"HEAD".equals(method)) {
            return false;
        }
        String path = request.getRequestURI().substring(request.getContextPath().length());
        if (path.isEmpty() || "/".equals(path)) {
            return false;
        }
        String lastSegment = path.substring(path.lastIndexOf('/') + 1);
        if (lastSegment.contains(".")) {
            return false;
        }
        String lowerPath = path.toLowerCase();
        return BACKEND_PREFIXES.stream().noneMatch(lowerPath::startsWith);
    }
}
