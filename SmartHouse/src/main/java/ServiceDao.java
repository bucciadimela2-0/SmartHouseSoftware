import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao extends Dao{

	public List<ServiceDto> getServiceList() throws SQLException {
	    List<ServiceDto> serviceList = new ArrayList<>();
	    String query = "SELECT * FROM TB_SERVICE_TABLE";
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        
	        preparedStatement = connection.prepareStatement(query);
	        resultSet = preparedStatement.executeQuery();
	        

	        // Iterating through the ResultSet and creating ServiceDTO objects
	        while (resultSet.next()) {
	        	
	        	ServiceDto serviceDTO = new ServiceDto();
	            String serviceName = resultSet.getString(StaticValue.serviceName);
	            int serviceID = resultSet.getInt(StaticValue.serviceID);

	            serviceDTO.setServiceID(serviceID);
	            serviceDTO.setServiceName(serviceName);
	            // Creating a ServiceDTO object and adding it to the list
	            
	            
	            
	            serviceList.add(serviceDTO);
	           
	        }
	    } catch (Exception e) {
	        loggerApplication.error(e.getMessage(), e);
	         rollback();
	    } finally {
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	    }

	    return serviceList;
	}
	
	/*public Service getServiceByServiceName(String serviceName) throws SQLException {
	    Service service = null;
	    String query = "SELECT * FROM TB_SERVICE_TABLE WHERE serviceName = ?";
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        loggerApplication.info(query);
	        
	        preparedStatement.setString(1, serviceName);
	        resultSet = preparedStatement.executeQuery();
	        

	        // Check if a service with the given service name exists
	        if (resultSet.next()) {
	            

	            // Creating a Service object with the retrieved data
	            service = new Service(serviceName);
	        }
	    } catch (Exception e) {
	        loggerApplication.error(e.getMessage(), e);
	        // rollback();
	    } finally {
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	    }

	    return service;
	}*/
}
