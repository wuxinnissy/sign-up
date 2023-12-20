package com.n1ssy2.controller;

import com.n1ssy2.constant.JwtClaimsConstant;
import com.n1ssy2.dto.TeacherDTO;
import com.n1ssy2.entity.Teacher;
import com.n1ssy2.properties.JwtProperties;
import com.n1ssy2.result.Result;
import com.n1ssy2.service.TeacherService;
import com.n1ssy2.utils.JwtUtil;
import com.n1ssy2.vo.TeacherLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
public class TeacherController {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private JwtProperties jwtProperties;
    /**
     * 登录
     * @param teacherDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<TeacherLoginVO> login(@RequestBody TeacherDTO teacherDTO){
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
}
