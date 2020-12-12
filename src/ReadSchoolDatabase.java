

public class ReadSchoolDatabase {

    static DataAccess dataAccess;

    static public DataAccess init() {
        try {
            dataAccess = new DataAccess();
            return dataAccess;
        } catch (Exception e) {
            System.err.println("The Data Base is not available");
            System.out.println();
            //e.printStackTrace();
        }
        return null;
    }

    static public void stop() {
        try {
            dataAccess.closeDb();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
