package com.n1ssy2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @PackageName: com.n1ssy2.dto
 * @ClassName: StudentDTO
 * @Arthor: N1ssy2
 * @Create: 2023/12/21 14:49
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO implements Serializable {
    private String studentId;
    private String password;
}
