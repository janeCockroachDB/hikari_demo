package jane.demo.connect;

import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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

    public static void fetchData() throws SQLException {
        try (Connection con = DataSource.getConnection();
             ) {
            int i = 0;
            while (true) {
                System.out.printf("pls %d th input the query:", i);
                Scanner scanner = new Scanner(System.in);
                String inputQuery = scanner.nextLine();
                if (inputQuery.equals("\\q")) break;
                PreparedStatement pst = con.prepareStatement( inputQuery );
                ResultSet rs = pst.executeQuery();
                printResultSet(rs);
                i++;
                int sleepTime = 1;
                System.out.printf("\nsleep fpr %d seconds\n", sleepTime);
                TimeUnit.SECONDS.sleep(sleepTime);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


