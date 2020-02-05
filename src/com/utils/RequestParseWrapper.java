package com.utils;

import org.apache.struts2.dispatcher.multipart.JakartaMultiPartRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @autor goh_liu
 * @date 2020/2/4 - 23:42
 */
public class RequestParseWrapper extends JakartaMultiPartRequest {
    @Override
    public void parse(HttpServletRequest servletRequest, String saveDir)throws IOException {   }
}
