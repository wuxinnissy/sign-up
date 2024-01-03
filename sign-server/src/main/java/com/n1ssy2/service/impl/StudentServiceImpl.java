package com.n1ssy2.service.impl;

import com.n1ssy2.constant.CheckinConstant;
import com.n1ssy2.constant.MessageConstant;
import com.n1ssy2.dto.CheckinRecordDTO;
import com.n1ssy2.dto.StudentDTO;
import com.n1ssy2.entity.CheckinRecord;
import com.n1ssy2.entity.Course;
import com.n1ssy2.entity.Student;
import com.n1ssy2.entity.StudentCourse;
import com.n1ssy2.exception.AccountNotFoundException;
import com.n1ssy2.exception.BaseException;
import com.n1ssy2.mapper.StudentMapper;
import com.n1ssy2.service.StudentService;
import com.n1ssy2.vo.StudentCheckinRecordVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
     *
     * @param studentDTO
     * @return
     */
    public Student login(StudentDTO studentDTO) {
        String id = studentDTO.getStudentId();
        String password = studentDTO.getPassword();

        //查询学生数据
        Student student = studentMapper.getById(id);

        //检查账号是否异常
        if (student == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(student.getPassword())) {
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }

        return student;
    }

    /**
     * 学生课表查询
     *
     * @param studentId
     * @return
     */
    public List<Course> getCourseByStudentId(String studentId) {
        List<Course> courses = studentMapper.getCourseByStudentId(studentId);
        //判断是否能查询到课表
        if (courses != null && courses.size() > 0) {
            return courses;
        } else {
            throw new BaseException(MessageConstant.COURSE_NOT_FOUND);
        }
    }

    /**
     * 学生签到
     *
     * @param checkinRecordDTO
     */
    public void checkin(CheckinRecordDTO checkinRecordDTO) {
        //通过签到码获取签到id
        Integer checkinId = studentMapper.getCheckinIdByCheckinNode(checkinRecordDTO.getCheckinNode());

        //检查签到码是否有效
        if (checkinId == null) {
            throw new BaseException(MessageConstant.CHECKIN_ID_ERROR);
        }



        //检查签到码时效
        Timestamp createTime = studentMapper.getCreateTimeByCheckinId(checkinId);
        Timestamp myTime = Timestamp.valueOf(LocalDateTime.now());
        Timestamp lateTime = new Timestamp(createTime.getTime() + (1 * 60 * 1000));//1min时效
        Timestamp maxTime = new Timestamp(createTime.getTime() + (studentMapper.getValidTimeByCheckinId(checkinId) * 60 * 1000));//30min时效

        if (myTime.compareTo(maxTime) > 0) {//超时缺勤
            throw new BaseException(MessageConstant.CHECKIN_TIME_ERROR);
        }


        CheckinRecord checkinRecord = CheckinRecord.builder()
                .checkinId(checkinId)
                .studentId(checkinRecordDTO.getStudentId())
                .checkinTime(Timestamp.valueOf(LocalDateTime.now()))
                .checkinStatus(myTime.compareTo(lateTime) > 0 ? CheckinConstant.STATUS_LATE : CheckinConstant.STATUS_CHECKED)
                .build();


        //检查该学生是否在这个签到码对应课程
        CheckinRecord checkinRecord1 = studentMapper.getCheckinRecord(checkinRecord);
        if (checkinRecord1 == null) {
            throw new BaseException(MessageConstant.STU_NOT_IN_COURSE);
        }

        //检查是否已经签到过
        if(!Objects.equals(studentMapper.getCheckinStatusByCheckinId(checkinRecord), CheckinConstant.STATUS_UNCHECKED)){
            throw new BaseException(MessageConstant.CHECKINED);
        }

        studentMapper.updateCheckinRecord(checkinRecord);
    }

    /**
     * 学生签到记录查询
     *
     * @param studentId
     * @return
     */
    public List<StudentCheckinRecordVO> queryByStudentId(String studentId) {
        //查询签到记录
        List<CheckinRecord> checkinRecords = studentMapper.queryByStudentId(studentId);

        if (checkinRecords != null && checkinRecords.size() > 0) {
            List<StudentCheckinRecordVO> recordVOS = new ArrayList<>();

            checkinRecords.forEach(checkinRecord -> {
                StudentCheckinRecordVO studentCheckinRecordVO = new StudentCheckinRecordVO();
                BeanUtils.copyProperties(checkinRecord, studentCheckinRecordVO);

                //获取courseName
                String courseId = studentMapper.getCourseIdBycheckinId(checkinRecord.getCheckinId());
                String courseName = studentMapper.getCourseNameByCourseId(courseId);

                studentCheckinRecordVO.setCourseName(courseName);

                recordVOS.add(studentCheckinRecordVO);
            });
            return recordVOS;
        } else {
            throw new BaseException(MessageConstant.STU_RECORD_NOT_FOUND);
        }
    }
}
