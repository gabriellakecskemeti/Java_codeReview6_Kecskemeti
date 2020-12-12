import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SchoolClass {
    private int id;
    private String name;

    public SchoolClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static void readSchoolClasses() {
        DataAccess dataAccess=ReadSchoolDatabase.init();
        if (dataAccess==null){
            return;
        }
        try {
            List<SchoolClass> schoolClasses=getAllRowsSchoolClasses(dataAccess);

            if (schoolClasses.size()==0){
                System.out.println("There are no  school classes in the database.");
                System.out.println();
                ReadSchoolDatabase.stop();
                return;
            }else {
                displayRowsSchoolClasses(schoolClasses);
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                dataAccess.getConnection().rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        } finally {
            try {
                dataAccess.getConnection().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ReadSchoolDatabase.stop();
        }
    }

    public static List<SchoolClass> getAllRowsSchoolClasses(DataAccess dataAccess) throws SQLException {
        String sql = "SELECT * FROM schoolclasses order by name";

        try {
            Connection connection = dataAccess.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SchoolClass> list = new ArrayList<>();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name  = resultSet.getString("name");


                list.add(new SchoolClass(id,name));
            }
            connection.commit();
            preparedStatement.close();
            return list;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }



    static public void displayRowsSchoolClasses(List<SchoolClass> list) {
        System.out.println("*************************");
        System.out.println("*  List of all classes  *");
        System.out.println("*************************");
        String formattedId="";
        for(SchoolClass item : list) {

            formattedId=String.valueOf(item.id);

            System.out.println("  ID: "+ListFormat.formatMyString(3,formattedId)+"   Name: "+ListFormat.formatMyString(15,item.name));
                     }
        System.out.println();
        System.out.println();

    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "SchoolClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
