package com.n1ssy2.service.impl;

import com.n1ssy2.constant.CheckinConstant;
import com.n1ssy2.constant.MessageConstant;
import com.n1ssy2.dto.CheckinCaseDTO;
import com.n1ssy2.dto.CheckinRecordDTO;
import com.n1ssy2.dto.StudentDTO;
import com.n1ssy2.entity.CheckinRecord;
import com.n1ssy2.entity.Student;
import com.n1ssy2.exception.AccountNotFoundException;
import com.n1ssy2.mapper.StudentMapper;
import com.n1ssy2.service.StudentService;
import com.n1ssy2.vo.CheckinRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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

    /**
     * 学生签到
     * @param checkinRecordVO
     */
    public void checkin(CheckinRecordVO checkinRecordVO){
        //通过签到码获取签到id
        Integer checkinId = studentMapper.getCheckinIdByCheckinNode(checkinRecordVO.getCheckinNode());

        CheckinRecord checkinRecord = CheckinRecord.builder()
                .checkinId(checkinId)
                .studentId(checkinRecordVO.getStudentId())
                .checkinTime(Timestamp.valueOf(LocalDateTime.now()))
                .checkinStatus(CheckinConstant.STATUS_CHECKED)
                .build();

        studentMapper.addCheckinRecord(checkinRecord);
    }
}
