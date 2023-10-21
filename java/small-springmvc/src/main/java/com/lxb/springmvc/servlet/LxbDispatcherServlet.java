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

                for (int i = 0; i < parameterTypes.length; ++i) {
                    if (parameterTypes[i].isAssignableFrom(HttpServletRequest.class)) {
                        parameters[i] = request;
                    } else if (parameterTypes[i].isAssignableFrom(HttpServletResponse.class)) {
                        parameters[i] = response;
                    }
                }
                for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue()[0];
                    System.out.println("请求参数: " + key + "----" + value);
                    int index = getMethodParametersIndex(handler.getMethod(), key);
                    if (index != -1) {
                        parameters[index] = value;
                    } else {
                        // 默认参数名字处理
                        List<String> parameterName = getParameterName(handler.getMethod());
                        for (String name : parameterName) {
                            if (key.equals(name)) {
                                int indexOf = parameterName.indexOf(name);
                                parameters[indexOf] = value;
                            }
                        }
                    }
                }

                Object result = handler.getMethod().invoke(handler.getController(), parameters);
                // 这里是视图解析器的简易实现方法
                // 非逻辑视图的解析, 没有逻辑直接跳转, thymeleaf视图处理器, 写好前缀和后缀
                if (result instanceof String) {
                    // 这里是跳转页面
                    String viewName = (String) result;
                    try {
                        if (viewName.contains(":")) {
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

                // 逻辑视图的解析, 跳转到自定义视图解析器, 执行逻辑语句, 并将model进行处理, 发送给view进行视图渲染
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

    public int getMethodParametersIndex(Method method, String name) {
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; ++i) {
            if (parameters[i].isAnnotationPresent(RequestParam.class)) {
                RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
                String paramName = requestParam.value();
                if (name.equals(paramName)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public List<String> getParameterName(Method method) {
        ArrayList<String> parameterName = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
            parameterName.add(parameter.getName());
        }
        System.out.println("参数列表的名字是 : " + parameterName);
        return parameterName;
    }
}
