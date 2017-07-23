package io.ecx.jira.servlet;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.template.TemplateSource;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.plugin.Plugin;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import java.io.BufferedReader;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.swing.plaf.basic.BasicBorders;
import org.apache.commons.codec.binary.Base64;

public class CreateProject extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CreateProject.class);
    private URL importUrl;
    private final Plugin plugin = ComponentAccessor.getPluginAccessor().getPlugin("io.ecx.jira.ReleasePlanPlugin");
    private PrintWriter pw;
    private final String br = "<br>";
    private String apiReq = "";

    @Inject
    private PageBuilderService pageBuilderService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        pw = resp.getWriter();
        apiReq = "https://ticket.ecx.io/rest/agile/1.0/";
        //pageBuilderService.assembler().resources().requireWebResource("io.ecx.jira.ReleasePlanPlugin:ReleasePlanPlugin-resources");
        String name = plugin.getName();
        print("<!DOCTYPE html><html><head><meta name=\"decorator\" content=\"atl.general\"/>");
        print("<title>Create Project - Release Planning Plugin</title>");
        print("</head><body>");
        //<editor-fold defaultstate="collapsed" desc="Script">
        print(    "<script>"
                + "window.onload=function()"
                + "{"
                + "document.getElementById('createProject').classList.add('aui-nav-selected');"
                + "initInputs();"
                + "}"
                + "</script>");
//</editor-fold>
        importUrl = plugin.getResource("/html/MainStyle.html");
        print(ISReader.readAllLines(importUrl.openStream()));
        importUrl = plugin.getResource("/html/CreateProjectForm.html");
        print(ISReader.readAllLines(importUrl.openStream()));
        print("</section>");
        print("</body></html>");
    }

    private String executeGetRequestApi(String cmd) {

        try {

            URL url = new URL(apiReq + cmd);
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{new InvalidCertificateTrustManager()}, null);

            SSLContext.setDefault(ctx);

            String authStr = "";
            String authEncoded = Base64.encodeBase64String(authStr.getBytes());

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);

            connection.setHostnameVerifier(new InvalidCertificateHostVerifier());

            return readAllLines(connection.getInputStream());
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
        } catch (MalformedURLException ex) {
            log.error("MalformedUrl", ex);
        } catch (NoSuchAlgorithmException ex) {
            log.error("NoSuchAlgorirhm", ex);
        } catch (IOException ex) {
            log.error("IOException", ex);
        } catch (KeyManagementException ex) {
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
    private void print(String s) {
        pw.println(s);
    }

    public void setPageBuilderService(PageBuilderService pageBuilderService) {
        this.pageBuilderService = pageBuilderService;
    }
}
