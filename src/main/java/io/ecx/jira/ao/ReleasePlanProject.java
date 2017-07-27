/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.ao;

import java.util.Date;
import net.java.ao.Entity;
import net.java.ao.Preload;

/**
 *
 * @author Alexander
 */
@Preload
public interface ReleasePlanProject extends Entity
{
    String getName();
    void setName(String name);
    
    Date getStartDate();
    void setStartDate(Date startDate);
    
    Date getEndDate();
    void setEndDate(Date endDate);
    
    Integer getSprints();
    void setSprints(Integer sprints);
    
    Integer getSprintDuration();
    void setSprintDuration(Integer sprintDuration);
    
    Integer getStorypoints();
    void setStorypoints(Integer storypoints);
    
    Integer getJiraProjectId();
    void setJiraProjectId(Integer jiraProjectId);
    
    boolean getProjectFinished();
    void setProjectFinished(boolean finished);
    
}
