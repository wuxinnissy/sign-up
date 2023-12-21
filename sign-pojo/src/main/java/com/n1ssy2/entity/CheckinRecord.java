package com.n1ssy2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * ClassName: CheckinRecord
 * Package: com.n1ssy2.entity
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/21 23:36
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckinRecord implements Serializable {
    private String checkinId;
    private String studentId;
    private LocalDateTime checkinTime;
    private Short checkinStatus;
}
