package io.ecx.jira.releaseplanningwebwork;

import com.atlassian.activeobjects.external.ActiveObjects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.transaction.TransactionCallback;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import io.ecx.jira.ao.ReleasePlanProject;
import javax.inject.Inject;

public class ReleasePlanningWebworkClass extends JiraWebActionSupport
{

    private static final Logger log = LoggerFactory.getLogger(ReleasePlanningWebworkClass.class);

    @Inject
    private PageBuilderService pageBuilderService;

    private ActiveObjects activeObjects;

    @Inject
    public ReleasePlanningWebworkClass(ActiveObjects activeObjects)
    {
        this.activeObjects = activeObjects;
    }

    @Override
    public String execute() throws Exception
    {

        pageBuilderService.assembler().resources().requireWebResource("io.ecx.jira.ReleasePlanPlugin:ReleasePlanPlugin-resources");
        //.requireWebResource("io.ecx.jira.ReleasePlanPlugin:ReleasePlanPlugin-resources");
        activeObjects.executeInTransaction(new TransactionCallback<Void>()
        {
            public Void doInTransaction()
            {

                for (ReleasePlanProject proj : activeObjects.find(ReleasePlanProject.class))
                {
                    log.warn(proj.getName() + " " + proj.getJiraProjectId());
                }
                return null;
            }
        });
        return "ReleasePlanningSuccess"; //returns SUCCESS
    }

    public void setPageBuilderService(PageBuilderService pageBuilderService)
    {
        this.pageBuilderService = pageBuilderService;
    }

}
