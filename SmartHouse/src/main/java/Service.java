import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service implements Component{
	public String serviceName;
	public List<Component> devices;
	private DigitalTwin digitalTwin;
	public boolean flag;
	private static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");
	private int total_num_broken;

	Map<Integer, Integer> broken_device; 
	Map<Integer, Integer> deviceOn;
	Map<Integer, Integer> deviceOff;
	Map<Integer, Integer> numEnergySaving;
	Map<Integer, Integer> numMaxLuminosity;
	Map<Integer, Integer> numMinLuminosity;
	Map<Integer, Integer> numMaxTemp;
	Map<Integer, Integer> numMinTemp;

	public Service(String serviceName) {
		this.serviceName = serviceName;
		this.devices = new ArrayList<>();
		this.digitalTwin = new DigitalTwin(serviceName);
		broken_device = new HashMap<>(); 
		deviceOn= new HashMap<>();
		deviceOff= new HashMap<>();
		numEnergySaving= new HashMap<>();
		numMaxLuminosity= new HashMap<>();
		numMinLuminosity= new HashMap<>();
		numMaxTemp= new HashMap<>();
		numMinTemp= new HashMap<>();

	}

	public void printAll ()
	{
		for (Component device : devices) {
			Device dev = (Device) device;
			System.out.println(dev.getDeviceName());
		}

	}       
	public DigitalTwin getDigitalTwin(){
		return this.digitalTwin;
	}

	@Override
	public String  command(String command) throws SQLException {
		String com = " ";
		String service = "Executing command '" + command + "' on Service: " + serviceName;
		for (Component device : devices) {


			com+= "\n " + device.command(command);

		}

		notifyDigitalTwin(service + com);

		return service + com;
		// Observation observation = new Observation();
		// digitalTwin.addObservation(observation);


	}


	@Override
	public Parameter getParameters() {
		Parameter parameters = new Parameter(); 
		double use = 0;
		broken_device = new HashMap<>();
		for (Component device : devices) {

			use+= device.getParameters().getUsageCostDaily();

			total_num_broken += device.getParameters().getNum_broken();
			Device dev = (Device) device;
			int name = dev.getDeviceName();
			broken_device.put(name,dev.getParameters().getNum_broken());
			deviceOn.put(name, dev.getParameters().getOn());
			deviceOff.put(name, device.getParameters().getOff());
			numEnergySaving.put(name, dev.getParameters().getEnergySaving());
			numMaxLuminosity.put(name, dev.getParameters().getMaxLuminosity());
			numMinLuminosity.put(name, dev.getParameters().getMinLuminosity());
			numMaxTemp.put(name, dev.getParameters().getMaxTemp());
			numMinTemp.put(name, dev.getParameters().getMinTemp());
		}


		parameters.setServiceName(serviceName);
		parameters.setUsageCostDaily(use);
		parameters.setNum_broken(total_num_broken);
		parameters.setBrokenDevice(broken_device);
		parameters.setDeviceOn(deviceOn);
		parameters.setDeviceOff(deviceOff);
		parameters.setNumEnergySaving(numEnergySaving);
		parameters.setNumMaxLuminosity(numMaxLuminosity);
		parameters.setNumMinLuminosity(numMinLuminosity);
		parameters.setNumMaxTemp(numMaxTemp);
		parameters.setNumMinTemp(numMinTemp);
		return parameters;
	}

	public void notifyDigitalTwin(String log){
		digitalTwin.update(log, this.getParameters());
	}

	public String getName() {
		return serviceName;
	}


}

