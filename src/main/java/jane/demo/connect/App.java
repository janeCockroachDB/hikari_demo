package jane.demo.connect;

import org.postgresql.util.PSQLException;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class App 
{
    public static void main( String[] args ) throws InterruptedException {
        System.out.println("Please start a cockroach server with `cockroach start-single-node --advertise-addr 'localhost' --insecure`" );
        // To set drain_wait and query_wait, do
        // cockroach sql --url="postgresql://root@Janes-MacBook-Pro.local:26257?sslmode=disable" -e " SET CLUSTER SETTING server.shutdown.drain_wait = '10s';"
        // and
        // cockroach sql --url="postgresql://root@Janes-MacBook-Pro.local:26257?sslmode=disable" -e " SET CLUSTER SETTING server.shutdown.query_wait = '30s';"
        try {
            DataSource.printConnectionPool();
            fetchData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        System.out.println("\n");
    }

    public static void printResultSet(ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(" | ");
                String columnValue = resultSet.getString(i);
                System.out.print(rsmd.getColumnName(i) + ": " + columnValue );
            }
            System.out.println("");
        }
    }

    public static void fetchData() throws SQLException, InterruptedException {
            int i = 0;
            while (true) {
                Connection con = DataSource.getConnection();
                System.out.printf("pls %d th input the query:", i);
                Scanner scanner = new Scanner(System.in);
                String inputQuery = scanner.nextLine();
                //inputQuery = "select 1;";
                // Example: SELECT 1;
//                String inputQuery = "select 1;";
                if (inputQuery.equals("\\q")) break;
                PreparedStatement pst = con.prepareStatement( inputQuery );
                printCurrentTime();
                System.out.printf("QUERY: %s", inputQuery);

                try {
                    ResultSet rs = pst.executeQuery();
                    printResultSet(rs);
                } catch (SQLException e) {
                    System.out.println(e);
                }

                i++;
                con.close();
                int secsleepTime = 600;
                System.out.printf("\nsleep fpr %d milliseconds\n", secsleepTime);
                TimeUnit.MILLISECONDS.sleep(secsleepTime);
            }
    }
}


