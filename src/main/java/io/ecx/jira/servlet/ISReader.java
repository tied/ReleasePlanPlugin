/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

/**
 *
 * @author Perndorfer
 */
public class ISReader {
    
    public static String readAllLines(InputStream in)
    {
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
    
}
