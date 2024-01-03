package com.n1ssy2.controller;

import com.n1ssy2.constant.JwtClaimsConstant;
import com.n1ssy2.dto.CheckinCaseDTO;
import com.n1ssy2.dto.TeacherDTO;
import com.n1ssy2.entity.Course;
import com.n1ssy2.entity.Teacher;
import com.n1ssy2.properties.JwtProperties;
import com.n1ssy2.result.Result;
import com.n1ssy2.service.TeacherService;
import com.n1ssy2.utils.JwtUtil;
import com.n1ssy2.vo.CheckinCaseVO;
import com.n1ssy2.vo.TeacherCheckinRecordVO;
import com.n1ssy2.vo.TeacherLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: TeacherController
 * Package: com.n1ssy2.controller
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/20 23:16
 * @Version: 1.0
 */
@RestController
@RequestMapping("/teacher")
@Slf4j
@Api(tags = "教师端管理文档")
@CrossOrigin(origins = "*")//跨域请求
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param teacherDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<TeacherLoginVO> login(@RequestBody TeacherDTO teacherDTO) {
        log.info("教师登录：{}", teacherDTO);

        Teacher teacher = teacherService.login(teacherDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.TEACHER_ID, teacher.getTeacherId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        TeacherLoginVO teacherLoginVO = TeacherLoginVO.builder()
                .teacherId(teacher.getTeacherId())
                .teacherName(teacher.getTeacherName())
                .token(token)
                .build();

        return Result.success(teacherLoginVO);
    }

    /**
     * 教师课表查询
     *
     * @param teacherId
     * @return
     */
    @GetMapping("/schedule")
    @ApiOperation("教师课表查询")
    public Result<List<Course>> getCourseByTeacherId(String teacherId) {
        log.info("教师课表查询，教师id：{}", teacherId);
        List<Course> list = teacherService.getCourseByTeacherId(teacherId);
        return Result.success(list);
    }

    /**
     * 创建签到
     * @param checkinCaseDTO
     * @return
     */
    @PostMapping("/checkin/create")
    @ApiOperation("教师创建签到")
    public Result<String> createCheckin(@RequestBody CheckinCaseDTO checkinCaseDTO){
        log.info("教师创建签到: {}", checkinCaseDTO);
        String checkinCode = teacherService.createCheckin(checkinCaseDTO);
        return Result.success(checkinCode);
    }

    /**
     * 签到实例查询
     * @param teacherId
     * @return
     */
    @GetMapping("/checkin/queryByTeacherId")
    @ApiOperation("签到实例查询")
    public Result<List<CheckinCaseVO>> queryByTeacherId(String teacherId){
        log.info("查询签到实例，教师id{}", teacherId);
        List<CheckinCaseVO> caseVOS = teacherService.queryByTeacherId(teacherId);
        return Result.success(caseVOS);
    }

    /**
     * 签到记录表查询
     * @param checkinId
     * @return
     */
    @GetMapping("/checkin/queryByCheckinId")
    @ApiOperation("签到记录表查询")
    public Result<List<TeacherCheckinRecordVO>> queryByCheckinId(Integer checkinId){
        log.info("签到记录表查询: checkinId:{}", checkinId);
        List<TeacherCheckinRecordVO> recordVOS = teacherService.queryByCheckinId(checkinId);
        return Result.success(recordVOS);
    }

//    /**
//     * 教师课表导出
//     * @param teacherId
//     * @return
//     */
//    @GetMapping("/course/excel")
//    @ApiOperation("教师课表导出")
//    public Result<String> getExcel(String teacherId){
//        log.info("教师课表导出");
//        File teacherService.getExcel(teacherId);
//        return Result.success();
//    }
}
