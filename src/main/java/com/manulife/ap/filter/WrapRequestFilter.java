//package com.manulife.ap.filter;
//
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author weidejiang
// */
//public class WrapRequestFilter extends OncePerRequestFilter {
//
//    private static final Map<String,String> paths=new HashMap<>();
//
//    static {
//        paths.put("xxl-job-admin","xxl/");
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
//        ServletContextFacadeRequestWrapper wrapper=new ServletContextFacadeRequestWrapper(httpServletRequest);
//        String path=getMatchingContextPathForRequest(httpServletRequest);
//        if(path!=null){
//            wrapper.setContextPath(httpServletRequest.getContextPath()+path);
//            String newPath=httpServletRequest.getServletPath().substring(path.length());
//            if(newPath.length()==0){
//                newPath="/";
//            }
//            wrapper.setServletPath(newPath);
//        }
//        filterChain.doFilter(wrapper,httpServletResponse);
//
//    }
//
//
//    public String getMatchingContextPathForRequest(HttpServletRequest httpServletRequest){
//        String servletPath=httpServletRequest.getServletPath();
//        if(servletPath.contains("static")){
//            for(String key:paths.keySet()) {
//                if (servletPath.contains(key)) {
//                    return paths.get(key) + servletPath;
//                }
//            }
//        }
//        return null;
//
//    }
//}
