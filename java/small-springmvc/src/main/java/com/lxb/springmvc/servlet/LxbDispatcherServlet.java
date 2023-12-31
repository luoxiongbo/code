package com.lxb.springmvc.servlet;
//-*- code = utf-8 -*-
//@Time : 2023-09-21 15:28:15
//@Authors : 罗雄波
//@File : LxbDispatcherServlet.java
//@Software : IntelliJ IDEA

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lxb.springmvc.annotation.Controller;
import com.lxb.springmvc.annotation.RequestMapping;
import com.lxb.springmvc.annotation.RequestParam;
import com.lxb.springmvc.annotation.ResponseBody;
import com.lxb.springmvc.context.LxbWebApplicationContext;
import com.lxb.springmvc.handler.LxbHandler;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LxbDispatcherServlet extends HttpServlet {
    private ArrayList<LxbHandler> lxbHandlers = new ArrayList<LxbHandler>();
    private LxbWebApplicationContext lxbWebApplicationContext;

    /**
     * 初始化分发处理器
     *
     * @param config
     * @throws ServletException
     */
    @Override
    public void init(ServletConfig config) throws ServletException {
        //super.init(config);
        lxbWebApplicationContext = new LxbWebApplicationContext();

        String configLocation = config.getInitParameter("contextConfigLocation");
        String context = configLocation.split(":")[1];
        lxbWebApplicationContext.init(context);

        initHandlerMap();
        System.out.println("分发处理器里的处理器映射关系 : " + lxbHandlers);
    }

    public void initHandlerMap() {
        ConcurrentHashMap<String, Object> ioc = lxbWebApplicationContext.getIoc();
        if (ioc == null) {
            throw new RuntimeException("spring容器是空的!!!");
        }
        for (Map.Entry<String, Object> entry : ioc.entrySet()) {

            String name = entry.getKey();
            Object value = entry.getValue();
            Class<?> clazz = value.getClass();
            if (clazz.isAnnotationPresent(Controller.class)) {
                Method[] methods = clazz.getDeclaredMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        RequestMapping requestMapping = method.getDeclaredAnnotation(RequestMapping.class);
                        lxbHandlers.add(new LxbHandler(requestMapping.value(), value, method));
                    }
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        System.out.println("-------doPost()-------");
        executeDispatcherServlet(req, resp);
    }

    public void executeDispatcherServlet(HttpServletRequest request, HttpServletResponse response) {
        String requestURI = request.getRequestURI();
        LxbHandler handler = getHandler(requestURI);
        if (handler == null) {
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().write("<h1>404页面找不到了</h1>");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                //handler.getMethod().invoke(handler.getController(), request, response);
                Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();

                Map<String, String[]> parameterMap = request.getParameterMap();

                Object[] parameters = new Object[parameterTypes.length];

                /**
                 * 老师是用名字匹配的, 我是用类型的(请求的类型)
                 */
                for (int i = 0; i < parameterTypes.length; ++i) {
                    /**
                     * 这步也很重要, 用的时候感觉都很难
                     */
                    if (parameterTypes[i].isAssignableFrom(HttpServletRequest.class)) {
                        parameters[i] = request;
                    } else if (parameterTypes[i].isAssignableFrom(HttpServletResponse.class)) {
                        parameters[i] = response;
                    }
                }
                // 遍历每一个参数
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue()[0];
                    System.out.println("请求参数: " + key + "----" + value);
                    // 获取参数下标
                    int index = getMethodParametersIndex(handler.getMethod(), key);
                    if (index != -1) {
                        parameters[index] = value;
                    } else {
                        // 如果没找到就用 默认参数名字处理
                        List<String> parameterName = getParameterName(handler.getMethod());
                        for (String name : parameterName) {
                            if (key.equals(name)) {
                                int indexOf = parameterName.indexOf(name);
                                parameters[indexOf] = value;
                            }
                        }
                    }
                }

                // 执行控制器方法
                Object result = handler.getMethod().invoke(handler.getController(), parameters);


                // 这里是视图解析器的简易实现方法

                // 非逻辑视图的解析 (这里模拟, 如果是个字符串的话, 就当作非逻辑视图进行处理)
                // ( 原生springmvc中只需要给默认视图解析器就可以了, 它会使用 thymeleaf视图处理器, 写好前缀和后缀 )
                if (result instanceof String) {
                    // 这里是跳转页面
                    String viewName = (String) result;
                    try {
                        if (viewName.contains(":")) {
                            // 这里是默认视图解析器用来判断路径是请求转发还是重定向
                            String pageType = viewName.split(":")[0];
                            String pagePath = viewName.split(":")[1];
                            if (pageType.equals("forward")) {
                                request.getRequestDispatcher(pagePath).forward(request, response);
                            } else if (pagePath.equals("redirect")) {
                                response.sendRedirect(pagePath);
                            }
                        } else {
                            request.getRequestDispatcher(viewName).forward(request, response);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                // 逻辑视图的解析, 跳转到自定义视图解析器, 执行逻辑语句
                // (原生的 springmvc 的自定义视图解析器需要自己写请求转发, 意味着它将请求和响应参数发给jsp处理机制, jsp处理机制将数据取出并写在前端页面, 所以自定义视图解析器的渲染功能相当于就是请求转发, 在我的md文件中有chat-GPT写的知识点)
                } else if(result instanceof ArrayList) {
                    Method method = handler.getMethod();
                    if(method.isAnnotationPresent(ResponseBody.class)) {
                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = objectMapper.writeValueAsString(result);
                        response.setContentType("text/html;charset=utf-8");
                        PrintWriter writer = response.getWriter();
                        writer.write(json);
                        writer.flush();
                        writer.close();

                    }
                }
            } catch (IllegalAccessException | InvocationTargetException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public LxbHandler getHandler(String uri) {
        // 在这里还是进行了处理, 因为uri中还有项目工程名, 所以在 tomcat 的配置里面要把工程路径改为 /
        for (LxbHandler lxbHandler : lxbHandlers) {
            if (uri.equals(lxbHandler.getUrl())) {
                return lxbHandler;
            }
        }
        return null;
    }

    /**
     * 得到参数下标
     * @param method
     * @param name
     * @return
     */
    public int getMethodParametersIndex(Method method, String name) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            // 如果它有注解就得到注解的value
            if (parameters[i].isAnnotationPresent(RequestParam.class)) {
                RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
                String paramName = requestParam.value();
                // 如果相同就返回下标
                if (name.equals(paramName)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 得到方法的简单名字
     * @param method
     * @return
     */
    public List<String> getParameterName(Method method) {
        ArrayList<String> parameterName = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
            parameterName.add(parameter.getName());
        }
        System.out.println("参数列表的名字是 : " + parameterName);
        return parameterName;
    }
}
