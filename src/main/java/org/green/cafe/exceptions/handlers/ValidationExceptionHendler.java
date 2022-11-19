package org.green.cafe.exceptions.handlers;

import org.green.cafe.exceptions.ValidationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;

@Provider
public class ValidationExceptionHendler implements ExceptionMapper<ValidationException> {
  @Override
  public Response toResponse(ValidationException exception) {
    Map<String, Object> resultMessage = new HashMap<>();
    resultMessage.put("message", exception.getMessage());
    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(resultMessage)
        .type(MediaType.APPLICATION_JSON)
        .build();
  }
}
