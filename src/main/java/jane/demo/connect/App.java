package jane.demo.connect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        System.out.println( "Hello World jane!" );
        try {
            fetchData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchData() throws SQLException {
        String SQL_QUERY = "select * from emp";
        try (Connection con = DataSource.getConnection();
             PreparedStatement pst = con.prepareStatement( SQL_QUERY );
             ResultSet rs = pst.executeQuery();) {
            while ( rs.next() ) {
                System.out.printf("empno: %d", rs.getInt( "empno" ));
                System.out.printf("ename: %s", rs.getString( "ename" ));
                System.out.printf("job: %s", rs.getString( "job" ));
                System.out.printf("mgr: %s", rs.getInt( "mgr" ));
                System.out.printf("sal: %s", rs.getInt( "sal" ));
                System.out.println("--------------------");
            }
        }
    }
}


