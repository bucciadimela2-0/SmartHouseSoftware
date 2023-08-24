import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.util.ArrayList;
//import java.util.List;

//Observation??
public class DigitalTwin implements Observer {
    private String serviceName;
    private static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");
    Report report;
    
    public DigitalTwin(String serviceName){
        this.serviceName = serviceName;
    }

   

    @Override
    public void update(String log, Parameter parameter) {
    	
        loggerApplication.info(log);
        
        report = new Report();
        report.addToList(parameter);
        
        
        
    }
    
    



}
