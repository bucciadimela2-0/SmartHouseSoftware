import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {


        Manager manager;
        Device device1;
        Device device2;
        Device device3;
        Device device4;
        Device device5;
        Service service1;
        Service service2;
        Report report = new Report();        //List<Device> devices;
       
        DeviceMap deviceMap = new DeviceMap();
        ServiceMap serviceMap = new ServiceMap();
        
       // DTO dto = new DTO(); 
        
        DeviceDao deviceDao = new DeviceDao();
        ServiceDao serviceDao = new ServiceDao();
        AssociatedTableDao associatedDao = new AssociatedTableDao();
        
        deviceDao.init();
        serviceDao.init();
        associatedDao.init();
        
        List<DeviceDto> devicesDto = deviceDao.getDeviceList();
        List<Device> devices = new ArrayList<Device>();
        
        for(DeviceDto device: devicesDto)
        	devices.add(deviceMap.mapToDevice(device,deviceDao));
        
        List<ServiceDto> serviceDto = serviceDao.getServiceList();
        List<Service> services = new ArrayList<Service>(); 
        
        for(ServiceDto service: serviceDto) {
        	services.add(serviceMap.mapToService(service));
        
        }
       // dto.init();
  
        
        //devices = dto.getDeviceList();
        //services = dto.getServiceList();
        
        
        
     
        
        manager = new Manager();
        manager.addDevices(devices);
        manager.addServices(services);
        
        

        manager.commandService(StaticValue.On, manager.selectService(StaticValue.LightsBedroom2));
        manager.commandService(StaticValue.On, manager.selectDevice(28));
     
       Thread.sleep(50000);
       
       manager.commandService(StaticValue.Off, manager.selectService(StaticValue.LightsBedroom2));
      
       manager.commandService(StaticValue.On, manager.selectService(StaticValue.OnAllLights));
        
       Thread.sleep(50000);
       
       manager.commandService(StaticValue.Off, manager.selectService(StaticValue.LightsBedroom2));
       
        Thread.sleep(50000);
        manager.commandService(StaticValue.Off, manager.selectService(StaticValue.LightsCorridor));
        Thread.sleep(50000);
        manager.commandService(StaticValue.On,manager.selectDevice(32) );
        manager.commandService(StaticValue.AddPresence,manager.selectDevice(32) );
        manager.commandService(StaticValue.EnableEnergySaving,manager.selectService(StaticValue.LightsBathroom) );
        
        manager.commandService(StaticValue.DisableEnergySaving,manager.selectService(StaticValue.LightsBathroom) );
        Thread.sleep(50000);
        
        manager.commandService(StaticValue.Off, manager.selectService(StaticValue.OnAllLights));

        

        
  
		deviceDao.showTable();
		report.function(services);
		
        
       
           }
}
