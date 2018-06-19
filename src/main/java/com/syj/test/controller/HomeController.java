package com.syj.test.controller;

import com.syj.test.model.Question;
import com.syj.test.model.User;
import com.syj.test.model.ViewObject;
import com.syj.test.service.QuestionService;
import com.syj.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    QuestionService questionService;
    @Autowired
    UserService userService;

    @RequestMapping(path = {"/","/index"}, method = RequestMethod.GET)
    //@ResponseBody
    public String index(Model model){

        List<ViewObject> vos = getQuestions(0, 0, 10);
        System.out.println("the size of vos: " + vos.size());
        model.addAttribute("vos", vos);
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET})
    public String userIndex(Model model, @PathVariable("userId") int userId){
        List<ViewObject> vos = getQuestions(userId, 0, 10);
        model.addAttribute("vos", vos);
        return "index";
    }


    private List<ViewObject> getQuestions(int userId, int offset, int limit){
        List<ViewObject> vos = new ArrayList<ViewObject>();
        List<Question> questionList = questionService.selectLatestQuestions(userId, offset, limit);
        for(Question question :questionList){
            User user = userService.getUser(question.getId());
//            System.out.println(user == null);
//            此处代码对应的前台功能需要改进，如果question所对应的user为空，则前端会显示异常，实际上这种情况不太可能出现
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("user", user);
            vos.add(vo);
        }
        return vos;
    }
}
