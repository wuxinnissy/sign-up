package com.n1ssy2.mapper;

import com.n1ssy2.entity.CheckinRecord;
import com.n1ssy2.entity.Course;
import com.n1ssy2.entity.Student;
import com.n1ssy2.entity.StudentCourse;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.nio.charset.CharsetEncoder;
import java.sql.Timestamp;
import java.util.List;

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

    /**
     * 通过签到码获取签到id
     * @param checkinNode
     * @return
     */
    @Select("select checkin_id from checkin_case where checkin_node = #{checkinNode}")
    Integer getCheckinIdByCheckinNode(String checkinNode);

    /**
     * 添加签到记录
     * @param checkinRecord
     */
    void updateCheckinRecord(CheckinRecord checkinRecord);

    /**
     * 读取签到记录确认签到记录是否存在
     * @param checkinRecord
     * @return
     */
    CheckinRecord getCheckinRecord(CheckinRecord checkinRecord);

    /**
     * 根据courseId查找对应课程学生
     * @param courseId
     * @return
     */
    @Select("select student_id from student_course where course_id = #{courseId}")
    List<String> getStudentIdByCourseId(String courseId);

    /**
     * 通过id查找学生姓名
     * @param studentId
     * @return
     */
    @Select("select student_name from student where student_id = #{studentId}")
    String getStudentNameByStudentId(String studentId);

    /**
     * 通过studentId查找签到记录
     * @param studentId
     * @return
     */
    @Select("select * from checkin_record where student_id = #{studentId}")
    List<CheckinRecord> queryByStudentId(String studentId);

    /**
     * 通过checkinId查找courseId
     * @param checkinId
     * @return
     */
    @Select("select course_id from checkin_case where checkin_id = #{checkinId}")
    String getCourseIdBycheckinId(Integer checkinId);

    /**
     * 通过courseId查询courseName
     * @parma courseId
     * @return
     */
    @Select("select course_name from course where course_id = #{courseId}")
    String getCourseNameByCourseId(String courseId);

    /**
     * 通过studentId查询course
     * @param studentId
     * @return
     */
    List<Course> getCourseByStudentId(String studentId);

    /**
     * 获取考勤创建时间
     * @param checkinId
     * @return
     */
    @Select("select create_time from checkin_case where checkin_id = #{checkinId}")
    Timestamp getCreateTimeByCheckinId(Integer checkinId);

    /**
     * 获取签到状态
     * @param checkinRecord
     * @return
     */
    Short getCheckinStatusByCheckinId(CheckinRecord checkinRecord);

    /**
     * 获取签到有效时长
     * @param checkinId
     * @return
     */
    @Select("select valid_time from checkin_case where checkin_id = #{checkinId}")
    Integer getValidTimeByCheckinId(Integer checkinId);

    /**
     * 查询学生信息
     * @param studentId
     * @return
     */
    @Select("select * from student where student_id = #{studentId}")
    Student getStudentByStudentId(String studentId);

    /**
     * 注册
     * @param student
     */
    @Insert("insert into student (student_id, student_name, password) values " +
            "(#{studentId}, #{studentName}, #{password})")
    void regist(Student student);

    /**
     * 添加学生课表
     * @param studentCourse
     */
    @Insert("insert into student_course(student_id, course_id) values (#{studentId}, #{courseId})")
    void addStudentCourse(StudentCourse studentCourse);

    /**
     * 获取学生课表
     * @param studentCourse
     * @return
     */
    @Select("select * from student_course where student_id = #{studentId} and course_id = #{courseId}")
    StudentCourse getStudentCourse(StudentCourse studentCourse);
}
