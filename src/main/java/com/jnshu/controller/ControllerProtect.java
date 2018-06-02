package com.jnshu.controller;

import com.jnshu.model.StudentCustom;
import com.jnshu.model.StudentQV;
import com.jnshu.service.ServiceDao;
import com.jnshu.tools.SendMailSDK;
import com.whalin.MemCached.MemCachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.List;

/**
 * @program: smsdemo
 * @description: 需要权限页面
 * @author: Mr.xweiba
 * @create: 2018-05-29 21:53
 **/
@Controller
@RequestMapping("/admin")
public class ControllerProtect {
    private static Logger logger = LoggerFactory.getLogger(ControllerProtect.class);
    @Autowired
    MemCachedClient memCachedClient;
    @Autowired
    ServiceDao serviceDao;
    @Autowired
    SendMailSDK sendMailSDK;

    // 搜索
    @RequestMapping(value = "/students", method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT})
    public String home(Model model, StudentQV studentQV) throws Exception {
        List<StudentCustom> studentCustomList = serviceDao.findListStudent(studentQV);

        model.addAttribute("findUserCustom", studentQV.getStudentCustom());
        model.addAttribute("userCustomList", studentCustomList);
        model.addAttribute("title", "后台综合管理");
        model.addAttribute("body", "control");
        return "protect";
    }

    // 更新页面
    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public String edit(Model model, @PathVariable Integer id) throws Exception {
        model.addAttribute("studentCustom", serviceDao.findStudentCustomById(id));
        model.addAttribute("title", "用户数据更新");
        model.addAttribute("body", "edit");
        return "protect";
    }

    // 更新
    @RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
    public String update(HttpServletRequest httpServletRequest, @PathVariable Integer id, StudentCustom studentCustom) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();
        String username = "";
        for (Cookie c :
                cookies) {
            if (c.getName().equals("username")){
                username = URLDecoder.decode(c.getValue(), "UTF-8");
            }
        }
        studentCustom.setUpdate_by(username);
        serviceDao.updateStudent(studentCustom);
        return "redirect:/admin/students";
    }

    // 插入
    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public String insert(HttpServletRequest httpServletRequest, Model model, StudentCustom studentCustom) throws Exception {
        Cookie[] cookies = httpServletRequest.getCookies();
        String username = "";
        for (Cookie c :
                cookies) {
            if (c.getName().equals("username")){
                username = URLDecoder.decode(c.getValue(),"UTF-8");
            }
        }
        studentCustom.setCreate_by(username);
        serviceDao.insertStudent(studentCustom);
        model.addAttribute("studentCustom", studentCustom);
        return "forward:/admin/students";
    }

    // 删除
    @RequestMapping(value = "/student/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Boolean delete(@PathVariable Integer id ) throws Exception {
        return serviceDao.deleteStudent(id);
    }

    // 上传图片到七牛


    // 邮箱设置
    @RequestMapping(value = "/sendMail", method = RequestMethod.GET)
    @ResponseBody
    public Boolean sendMail(HttpServletRequest httpServletRequest, StudentCustom studentCustom){
        String httpUrl = httpServletRequest.getRequestURL().toString();
        logger.debug("访问项目网址为: " + httpUrl);
        studentCustom.setStuMail("xiaoweiba1028@gmail.com");
        return sendMailSDK.sendMail(httpUrl, studentCustom);
    }

    // 效验
    @RequestMapping(value = "/sendMail/{verifyCode}", method = RequestMethod.GET)
    @ResponseBody
    public String verifyCode(@PathVariable(value = "verifyCode") String verifyCode){
        return verifyCode;
    }
}