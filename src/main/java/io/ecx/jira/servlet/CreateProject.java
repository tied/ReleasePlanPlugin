package io.ecx.jira.servlet;

import com.atlassian.activeobjects.external.ActiveObjects;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.plugin.Plugin;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import io.ecx.jira.ao.ReleasePlanProject;
import java.beans.XMLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
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
        final String name = req.getParameter("ProjectName");
        final int sprints = Integer.parseInt(req.getParameter("Sprints"));
        final int sprintDuration = Integer.parseInt(req.getParameter("SprintDuration"));
        final int storypoints = Integer.parseInt(req.getParameter("StoryPoints"));
        final String sD = req.getParameter("StartDate");
        final String eD = req.getParameter("EndDate");
        final int jiraProjectId = Integer.parseInt(req.getParameter("Project"));

        String[] split = sD.split("\\.");
        GregorianCalendar cal = new GregorianCalendar(Integer.parseInt(split[2]), Integer.parseInt(split[1])-1, Integer.parseInt(split[0]));
        final Date startDate = cal.getTime();
        split = eD.split("\\.");
        cal = new GregorianCalendar(Integer.parseInt(split[2]), Integer.parseInt(split[1])-1, Integer.parseInt(split[0]));
        final Date endDate = cal.getTime();

        activeObjects.executeInTransaction(new TransactionCallback<ReleasePlanProject>()
        {
            public ReleasePlanProject doInTransaction()
            {
                final ReleasePlanProject proj = activeObjects.create(ReleasePlanProject.class);
                proj.setName(name);
                proj.setJiraProjectId(jiraProjectId);
                proj.setStartDate(startDate);
                proj.setEndDate(endDate);
                proj.setSprints(sprints);
                proj.setStorypoints(storypoints);
                proj.setSprintDuration(sprintDuration);
                proj.setProjectFinished(false);
                proj.save();
                return proj;
            }
        });
        resp.sendRedirect("http://local");
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
                    pw.print("Name: "+proj.getName()+" JiraProjectId: "+proj.getJiraProjectId()+" Sprints: "+proj.getSprints()+ " SprintDuration: "+proj.getSprintDuration()+" StartDate: "+proj.getStartDate()+" EndDate: "+proj.getEndDate()+" Storypoints: "+proj.getStorypoints()+"Project Finished: "+proj.getProjectFinished()+br);
                    //activeObjects.delete(proj);
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
            //<editor-fold>
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
    //<editor-fold>
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
    public void setPageBuilderService(PageBuilderService pageBuilderService)
    {
        this.pageBuilderService = pageBuilderService;
    }
}
