//import java.util.ArrayList;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Data
public class Device implements Component{
    
	
    private int deviceName;
    private String location;
    private String deviceType;
    int temperature;
    private static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");
    DeviceDao deviceDao;
    Instant inizio;
    Instant fine; 
    String info;
    Parameter parameter;
    int num_broken;
    int numOn;
    int numOff;
    int numEnergySaving;
    int numMaxLuminosity;
    int numMinLuminosity;
    int numMaxTemp;
    int numMinTemp;
    double expenses;

    public Device(int id, String location, String deviceType, DeviceDao deviceDao) throws Exception {
    	
        this.deviceName = id;
        this.location = location;
        this.deviceDao = deviceDao;
        this.deviceType = deviceType;
        parameter = new Parameter();
        num_broken = 0;
        numOn = 0;
        numOff = 0;
        numEnergySaving = 0;
        numMaxLuminosity= 0;
        numMinLuminosity=0;
        numMaxTemp=0;
        numMinTemp=0;
       
    }

   
    public String command(String command) throws SQLException {
    	
        switch (command.toLowerCase()) {
            case StaticValue.On:
            	
                if (isDeviceOn()==1) {
                	
                    info = "il/la " + deviceType +" "+ deviceName + " è già acceso/a";
                    return info;
                } 
                
                else if(isDeviceOn() == 0) {
                	
                	numOn+=1;
                	Random random = new Random();
                	int randomNumber = random.nextInt(100);

                	
                	int value;
                	if (randomNumber < 5) {
                	    value = 1;
                	    deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.onOff, -1);
                	    info = "Il/la " + deviceType + " "+ deviceName +  " è rotto, aggiustalo";
                	    num_broken++;
                	    
                	    return info;
                	    
                	    
                	}
                	
                	inizio = Instant.now();
                	double minuti = inizio.getEpochSecond()/360;
                	
                	
                	deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.StartTime, minuti);
                	//dto.setStartTime(this,minuti );
                	deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.onOff , 1);
                    //dto.setOnOffByDeviceName(deviceName, 1);
                    info = "Il/la " + deviceType + " "+ deviceName +  " è stato acceso";
                    return info;
                }
                
                else if (isDeviceOn() == -1) {
                    info += "Il/la " + deviceType + " "+ deviceName +  " è rotto, aggiustalo";
                    this.fixDevice(deviceName);
                    info += "Il/la " + deviceType + " "+ deviceName +  " è stato aggiustato";
                	return info;
                }
                else {
                	info = "Il dispositivo " + deviceName +  " non è stato trovato";
                	return info;
                }
                
                

            case StaticValue.Off:
                if (isDeviceOn() == 0) {
                    info = "il/la " +deviceType+" "+ deviceName + " è già spento";
                    return info;

                }

                else if(isDeviceOn() == 1) {
                	numOff+=1;
                	
                	fine = Instant.now();
                	double f = fine.getEpochSecond()/360;
                	deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.endTime, f);
                	deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.onOff, 0);
                	//dto.setEndTime(this, f);
                    //dto.setOnOffByDeviceName(deviceName, 0);
                    info = "il/la "+ deviceType +" " + deviceName +" è stato spento";
                    this.calculateParameters();
                    return info;
                }
                
                else {
                	info = "Il dispositivo " + deviceName +  " non è stato trovato";
                	return info;
                
                }
            case StaticValue.IncreasedLuminosity: 
            	
            	numMaxLuminosity+=1;
            	if(getDeviceLuminosity() >= 100 && isDeviceOn() == 1) {
            		info = "La luce " + deviceName + " situata in "+ location + " è al massimo della sua luminosità";
            		return info;
            	}
            	
            	else if (getDeviceLuminosity() == -1) {
            		
            		info = "il Device " + deviceName + " non è una luce o non è stata trovata el database";
            		return info;
            	}
            	
            	else if (isDeviceOn() == 1 && getDeviceLuminosity() < 100){
            		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.lightLuminosity, 1);
            		//dto.setLightLuminosityByDeviceName(this, 20);
            		info ="La luce " + deviceName + " situata in "+ location + " ha aumentato la sua luminosità ";
            		return info;
            	}
            	
            	else {
            		info = "La luce " + deviceName + " situata in "+ location + " è spenta ";
            		return info;
            		
            	}
            	
            	
            case StaticValue.DecreasedLuminosity: 
            	numMinLuminosity +=1;
            	if(getDeviceLuminosity() == 0 && isDeviceOn()==1) {
            		info = "La luce " + deviceName + " situata in "+ location + " è al minimo della sua luminosità";
            		return info;
            	}
            	
            	else if (isDeviceOn()==2 ) {
            		
            		info = "il Device " + deviceName + " non è una luce o non è stata trovata";
            		return info;
            	}
            	
            	else if(isDeviceOn()==1)  {
            		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.lightLuminosity, -20);
            		//dto.setLightLuminosityByDeviceName(this, -20);
            		info = "La luce " + deviceName + " situata in "+ location + " ha diminuito la sua luminosità ";
            		return info;
            	}
            	else if(isDeviceOn()==-1)  {
            		
            		
            		info = "La luce " + deviceName + " è rotta ";
            		return info;
            	}
            	else {
            		info = "La luce " + deviceName + " situata in "+ location + " è spenta ";
            		return info;
            		
            	}
            	
            	
            	
            case StaticValue.EnableEnergySaving: 
            	if (getEnergySaving() == 0) {
            		
            		numEnergySaving+=1;
            		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.energySaving, 1);
            		//dto.setEnergySavingByDeviceName(deviceName, 1);
            		info = "Il device " + deviceName + " situata in "+ location + " ha attivato la modalità risparmio ";
            		
            		switch(deviceType) {
            		
            		case StaticValue.luce: 
            			int value = (int) deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.lightLuminosity);
            			int luminosity = 20 - value;
            			deviceDao.setColumnValueByDeviceName(luminosity, StaticValue.lightLuminosity, luminosity);
            			//int luminosity = 20 - dto.getLightLuminosityByDeviceName(this);
            			//dto.setLightLuminosityByDeviceName(this, luminosity);
            			
            			//if (dto.getPresenceByLocation(location)==0)
            			int presence = deviceDao.getPresenceByLocation(location);
            			int onOff = deviceDao.getonOffByLocation(location);
            			//if (dto.getPresenceByLocation(location)==0)
                		if(presence == 0 && onOff == 1)
            				this.command(StaticValue.Off);
            			
                		info+= "Il device " + deviceName + "ha diminuito la sua luminosità a ";
            			//info+= "Il device " + deviceName + "ha diminuito la sua luminosità a:  " + dto.getLightLuminosityByDeviceName(this);
                		return info;
            			//'?? altro?
            			// si spengono quando escono, farlo quando hai fatto sensore di presenza 
            		
            		
            		case StaticValue.termostato:
            			 Random random = new Random();
            			 
            			
            			temperature = random.nextInt(41);
            			int t =0;
            			int temperatureSet = (int) deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.setTemperature);
            			loggerApplication.info(" " + temperature);
            			
            			if (temperature < 9)
            			{
            				t = 16 - temperatureSet ;
            				//int t = 16 - dto.getSetTemperatureByDeviceName(this) ;
            			}
            			
            			else if (temperature < 18 && temperature > 10) {
            				
            				t = temperature + 7 - temperatureSet ;
            				//int t = temperature + 7 - dto.getSetTemperatureByDeviceName(this) ;
            			}
            		
            			else if (temperature < 23 && temperature > 19) {
            				
            				t = temperature + 1 - temperatureSet ;
            				//int t = temperature + 1 - dto.getSetTemperatureByDeviceName(this) ;
            			}
            			
            			else {
            				
            				t = temperature -7 - temperatureSet ;
            				//int t = temperature -7 - dto.getSetTemperatureByDeviceName(this) ;
            			}
            			deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.setTemperature, t);
            			//dto.setTemperatureByDeviceName(this, temperature);
            			return info;
            			
            		
            		
            		}
            			
            		
            	}
            	
            	else if (getEnergySaving() ==1) {
            		info ="Il device " + deviceName + " situata in "+ location + " ha già attivato la modalità risparmio";
            		return info;
            	}
            	
            	else {
            		info = "Il device " + deviceName + " situata in "+ location + " non ha modalità risparmio";
            		return info;
            	}
            
            	
            case StaticValue.DisableEnergySaving: 
            	if (getEnergySaving() == 0) {
            		info = "Il device " + deviceName + " situata in "+ location + " non ha attivato la modalità risparmio";
            		return info;
            	}
            	
            	else if (getEnergySaving() == 1) {
					deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.energySaving, 0);
            		//dto.setEnergySavingByDeviceName(deviceName, 0);
            		info = "Il device " + deviceName + " situata in "+ location + " ha disattivato la modalità risparmio ";
            		
            		switch(deviceType) {
            		
            		case StaticValue.luce: 
            			
            			int luminosity = 30;
            			
            			deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.lightLuminosity, luminosity);
            			//dto.setLightLuminosityByDeviceName(this, luminosity);
            			info +="Il device " + deviceName + "ha impostato la sua luminosità a:  " + deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.lightLuminosity);
            			//info +="Il device " + deviceName + "ha impostato la sua luminosità a:  " + dto.getLightLuminosityByDeviceName(this);
            			
            			return info;
            			//'?? altro?
            			// se spengono quando escono, farlo quando hai fatto sensore di presenza 
            		
            		
            		case StaticValue.termostato:
            			int temperature = 5;
            			deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.setTemperature, temperature);
            			//dto.setTemperatureByDeviceName(this, temperature);
            			return info;
            			
            			//altro?
            		
            		
            		}
            		
            	}
            	
            	
            	else {
            		info =("Il device " + deviceName + " situata in "+ location + " non ha modalità risparmio");
            	}
            
            
            case StaticValue.IncreasedTemperature:
            	
            	if(getDeviceTemperature() == 30 && isDeviceOn() == 1) {
            		info +="Il condizionatore " + deviceName + " situato in "+ location + " è al massimo della sua temperatura";
            		return info;
            	}
            	
            	else if (getDeviceTemperature() == -1) {
            		
            		info+="il Device " + deviceName + " non è un condizionatore o  non è stato trovata el database";
            		return info;
            	}
            	
            	else if (isDeviceOn() == 1){
            		numMaxTemp+=1;
            		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.setTemperature, 2);
            		//dto.setTemperatureByDeviceName(this, 2);
            		info+= "Il condizionatore " + deviceName + " situata in "+ location + " ha aumentato la sua temperatura";
            		return info;
            	}
            	
            	else {
            		
            		info+= "Il condizionatore " + deviceName + " situata in "+ location + " è spento ";
            		return info;
            		
            	}
            	
            	
            	
            	
            case StaticValue.DecreasedTemperature: 
            	if(getDeviceTemperature() == 18 && isDeviceOn()==1) {
            		info ="Il condizionatore " + deviceName + " situata in "+ location + " è al minimo della sua temperatura";
            		return info;
            	}
            	
            	else if (getDeviceTemperature()==-1) {
            		
            		info="il Device " + deviceName + " non è un condizionatore o non è stata trovata";
            		return info;
            	}
            	
            	else if(isDeviceOn()==1)  {
            		numMinTemp+=1;
            		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.setTemperature, -5);
            		//dto.setTemperatureByDeviceName(this, -5);
            		info = "Il device " + deviceName + " situata in "+ location + " ha diminuito la sua temperatura ";
            		return info;
            	}
            	
            	else {
            		info = "Il dispositivo" + deviceName + " situata in "+ location + " è spento ";
            		return info;
            		
            	}
            	
            case StaticValue.AddPresence:
            	
            	 Random random = new Random();
            	 
            	 if((int)deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.Presence) == 0)
            		 deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.Presence, 1);
            	 
            	//if(dto.getPresenceByDeviceName(deviceName) == 0)
            		//dto.setPresenceByDeviceName(deviceName,1);
            	break;
            	 
            case StaticValue.RemovePresence:
            	
            	if((int) deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.Presence) == 1)
           	 		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.Presence, 0);
           	 	
           	 	/*if(dto.getPresenceByDeviceName(deviceName) == 1) {
           		 dto.setPresenceByDeviceName(deviceName,0);*/
            	
           	 	
           		 
           		 
           	 	break;
           	 
            	
            	
            	
        }
        
        return null;

    }



	/*@Override
    public Object Query(String command) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Query'");
    }*/

    
    public void calculateParameters() throws SQLException {
    	if(deviceType.equalsIgnoreCase(StaticValue.luce) || deviceType.equalsIgnoreCase(StaticValue.termostato)) {
			  int eSaving = getEnergySaving();
			
			   double kilowattOra = (eSaving == 1) ? 0.05  : 0.10;
			   double end = (double)deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.endTime);
			   double start = (double)deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.StartTime);
				double durata = (end-start) * 1000;
				//long durata = (dto.getEndTime(deviceName)-dto.getStartTime(deviceName));
				
				expenses += durata * kilowattOra*0.25;
				loggerApplication.info("expenses: " + expenses);	
			
		}
    	
    	loggerApplication.info("expenses: " + expenses);
    	
    	
    	
    }
       @Override
    public Parameter getParameters() {
    	   
    	   
    	   
    	   parameter.setUsageCostDaily(expenses);
    	   parameter.setNum_broken(num_broken);
    	   parameter.setOff(numOff);
    	   parameter.setOn(numOff);
    	   parameter.setEnergySaving(numEnergySaving);
    	   parameter.setEnergySaving(numEnergySaving);
    	   parameter.setMaxLuminosity(numMaxLuminosity);
    	   parameter.setMinLuminosity(numMinLuminosity);
    	   parameter.setMinTemp(numMinTemp);
    	   parameter.setMaxTemp(numMaxTemp);
    	   
    	   return parameter;
    	   
		
    	   
	}
       

   public int isDeviceOn() throws SQLException {
    	
    	return (int)deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.onOff);
        //return dto.getOnOffByDeviceName(deviceName);
    }
    int getDeviceLuminosity() throws SQLException {
    	
    	return (int)deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.lightLuminosity);
    	//return dto.getLightLuminosityByDeviceName(this);
    }
    
    int getEnergySaving() throws SQLException {
    	return (int)deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.energySaving);
    	//return dto.getEnergySavingByDeviceName(this);
    }

    int getDeviceTemperature() throws SQLException {
    	return (int)deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.setTemperature);
    	//return dto.getSetTemperatureByDeviceName(this);
    }
    
    public void fixDevice(int deviceName) throws SQLException {
    	
    	deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.onOff, 0);
    	
    	//dto.setOnOffByDeviceName(deviceName, 0);
    	
    }


	public void setDeviceOn(int deviceName, int b) throws SQLException {
		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.onOff, b);
		
		
	}


	public void setDeviceLuminosity(int deviceName, int i) throws SQLException {
		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.lightLuminosity, i);
		
		
	}


	public void setEnergySaving(int deviceName, int i) throws SQLException {
		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.energySaving, i);
		
	}


	public void setDeviceTemperature(int deviceName, int i) throws SQLException {
		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.setTemperature, i);
		
	}


	public void setPresence(int deviceName, int i) throws SQLException {
		deviceDao.setColumnValueByDeviceName(deviceName, StaticValue.Presence, i);
		
	}


	public Object getPresence() throws SQLException {
		return deviceDao.getColumnValueByDeviceName(deviceName, StaticValue.Presence);
	}
    

    
}
