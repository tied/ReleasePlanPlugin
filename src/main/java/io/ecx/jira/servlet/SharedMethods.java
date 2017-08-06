/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.servlet;

import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author Perndorfer
 */
public class SharedMethods {
    
    public static String readAllLines(InputStream in)
    {
        String allLines = "";
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new InputStreamReader(in));
            String lines = "";
            while ((lines = bfr.readLine()) != null) {
                allLines += lines+"\r\n";
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
    
    public static String insertProjectsToSelect(InputStream in)
    {
        String allLines = SharedMethods.readAllLines(in);
        try
        {
            StringBuffer buf = new StringBuffer(allLines);
            int start = buf.indexOf("<!--breakpoint-->");
            
            String options = "";
            String rootString = executeGetRequestApi();
            JSONArray projects = new JSONArray(rootString);
            for (int i = 0; i < projects.length(); i++)
            {
                JSONObject proj = projects.getJSONObject(i);
                String self = proj.getString("self");
                int id = proj.getInt("id");
                String name = proj.getString("name");
                options += "<option value ='" + id + "'>" + name + "</option>\r\n";
            }
            
            buf.replace(start, start + "<!--breakpoint-->".length(), options);
            
            return buf.toString();
        } catch (JSONException ex)
        {
            java.util.logging.Logger.getLogger(ReleaseplanningServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allLines;
    }
    
    private static String executeGetRequestApi()
    {

        try
        {
            String apiReq = "https://ticket.ecx.io/rest/api/2/project";
            URL url = new URL(apiReq);
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
        } catch (KeyManagementException ex)
        {
            java.util.logging.Logger.getLogger(ReleaseplanningServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex)
        {
            java.util.logging.Logger.getLogger(ReleaseplanningServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex)
        {
            java.util.logging.Logger.getLogger(ReleaseplanningServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            java.util.logging.Logger.getLogger(ReleaseplanningServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
}
