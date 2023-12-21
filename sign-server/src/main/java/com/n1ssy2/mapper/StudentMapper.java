package com.n1ssy2.mapper;

import com.n1ssy2.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @PackageName: com.n1ssy2.mapper
 * @ClassName: StudentMapper
 * @Arthor: N1ssy2
 * @Create: 2023/12/21 14:52
 * @Version: 1.0
 **/
@Mapper
public interface StudentMapper {
    /**
     * 根据id查询信息
     * @param studentId
     * @return
     */
    @Select("select * from student where student_id = #{studentId}")
    Student getById(String studentId);
}
