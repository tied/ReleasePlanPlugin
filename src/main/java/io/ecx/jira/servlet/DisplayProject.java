package io.ecx.jira.servlet;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.plugin.Plugin;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.ecx.jira.ao.ReleasePlanProject;
import io.ecx.jira.servlet.dbObjects.ReleasePlanProjectBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import javax.inject.Inject;
import net.java.ao.Query;

public class DisplayProject extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(DisplayProject.class);
    private final Plugin plugin = ComponentAccessor.getPluginAccessor().getPlugin("io.ecx.jira.ReleasePlanPlugin");
    private PrintWriter pw;
    private URL importUrl;
    private ActiveObjects activeObjects;

    @Inject
    private PageBuilderService pageBuilderService;

    @Inject
    public DisplayProject(ActiveObjects activeObjects)
    {
        this.activeObjects = activeObjects;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String id = req.getParameter("id");
        ReleasePlanProject[] projects = activeObjects.find(ReleasePlanProject.class, Query.select().where("JIRA_PROJECT_ID = ?", id));
        pageBuilderService.assembler().resources().requireWebResource("io.ecx.jira.ReleasePlanPlugin:DisplayProject-resources");
        pageBuilderService.assembler().resources().requireWebResource("io.ecx.jira.ReleasePlanPlugin:MainStyle-resources");

        resp.setContentType("text/html");
        pw = resp.getWriter();
        pw.println("<html>\n"
                + "    <head>\n"
                + "        <title>Planning Page - Release Planning Plugin</title>\n"
                + "        <meta name=\"decorator\" content=\"atl.general\">\n"
                + "    </head>"
                + "<body>");
        String projectJson = "";
        if (projects.length > 0)
        {
            ReleasePlanProjectBean project = new ReleasePlanProjectBean(projects[0].getTitle(),projects[0].getNote(), projects[0].getStartDate(), projects[0].getEndDate(), projects[0].getSpPerEpicPerSprint(), projects[0].getSprintDuration(),projects[0].getPersonDays(),projects[0].getFactor(), projects[0].getStorypoints(), projects[0].getJiraProjectId(), projects[0].getProjectFinished());
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("yyyy-MM-dd");
            Gson gson = gsonBuilder.create();
            projectJson = gson.toJson(project);
            pw.println("<script>"
                    + "var project = " + projectJson + ";"
                    + "</script>");
        }
        importUrl = plugin.getResource("/html/MainStyle.html");
        String allLines = SharedMethods.insertProjectsToSelect(importUrl.openStream());
        pw.print(allLines);
        importUrl = plugin.getResource("/html/displayProject.html");
        allLines = SharedMethods.readAllLines(importUrl.openStream());
        pw.println(allLines + "</section></body></html>");
        
    }

}
