package kr.minj.helper;
import java.util.regex.Pattern;
import kr.minj.exception.RegexException;

public class RegexHelper {
  private static RegexHelper current = null;
  public static RegexHelper getInstance() {
    if (current == null) {
      current = new RegexHelper();
    }
    return current;
  }
  private RegexHelper() {
    super();
  }

  /**
   * string != null
   * @param str
   * @param msg
   * @throws RegexException
   */
  public void isValue(String str, String msg) throws RegexException {
    if (str == null || str.trim().equals("")) {
      throw new RegexException(msg);
    }
  }

  /**
   * number
   * @param str
   * @param msg
   * @throws RegexException
   */
  public void isNum(String str, String msg) throws RegexException {
    if (!Pattern.matches("^[0-9]*$", msg)) {
      throw new RegexException(msg);
    }
  }

  
}
