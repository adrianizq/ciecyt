package co.edu.itp.ciecyt.config;

import java.util.Map;

import org.zalando.problem.Problem;

import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

public class ProblemSerializer extends ValueSerializer<Problem> {

    @Override
    public void serialize(Problem value, JsonGenerator gen, SerializationContext context) {
        gen.writeStartObject();
        if (value.getType() != null) {
            gen.writeStringProperty("type", value.getType().toString());
        }
        if (value.getTitle() != null) {
            gen.writeStringProperty("title", value.getTitle());
        }
        if (value.getStatus() != null) {
            gen.writeNumberProperty("status", value.getStatus().getStatusCode());
        }
        if (value.getDetail() != null) {
            gen.writeStringProperty("detail", value.getDetail());
        }
        if (value.getInstance() != null) {
            gen.writeStringProperty("instance", value.getInstance().toString());
        }
        for (Map.Entry<String, Object> entry : value.getParameters().entrySet()) {
            Object parameter = entry.getValue();
            if (parameter == null) {
                continue;
            }
            gen.writeName(entry.getKey());
            context.writeValue(gen, parameter);
        }
        gen.writeEndObject();
    }
}
