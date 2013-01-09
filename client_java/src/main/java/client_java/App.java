package client_java;

import java.text.MessageFormat;
import java.util.Properties;

import org.apache.oozie.client.OozieClient;
import org.apache.oozie.client.WorkflowAction;
import org.apache.oozie.client.WorkflowJob;
import org.apache.oozie.local.LocalOozie;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {
        System.getProperties().put("oozie.home.dir", "/home/junaoki/apps/oozie-3.3.0");
//        LocalOozie.start();
//        OozieClient oc = LocalOozie.getClient();
        OozieClient oc = new OozieClient("http://localhost:11000/oozie");
        
        Properties conf = oc.createConfiguration();
//        conf.setProperty("oozie.home.dir", "/home/junaoki/apps/oozie-3.3.0");
        
        conf.setProperty(OozieClient.APP_PATH, "hdfs://localhost:8020/user/junaoki/examples/apps/pig");

        String jobId = oc.run(conf);
        System.out.println("jobId:" + jobId);
        while (oc.getJobInfo(jobId).getStatus() == WorkflowJob.Status.RUNNING) {
            System.out.println("Workflow job running ...");
            printWorkflowInfo(oc.getJobInfo(jobId));
            Thread.sleep(10 * 1000);
        }

        System.out.println( "Hello World!" );
    }
    
    private static void printWorkflowInfo(WorkflowJob wf) {
        System.out.println("Application Path   : " + wf.getAppPath());
        System.out.println("Application Name   : " + wf.getAppName());
        System.out.println("Application Status : " + wf.getStatus());
        System.out.println("Application Actions:");
        for (WorkflowAction action : wf.getActions()) {
            System.out.println(MessageFormat.format("   Name: {0} Type: {1} Status: {2}", action.getName(),
                                                    action.getType(), action.getStatus()));
        }
        System.out.println();
    }

}
