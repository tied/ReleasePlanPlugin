/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.ao;

import java.util.Date;
import net.java.ao.Entity;
import net.java.ao.Preload;
import net.java.ao.schema.PrimaryKey;
/**
 *
 * @author Alexander
 */
@Preload
public interface ReleasePlanProject extends Entity
{
    String getTitle();
    void setTitle(String title);
    
    Date getStartDate();
    void setStartDate(Date startDate);
    
    Date getEndDate();
    void setEndDate(Date endDate);
    
    Integer getSpPerEpicPerSprint();
    void setSpPerEpicPerSprint(Integer spPerEpicPerSprint);
    
    Integer getSprintDuration();
    void setSprintDuration(Integer sprintDuration);
    
    Integer getPersonDays();
    void setPersonDays(Integer pd);
    
    Integer getFactor();
    void setFactor(Integer factor);
    
    Integer getStorypoints();
    void setStorypoints(Integer storypoints);
    
    @PrimaryKey("JIRA_PROJECT_ID")
    Integer getJiraProjectId();
    void setJiraProjectId(Integer jiraProjectId);
    
    boolean getProjectFinished();
    void setProjectFinished(boolean finished);
    
    String getNote();
    void setNote(String finished);
    
}
