package com.n1ssy2.service;

import com.n1ssy2.dto.CheckinRecordDTO;
import com.n1ssy2.dto.StudentDTO;
import com.n1ssy2.entity.Course;
import com.n1ssy2.entity.Student;
import com.n1ssy2.vo.StudentCheckinRecordVO;

import java.util.List;

/**
 * @PackageName: com.n1ssy2.service
 * @ClassName: StudentService
 * @Arthor: N1ssy2
 * @Create: 2023/12/21 14:52
 * @Version: 1.0
 **/
public interface StudentService {
    /**
     * 学生登录
     * @param studentDTO
     * @return
     */
    Student login(StudentDTO studentDTO);

    /**
     * 学生课表查询
     * @param studentId
     * @return
     */
    List<Course> getCourseByStudentId(String studentId);

    /**
     * 学生签到
     * @param checkinRecordDTO
     */
    void checkin(CheckinRecordDTO checkinRecordDTO);

    /**
     * 学生签到记录查询
     * @param studentId
     * @return
     */
    List<StudentCheckinRecordVO> queryByStudentId(String studentId);

    /**
     * 学生注册
     * @param student
     */
    void regist(Student student);
}
