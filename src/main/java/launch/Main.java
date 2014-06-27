package launch;

import java.io.File;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
public class Main {

    public static void main(String[] args) throws Exception{

        String webappDirLocation = "WebContent/";
        Tomcat tomcat = new Tomcat();

        String webPort = System.getenv("PORT");
        if(webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        tomcat.setPort(Integer.valueOf(webPort));

        tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());
        System.out.println("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

        Context rootContext = tomcat.addContext("", (new File(".")).getAbsolutePath());
        Tomcat.initWebappDefaults(rootContext);

        tomcat.start();
        tomcat.getServer().await();
    }

}
