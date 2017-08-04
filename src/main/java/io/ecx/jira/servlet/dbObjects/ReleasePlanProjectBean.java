/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.servlet.dbObjects;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author alexander
 */
public class ReleasePlanProjectBean
{
    private String name;
    private Date startDate, endDate;
    private Integer sprints, sprintDuration,storyPoints, jiraProjectId;
    private boolean projectfinished;

    public ReleasePlanProjectBean(String name, Date startDate, Date endDate, Integer sprints, Integer sprintDuration, Integer storyPoints, Integer jiraProjectId, boolean projectfinished)
    {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.sprints = sprints;
        this.sprintDuration = sprintDuration;
        this.storyPoints = storyPoints;
        this.jiraProjectId = jiraProjectId;
        this.projectfinished = projectfinished;
    }

    public ReleasePlanProjectBean()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Date getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Date startDate)
    {
        this.startDate = startDate;
    }

    public Date getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Date endDate)
    {
        this.endDate = endDate;
    }

    public Integer getSprints()
    {
        return sprints;
    }

    public void setSprints(Integer sprints)
    {
        this.sprints = sprints;
    }

    public Integer getSprintDuration()
    {
        return sprintDuration;
    }

    public void setSprintDuration(Integer sprintDuration)
    {
        this.sprintDuration = sprintDuration;
    }

    public Integer getStoryPoints()
    {
        return storyPoints;
    }

    public void setStoryPoints(Integer storyPoints)
    {
        this.storyPoints = storyPoints;
    }

    public Integer getJiraProjectId()
    {
        return jiraProjectId;
    }

    public void setJiraProjectId(Integer jiraProjectId)
    {
        this.jiraProjectId = jiraProjectId;
    }

    public boolean isProjectfinished()
    {
        return projectfinished;
    }

    public void setProjectfinished(boolean projectfinished)
    {
        this.projectfinished = projectfinished;
    }
    
    
    
}
