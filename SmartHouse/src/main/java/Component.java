import java.sql.SQLException;
import java.util.List;

public interface Component {

    String command(String command) throws SQLException;
    //Object Query(String command);
    Parameter getParameters();

}