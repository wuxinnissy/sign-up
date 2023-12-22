package com.n1ssy2.service.impl;

import com.n1ssy2.constant.CheckinConstant;
import com.n1ssy2.constant.MessageConstant;
import com.n1ssy2.dto.CheckinCaseDTO;
import com.n1ssy2.dto.TeacherDTO;
import com.n1ssy2.entity.*;
import com.n1ssy2.exception.AccountNotFoundException;
import com.n1ssy2.mapper.StudentMapper;
import com.n1ssy2.mapper.TeacherMapper;
import com.n1ssy2.service.TeacherService;
import com.n1ssy2.utils.RandomStr;
import com.n1ssy2.vo.CheckinCaseVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
     *
     * @param teacherDTO
     * @return
     */
    public Teacher login(TeacherDTO teacherDTO) {
        String teacherId = teacherDTO.getTeacherId();
        String password = teacherDTO.getPassword();

        //1. 查询账号数据
        Teacher teacher = teacherMapper.getById(teacherId);

        //2. 检验账号是否异常
        if (teacher == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(teacher.getPassword())) {
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }

        //3. 返回对象
        return teacher;
    }

    /**
     * 查询课表
     *
     * @param teacherId
     * @return
     */
    public List<Course> getCourseByTeacherId(String teacherId) {
        List<Course> list = teacherMapper.getCourseByTeacherId(teacherId);
        return list;
    }

    /**
     * 创建签到
     *
     * @param checkinCaseDTO
     * @return
     */
    public String createCheckin(CheckinCaseDTO checkinCaseDTO) {
        //获取随机8位字符串作为签到码，并检查签到码是否重复
        String checkinNode = RandomStr.generateRandomStr();
        String node = teacherMapper.equalsCheckinNode(checkinNode);
        while (node != null) {//防止生成重复的签到码
            checkinNode = RandomStr.generateRandomStr();
            node = teacherMapper.equalsCheckinNode(checkinNode);
        }


        CheckinCase checkinCase = new CheckinCase();
        BeanUtils.copyProperties(checkinCaseDTO, checkinCase);

        checkinCase.setCheckinNode(checkinNode);
        checkinCase.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));

        teacherMapper.addCheckinCase(checkinCase);
        Integer checkinId = teacherMapper.getCheckinId(checkinNode);
        checkinCase.setCheckinId(checkinId);

        //将该课程班上的所有学生提取出来
        List<String> studentIds = teacherMapper.getStudentCourseByCourseId(checkinCase.getCourseId());
        List<CheckinRecord> checkinRecords = new ArrayList<>();
        for (String studentId : studentIds) {
            CheckinRecord checkinRecord = CheckinRecord.builder()
                    .checkinId(checkinId)
                    .studentId(studentId)
                    .checkinStatus(CheckinConstant.STATUS_UNCHECKED)
                    .build();
            checkinRecords.add(checkinRecord);
        }

        //创建签到记录表
        teacherMapper.createCheckinRecord(checkinRecords);

        return checkinNode;
    }

    /**
     * 签到实例查询
     *
     * @param teacherId
     * @return
     */
    public List<CheckinCaseVO> queryByTeacherId(String teacherId) {
        //通过教师id查找签到实例
        List<CheckinCase> cases = teacherMapper.queryByTeacherId(teacherId);

        //将签到实例对应的课程名字找出来
        List<String> courseNames = teacherMapper.getCourseNamesByCourseId(cases);

        //新建返回类型然后将结果赋值
        List<CheckinCaseVO> caseVOS = new ArrayList<>();
        int size = cases.size();
        for (int i = 0; i < size; i++) {
            CheckinCaseVO checkinCaseVO = new CheckinCaseVO();
            BeanUtils.copyProperties(cases.get(i), checkinCaseVO);
            checkinCaseVO.setCourseName(courseNames.get(i));
            caseVOS.add(checkinCaseVO);
        }

        return caseVOS;
    }
}
