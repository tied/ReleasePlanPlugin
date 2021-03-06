package io.ecx.jira.servlet;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.config.properties.APKeys;
import com.atlassian.jira.security.JiraAuthenticationContext;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.plugin.Plugin;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import io.ecx.jira.ao.Epic;
import io.ecx.jira.ao.ReleasePlanProject;
import io.ecx.jira.ao.Sprint;
import io.ecx.jira.ao.SprintToEpic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import net.java.ao.DBParam;
import org.apache.commons.codec.binary.Base64;

public class CreateProject extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(CreateProject.class);
    private URL importUrl;
    private final Plugin plugin = ComponentAccessor.getPluginAccessor().getPlugin("io.ecx.jira.ReleasePlanPlugin");
    private PrintWriter pw;
    private final String br = "<br>";
    private String apiReq = "";
    private ActiveObjects activeObjects;

    @Inject
    private PageBuilderService pageBuilderService;

    @Inject
    public CreateProject(ActiveObjects activeObjects)
    {
        this.activeObjects = activeObjects;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        pw = resp.getWriter();
        String redirect = ComponentAccessor.getApplicationProperties().getString(APKeys.JIRA_BASEURL);
        JiraAuthenticationContext jiraAuthenticationContext = ComponentAccessor.getJiraAuthenticationContext();
        ApplicationUser user = jiraAuthenticationContext.getLoggedInUser();
        if (user == null)
        {
            resp.sendRedirect(redirect);
            return;
        }
        final String action = req.getParameter("Action");

        if (action.toLowerCase().equals("create"))
        {
            int jiraProjectId = createReleasePlan(req);
            resp.sendRedirect(redirect + "/plugins/servlet/displayproject?id=" + jiraProjectId);//parameter mitgeben damit richtiges Projekt geladen werden kann
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html");
        pw = resp.getWriter();
        apiReq = "https://ticket.ecx.io/rest/agile/1.0/";
        //pageBuilderService.assembler().resources().requireWebResource("io.ecx.jira.ReleasePlanPlugin:ReleasePlanPlugin-resources");
        String name = plugin.getName();

        activeObjects.executeInTransaction(new TransactionCallback<Void>()
        {
            public Void doInTransaction()
            {

                for (ReleasePlanProject proj : activeObjects.find(ReleasePlanProject.class))
                {
                    pw.print("Title: " + proj.getTitle() + br + "\r\nNotes: " + proj.getNote() + br + "\r\nJiraProjectId: " + proj.getJiraProjectId() + br + "\r\nSpPerSprintPerEpic: " + proj.getSpPerEpicPerSprint() + br + "\r\nSprintDuration: " + proj.getSprintDuration() + br + "\r\nStartDate: " + proj.getStartDate() + br + "\r\nEndDate: " + proj.getEndDate() + br + "\r\nStorypoints: " + proj.getStorypoints() + br + "\r\nProject Finished: " + proj.getProjectFinished() + br + "Person Days: " + proj.getPersonDays() + " Factor: " + proj.getFactor() + "<hr>");
                    //activeObjects.delete(proj);
                }
                return null;
            }
        });
//        try{
//        activeObjects.executeInTransaction(new TransactionCallback<Void>()
//        {
//            public Void doInTransaction()
//            {
//                Epic e1 = activeObjects.create(Epic.class, new DBParam("ID",1), new DBParam("NAME", "Epic 1"));
//                e1.save();
//                Epic e2 = activeObjects.create(Epic.class, new DBParam("ID",2), new DBParam("NAME", "Epic 2"));
//                e2.save();
//                
//                Sprint s1 = activeObjects.create(Sprint.class, new DBParam("ID", 1), new DBParam("NAME", "Sprint 1"));
//                Sprint s2 = activeObjects.create(Sprint.class, new DBParam("ID", 2), new DBParam("NAME", "Sprint 2"));
//                
//                SprintToEpic ste = activeObjects.create(SprintToEpic.class);
//                ste.setEpic(e1);
//                ste.setSprint(s1);
//                ste.save();
//                SprintToEpic ste1 = activeObjects.create(SprintToEpic.class);
//                ste1.setEpic(e1);
//                ste1.setSprint(s2);
//                ste1.save();
//                SprintToEpic ste2 = activeObjects.create(SprintToEpic.class);
//                ste2.setEpic(e2);
//                ste2.setSprint(s2);
//                ste2.save();
//                
//                return null;
//            }
//        });}
//        catch (Exception ex)
//        {
//            ex.printStackTrace(pw);
//        }

//        activeObjects.executeInTransaction(new TransactionCallback<Void>()
//        {
//            public Void doInTransaction()
//            {
//                Sprint s1 = activeObjects.create(Sprint.class, new DBParam("ID", 1), new DBParam("NAME", "Sprint 1"));
//                Sprint s2 = activeObjects.create(Sprint.class, new DBParam("ID", 2), new DBParam("NAME", "Sprint 2"));
//                
//                Epic e1 = activeObjects.create(Epic.class, new DBParam("ID",1), new DBParam("NAME", "Epic 1"));
//                e1.setStartSprint(s1);
//                e1.save();
//                Epic e2 = activeObjects.create(Epic.class, new DBParam("ID",2), new DBParam("NAME", "Epic 2"));
//                e2.setStartSprint(s1);
//                e2.save();
//                
//                
//                return null;
//            }
//        });

        activeObjects.executeInTransaction(new TransactionCallback<Void>()
        {
            public Void doInTransaction()
            {
                for(Epic e : activeObjects.find(Epic.class))
                {
                    pw.write("Name: "+e.getName()+br+"Startsprint: "+e.getStartSprint().getName());
                    pw.write("<hr>");
                }
                pw.write("Sprints:"+br+"<hr>");
                for(Sprint s : activeObjects.find(Sprint.class))
                {
                    pw.write("Name: "+s.getName()+br);
                    for(Epic e : s.getEpics())
                    {
                        pw.write("Name: "+e.getName()+br+"Startsprint: "+e.getStartSprint().getName()+"<hr>");
                    }
                    
                }
                return null;
            }
        });
    }

    private String executeGetRequestApi(String cmd)
    {

        try
        {

            URL url = new URL(apiReq + cmd);
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]
            {
                new InvalidCertificateTrustManager()
            }, null);

            SSLContext.setDefault(ctx);

            String authStr = "";
            String authEncoded = Base64.encodeBase64String(authStr.getBytes());

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);

            connection.setHostnameVerifier(new InvalidCertificateHostVerifier());

            return SharedMethods.readAllLines(connection.getInputStream());
            //<editor-fold defaultstate="collapsed">
//        String ret = "";
//        try {
//            URL url = new URL(apiReq + cmd);
//            String userPassword = "username:password";
//            String encoding = org.apache.commons.codec.binary.Base64.encodeBase64String(userPassword.getBytes());
//            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Authorization", "Basic " + encoding);
//            if (connection.getInputStream() == null) {
//                return "No Data Found";
//            }
//            ret = readAllLines(connection.getInputStream());
//        } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return ret;
//</editor-fold>
        } catch (MalformedURLException ex)
        {
            log.error("MalformedUrl", ex);
        } catch (NoSuchAlgorithmException ex)
        {
            log.error("NoSuchAlgorirhm", ex);
        } catch (IOException ex)
        {
            log.error("IOException", ex);
        } catch (KeyManagementException ex)
        {
            log.error("KeyManagementException", ex);
        }
        return "";

    }
    //<editor-fold defaultstate="collapsed">
//    private String executePostRequestApi(String dataToSend)
//    {
//        String lines ="";
//        try {
//            URL url = new URL("http://localhost:2990/jira/rest/api/2/issue/"+issue);
//            String userPassword = "admin:admin";
//            String encoding = org.apache.commons.codec.binary.Base64.encodeBase64String(userPassword.getBytes());
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Authorization", "Basic " + encoding);
//            lines = readAllLines(connection.getInputStream());
//        } catch (Exception ex) {
//            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return lines;
//    }

//    private String readAllLines(InputStream in) {
//        String allLines = "";
//        BufferedReader bfr = null;
//        try {
//            bfr = new BufferedReader(new InputStreamReader(in));
//            String lines = "";
//            while ((lines = bfr.readLine()) != null) {
//                allLines += lines;
//            }
//        } catch (IOException ex) {
//            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                bfr.close();
//            } catch (IOException ex) {
//                java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        return allLines;
//    }
    //</editor-fold>
    private int createReleasePlan(HttpServletRequest req)
    {
        final String title = req.getParameter("Title");
        final int spPerEpicPerSprint = Integer.parseInt(req.getParameter("SpPerEpicPerSprint"));
        final int sprintDuration = Integer.parseInt(req.getParameter("SprintDuration"));
        final int storypoints = Integer.parseInt(req.getParameter("StoryPoints"));
        final String sD = req.getParameter("StartDate");
        final String eD = req.getParameter("EndDate");
        final int jiraProjectId = Integer.parseInt(req.getParameter("Project"));
        final int personDays = Integer.parseInt(req.getParameter("PersonDays"));
        final int factor = Integer.parseInt(req.getParameter("Factor"));

        String[] split = sD.split("\\.");
        GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(split[2]), Integer.parseInt(split[1]) - 1, Integer.parseInt(split[0]));
        final Date startDate = cal.getTime();
        split = eD.split("\\.");
        cal = new GregorianCalendar(Integer.parseInt(split[2]), Integer.parseInt(split[1]) - 1, Integer.parseInt(split[0]));
        final Date endDate = cal.getTime();

        final HashMap<String,Object> params = new HashMap<String, Object>();
        params.put("TITLE", title);
        params.put("JIRA_PROJECT_ID", jiraProjectId);
        params.put("START_DATE", startDate);
        params.put("END_DATE", endDate);
        params.put("NOTE", "");
        params.put("PROJECT_FINISHED", false);
        params.put("SP_PER_EPIC_PER_SPRINT",spPerEpicPerSprint);
        params.put("SPRINT_DURATION", sprintDuration);
        params.put("STORYPOINTS", storypoints);
        params.put("PERSON_DAYS", personDays);
        params.put("FACTOR", factor);
        try
        {
            activeObjects.executeInTransaction(new TransactionCallback<ReleasePlanProject>()
            {
                public ReleasePlanProject doInTransaction()
                {
//                    final ReleasePlanProject proj = activeObjects.create(ReleasePlanProject.class);
//                    proj.setTitle(title);
//                    proj.setJiraProjectId(jiraProjectId);
//                    proj.setStartDate(startDate);
//                    proj.setEndDate(endDate);
//                    proj.setSpPerEpicPerSprint(spPerEpicPerSprint);
//                    proj.setStorypoints(storypoints);
//                    proj.setSprintDuration(sprintDuration);
//                    proj.setProjectFinished(false);
//                    proj.setNote("");
//                    proj.setPersonDays(personDays);
//                    proj.setFactor(factor);
//                    proj.save();
//                    return proj;
                    
                    final ReleasePlanProject proj = activeObjects.create(ReleasePlanProject.class, params);
                    return proj;
                }
            });
        } catch (Exception ex)
        {
            ex.printStackTrace(pw);
        }

        return jiraProjectId;
    }

}
