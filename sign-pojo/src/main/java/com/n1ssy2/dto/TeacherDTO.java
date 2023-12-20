package com.n1ssy2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * ClassName: TeacherDTO
 * Package: com.n1ssy2.dto
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/20 23:22
 * @Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherDTO implements Serializable {
    private String teacherId;
    private String password;
}
