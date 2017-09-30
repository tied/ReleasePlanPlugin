/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.ao;

import java.util.Date;
import net.java.ao.OneToMany;
import net.java.ao.Preload;
import net.java.ao.RawEntity;
import net.java.ao.schema.PrimaryKey;

/**
 *
 * @author alexander
 */
@Preload
public interface Sprint extends RawEntity<Integer>
{

    @PrimaryKey("ID")
    int getId();
    void setId(int id);
    
    String getName();
    void setName(String name);
    
    Date getStartDate();
    void setStartDate(Date startDate);
    
    Date getEndDate();
    void setEndDate(Date endDate);
    
    int getStoryPointCapacity();
    void setStoryPointCapacity(int capacity);
    
    @OneToMany
    Epic[] getEpics();
    //void setEpics(Epic[] epics);
}
