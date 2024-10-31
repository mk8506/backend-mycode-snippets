package kr.minj.service.impl;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.minj.exceptions.ServiceNoResultException;
import kr.minj.mappers.DepartmentMapper;
import kr.minj.models.Department;
import kr.minj.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

  private SqlSession sqlSession;

  public DepartmentServiceImpl(SqlSession sqlSession) {
    this.sqlSession = sqlSession;
  }

  @Autowired
  DepartmentMapper departmentMapper;

  @Override
  public Department add(Department params) throws ServiceNoResultException, Exception {
    Department result = null;
    int rows = departmentMapper.insert(params);
    if (rows == 0) {
      throw new ServiceNoResultException("no data added");
    }
    result = sqlSession.selectOne("kr.minj.mappers.DepartmentMapper.selectOne", params);
    return result;
  }

  @Override
  public Department edit(Department params) throws ServiceNoResultException, Exception {
    Department result = null;
    int rows = departmentMapper.update(params);
    if (rows == 0) {
      throw new ServiceNoResultException("no data updated");
    }
    result = departmentMapper.selectOne(params);
    return result;
  }

  @Override
  public int delete(Department params) throws ServiceNoResultException, Exception {
    int result = 0;
    int rows = departmentMapper.delete(params);
    if (rows == 0) {
      throw new ServiceNoResultException("no data deleted");
    }
    return result;
  }

  @Override
  public Department getItem(Department params) throws ServiceNoResultException, Exception {
    return departmentMapper.selectOne(params);
  }

  @Override
  public List<Department> getList() throws ServiceNoResultException, Exception {
    return departmentMapper.selectList(); //try-catch -> java error (what i got before..)
  }
  
  @Override
  public List<Department> getList(Department params) throws Exception {
    return departmentMapper.selectList1(params);
  }

  @Override
  public int getCnt(Department params) throws Exception {
    return departmentMapper.selectCount(params);
  }
}
