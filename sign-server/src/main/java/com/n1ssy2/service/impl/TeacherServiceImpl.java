package com.n1ssy2.service.impl;

import com.n1ssy2.constant.CheckinConstant;
import com.n1ssy2.constant.MessageConstant;
import com.n1ssy2.context.BaseContext;
import com.n1ssy2.dto.CheckinCaseDTO;
import com.n1ssy2.dto.TeacherDTO;
import com.n1ssy2.entity.*;
import com.n1ssy2.exception.AccountNotFoundException;
import com.n1ssy2.exception.BaseException;
import com.n1ssy2.mapper.StudentMapper;
import com.n1ssy2.mapper.TeacherMapper;
import com.n1ssy2.service.TeacherService;
import com.n1ssy2.utils.RandomStr;
import com.n1ssy2.vo.CheckinCaseVO;
import com.n1ssy2.vo.TeacherCheckinRecordVO;
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
    @Autowired
    private StudentMapper studentMapper;

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
        List<Course> courses = teacherMapper.getCourseByTeacherId(teacherId);
        //判断是否能查询到课表
        if(courses != null && courses.size() > 0){
            return courses;
        }else{
            throw new BaseException(MessageConstant.COURSE_NOT_FOUND);
        }
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
        if(checkinCase.getValidTime() == null){
            checkinCase.setValidTime(30);
        }

        teacherMapper.addCheckinCase(checkinCase);
        Integer checkinId = teacherMapper.getCheckinId(checkinNode);
        checkinCase.setCheckinId(checkinId);

        //将该课程班上的所有学生提取出来
        List<String> studentIds = studentMapper.getStudentIdByCourseId(checkinCase.getCourseId());
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

        //判断该教师是否任何签到实例
        if(cases != null && cases.size() > 0){
            List<CheckinCaseVO> caseVOS = new ArrayList<>();
            cases.forEach(checkinCase -> {
                CheckinCaseVO checkinCaseVO = new CheckinCaseVO();
                BeanUtils.copyProperties(checkinCase, checkinCaseVO);
                checkinCaseVO.setCourseName(studentMapper.getCourseNameByCourseId(checkinCase.getCourseId()));
                caseVOS.add(checkinCaseVO);
            });

            return caseVOS;
        }else{
            throw new BaseException(MessageConstant.CASE_NOT_FOUND);
        }
    }

    /**
     * 签到记录表查询
     * @param checkinId
     * @return
     */
    public List<TeacherCheckinRecordVO> queryByCheckinId(Integer checkinId){
        //查找签到记录表
        List<CheckinRecord> records = teacherMapper.getCheckinRecordByCheckinId(checkinId);

        if(records != null && records.size() > 0){

            List<TeacherCheckinRecordVO> recordVOS = new ArrayList<>();
            records.forEach(checkinRecord -> {
                TeacherCheckinRecordVO teacherCheckinRecordVO = new TeacherCheckinRecordVO();
                BeanUtils.copyProperties(checkinRecord, teacherCheckinRecordVO);

                //获取studentName
                String studentName = studentMapper.getStudentNameByStudentId(checkinRecord.getStudentId());
                teacherCheckinRecordVO.setStudentName(studentName);
                recordVOS.add(teacherCheckinRecordVO);
            });
            return recordVOS;
        }else{
            throw new BaseException(MessageConstant.RECORD_NOT_FOUND);
        }
    }

    /**
     * 教师课表导入
     * @param dataList
     * @return
     */
    public void setCourseByFile(List<Course> dataList){
        dataList.forEach(data -> {
            TeacherCourse teacherCourse = TeacherCourse.builder()
                    .teacherId(BaseContext.getCurrentId())
                    .courseId(data.getCourseId())
                    .build();

            //检查课程是否存在
            Course course = teacherMapper.getCourseByCourseId(data.getCourseId());
            if(course == null){
                teacherMapper.addCourse(data);
                teacherMapper.addTeacherCourse(teacherCourse);
            }else{
                //检查教师是否有该课程
                TeacherCourse teacherCourse1 = teacherMapper.getTeacherCourse(teacherCourse);
                if(teacherCourse1 == null){
                    teacherMapper.addTeacherCourse(teacherCourse);
                }
            }
        });
    }

    /**
     * 教师注册
     * @param teacher
     */
    public void regist(Teacher teacher){
        //检查工号重复
        Teacher tea = teacherMapper.getTeacherByteacherId(teacher.getTeacherId());
        if(tea != null){
            throw new BaseException(MessageConstant.ACCOUNT_EXIST);
        }

        //检查密码是否大于6位
        String password = teacher.getPassword();
        if(password.length()<6){
            throw new BaseException(MessageConstant.PASSWORD_TOO_SHORT);
        }

        //密码md5加密
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        teacher.setPassword(password);

        //注册
        teacherMapper.regist(teacher);
    }
}
