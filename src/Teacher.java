import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Teacher {

    private int id;
    private String surName;
    private String lastName;
    private String email;

    public Teacher(int id, String surName, String lastName, String email) {
        this.id = id;
        this.surName = surName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     *
     * @param option  = defines the columns to be listed
     *                    default=all column
     *                    1=surname,lastname,email
     *                    2=id,surname,lastname
     * @return boolean  = if display was successful
     *                    true= if it could display the teachers
     *                    false= if it could not display it
     */
    public static boolean readTeachers(int option) {
        DataAccess dataAccess=ReadSchoolDatabase.init();
        boolean success=false;
        if (dataAccess==null){
            success=false;
        }
        try {
            List<Teacher> teachers=getAllRowsTeachers(dataAccess);
            if (teachers.size()==0){
                System.out.println("There are no teachers in the database! Please enter first teacher data!");
                System.out.println();
                success=false;

            }else {
                displayRowsTeachers(teachers,option);
                success= true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                dataAccess.getConnection().rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
        finally {
            try {
                dataAccess.getConnection().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();

            }
            ReadSchoolDatabase.stop();
            return success;
        }
    }

    /**
     *
     * @param dataAccess  the opened connection object
     * @return            it returns a List object containing all teacher data from the teacher table
     * @throws SQLException  throws exception if the connection is broken
     */
    public static List<Teacher> getAllRowsTeachers(DataAccess dataAccess) throws SQLException {
        String sql = "SELECT * FROM teachers order by lastname,surname";

        try {
            Connection connection = dataAccess.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Teacher> list = new ArrayList<>();

            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String surname  = resultSet.getString("surname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");

                list.add(new Teacher(id,surname,lastname,email));
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


    /**
     *
     * @param list
     * @param option defines the columns to be listed
     *               default=all column
     *               1=surname,lastname,email
     *               2=id,surname,lastname
     */
    static public void displayRowsTeachers(List<Teacher> list,int option) {
        System.out.println("*************************************************************************");
        System.out.println("*                       List of all teachers                            *");
        System.out.println("*************************************************************************");
        String formattedId="";
        for(Teacher item : list) {

            formattedId=String.valueOf(item.id);
            switch (option){
                case 1:
                    System.out.println("  "+
                            ListFormat.formatMyString(15,item.surName)+
                            "  "+ListFormat.formatMyString(20,item.lastName)+"  "+
                            ListFormat.formatMyString(30,item.email));
                    break;
                case 2:
                    System.out.println("ID: "+ListFormat.formatMyString(3,formattedId)+"    "+
                            ListFormat.formatMyString(15,item.surName)+
                            "  "+ListFormat.formatMyString(20,item.lastName));
                    break;
                default:
                    System.out.println("ID: "+ListFormat.formatMyString(3,formattedId)+"    "+
                            ListFormat.formatMyString(15,item.surName)+
                            "  "+ListFormat.formatMyString(20,item.lastName)+"  "+
                            ListFormat.formatMyString(30,item.email));
                    break;
            }
        }
        System.out.println();
        System.out.println();

    }

//*********************************************getter, setter, to string***************************
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", surName='" + surName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
