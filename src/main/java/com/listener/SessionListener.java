package com.listener;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class SessionListener implements HttpSessionBindingListener {

    //保存username和session的映射
    public static HashMap<String, HttpSession> MAP1 = new HashMap<String, HttpSession>();
    //保存sessionID和username的映射
    public static HashMap MAP2 = new HashMap();

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        // TODO Auto-generated method stub
    }

    public static void userLogin(HttpSession session, String sUserName) {
        //已登录
        if (MAP2.containsValue(sUserName)) {
            HttpSession httpSession = MAP1.get(sUserName);
            //不同浏览器,同一用户(强制下线前一个)
            if (httpSession != null && !httpSession.getId().equals(session.getId())) {
                MAP1.remove(sUserName);
                MAP2.remove(httpSession.getId());
                httpSession.setAttribute("msg", "您的账号已在另一处登录!");
                MAP2.put(session.getId(), sUserName);
                MAP1.put(sUserName, session);
            }
            //同一浏览器，同一用户(不做任何变动)
        } else {
            //未登录
            if (MAP2.containsKey(session.getId())) {
                //同一浏览器，不同用户(不做任何变动)
            } else {
                //不同浏览器，不同用户(正常添加)
                MAP2.put(session.getId(), sUserName);
                MAP1.put(sUserName, session);
            }
        }
    }

    /*
     * 判断用户是否重复登录
     */
    @RequestMapping(value = "/checkUserOnline")
    @ResponseBody
    public void checkUserOnline(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        String msg = "";
        if (session.getAttribute("msg") != null) {
            msg = session.getAttribute("msg").toString();
            System.out.println(msg);
        }
        out.print(session.getAttribute("msg"));
    }
}
