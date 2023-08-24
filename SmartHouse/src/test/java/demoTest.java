import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class demoTest {

	@Before
	public void setUp() throws Exception{
		Manager manager = new Manager();

	}




	@Test
	public void testSingletonManager() throws Exception {

		System.out.println("Inizio Test testSingletonManager");
		Manager m1 = Manager.getManager();
		Manager m2 = Manager.getManager();
		assertEquals(m1, m2);
		System.out.println("Fine Test testSingletonMonitor");
	}

	@Test
	public void testAddServices() throws Exception {
		// Creazione di una lista di servizi
		Manager manager = Manager.getManager();
		Service service1 = new Service("Service 1");
		Service service2 = new Service("Service 2");
		List<Service> services = Arrays.asList(service1, service2);

		// Aggiunta dei servizi al manager
		manager.addServices(services);

		// Verifica che i servizi siano stati correttamente aggiunti al manager
		assertEquals(manager.selectService(service1.serviceName),service1);
		assertEquals(manager.selectService(service2.serviceName), service2);
	}

	@Test
	public void testSelectService() throws Exception {
		// Creazione di un servizio
		Service service = new Service("OnAllLights");
		Manager manager = Manager.getManager();

		// Aggiunta del servizio al manager
		manager.addServices(Collections.singletonList(service));

		// Selezione del servizio dal manager
		Service selectedService = manager.selectService("OnAllLights");

		// Verifica che il servizio selezionato corrisponda al servizio aggiunto
		assertEquals(service, selectedService);
	}

	@Test
	public void testSelectDevice() throws Exception {
		Manager manager = Manager.getManager();
		// Creazione di un dispositivo
		DeviceDao dao = new DeviceDao();
		dao.init();
		Device device = new Device(1, "bagno", "luce",dao );

		// Aggiunta del dispositivo al manager
		manager.addDevices(Collections.singletonList(device));

		// Selezione del dispositivo dal manager
		Device selectedDevice = manager.selectDevice(1);

		// Verifica che il dispositivo selezionato corrisponda al dispositivo aggiunto
		assertEquals(device.getDeviceName(), selectedDevice.getDeviceName());
	}





	// Aggiungi altri metodi di test per gli altri metodi della classe Manager


	@Test
	public void testCommandService() throws Exception {
		Manager manager = Manager.getManager();
		DeviceDao dao = new DeviceDao();
		dao.init();

		Device device = new Device(1,  "bagno", "luce", dao );
		// Esecuzione di un comando sul dispositivo tramite il metodo commandService()
		manager.commandService("accendi", device);

		// Verifica che il dispositivo sia stato acceso correttamente
		assertEquals(1, device.isDeviceOn());

		// Esecuzione di un altro comando sul dispositivo tramite il metodo commandService()
		manager.commandService("spengi", device);

		// Verifica che il dispositivo sia stato spento correttamente
		assertEquals(0, device.isDeviceOn());
	}


	@Test
	public void testAddDeviceAndServiceToMap() throws Exception {

		DeviceDao dao = new DeviceDao();
		ServiceDao serviceDao = new ServiceDao();
		dao.init();
		serviceDao.init();

		List<DeviceDto> devicesDto = dao.getDeviceList();
		List<Device> devices = new ArrayList<Device>();

		DeviceMap deviceMap = new DeviceMap();
		ServiceMap serviceMap = new ServiceMap();

		for(DeviceDto device: devicesDto)
			devices.add(deviceMap.mapToDevice(device,dao));

		assertEquals(dao.getDeviceList().size(), devices.size());


		List<ServiceDto> serviceDto = serviceDao.getServiceList();
		List<Service> services = new ArrayList<Service>(); 

		for(ServiceDto service: serviceDto)
			services.add(serviceMap.mapToService(service));

		assertEquals(serviceDao.getServiceList().size(), services.size());

	}
	@Test
	public void testBrokenDevice() throws Exception {
		Manager manager = Manager.getManager();
		DeviceDao deviceDao = new DeviceDao();
		deviceDao.init();
		Device device = new Device(1,  "bagno", "luce", deviceDao );
		manager.addDevices(Collections.singletonList(device));
		deviceDao.setColumnValueByDeviceName(device.getDeviceName(), StaticValue.onOff, -1);
		//verifica dispositivo è rotto
		assertEquals(-1,deviceDao.getColumnValueByDeviceName(device.getDeviceName(),StaticValue.onOff ));
		manager.commandService(StaticValue.On,manager.selectDevice(1));
		//verifica dispositivo è stato aggiustato
		assertEquals(0,deviceDao.getColumnValueByDeviceName(device.getDeviceName(),StaticValue.onOff ));



	}



}











    
    
 


	


