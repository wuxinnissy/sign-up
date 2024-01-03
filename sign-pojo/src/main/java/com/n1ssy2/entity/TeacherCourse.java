package com.n1ssy2.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: TeacherCourse
 * Package: com.n1ssy2.entity
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2024/1/3 9:56
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherCourse implements Serializable {
    private String teacherId;
    private String courseId;
}
