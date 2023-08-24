

	import java.sql.Connection;
	import org.apache.commons.dbcp2.BasicDataSource;
	import org.slf4j.Logger;
	import org.slf4j.LoggerFactory;




	public class ConnectionManager {

		private static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");

	//	
		private static ConnectionManager instance = null;
		private static BasicDataSource connectionPool = null;
		
		private ConnectionManager() {}

		public static ConnectionManager getInstance() throws Exception {
			
			if (instance == null) {
				
				instance = new ConnectionManager();
				initConnectionPool();

			}
			return instance;
		}
		
		public Connection getConnection() throws Exception{
			return connectionPool.getConnection();
		}
		
		private static void initConnectionPool() throws Exception {

			try {
				
				int maxConnections = 30;

				loggerApplication.info("Creating connection pool (" + maxConnections + " connections) - START");
				
			
				connectionPool = new BasicDataSource();
			
//				
				connectionPool.setUsername("smart");
				connectionPool.setPassword("test");
				connectionPool.setDriverClassName("org.sqlite.JDBC");
				
				
				connectionPool.setUrl("jdbc:sqlite:/Users/kepler/Desktop/EclipseWS/SmartHouse/DB/SmartHouseDB");
				connectionPool.setInitialSize(5);
				connectionPool.setMaxTotal(maxConnections);
				connectionPool.setDefaultAutoCommit(false);
				
				
				
				loggerApplication.info("Creating connection pool - STOP");

			} catch (Exception e) {
				loggerApplication.error(e.getMessage(), e);
				throw e;
			}

		}


	}

