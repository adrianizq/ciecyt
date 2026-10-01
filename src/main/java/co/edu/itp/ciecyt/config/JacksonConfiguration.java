package co.edu.itp.ciecyt.config;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.Problem;

import tools.jackson.databind.module.SimpleModule;
import tools.jackson.datatype.hibernate7.Hibernate7Module;

@Configuration
public class JacksonConfiguration {

    @Bean
    public JsonMapperBuilderCustomizer jacksonMapperCustomizer() {
        return builder -> {
            builder.addModule(new Hibernate7Module());
            SimpleModule problemModule = new SimpleModule("problem");
            problemModule.addSerializer(Problem.class, new ProblemSerializer());
            builder.addModule(problemModule);
        };
    }
}
