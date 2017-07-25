package io.ecx.jira.servlet;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.plugin.navigation.PluggableTopNavigation;
import com.atlassian.jira.plugin.navigation.TopNavigationModuleDescriptor;
import com.atlassian.plugin.Plugin;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

public class ReleaseplanningServlet extends HttpServlet{
    private static final Logger log = LoggerFactory.getLogger(ReleaseplanningServlet.class);
    private final Plugin plugin = ComponentAccessor.getPluginAccessor().getPlugin("io.ecx.jira.ReleasePlanPlugin");
    private PrintWriter pw;
    private URL importUrl;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html");
        pw = resp.getWriter();
        importUrl = plugin.getResource("/html/dateiname.html");
        String allLines = ISReader.readAllLines(importUrl.openStream());
        pw.print(allLines);
        
       

    }

}