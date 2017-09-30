/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.ao;

import net.java.ao.Entity;
import net.java.ao.RawEntity;

/**
 *
 * @author alexander
 */
public interface SprintToEpic extends Entity
{
    public void setSprint(Sprint sprint);
    public Sprint getSprint();

    public void setEpic(Epic epic);
    public Epic getEpic();
}
