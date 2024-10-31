package kr.minj.helper;
import java.util.Calendar;

public class DatePrinter {
  public static void printDateTime(Calendar cal) {
    int yy = cal.get(Calendar.YEAR);
    int mm = cal.get(Calendar.MONTH)+1;
    int dd = cal.get(Calendar.DAY_OF_MONTH);
    int hh = cal.get(Calendar.HOUR_OF_DAY); //-> 24 vs HOUR -> 12
    int mi = cal.get(Calendar.MINUTE);
    int ss = cal.get(Calendar.SECOND);

    System.out.printf("%dyear %dmonth %dday %dhour %dminute %dsecond\n", yy, mm, dd, hh, mi, ss);
  }
}
