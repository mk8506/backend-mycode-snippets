package kr.minj.helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebHelper {
  @Autowired
  private HttpServletRequest request;

  @Autowired
  private HttpServletResponse response;

  /**
   * get client's ip address
   * @return ip
   */
  public String getClientIp() {
    String ip = request.getHeader("X-Forwarded-For");
    if (ip == null) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null) {
        ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null) {
        ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null) {
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null) {
        ip = request.getRemoteAddr();
    }  
    return ip;
  }

  /**
   * save cookie
   * @param name
   * @param value
   * @param maxAge
   * @param domain
   * @param path
   * @throws Exception
   */
  public void writeCookie(String name, String value, int maxAge, String domain, String path) throws Exception {
    value = URLEncoder.encode(value, "utf-8");
    Cookie cookie = new Cookie(name, value);
    cookie.setPath(path);
    response.addCookie(cookie);
  }

  /**
   * delete cookie
   * @param name
   * @throws Exception
   */
  public void deleteCookie(String name) throws Exception {
    this.writeCookie(name, null, -1, null, "/");
  }

  /**
   * set status code, alert msg, then go to {url}
   * @param statusCode
   * @param url
   * @param msg
   */
  public void redirect(int statusCode, String url, String msg) {
    response.setStatus(statusCode);
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = null;
    try {
      out = response.getWriter();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (msg !=null && !msg.equals("")) { //msg exists
      out.print("<script>");
      out.print("alert('" +msg+ "');");
      out.print("</script>");
    }

    if (url != null && !url.equals("")) { //url exists
      out.println("<meta http-equiv='refresh' content='0; url=" +url+ "' />");
    } else { //if not go back
      out.print("<script>");
      out.print("history.back();");
      out.print("</script>");
    }

    out.flush(); //write status code
  }

  /**
   * wrong param
   * @param e
   * @param msg
   */
  public void badRequest(Exception e) { //String msg
    this.redirect(403, null, e.getMessage()); //or msg (error msg defined by me)
  }

  /**
   * wrong java / sql
   * @param e
   */
  public void serverError(Exception e) {
    String msg = e.getMessage().trim().replace("'", "\\'").split(System.lineSeparator())[0];
    this.redirect(500, null, msg);
  }
}
