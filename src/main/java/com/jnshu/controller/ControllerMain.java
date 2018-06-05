package com.jnshu.controller;

import com.jnshu.model.*;
import com.jnshu.service.ServiceDao;
import com.jnshu.tools.DESUtil;
import com.jnshu.tools.MD5Util;
import com.jnshu.tools.SendSMSSDK;
import com.whalin.MemCached.MemCachedClient;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

/**
 * @program: smsdemo
 * @description: 非权限页面
 * @author: Mr.xweiba
 * @create: 2018-05-29 20:54
 **/
@Controller
public class ControllerMain {
    private static Logger logger = LoggerFactory.getLogger(ControllerMain.class);
    @Autowired
    ServiceDao serviceDao;
    @Autowired
    MemCachedClient memCachedClient;
    @Autowired
    SendSMSSDK rlSMS;

    // private String codeValidate;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home(Model model, StudentQV studentQV) throws Exception {
        StudentCustom studentCustom = new StudentCustom();
        studentCustom.setIsSuper(1);
        studentQV.setStudentCustom(studentCustom);
        model.addAttribute("studentCount", serviceDao.countStudent());
        model.addAttribute("countWorkStundet", serviceDao.countWorkStundet());
        model.addAttribute("superStudent", serviceDao.findListStudent(studentQV));
        return "home";
    }

    @RequestMapping(value = "/profession", method = RequestMethod.GET)
    public String profession(Model model) throws Exception {
        List<Profession> professionList = serviceDao.findByListProfession();
        model.addAttribute("professionList", professionList);
        return "profession";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public String validate(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, UserAuth userAuth, String code) throws Exception {
        logger.debug("code: " + code);
        // 判断code
        if(memCachedClient.get(code + httpServletRequest.getSession().getId())==null){
            logger.debug("code 验证失败");
            return "redirect:/admin/login";
        }
        String passWordMd5 = MD5Util.stringToMD5(userAuth.getAu_password());
        userAuth.setAu_password(passWordMd5);
        Integer userId = serviceDao.userAuth(userAuth);
        logger.debug("userId" + userId);
        if (userId != null) {
            logger.debug("验证通过");
            // token
            String token = userId + "=" + System.currentTimeMillis();
            // key
            String key = "liuhuan1";

            byte[] bytes = DESUtil.encrypt(token, key);
            Cookie cookie = new Cookie("token", Base64.encodeBase64String(bytes));
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath("/");

            // 将用户信息保存在Cookie中
            Cookie cookie1 = new Cookie("username", URLEncoder.encode(userAuth.getAu_username(), "UTF-8"));
            cookie1.setMaxAge(60 * 60 * 24 * 7);
            cookie1.setPath("/");
            httpServletResponse.addCookie(cookie);
            httpServletResponse.addCookie(cookie1);

            // 将Session保存到缓存中
            memCachedClient.set(httpServletRequest.getSession().getId(), "session_id");
            return "redirect:/admin/students";
        }
        return "redirect:/admin/login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if(c.getName().equals("token")){
                    c.setMaxAge(0);
                    httpServletResponse.addCookie(c);
                }
            }
        }
        // 删除Session 缓存
        memCachedClient.delete(httpServletRequest.getSession().getId());
        // 删除session
        httpServletRequest.getSession().invalidate();
        return "redirect:/home";
    }

    // 短信验证
    @RequestMapping(value = "/SMS", method = RequestMethod.POST)
    @ResponseBody
    public Boolean SMS(String telePhone, HttpServletRequest httpServletRequest){
        String Session = httpServletRequest.getSession().getId();
        logger.debug("接收号码" + telePhone);
        boolean flag = rlSMS.testSMS(telePhone, Session);
        // this.codeValidate = rlSMS.getRand_Code();
        logger.debug("session" + Session);
        // logger.debug("rlSMS.getRand_Code():" + codeValidate);
        return flag;
    }

    // 效验 效验邮箱不需要登陆验证
    @RequestMapping(value = "/verifyMail/{verifyCode}", method = RequestMethod.GET)
    public String verifyCode(@PathVariable(value = "verifyCode") String verifyCode, Model model){
        StudentCustom studentCustom = (StudentCustom) memCachedClient.get(verifyCode);
        if (studentCustom != null ){
            logger.debug("studentCustom 邮箱验证:" + studentCustom.toString());
            // 该验证码请求只要被接收到就失效
            memCachedClient.delete(verifyCode);
            // 改变邮箱状态
            studentCustom.setStuMailState(1);
            try {
                // 存入数据库 判断是否存入成功
                if(serviceDao.updateEmail(studentCustom)){
                    model.addAttribute("message", "验证成功");
                    return "index";
                }
            } catch (Exception e) {
                model.addAttribute("message", "写入数据库失败");
                e.printStackTrace();
            }
        }
        model.addAttribute("message", "验证码无效");
        return "index";
    }
}