package ut.io.ecx.jira.ReleasePlanPlugin;

import org.junit.Test;
import io.ecx.jira.ReleasePlanPlugin.api.MyPluginComponent;
import io.ecx.jira.ReleasePlanPlugin.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}