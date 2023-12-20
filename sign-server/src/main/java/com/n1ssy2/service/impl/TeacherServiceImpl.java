package com.n1ssy2.service.impl;

import com.n1ssy2.constant.MessageConstant;
import com.n1ssy2.dto.TeacherDTO;
import com.n1ssy2.entity.Teacher;
import com.n1ssy2.exception.AccountNotFoundException;
import com.n1ssy2.mapper.TeacherMapper;
import com.n1ssy2.service.TeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
 * ClassName: TeacherServiceImpl
 * Package: com.n1ssy2.service.impl
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/20 23:41
 * @Version: 1.0
 */
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;
    /**
     * 登录
     * @param teacherDTO
     * @return
     */
    public Teacher login(TeacherDTO teacherDTO){
        String teacherId = teacherDTO.getTeacherId();
        String password = teacherDTO.getPassword();

        //1. 查询账号数据
        Teacher teacher = teacherMapper.getById(teacherId);

        //2. 检验账号是否异常
        if(teacher == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(teacher.getPassword())){
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }

        //3. 返回对象
        return teacher;
    }

}
