import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssociatedTableDao extends Dao{
	
//	protected Connection connection;
//	protected static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");
//	
//	
//	public void init() throws Exception {
//		this.connection = ConnectionManager.getInstance().getConnection();
//		//this.connection = ConnectionManager.getInstance().getConnection();
//		connection.setAutoCommit(true);
//	}
//
//	public void closeConnection() throws Exception {
//
//		if (connection != null) {
//			connection.close();
//		}
//	}
//
//
//	public boolean commit() {
//		try {
////			loggerApplication.info("eseguo commit ");
//			connection.commit();
////			loggerApplication.info("commit terminata");
//			connection.setAutoCommit(false);
//			return true;
//		} catch (Exception ex) {
//			try {
//				connection.setAutoCommit(false);
//			} catch (SQLException e) {
//			}
//			loggerApplication.error("Transazione fallita", ex);
//			return false;
//		}
//	}
//
//	public boolean rollback() {
//		try {
//			connection.rollback();
//			loggerApplication.info("RollBack eseguita");
//			connection.setAutoCommit(false);
//			return false;
//		} catch (Exception ex) {
//			if (connection != null)
//				try {
//					connection.setAutoCommit(false);
//				} catch (SQLException e) {
//				}
//			loggerApplication.error("transazione fallita", ex);
//			return false;
//		}
//
//	}
	
	public List<DeviceDto> getDevicesByService(String serviceName) throws SQLException {
        List<DeviceDto> deviceList = new ArrayList<>();
        String query = "SELECT deviceName, deviceType, location FROM TB_SERVICES_ASSOCIATED WHERE serviceName = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, serviceName);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String deviceType = resultSet.getString(StaticValue.deviceType);
                int deviceName = resultSet.getInt(StaticValue.deviceName);
                String location = resultSet.getString(StaticValue.location);

                DeviceDto device = new DeviceDto();
                device.setDeviceType(deviceType);
                device.setDeviceName(deviceName);
                device.setLocation(location);

                deviceList.add(device);
            }
        } catch (SQLException e) {
            loggerApplication.error(e.getMessage(), e);
            this.rollback();
            throw e;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    loggerApplication.error(e.getMessage(), e);
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    loggerApplication.error(e.getMessage(), e);
                }
            }
        }

        return deviceList;
    }

//    public List<ServiceDto> getServicesByDevice(int deviceName) throws SQLException {
//        List<ServiceDto> serviceList = new ArrayList<>();
//        String query = "SELECT serviceName, serviceID FROM TB_SERVICES_ASSOCIATED WHERE deviceName = ?";
//        PreparedStatement preparedStatement = null;
//        ResultSet resultSet = null;
//
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setInt(1, deviceName);
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                String serviceName = resultSet.getString(StaticValue.serviceName);
//                int serviceID = resultSet.getInt(StaticValue.serviceID);
//
//                ServiceDto service = new ServiceDto();
//                service.setServiceName(serviceName);
//                service.setServiceID(serviceID);
//
//                serviceList.add(service);
//            }
//        } catch (SQLException e) {
//            loggerApplication.error(e.getMessage(), e);
//            this.rollback();
//            throw e;
//        } finally {
//            if (resultSet != null) {
//                try {
//                    resultSet.close();
//                } catch (SQLException e) {
//                    loggerApplication.error(e.getMessage(), e);
//                }
//            }
//            if (preparedStatement != null) {
//                try {
//                    preparedStatement.close();
//                } catch (SQLException e) {
//                    loggerApplication.error(e.getMessage(), e);
//                }
//            }
//        }
//
//        return serviceList;
//    }
	/*public List<Integer> getDevicesByService(String serviceName) throws SQLException {
	    List<Integer> deviceList = new ArrayList<>();
	    String query = "SELECT deviceName, deviceType,location FROM TB_SERVICES_ASSOCIATED WHERE serviceName = ?";
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	       
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, serviceName);
	        resultSet = preparedStatement.executeQuery();
	       

	        // Itera attraverso il ResultSet e crea gli oggetti Device
	        while (resultSet.next()) {
	        	int deviceName = resultSet.getInt(StaticValue.deviceName);
	            String deviceType = resultSet.getString(StaticValue.deviceType);
	            String location = resultSet.getString(StaticValue.location);
	            

	            
	            // Creazione dell'oggetto Device e aggiunta alla lista
	            //ricerca nella lista
	            int device = deviceName;
	            deviceList.add(device);
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

	    return deviceList;
	}

	public List<Service> getServicesByDevice(long deviceName, String deviceType) throws SQLException {
	    List<Service> serviceList = new ArrayList<>();
	    String query = "SELECT * FROM TB_SERVICES_ASSOCIATED WHERE deviceName = ? ";
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;

	    try {
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setLong(1, deviceName);
	        preparedStatement.setString(2, deviceType);
	        resultSet = preparedStatement.executeQuery();
	        

	        // Itera attraverso il ResultSet e crea gli oggetti Service
	        while (resultSet.next()) {
	            String serviceName = resultSet.getString(StaticValue.serviceName);
	            int serviceID = resultSet.getInt(StaticValue.serviceID);

	            // Creazione dell'oggetto Service e aggiunta alla lista
	            Service service = new Service(serviceName);
	            serviceList.add(service);
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

	    return serviceList;
	}*/

}
