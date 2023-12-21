package com.n1ssy2.mapper;

import com.n1ssy2.entity.Course;
import com.n1ssy2.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: TeacherMapper
 * Package: com.n1ssy2.mapper
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/21 0:02
 * @Version: 1.0
 */
@Mapper
public interface TeacherMapper {
    /**
     * 根据id查找教师信息
     * @param teacherId
     * @return
     */
    @Select("select * from teacher where teacher_id = #{teacherId}")
    Teacher getById(String teacherId);

    /**
     * 根据教师id查找教师课表
     * @param teacherId
     * @return
     */
    List<Course> getCourseByTeacherId(String teacherId);
}
