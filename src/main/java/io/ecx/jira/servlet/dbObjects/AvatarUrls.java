/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.ecx.jira.servlet.dbObjects;

/**
 *
 * @author Alexander
 */
public class AvatarUrls
{
    private String s48, s24, s16, s32;

    public AvatarUrls()
    {
    }

    public String getS48()
    {
        return s48;
    }

    public void setS48(String s48)
    {
        this.s48 = s48;
    }

    public String getS24()
    {
        return s24;
    }

    public void setS24(String s24)
    {
        this.s24 = s24;
    }

    public String getS16()
    {
        return s16;
    }

    public void setS16(String s16)
    {
        this.s16 = s16;
    }

    public String getS32()
    {
        return s32;
    }

    public void setS32(String s32)
    {
        this.s32 = s32;
    }

    @Override
    public String toString()
    {
        return "AvatarUrls{" + "s48=" + s48 + ", s24=" + s24 + ", s16=" + s16 + ", s32=" + s32 + '}';
    }
    
    
}
