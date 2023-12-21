package com.n1ssy2.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @PackageName: com.n1ssy2.vo
 * @ClassName: StudentLoginVO
 * @Arthor: N1ssy2
 * @Create: 2023/12/21 14:46
 * @Version: 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentLoginVO implements Serializable {
    private String studentId;
    private String studentName;
    private String token;
}
