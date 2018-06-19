package com.syj.test.dao;

import com.syj.test.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository(value = "questionDao")
//@Mapper
public interface QuestionDao {

    String TABLE_NAME=" question ";
    String INSERT_FIELDS=" title,content,user_id,created_date,comment_count ";
    String SELECT_FIELDS=" id, " + INSERT_FIELDS;

    @Insert({"insert into " + TABLE_NAME + " (" + INSERT_FIELDS + ") " + " values(#{title},#{content},#{userId},#{createdDate},#{commentCount})"})
    int addQuestion(Question question);

    //@Select({"select id, title, content, comment_count,created_date,user_id from question where user_id = #{userId} order by id desc limit #{offset},#{limit}"})
    List<Question> selectLatestQuestions(@Param("userId") int userid,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);

}
