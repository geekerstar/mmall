package com.mmall.controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: Geekerstar(jikewenku.com)
 * @Date: 2018/6/22 9:04
 * @Description:
 */
@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;
    /*
     * @Description: 用户登录
     *
     * @auther: Geekerstar(jikewenku.com)
     * @date: 2018/6/22 9:28
     * @param: [username, password, session]
     * @return: java.lang.Object
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String username, String password, HttpSession session){
        //service-->mybatis-->dao
        ServerResponse<User> response = iUserService.login(username,password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;

    }

    /*
     * @Description: 用户登出
     *
     * @auther: Geekerstar(jikewenku.com)
     * @date: 2018/6/22 11:29
     * @param: [session]
     * @return: com.mmall.common.ServerResponse<java.lang.String>
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /*
     * @Description: 用户注册
     *
     * @auther: Geekerstar(jikewenku.com)
     * @date: 2018/6/22 12:09
     * @param: 
     * @return: 
     */

    @RequestMapping(value = "register.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }

    /*
     * @Description: 校验功能
     *
     * @auther: Geekerstar(jikewenku.com)
     * @date: 2018/6/22 14:51
     * @param: [str, type]
     * @return: com.mmall.common.ServerResponse<java.lang.String>
     */
    @RequestMapping(value = "check_valid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
        return iUserService.checkValid(str,type);
    }

    /*
     * @Description: 获取用户登录信息
     *
     * @auther: Geekerstar(jikewenku.com)
     * @date: 2018/6/22 14:52
     * @param: 
     * @return: 
     */

    @RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByErrorMessgae("用户未登录，无法获取当前用户的信息");
    }
    /*
     * @Description: 忘记密码
     *
     * @auther: Geekerstar(jikewenku.com)
     * @date: 2018/6/22 14:52
     * @param: [username]
     * @return: com.mmall.common.ServerResponse<java.lang.String>
     */

    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }


    /*
     * @Description: 检查问题答案
     *
     * @auther: Geekerstar(jikewenku.com)
     * @date: 2018/6/22 14:53
     * @param: 
     * @return: 
     */

    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }
}