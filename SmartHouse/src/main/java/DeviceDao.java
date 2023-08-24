import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeviceDao extends Dao {
	
//	protected Connection connection;
	protected static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");
	
	
//	public void init() throws Exception {
//		this.connection = ConnectionManager.getInstance().getConnection();
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
	

	
	
	public void showTable() throws SQLException {
	    String query = "SELECT * FROM TB_DEVICE_TABLE";
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	       
	        preparedStatement = connection.prepareStatement(query);
	        resultSet = preparedStatement.executeQuery();
	        
	        
	        // Itera attraverso il ResultSet e stampa i dati delle colonne
	        while (resultSet.next()) {
	        	
	        	String deviceType = resultSet.getString(StaticValue.deviceType);
	        	int deviceName = resultSet.getInt(StaticValue.deviceName);
	        	String location = resultSet.getString(StaticValue.location);
	        	int onOff = resultSet.getInt(StaticValue.onOff);
	        	int lightLuminosity = resultSet.getInt(StaticValue.lightLuminosity);
	        	int setAlarm = resultSet.getInt(StaticValue.setAlarm);
	        	int setTemperature = resultSet.getInt(StaticValue.setTemperature);
	        	int energySaving = resultSet.getInt(StaticValue.energySaving);
	        	double startTime = resultSet.getDouble(StaticValue.StartTime);
	        	double endTime = resultSet.getDouble(StaticValue.endTime);
	        	int presence = resultSet.getInt(StaticValue.Presence);


                // Stampa i dati
                System.out.println("Device Type: " + deviceType);
                System.out.println("Device Name: " + deviceName);
                System.out.println("Location: " + location);
                // Stampa gli altri dati

                System.out.println("----------------------");
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
	}
	
	public Object getColumnValueByDeviceName(int deviceName, String columnName) throws SQLException {
	       Object columnValue = null;
	        PreparedStatement preparedStatement = null;
	        ResultSet resultSet = null;
		    
	        try {
	        	
	            String query = "SELECT " + columnName + " FROM TB_DEVICE_TABLE WHERE deviceName = ?";
	            PreparedStatement statement = connection.prepareStatement(query);
	            statement.setInt(1, deviceName);

	            resultSet = statement.executeQuery();

	            if (resultSet.next()) {
	            	if(columnName.equalsIgnoreCase(StaticValue.deviceType) ||columnName.equalsIgnoreCase(StaticValue.location) ) {
	            		columnValue = resultSet.getString("columnName");
	            		String value = columnValue.toString();
	            		return value;}
	            	
	            	else if (columnName.equalsIgnoreCase(StaticValue.StartTime)||columnName.equalsIgnoreCase(StaticValue.endTime)) {
	            		columnValue = resultSet.getDouble(columnName);
	            		double value = (double) columnValue;
//	            		double value = Double.valueOf(columnName.toString());
	            		return value;
	            	}
	            	
	            	else {
	            		
	            		columnValue = resultSet.getInt(columnName);
	            		int value = (int) columnValue;
//	            		int value = Integer.valueOf(columnValue.toString());
	            		return value;
	            		}
	            		
	            }
	        } catch (SQLException e) {
	            loggerApplication.error("Errore durante la lettura dell'informazione dalla tabella.", e);
	        } finally {
	            if (resultSet != null) {
	                resultSet.close();
	            }
	            if (preparedStatement != null) {
	                preparedStatement.close();
	            }
	        }
	        return null;
	    }

	public void setColumnValueByDeviceName(int deviceName, String columnName, Object columnValue) throws SQLException {
		
		//Object columnValue = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    try {
	    	
	        String query = "UPDATE TB_DEVICE_TABLE SET " + columnName + " = ? WHERE deviceName = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        
	        if(columnValue instanceof String) {
	        	
	        	String value = (String) columnValue;
	        	statement.setString(1, value);
	        	statement.setInt(2, deviceName);}
	        
	        else if(columnValue instanceof Integer) {
	        	
	        	int value = (int) columnValue;
	        	statement.setInt(1, value);
	        	statement.setInt(2, deviceName);}
	        
	        else if(columnValue instanceof Double) {
	        	
	        	double value = (double) columnValue;
	        	statement.setDouble(1, value);
	        	statement.setInt(2, deviceName);}
	
	        statement.executeUpdate();
	    } catch (SQLException e) {
            loggerApplication.error("Errore durante l'aggiornamento dell'informazione nella tabella.", e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }


	}
	
	public int getPresenceByLocation(String location) throws SQLException {
	    String query = "SELECT presence FROM TB_DEVICE_TABLE WHERE location = ? AND deviceType = 'sensore presenza'";
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, location);

	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getInt(StaticValue.Presence);
	        } else {
	            loggerApplication.info("Nessun dato trovato per la location: " + location);
	        }
	    } catch (SQLException e) {
	        loggerApplication.error("Errore durante la lettura dell'informazione dalla tabella.", e);
	    } finally {
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	    }

	    return 0;  // Valore di default se l'informazione non viene trovata
	}
	
	public int getonOffByLocation(String location) throws SQLException {
	    String query = "SELECT onOff FROM TB_DEVICE_TABLE WHERE location = ? AND deviceType = 'sensore presenza'";
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    try {
	        
	        preparedStatement = connection.prepareStatement(query);
	        preparedStatement.setString(1, location);

	        resultSet = preparedStatement.executeQuery();
	        if (resultSet.next()) {
	            return resultSet.getInt(StaticValue.onOff);
	        } else {
	            loggerApplication.info("Nessun dato trovato per la location: " + location);
	        }
	    } catch (SQLException e) {
	        loggerApplication.error("Errore durante la lettura dell'informazione dalla tabella.", e);
	    } finally {
	        if (resultSet != null) {
	            resultSet.close();
	        }
	        if (preparedStatement != null) {
	            preparedStatement.close();
	        }
	    }

	    return 0;  // Valore di default se l'informazione non viene trovata
	}

	public List<DeviceDto> getDeviceList() throws SQLException {
	    List<DeviceDto> deviceList = new ArrayList<>();
	    String query = "SELECT * FROM TB_DEVICE_TABLE";
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    
	    try {
	        preparedStatement = connection.prepareStatement(query);
	        resultSet = preparedStatement.executeQuery();
	        
	        while (resultSet.next()) {
	            String deviceType = resultSet.getString(StaticValue.deviceType);
	            int deviceName = resultSet.getInt(StaticValue.deviceName);
	            String location = resultSet.getString(StaticValue.location);
	            int onOff = resultSet.getInt(StaticValue.onOff);
	            int lightLuminosity = resultSet.getInt(StaticValue.lightLuminosity);
	            int setAlarm = resultSet.getInt(StaticValue.setAlarm);
	            int setTemperature = resultSet.getInt(StaticValue.setTemperature);
	            int energySaving = resultSet.getInt(StaticValue.energySaving);
	            double startTime = resultSet.getDouble(StaticValue.StartTime);
	            double endTime = resultSet.getDouble(StaticValue.endTime);
	            int presence = resultSet.getInt(StaticValue.Presence);
	            
	            DeviceDto device = new DeviceDto();
	            device.setDeviceName(deviceName);
	            device.setDeviceType(deviceType);
	            device.setLocation(location);
	            device.setOnOff(onOff);
	            device.setLightLuminosity(lightLuminosity);
	            device.setSetAlarm(setAlarm);
	            device.setSetTemperature(setTemperature);
	            device.setEnergySaving(energySaving);
	            device.setEndTime(endTime);
	            device.setStartTime(startTime);
	            device.setPresence(presence);
	          
	            deviceList.add(device);
	        }
	    } catch (SQLException e) {
	        loggerApplication.error(e.getMessage(), e);
	        // rollback();
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
	


}
