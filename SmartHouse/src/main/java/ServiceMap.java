
public class ServiceMap {
	
	    public static Service mapToService(ServiceDto serviceDto) {
	        Service service = new Service(serviceDto.getServiceName());
	       
	        // Altre proprietà specifiche di Service che devono essere mappate
	        
	        return service;
	    }
	    
	    public static ServiceDto mapToServiceDto(Service service) {
	        ServiceDto serviceDto = new ServiceDto();
	        serviceDto.setServiceName(service.getName());
	        // Altre proprietà specifiche di ServiceDto che devono essere mappate
	        
	        return serviceDto;
	    }
	

}
