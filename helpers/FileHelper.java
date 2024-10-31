package kr.minj.helpers;
import java.io.*;

import java.io.FileOutputStream;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

//manage all logs here in helpers!
@Slf4j
@Component
public class FileHelper {
  //singleton
  private static FileHelper current;
  public static FileHelper getInstance() {
      if (current == null) {
          current = new FileHelper();
      }
      return current;
  }
  private FileHelper() {
      super();
  }

  /**
   * write
   * @param filePath
   * @param data
   * @throws Exception
   */
  public void write(String filePath, byte[] data) throws Exception {
    OutputStream os = null;
    try {
      os = new FileOutputStream(filePath);
      os.write(data);
    } catch (FileNotFoundException e) {
      log.error("cannot find the file", e);
      throw e;
    } catch (IOException e) {
      log.error("cannot write the file (IOException)", e);
      throw e;
    } catch (Exception e) {
      log.error("cannot write the file (Exception)", e);
      throw e;
    } finally {
      if (os != null) {
        try {
          os.close();
        } catch (IOException e) {
          log.error("cannot close the file", e);
          throw e;
        }
      }
    }
  }

  /**
   * read
   * @param filePath
   * @return
   * @throws Exception
   */
  public byte[] read(String filePath) throws Exception {
    InputStream is = null;
    byte[] data = null;

    try {
      is = new FileInputStream(filePath);
      data = new byte[is.available()];
      is.read(data);
    } catch (FileNotFoundException e) {
      log.error("cannot find the file", e);
      throw e;
    } catch (IOException e) {
      log.error("cannot write the file (IOException)", e);
      throw e;
    } catch (Exception e) {
      log.error("cannot write the file (Exception)", e);
      throw e;
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
          log.error("cannot close the file", e);
          throw e;
        }
      }
    }

    return data;
  }

  /**
   * write String
   * @param filePath
   * @param content
   * @throws Exception
   */
  public void writeString(String filePath, String content) throws Exception {
    try {
      this.write(filePath, content.getBytes("utf-8"));
    } catch (Exception e) {
      log.error("incoding error", e);
      throw e;
    }
  }

  /**
   * read String
   * @param filePath
   * @return
   * @throws Exception
   */
  public String readString(String filePath) throws Exception {
    String content = null;
    try {
      byte[] data = read(filePath);
      content = new String(data, "utf-8");
    } catch (Exception e) {
      log.error("incoding error", e);
      throw e;
    }
    return content;
  }
}