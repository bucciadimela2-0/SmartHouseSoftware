import lombok.Data;

import lombok.Getter;
import lombok.Setter;

@Data 

public class DeviceDto{
	

	
	private String deviceType;
	private int deviceName;
	private String location;
	private int onOff;
	private int lightLuminosity;
	private int setAlarm;
	private int setTemperature;
	private int energySaving;
	private double startTime;
	private double endTime;
	private int presence;
	
	
}
