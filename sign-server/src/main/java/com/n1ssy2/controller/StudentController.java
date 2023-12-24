package com.n1ssy2.controller;

import com.n1ssy2.constant.JwtClaimsConstant;
import com.n1ssy2.dto.CheckinRecordDTO;
import com.n1ssy2.dto.StudentDTO;
import com.n1ssy2.entity.Student;
import com.n1ssy2.properties.JwtProperties;
import com.n1ssy2.result.Result;
import com.n1ssy2.service.StudentService;
import com.n1ssy2.utils.JwtUtil;
import com.n1ssy2.vo.StudentCheckinRecordVO;
import com.n1ssy2.vo.StudentLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @PackageName: com.n1ssy2.controller
 * @ClassName: StudentController
 * @Arthor: N1ssy2
 * @Create: 2023/12/21 14:51
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/student")
@Api(tags = "学生端管理文档")
@Slf4j
@CrossOrigin(origins = "*")//跨域请求
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 学生登录
     *
     * @param studentDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("学生登录")
    public Result<StudentLoginVO> login(@RequestBody StudentDTO studentDTO) {
        log.info("学生登录:{}", studentDTO);
        Student student = studentService.login(studentDTO);

        //登录成功之后生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.STUDENT_ID, student.getStudentId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims
        );

        StudentLoginVO studentLoginVO = StudentLoginVO.builder()
                .studentId(student.getStudentId())
                .studentName(student.getStudentName())
                .token(token)
                .build();

        return Result.success(studentLoginVO);
    }

    /**
     * 学生签到
     * @param checkinRecordDTO
     * @return
     */
    @PostMapping("/checkin/checkinByStudent")
    @ApiOperation("学生签到")
    public Result<String> checkin(@RequestBody CheckinRecordDTO checkinRecordDTO){
        log.info("学生签到：{}", checkinRecordDTO);
        studentService.checkin(checkinRecordDTO);
        return Result.success();
    }

    /**
     * 学生签到记录查询
     * @param studentId
     * @return
     */
    @GetMapping("/checkin/queryByStudentId")
    @ApiOperation("学生签到记录查询")
    public Result<List<StudentCheckinRecordVO>> queryByStudentId(String studentId){
        log.info("学生签到记录查询, id:{}",studentId);
        List<StudentCheckinRecordVO> recordVOS = studentService.queryByStudentId(studentId);
        return Result.success(recordVOS);
    }
}
