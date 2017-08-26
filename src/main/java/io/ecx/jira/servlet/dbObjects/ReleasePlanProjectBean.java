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
    private String title, notes;
    private Date startDate, endDate;
    private Integer spPerEpicPerSprint, sprintDuration,storyPoints, jiraProjectId, personDays, factor;
    private boolean projectfinished;

    public ReleasePlanProjectBean(String title,String notes, Date startDate, Date endDate, Integer spPerEpicPerSprint, Integer sprintDuration,Integer personDays, Integer factor, Integer storyPoints, Integer jiraProjectId, boolean projectfinished)
    {
        this.title = title;
        this.notes = notes;
        this.startDate = startDate;
        this.endDate = endDate;
        this.spPerEpicPerSprint = spPerEpicPerSprint;
        this.sprintDuration = sprintDuration;
        this.personDays = personDays;
        this.factor = factor;
        this.storyPoints = storyPoints;
        this.jiraProjectId = jiraProjectId;
        this.projectfinished = projectfinished;
    }

    public ReleasePlanProjectBean()
    {
    }


   

    public Integer getFactor()
    {
        return factor;
    }

    public void setFactor(Integer factor)
    {
        this.factor = factor;
    }
    
    public String getNotes()
    {
        return notes;
    }

    public void setNotes(String notes)
    {
        this.notes = notes;
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

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Integer getSpPerEpicPerSprint()
    {
        return spPerEpicPerSprint;
    }

    public void setSpPerEpicPerSprint(Integer spPerEpicPerSprint)
    {
        this.spPerEpicPerSprint = spPerEpicPerSprint;
    }

    public Integer getPersonDays()
    {
        return personDays;
    }

    public void setPersonDays(Integer personDays)
    {
        this.personDays = personDays;
    }
    
    
    
    
}
