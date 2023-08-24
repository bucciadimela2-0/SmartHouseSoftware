
public class DeviceMap {
	
	    public static Device mapToDevice(DeviceDto deviceDto, DeviceDao deviceDao) throws Exception {
	        Device device = new Device(deviceDto.getDeviceName(), deviceDto.getLocation(), deviceDto.getDeviceType(),deviceDao  );
	        /*device.setDeviceName(deviceDto.getDeviceName());
	        device.setLocation(deviceDto.getLocation());
	        device.setDeviceType(deviceDto.getDeviceType());*/
	        // Altre proprietà specifiche di Device che devono essere mappate
	        
	        return device;
	    }
	    
	    public static DeviceDto mapToDeviceDto(Device device) {
	        DeviceDto deviceDto = new DeviceDto();
	        deviceDto.setDeviceName(device.getDeviceName());
	        deviceDto.setLocation(device.getLocation());
	        deviceDto.setDeviceType(device.getDeviceType());
	        // Altre proprietà specifiche di DeviceDto che devono essere mappate
	        
	        return deviceDto;
	    }
	


}
