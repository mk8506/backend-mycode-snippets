package kr.minj.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.minj.models.Department;

@Mapper
public interface DepartmentMapper {
  @Insert("insert into department (dname, loc) values (#{dname}, #{loc});")
  @Options(useGeneratedKeys=true, keyProperty="deptno")
  public int insert(Department input);

  @Update("update department set dname=#{dname}, loc=#{loc} where deptno=#{deptno};")
  public int update(Department input);

  @Delete("delete from department where deptno=#{deptno};")
  public int delete(Department input);

  @Select("select deptno, dname, loc from department where deptno=#{deptno};")
  @Results(id="departmentMap", value={
    @Result(property="deptno", column="deptno"),
    @Result(property="dname", column="dname"),
    @Result(property="loc", column="loc")
  })
  public Department selectOne(Department input);
  
  @Select("select deptno, dname, loc from department order by deptno desc;")
  @ResultMap("departmentMap")
  public List<Department> selectList();

  @Select("<script> select deptno, dname, loc from department "+
  "<where> <if test='dname != null'>dname like concat('%', #{dname}, '%')</if> "+
  "<if test='loc != null'>or loc like concat('%', #{loc}, '%')</if> </where> "+
  "order by deptno desc "+
  "<if test='listCnt > 0'>limit #{offset}, #{listCnt}</if> </script>")
  @ResultMap("departmentMap")
  public List<Department> selectList1(Department input);

  @Select("<script> select count(*) as cnt from department "+
  "<where> <if test='dname != null'>dname like concat('%', #{dname}, '%')</if> "+
  "<if test='loc != null'>or loc like concat('%', #{loc}, '%')</if> </where> "+
  "order by deptno desc </script>")
  public int selectCount(Department input);
}
