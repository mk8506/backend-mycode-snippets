package kr.minj.controllers.restful;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import org.springframework.beans.factory.annotation.Autowired;

import kr.minj.helpers.Pagination;
import kr.minj.helpers.RestHelper;
import kr.minj.models.Department;
import kr.minj.service.DepartmentService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RestController
public class DepartmentRestController {
  @Autowired
  private DepartmentService departmentService;

  @Autowired
  private RestHelper restHelper;

  /**
   * getList
   * @param keyword
   * @param nowPage
   * @return dataReturn
   */
  @GetMapping("/api/department")
  public Map<String, Object> getList(
    @RequestParam(value = "keyword", required = false) String keyword,
    @RequestParam(value = "page", defaultValue = "0") int nowPage,
    HttpServletResponse response
  ) {
    /* input & output init */
    Department input = new Department();
    input.setDname(keyword);
    input.setLoc(keyword); 
    List<Department> output = null;

    /* page number */
    if (nowPage != 0) {
      int totalCnt = 0;
      try {
        totalCnt = departmentService.getCnt(input);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      int listCnt = 10;
      int groupCnt = 5;
      Pagination pagination = new Pagination(nowPage, totalCnt, listCnt, groupCnt);
      Department.setOffset(pagination.getOffset());
      Department.setListCnt(pagination.getListCnt());
      Department.setNowPage(pagination.getNowPage());
    }

    /* search */
    try {
      output = departmentService.getList(input);
    } catch (Exception e) {
      restHelper.serverError(e);
      System.out.println("Exception");
      return null;
    }

    Map<String, Object> dataReturn =  new LinkedHashMap<String,Object>();
    dataReturn.put("departments", output);
    dataReturn.put("keyword", keyword);
    dataReturn.put("page", nowPage);

    return restHelper.sendJson(dataReturn);
  }
  
  /**
   * detail
   * @param response
   * @param deptno
   * @return dataReturn
   */
  @GetMapping("/api/department/{deptno}")
  public Map<String, Object> getItem(
    HttpServletResponse response,
    @PathVariable("deptno") int deptno
  ) {
    Department department = new Department();
    department.setDeptno(deptno);
    Department data = null;
    try {
      data = departmentService.getItem(department);
    } catch (Exception e) {
      restHelper.serverError(e);
      return null;
    }

    Map<String, Object> dataReturn =  new LinkedHashMap<String,Object>();
    dataReturn.put("department", data);
    return restHelper.sendJson(dataReturn);
  }
  
  /**
   * add
   * @return dataReturn
   */
  @GetMapping("/api/department/add")
  public Map<String, Object> add() {
    Map<String, Object> dataReturn =  new LinkedHashMap<String,Object>();
    return restHelper.sendJson(dataReturn);
  }
 
  /**
   * add_action
   * @param request
   * @param dname
   * @param loc
   * @return dataReturn
   */
  @PostMapping("/api/department/add_action")
  public Map<String, Object> add_action(
    HttpServletRequest request,
    @RequestParam("dname") String dname,
    @RequestParam("loc") String loc
  ) {
    //wrong access
    String referer = request.getHeader("referer"); //previous page
    if (referer == null || !referer.contains("/department")) {
      restHelper.badRequest("wrong access"); //redirect()
      return null;
    }

    Department department = new Department();
    department.setDname(dname);
    department.setLoc(loc);
    Department output = null;
    try {
      output = departmentService.add(department); // does it return [select] data?
    } catch (Exception e) {
      restHelper.serverError(e);
      System.out.println("Exception");
    }

    Map<String, Object> dataReturn =  new LinkedHashMap<String,Object>();
    dataReturn.put("item", output);
    return restHelper.sendJson(dataReturn);
  }

  /**
   * edit
   * @param deptno
   * @return dataReturn
   */
  @GetMapping("/api/department/edit/{deptno}")
  public Map<String, Object> edit(
     
    @PathVariable int deptno
  ) {
    Department department = new Department();
    department.setDeptno(deptno);
    Department data = null;
    try {
      data = departmentService.getItem(department);
    } catch (Exception e) {
      restHelper.serverError(e);
      System.out.println("Exception");
      return null;
    }
    Map<String, Object> dataReturn =  new LinkedHashMap<String,Object>();
    dataReturn.put("departments", data);
    return restHelper.sendJson(dataReturn);
  }

  /**
   * edit_action
   * @param request
   * @param deptno
   * @param dname
   * @param loc
   * @return sendJson(200, "OK", data, null);
   */
  @PostMapping("/api/department/edit_action/{deptno}")
  public Map<String, Object> edit_action(
    HttpServletRequest request,
    @PathVariable("deptno") int deptno,
    @RequestParam("dname") String dname,
    @RequestParam("loc") String loc
  ) {
    //wrong access
    String referer = request.getHeader("referer"); //previous page
    if (referer == null || !referer.contains("/department")) {
      restHelper.badRequest("wrong access"); //redirect()
      return null;
    }

    Department department = new Department();
    department.setDeptno(deptno);
    department.setDname(dname);
    department.setLoc(loc);
    Department output = null;
    try {
      output = departmentService.edit(department);
    } catch (Exception e) {
      restHelper.serverError(e);
      System.out.println("Exception");
    }

    Map<String, Object> dataReturn =  new LinkedHashMap<String,Object>();
    dataReturn.put("item", output);
    return restHelper.sendJson(dataReturn);
    // return this.sendJson(200, "OK", data, null);
  }
 
  /**
   * delete
   * @param request
   * @param deptno
   * @return sendJson(200, "OK", null, null);
   */
  @DeleteMapping("/api/department/delete_action/{deptno}")
  public Map<String, Object> delete(
    HttpServletRequest request,
    @PathVariable("deptno") int deptno
  ) {
    //wrong access
    String referer = request.getHeader("referer"); //previous page
    if (referer == null || !referer.contains("/department")) {
      restHelper.badRequest("wrong access"); //redirect()
      return null;
    }

    Department department = new Department();
    department.setDeptno(deptno);
    int output = 0;
    try {
      output = departmentService.delete(department);
    } catch (Exception e) {
      restHelper.serverError(e);
      System.out.println("Exception");
    }
    System.out.println(output);
    return restHelper.sendJson();
    // return this.sendJson(200, "OK", null, null);
  }
}
