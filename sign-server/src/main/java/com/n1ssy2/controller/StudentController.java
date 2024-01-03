package com.n1ssy2.controller;

import com.n1ssy2.constant.JwtClaimsConstant;
import com.n1ssy2.constant.MessageConstant;
import com.n1ssy2.dto.CheckinRecordDTO;
import com.n1ssy2.dto.StudentDTO;
import com.n1ssy2.entity.Course;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.zip.CheckedOutputStream;

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
     * 学生课表查询
     * @param studentId
     * @return
     */
    @GetMapping("/schedule")
    @ApiOperation("学生课表查询")
    public Result<List<Course>> getCourseByStudentId(String studentId){
        log.info("学生课表查询, 学生id: {}",studentId);
        List<Course> courses = studentService.getCourseByStudentId(studentId);
        return Result.success(courses);
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

    /**
     * 注册
     * @param student
     * @return
     */
    @PostMapping("/regist")
    @ApiOperation("学生注册")
    public Result<String> regist(@RequestBody Student student){
        log.info("学生注册：{}", student);
        studentService.regist(student);
        return Result.success();
    }

    /**
     * 教师课表导出
     * @param studentId
     * @return
     */
    @GetMapping(value = "/export", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation("学生课表导出")
    public ResponseEntity<byte[]> exportData(String studentId) {
        log.info("学生课表导出，学生id{}",studentId);
        // 查询数据库，获取数据列表
        List<Course> dataList = studentService.getCourseByStudentId(studentId);

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
    @ApiOperation("学生课表导入")
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
            studentService.setCourseByFile(dataList);

            return Result.success();
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }
}
