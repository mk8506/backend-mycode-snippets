package kr.minj.helpers;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class RestHelper {
  @Autowired
  private HttpServletResponse response;

  /**
   * template method of server error
   * @param status
   * @param message
   * @param data
   * @param error
   * @return json(Map)
   */
  public Map<String, Object> sendJson(
    int status, String message, Map<String, Object> data, Exception error
  ) {
    //header
    response.setContentType("application/json; charset=UTF-8");
    response.setStatus(status);
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
    response.setHeader("Access-Control-Allow-Origin", "*");
    
    //default
    Map<String, Object> result = new LinkedHashMap<String, Object>();
    result.put("status", status);
    result.put("message", message);
    result.put("timestamp", LocalDateTime.now().toString());

    //yes data
    if (data != null) {
      result.putAll(data);
    }

    //yes error
    if (error != null) {
      result.put("error", error.getClass().getName());
      result.put("message", error.getMessage());
      ByteArrayOutputStream bos = new ByteArrayOutputStream();
      PrintStream ps = new PrintStream(bos);
      error.printStackTrace(ps);
      result.put("trace", bos.toString());
    }

    return result;
  }

  // public Map<String, Object> sendJson(Map<String, Object> data) {
  //   return this.sendJson(200, "OK", data, null);
  // }

  // public Map<String, Object> sendJson() {
  //   return this.sendJson(200, "OK", null, null);
  // }

  /**
   * send error json when error
   * @param status
   * @param message
   * @return sendJson()
   */
  public Map<String, Object> sendError(int status, String message) {
    Exception error = new Exception(message);
    return this.sendJson(200, null, null, error);
  }

  // public Map<String, Object> sendError(String message) {
  //   return this.sendError(400, message);
  // }

  public Map<String, Object> badRequest(String message) {
    return this.sendError(400, message);
  }

  public Map<String, Object> badRequest(Exception error) {
    return this.sendJson(400, null, null, error);
  }

  public Map<String, Object> serverError(String message) {
    return this.sendError(400, message);
  }
  
  public Map<String, Object> serverError(Exception error) {
    return this.sendJson(500, null, null, error);
  }

}
