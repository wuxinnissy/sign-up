package com.n1ssy2.service.impl;

import com.n1ssy2.constant.MessageConstant;
import com.n1ssy2.dto.CheckinCaseDTO;
import com.n1ssy2.dto.StudentDTO;
import com.n1ssy2.entity.Student;
import com.n1ssy2.exception.AccountNotFoundException;
import com.n1ssy2.mapper.StudentMapper;
import com.n1ssy2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * @PackageName: com.n1ssy2.service.impl
 * @ClassName: StudentServiceImpl
 * @Arthor: N1ssy2
 * @Create: 2023/12/21 14:52
 * @Version: 1.0
 **/
@Service
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    /**
     * 学生登录
     * @param studentDTO
     * @return
     */
    public Student login(StudentDTO studentDTO){
        String id = studentDTO.getStudentId();
        String password = studentDTO.getPassword();

        //查询学生数据
        Student student = studentMapper.getById(id);

        //检查账号是否异常
        if(student == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(student.getPassword())){
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }

        return student;
    }
}
