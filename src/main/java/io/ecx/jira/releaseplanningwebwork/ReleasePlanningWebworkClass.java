package io.ecx.jira.releaseplanningwebwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;

public class ReleasePlanningWebworkClass extends JiraWebActionSupport
{
    private static final Logger log = LoggerFactory.getLogger(ReleasePlanningWebworkClass.class);

    @Override
    public String execute() throws Exception {

        return "ReleasePlanningSuccess"; //returns SUCCESS
    }
}
