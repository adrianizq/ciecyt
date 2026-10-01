package co.edu.itp.ciecyt.config;

import org.springframework.boot.webmvc.test.autoconfigure.MockMvcBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcConfigurerAdapter;
import org.springframework.web.context.WebApplicationContext;

@Configuration(proxyBeanMethods = false)
public class MockMvcSecurityConfiguration {

    @Bean
    public MockMvcBuilderCustomizer securityMockMvcBuilderCustomizer() {
        return builder -> builder.apply(new MockMvcConfigurerAdapter() {
            @Override
            public RequestPostProcessor beforeMockMvcCreated(ConfigurableMockMvcBuilder<?> mockMvcBuilder, WebApplicationContext wac) {
                return SecurityMockMvcRequestPostProcessors.testSecurityContext();
            }
        });
    }
}
