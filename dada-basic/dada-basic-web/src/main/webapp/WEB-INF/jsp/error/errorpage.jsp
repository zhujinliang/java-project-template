<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*"%>
<%@ page isErrorPage="true" %>
<%=request.getAttribute("javax.servlet.forward.request_uri") %>
With the following stack trace:
<% exception.printStackTrace();
  	ByteArrayOutputStream ostr = new ByteArrayOutputStream();
  	exception.printStackTrace(new PrintStream(ostr));
  	out.print(ostr);
%>