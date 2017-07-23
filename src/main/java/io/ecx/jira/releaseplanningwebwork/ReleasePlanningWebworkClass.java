package io.ecx.jira.releaseplanningwebwork;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import javax.inject.Inject;

public class ReleasePlanningWebworkClass extends JiraWebActionSupport
{
    private static final Logger log = LoggerFactory.getLogger(ReleasePlanningWebworkClass.class);

    @Inject
    private PageBuilderService pageBuilderService;
    
    @Override
    public String execute() throws Exception {

        pageBuilderService.assembler().resources().requireWebResource("io.ecx.jira.ReleasePlanPlugin:ReleasePlanPlugin-resources");
                //.requireWebResource("io.ecx.jira.ReleasePlanPlugin:ReleasePlanPlugin-resources");
        return "ReleasePlanningSuccess"; //returns SUCCESS
    }

    public void setPageBuilderService(PageBuilderService pageBuilderService) {
        this.pageBuilderService = pageBuilderService;
    }
    
    
}
