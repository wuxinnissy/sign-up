package com.n1ssy2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * PackageName: com.n1ssy2.entity
 * ClassName: StudentCourse
 * Description: 学生课程表
 *
 * @Arthor: N1ssy2
 * @Create: 2023/12/22 12:53
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentCourse implements Serializable {
    private String studentId;
    private String courseId;
}
