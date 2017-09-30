/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.ao;

import net.java.ao.ManyToMany;
import net.java.ao.Preload;
import net.java.ao.RawEntity;
import net.java.ao.schema.PrimaryKey;

/**
 *
 * @author alexander
 */
@Preload
public interface Epic extends RawEntity<Integer>
{
    @PrimaryKey("ID")
    int getId();
    void setId(int id);
    
    String getName();
    void setName(String name);
    
    int getStoryPoints();
    void setStoryPoints(int sp);
    
//    @ManyToMany(value = SprintToEpic.class)
//    Sprint[] getSprints();
//    void setSprints(Sprint[] sprints);
    
    Sprint getStartSprint();
    void setStartSprint(Sprint start);
    
    
    
}
