package com.syj.test.service;

import com.syj.test.dao.QuestionDao;
import com.syj.test.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    public List<Question> selectLatestQuestions(int userId, int offset, int limit){
        return questionDao.selectLatestQuestions(userId,offset,limit);
    }
}
