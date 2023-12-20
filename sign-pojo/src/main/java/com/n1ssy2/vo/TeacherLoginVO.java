package com.n1ssy2.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: TeacherLoginVO
 * Package: com.n1ssy2.vo
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/20 23:51
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeacherLoginVO implements Serializable {
    private String teacherId;
    private String teacherName;
    private String token;
}
