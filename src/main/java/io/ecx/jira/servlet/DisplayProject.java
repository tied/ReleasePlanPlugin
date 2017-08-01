package io.ecx.jira.servlet;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

public class DisplayProject extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(DisplayProject.class);
    private final Plugin plugin = ComponentAccessor.getPluginAccessor().getPlugin("io.ecx.jira.ReleasePlanPlugin");
    private PrintWriter pw;
    private URL importUrl;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html");
        pw=resp.getWriter();
        pw.println("<html>\n"
                + "    <head>\n"
                + "        <title>Planning Page - Release Planning Plugin</title>\n"
                + "        <meta name=\"decorator\" content=\"atl.general\">\n"
                + "        <link rel=\"stylesheet\" type=\"text/css\" href=\"../displayProjectstyle.css\">"
                + "        <script src=\"../displayProjectscript.js\"></script>"
                + "    </head>"
                + "<body>");
        
        importUrl = plugin.getResource("/html/displayProject.html");
        String allLines = SharedMethods.insertProjectsToSelect(importUrl.openStream());
        pw.print(allLines);
    }

}