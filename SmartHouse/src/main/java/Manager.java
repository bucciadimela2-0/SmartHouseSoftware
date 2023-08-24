import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import javax.management.ObjectInstance;

import lombok.Data;

@Data
public class Manager {

	private static Manager instance;
	private List<Service> allServices = new ArrayList<Service>();

	private List<Device> allDevices;

	AssociatedTableDao associatedDao;

	public Manager () throws Exception{
		this.allServices = new ArrayList<Service>();
		this.allDevices = new ArrayList<>();
		associatedDao = new AssociatedTableDao();
		associatedDao.init();

	}

	private void notifyAllDigitalTwin(Component component){
		//notifica tutti i servizi (e perciò i loro digitaltwin abbinati) dell'accensione di un dispositivo in comune
		//al servizio/dispositivo interrogto

		//C'è in primo luogo da distinguere l'oggetto, se device o component
		// filtro in tutti i servizi, vedendo cosa appartiene e cosa no
		//e poi notifico i rispettivi digital twin

		if (component instanceof Device ){

			//prende la lista dei servizi, la scorre controllando che all'interno ci sia il dispositivo e nel caso ci sia, notifichi il rispettivo digitaltwin

			//rimettere privata la lista

			Device dev = (Device) component;


			allServices.stream().forEach(s -> {

				s.devices.forEach(device->{

					Device d = (Device) device;
					if(d.getDeviceName() == dev.getDeviceName())

						s.notifyDigitalTwin(d.info); 
				});
			});


			// s.components.contains(component)? s.notif : });

		}

		else if (component instanceof Service){

			Service service = (Service) component;
			service.flag = true;
			service.devices.stream().forEach(s -> {
				// if(component != service)

				this.notifyAllDigitalTwin(s); 
			});

			allServices.stream().forEach(s->{s.flag = false;});

		}




	}

	public void commandService(String command, Component component) throws SQLException{

		//va dato l'esecuzione, va differenziato se è un service o un device, nel caso del service vanno 
		//accesi tutti i dispositivi interrograti

		component.command(command);
		notifyAllDigitalTwin(component);


	}



	public void removeServices(Service service){
		allServices.remove(service);
	}

	public void addServices(List<Service> services) {
		for(Service service: services) {
			allServices.add(service);	

		}

		allServices.stream().forEach(s->{
			try {
				addDeviceToService(s);
			} catch (SQLException e) {

				e.printStackTrace();
			}


		});



	}

	public void addDevices( List<Device> devices) {

		for(Device device: devices) {
			allDevices.add(device);	


		}

	}

	public void addDeviceToService(Service service) throws SQLException {
		
		List<DeviceDto> deviceIDs = associatedDao.getDevicesByService(service.getName());
		//List<DeviceDto> deviceIDs = associatedDao.getDevicesByService(service.getName());
		//List<Integer> deviceIDs = dto.getDevicesByService(service.serviceName);



		deviceIDs.stream().forEach(idDevice -> {
			allDevices.forEach(device->{
				if(device.getDeviceName() == idDevice.getDeviceName())
					service.devices.add(device);
			});
		});
	}




	public Service selectService(String serviceName){
		Service service = allServices.stream()
				.filter(s -> s.getName().equals(serviceName))
				.findFirst()
				.orElse(null);

		return service;

	}


	public Device selectDevice(int deviceName){
		Device device = allDevices.stream()
				.filter(s -> s.getDeviceName() == (deviceName))
				.findFirst()
				.orElse(null);

		return device;

	}

	public static Manager getManager() throws Exception {

		if(instance== null)
			instance = new Manager();

		return instance;
	}


}
