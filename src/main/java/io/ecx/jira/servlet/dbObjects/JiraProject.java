/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.servlet.dbObjects;

import java.util.ArrayList;

/**
 *
 * @author Alexander
 */
public class JiraProject
{
    private String expand,self,id,key,name;
    private AvatarUrls avatarUrls;
    private ProjectCategory projectCategory;

    public JiraProject()
    {
    }
    
    

    public String getExpand()
    {
        return expand;
    }

    public void setExpand(String expand)
    {
        this.expand = expand;
    }

    public String getSelf()
    {
        return self;
    }

    public void setSelf(String self)
    {
        this.self = self;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public AvatarUrls getAvatarUrls()
    {
        return avatarUrls;
    }

    public void setAvatarUrls(AvatarUrls avatarUrls)
    {
        this.avatarUrls = avatarUrls;
    }

    public ProjectCategory getProjectCategory()
    {
        return projectCategory;
    }

    public void setProjectCategory(ProjectCategory projectCategory)
    {
        this.projectCategory = projectCategory;
    }

    @Override
    public String toString()
    {
        return "JiraProject{" + "expand=" + expand + ", self=" + self + ", id=" + id + ", key=" + key + ", name=" + name + ", avatarUrls=" + avatarUrls + ", projectCategory=" + projectCategory + '}';
    }
    
    
}
