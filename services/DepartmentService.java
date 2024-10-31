package kr.minj.service;

import java.util.List;

import kr.minj.exceptions.MyBatisException;
import kr.minj.exceptions.ServiceNoResultException;
import kr.minj.models.Department;

//에러는 호출한 쪽에서 발생시키기


public interface DepartmentService {
  public Department add(Department params) throws MyBatisException, ServiceNoResultException, Exception;
  public Department edit(Department params) throws MyBatisException, ServiceNoResultException, Exception;
  public int delete(Department params) throws MyBatisException, ServiceNoResultException, Exception;
  public Department getItem(Department params) throws MyBatisException, ServiceNoResultException, Exception;
  public List<Department> getList() throws MyBatisException, ServiceNoResultException, Exception;
  public List<Department> getList(Department params) throws Exception;
  public int getCnt(Department params) throws Exception;
}
