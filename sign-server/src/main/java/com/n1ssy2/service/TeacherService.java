package com.n1ssy2.service;

import com.n1ssy2.dto.TeacherDTO;
import com.n1ssy2.entity.Teacher;

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
}
