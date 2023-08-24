import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Dao {
	
	protected Connection connection;
	protected static final Logger loggerApplication = LoggerFactory.getLogger("logApplication");
	
	
	public void init() throws Exception {
		this.connection = ConnectionManager.getInstance().getConnection();
		connection.setAutoCommit(true);
	}

	public void closeConnection() throws Exception {

		if (connection != null) {
			connection.close();
		}
	}


	public boolean commit() {
		try {
//			loggerApplication.info("eseguo commit ");
			connection.commit();
//			loggerApplication.info("commit terminata");
			connection.setAutoCommit(false);
			return true;
		} catch (Exception ex) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e) {
			}
			loggerApplication.error("Transazione fallita", ex);
			return false;
		}
	}

	public boolean rollback() {
		try {
			connection.rollback();
			loggerApplication.info("RollBack eseguita");
			connection.setAutoCommit(false);
			return false;
		} catch (Exception ex) {
			if (connection != null)
				try {
					connection.setAutoCommit(false);
				} catch (SQLException e) {
				}
			loggerApplication.error("transazione fallita", ex);
			return false;
		}

	}
	
}
