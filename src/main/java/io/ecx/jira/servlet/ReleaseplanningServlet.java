package io.ecx.jira.servlet;

import com.atlassian.jira.component.ComponentAccessor;
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

public class ReleaseplanningServlet extends HttpServlet
{

    private static final Logger log = LoggerFactory.getLogger(ReleaseplanningServlet.class);
    private final Plugin plugin = ComponentAccessor.getPluginAccessor().getPlugin("io.ecx.jira.ReleasePlanPlugin");
    private PrintWriter pw;
    private URL importUrl;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html");
        pw = resp.getWriter();
        pw.println("<html>\n"
                + "    <head>\n"
                + "        <title>Planning Page - Release Planning Plugin</title>\n"
                + "        <meta name=\"decorator\" content=\"atl.general\">\n"
                + "    </head>"
                + "<body>");
        importUrl = plugin.getResource("/html/MainStyle.html");
        String allLines = SharedMethods.insertProjectsToSelect(importUrl.openStream());
        pw.print(allLines);

    }

}
