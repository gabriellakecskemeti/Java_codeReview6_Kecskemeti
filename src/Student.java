import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Student {
    private int id;
    private String surName;
    private String lastName;
    private String email;
    private int fk_schoolClasses_id;
    private String schoolClassName;

    public Student(int id, String surName, String lastName, String email, int fk_schoolclasses_id,String schoolClassName) {
        this.id = id;
        this.surName = surName;
        this.lastName = lastName;
        this.email = email;
        this.fk_schoolClasses_id = fk_schoolclasses_id;
        this.schoolClassName=schoolClassName;
    }

    public static void readStudents() {
        DataAccess dataAccess=ReadSchoolDatabase.init();
        if (dataAccess==null){
            return;
        }
        try {
            List<Student> students=getAllRowsStudents(dataAccess);
            if (students.size()==0){
                System.out.println("There are no students in the database.");
                System.out.println();
                ReadSchoolDatabase.stop();
                return;
            }else {
                displayRowsStudents(students);
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

    public static List<Student> getAllRowsStudents(DataAccess dataAccess) throws SQLException {
        String sql = "SELECT students.id,students.surname,students.lastname, students.email,students.fk_schoolclasses_id, " +
                "schoolclasses.name FROM students " +
                "inner join schoolclasses on students.fk_schoolclasses_id=schoolclasses.id " +
                "order by students.lastname, students.surname";

        try {
            Connection connection = dataAccess.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Student> list = new ArrayList<>();

            while(resultSet.next()) {
                int id = resultSet.getInt("students.id");
                String surname  = resultSet.getString("students.surname");
                String lastname = resultSet.getString("students.lastname");
                String email = resultSet.getString("students.email");
                int fk_schoolClasses_id = resultSet.getInt("students.fk_schoolclasses_id");

                String schoolClassName  =resultSet.getString("schoolclasses.name");

                list.add(new Student(id,surname,lastname,email,fk_schoolClasses_id,schoolClassName));
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



    static public void displayRowsStudents(List<Student> list) {
        System.out.println("*****************************************************************************");
        System.out.println("*                         List of all students                              *");
        System.out.println("*****************************************************************************");
        //String formattedId="";
        for(Student item : list) {
            //I take off the ID because it was not in the specification an it makes the list not nicer
            //formattedId=String.valueOf(item.id);
            //"ID: "+ListFormat.formatMyString(3,formattedId)+"    "+
            System.out.println(" "+ListFormat.formatMyString(15,item.surName)+
                    "  "+ListFormat.formatMyString(20,item.lastName)+"  "+
                    ListFormat.formatMyString(30,item.email)+"  "+item.schoolClassName);

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

    public int getFk_schoolClasses_id() {
        return fk_schoolClasses_id;
    }

    public void setFk_schoolClasses_id(int fk_schoolClasses_id) {
        this.fk_schoolClasses_id = fk_schoolClasses_id;
    }

    public String getSchoolClassName() {
        return schoolClassName;
    }

    public void setSchoolClassName(String schoolClassName) {
        this.schoolClassName = schoolClassName;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", surName='" + surName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", fk_schoolClasses_id=" + fk_schoolClasses_id +
                ", schoolClassName='" + schoolClassName + '\'' +
                '}';
    }
}
