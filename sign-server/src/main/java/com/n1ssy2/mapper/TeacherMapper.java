package com.n1ssy2.mapper;

import com.n1ssy2.entity.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * ClassName: TeacherMapper
 * Package: com.n1ssy2.mapper
 * Description:
 *
 * @Auther: N1ssy2
 * @Create: 2023/12/21 0:02
 * @Version: 1.0
 */
@Mapper
public interface TeacherMapper {
    /**
     * 根据id查找教师信息
     * @param teacherId
     * @return
     */
    @Select("select * from teacher where teacher_id = #{teacherId}")
    Teacher getById(String teacherId);

    /**
     * 根据教师id查找教师课表
     * @param teacherId
     * @return
     */
    List<Course> getCourseByTeacherId(String teacherId);

    /**
     * 检查有无相同签到码
     * @param checkinNode
     * @return
     */
    @Select("select checkin_node from checkin_case where checkin_node = #{checkinNode}")
    String equalsCheckinNode(String checkinNode);

    /**
     * 创建签到实例
     * @param checkinCase
     * @return
     */
    @Insert("insert into checkin_case(create_time, course_id, teacher_id, checkin_node) " +
            "values (#{createTime}, #{courseId}, #{teacherId}, #{checkinNode})")
    void addCheckinCase(CheckinCase checkinCase);

    /**
     * 获取checkinId
     * @param checkinNode
     * @return
     */
    @Select("select checkin_id from checkin_case where checkin_node = #{checkinNode}")
    Integer getCheckinId(String checkinNode);



    /**
     * 创建签到记录表
     * @param checkinRecords
     * @return
     */
    void createCheckinRecord(List<CheckinRecord> checkinRecords);

    /**
     * 根据teacherId查询签到实例
     * @param teacherId
     * @return
     */
    @Select("select * from checkin_case where teacher_id = #{teacherId}")
    List<CheckinCase> queryByTeacherId(String teacherId);

    /**
     * 根据courseId查询课程名字
     * @param cases
     * @return
     */
    List<String> getCourseNamesByCourseId(List<CheckinCase> cases);

    /**
     * 查询签到记录表
     * @param checkinId
     * @return
     */
    @Select("select * from checkin_record where checkin_id = #{checkinId}")
    List<CheckinRecord> getCheckinRecordByCheckinId(Integer checkinId);
}
