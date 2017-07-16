package io.ecx.jira.servlet;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.IssueManager;
import com.atlassian.jira.user.ApplicationUser;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.plugin.Plugin;
import java.io.BufferedReader;
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
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.commons.codec.binary.Base64;

public class CreateProject extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(CreateProject.class);
    private URL importUrl;
    private final Plugin plugin = ComponentAccessor.getPluginAccessor().getPlugin("io.ecx.jira.ReleasePlanPlugin");
    private PrintWriter pw;
    private final String br = "<br>";
    private String apiReq = "";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        pw = resp.getWriter();
        String name = plugin.getName();
        importUrl = plugin.getResource("/html/StyleImports.html");
        String allLines = readAllLines(importUrl.openStream());
        pw.print(allLines);
        print("<html><head>");
        log.warn(allLines);
        print(allLines);
        print("</head><body>");
        importUrl = plugin.getResource("/html/Header.html");
        allLines = readAllLines(importUrl.openStream());
        print(allLines);
        String uri = req.getRequestURI();
        StringBuffer url = req.getRequestURL();
        apiReq = url.toString().replace(uri, "") + req.getContextPath() + "/rest/agile/1.0/";
        print(apiReq + " apireq" + br);
        print(req.getRequestURI() + "<br>");
        print(req.getRequestURL() + "<br>");
        print(req.getContextPath() + "<br>");
        print(req.getAuthType() + "<br>");
        print(ComponentAccessor.getIssueManager().getIssueCount() + "issueCount" + br);
        //print("Issue Scrum 3: "+ComponentAccessor.getIssueManager().getIssueObject("DEBUG-3").getDescription());

        boolean loggedin = ComponentAccessor.getJiraAuthenticationContext().isLoggedInUser();
        ApplicationUser user = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser();
        IssueManager issueManager = ComponentAccessor.getIssueManager();

        print(loggedin + "<br>" + user.getName());
        print(ComponentAccessor.getJiraAuthenticationContext().getLocale().getCountry());

        JSONObject root;
        JSONArray epics;
        apiReq = "https://ticket.ecx.io/rest/agile/1.0/";
        String rootstr = executeGetRequestApi("board/10386/epic");
        print(br + "Epics von Board mit ID = 10386");
        print(apiReq + " apireq" + br);
        pw.write(rootstr);
        try {
            root = new JSONObject(rootstr);
            epics = root.getJSONArray("values");
            for (int i = 0; i < epics.length(); i++) {

            }
            log.warn("Rootstr");
        } catch (JSONException ex) {
            log.error("JSON Fehler", ex);
        }
        print("<br><button class=\"aui-button aui-button-primary\">Button</button>");
        print("</body></html>");
    }

    private String executeGetRequestApi(String cmd) {

        
        try {

            URL url = new URL(apiReq + cmd);
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[]{new InvalidCertificateTrustManager()}, null);

            SSLContext.setDefault(ctx);

            String authStr = "username:password";
            String authEncoded = Base64.encodeBase64String(authStr.getBytes());

            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);

            connection.setHostnameVerifier(new InvalidCertificateHostVerifier());

            return readAllLines(connection.getInputStream());
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
        } catch (MalformedURLException ex) {
            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
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
    //</editor-fold>

    private String readAllLines(InputStream in) {
        String allLines = "";
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(in));
            String lines = "";
            while ((lines = bfr.readLine()) != null) {
                allLines += lines;
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bfr.close();
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(CreateProject.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return allLines;
    }

    private void print(String s) {
        pw.println(s);
    }

}
