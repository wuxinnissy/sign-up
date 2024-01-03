package com.n1ssy2.controller;

import com.n1ssy2.constant.JwtClaimsConstant;
import com.n1ssy2.constant.MessageConstant;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.util.*;

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

    /**
     * 教师课表导出
     * @param teacherId
     * @return
     */
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportData(String teacherId) {
        log.info("教师课表导出，教师id{}",teacherId);
        // 查询数据库，获取数据列表
        List<Course> dataList = teacherService.getCourseByTeacherId(teacherId);

        // 创建Excel工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("course_id");
        headerRow.createCell(1).setCellValue("course_name");
        headerRow.createCell(2).setCellValue("begin_time");
        headerRow.createCell(3).setCellValue("end_time");

        // 填充数据
        int rowNum = 1;
        for (Course data : dataList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(data.getCourseId());
            row.createCell(1).setCellValue(data.getCourseName());
            row.createCell(2).setCellValue(data.getBeginTime());
            row.createCell(3).setCellValue(data.getEndTime());
        }

        // 将Excel内容写入字节数组
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            workbook.write(out);
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 设置响应头，告诉浏览器下载文件
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "data.xlsx");

        // 将Excel内容以字节数组形式返回给前端
        return new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
    }

    /**
     * 导入课表
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream()); // 读取上传的Excel文件
            Sheet sheet = workbook.getSheetAt(0); // 假设Excel中只有一个sheet，取第一个

            List<Course> dataList = new ArrayList<>();
            Iterator<Row> iterator = sheet.iterator();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getRowNum() == 0) { // 跳过表头
                    continue;
                }
                Course data = new Course();
                data.setCourseId(currentRow.getCell(0).getStringCellValue());
                data.setCourseName(currentRow.getCell(1).getStringCellValue());
                data.setBeginTime(Timestamp.valueOf(currentRow.getCell(2).getLocalDateTimeCellValue()));
                data.setEndTime(Timestamp.valueOf(currentRow.getCell(3).getLocalDateTimeCellValue()));

                dataList.add(data);
            }

            // 保存数据到数据库
            teacherService.setCourseByFile(dataList);

            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
