import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data 
@Getter 
public class Parameter {
	
	double UsageCostDaily;
	int num_broken=0;
	int On =0;
	int Off=0;
	int energySaving=0;
	int maxLuminosity=0;
	int minLuminosity=0;
	int maxTemp=0;
	int minTemp=0;
	String serviceName="";
	Map<Integer, Integer> brokenDevice;
	Map<Integer, Integer> deviceOn;
	Map<Integer, Integer> deviceOff;
	Map<Integer, Integer> numEnergySaving;
	Map<Integer, Integer> numMaxLuminosity;
	Map<Integer, Integer> numMinLuminosity;
	Map<Integer, Integer> numMaxTemp;
	Map<Integer, Integer> numMinTemp;
	
	
   
}
