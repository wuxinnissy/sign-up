package com.n1ssy2.service;

import com.n1ssy2.dto.CheckinCaseDTO;
import com.n1ssy2.dto.TeacherDTO;
import com.n1ssy2.entity.Course;
import com.n1ssy2.entity.Teacher;

import java.util.List;

/**
 * ClassName: TeacherService
 * Package: com.n1ssy2.service
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/20 23:40
 * @Version: 1.0
 */
public interface TeacherService {
    /**
     * 登录
     * @param teacherDTO
     * @return
     */
    Teacher login(TeacherDTO teacherDTO);

    /**
     * 查询课表
     * @param teacherId
     * @return
     */
    List<Course> getCourseByTeacherId(String teacherId);

    /**
     * 创建签到
     * @param checkinCaseDTO
     * @return
     */
    String createCheckin(CheckinCaseDTO checkinCaseDTO);
}
