package ssb.no.debugjavonet;

import com.javonet.Javonet;
import com.javonet.JavonetFramework;
import com.javonet.api.NObject;
import com.javonet.api.NType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DebugJavonetApplication {
    private static final Logger logger = LogManager.getLogger(DebugJavonetApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DebugJavonetApplication.class, args);
        try {
            Javonet.activate("r2BX-j4P8-m4ME-Bt83-z7AQ", "software_licenses@ssb.no", "Javonet2018", JavonetFramework.v40);
//            Javonet.activate("r2BX-j4P8-m4ME-Bt83-z7AQ", "aase@jpro.no", "Javonet2018", JavonetFramework.v40);

            Javonet.addReference("statneth.blaise.api.dll");
            Javonet.addReference("System.Data.SQLite.dll");


            NType eventsManager = Javonet.getType("EventsManager");
            NType myListType = Javonet.getType("System.Collections.Generic.List`1", "String");
            NObject myList = myListType.create();

            myList.invoke("Add", "StartSession");
            myList.invoke("Add", "EndSession");
            myList.invoke("Add", "KeyvalueDetermined");
            myList.invoke("Add", "CaseIssued");
            myList.invoke("Add", "DialNumberRequested");
            myList.invoke("Add", "DialAttemptEnded");
            myList.invoke("Add", "AppointmentMade");
            myList.invoke("Add", "CaseCompleted");

            NObject eventManager = eventsManager.invoke("EventsListenServiceStarted",
                    "http://localhost:8080/blaiseevents",
                    myList,
                    "localhost",
                    8033);

            logger.info("Alt er ok");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            logger.error("Subscribe feilet", e);
        }
    }
}
