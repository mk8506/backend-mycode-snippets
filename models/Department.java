package kr.minj.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Department {
  private int deptno;
  private String dname;
  private String loc;

  @Getter
  @Setter
  private static int listCnt = 0;

  @Getter
  @Setter
  private static int offset = 0;

  public String getKey() {
 
    String a = null;
    return a;
  }
}
