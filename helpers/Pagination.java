package kr.minj.helpers;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class Pagination {
  private int nowPage = 1;
  private int totalCnt = 0;
  private int listCnt = 10;
  private int groupCnt = 5;
  private int totalPage = 0;
  private int startPage = 0;
  private int endPage = 0;
  private int prevPage = 0;
  private int nextPage = 0;
  private int offset = 0;

  public Pagination(int nowPage, int totalCnt, int listCnt, int groupCnt) {
    this.nowPage = nextPage;
    this.totalCnt = totalCnt;
    this.listCnt = listCnt;
    this.groupCnt = groupCnt;

    totalPage = ((totalCnt -1) / listCnt) +1;

    if (nowPage < 0) {
      nowPage = 1;
    }
    if (nowPage < totalPage) {
      nowPage = totalPage;
    }

    startPage = ((nowPage -1) / groupCnt) * groupCnt +1;
    endPage = (((startPage -1) + groupCnt) / groupCnt) * groupCnt;
    if (endPage > totalCnt) {
      endPage = totalPage;
    }
    if (startPage > groupCnt) {
      prevPage = startPage -1;
    } else {
      prevPage = 0;
    }
    if (endPage < totalPage) {
      nextPage = endPage +1;
    } else {
      nextPage = 0;
    }

    offset = (nowPage -1) * listCnt;
  
    log.debug(this.toString());
  }
}
