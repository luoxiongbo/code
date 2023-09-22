package com.lxb.controller;
//-*- code = utf-8 -*-
//@Time : 2023-09-21 15:37:42
//@Authors : 罗雄波
//@File : MonsterController.java
//@Software : IntelliJ IDEA

import com.lxb.entity.Monster;
import com.lxb.service.MonsterService;
import com.lxb.springmvc.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
public class MonsterController {
    @AutoWired
    private MonsterService monsterService;

    @RequestMapping("/monster/list")
    public void monsterList(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        try {
            List<Monster> monsters = monsterService.monsterList();
            StringBuilder content = new StringBuilder("<h1>妖怪列表</h1>");
            // 设置表格的属性
            content.append("<table width='500px' style='border-collapse:collapse' border='1px'>");
            // 表格
            for (Monster monster : monsters) {
                content.append("<tr><td>" + monster.getId() + "</td><td>" + monster.getName() + "</td><td>" + monster.getSkill() + "</td>");
            }

            content.append("</table>");
            response.getWriter().write(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/monster/find")
    public void findMonsterByName(HttpServletRequest request, HttpServletResponse response, /*@RequestParam("name")*/String sname) {
        if(sname == null) {
            sname = "";
        }
        response.setContentType("text/html;charset=utf-8");
        try {
            List<Monster> monsters = monsterService.monsterFind(sname);
            System.out.println("接收到的name = " + sname);
            System.out.println("查询到的个数是 : " + monsters.size());
            StringBuilder content = new StringBuilder("<h1>妖怪列表</h1>");
            content.append("<table width='500px' style='border-collapse:collapse' border='1px'>");
            // 表格
            for (Monster monster : monsters) {
                content.append("<tr><td>" + monster.getId() + "</td><td>" + monster.getName() + "</td><td>" + monster.getSkill() + "</td>");
            }

            content.append("</table>");
            response.getWriter().write(content.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/monster/login")
    public String login(HttpServletRequest request, HttpServletResponse response, String name) {
        boolean login = monsterService.login(name);
        request.setAttribute("mName", name);
        if(login) {
            return "forward:/login_ok.jsp";
        }
        return "forward:/login_error.jsp";
    }

    @RequestMapping("/monster/list/json")
    @ResponseBody
    public List listMonstersByJson(HttpServletRequest request, HttpServletResponse response) {
        List<Monster> monsters = monsterService.monsterList();
        return monsters;
    }
}
