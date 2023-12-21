package com.n1ssy2.service;

import com.n1ssy2.dto.StudentDTO;
import com.n1ssy2.entity.Student;

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
}
