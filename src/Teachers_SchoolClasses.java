import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Teachers_SchoolClasses {
    private int fk_schoolClasses_id;
    private String schoolClassName;
    private int fk_teachers_id;
    private String teacherSurName;
    private String teacherLastName;
    private int fk_subjects_id;
    private String subjectName;

    public Teachers_SchoolClasses(int fk_schoolClasses_id, String schoolClassName, int fk_teachers_id,
                   String teacherSurName, String teacherLastName, int fk_subjects_id, String subjectName) {
        this.fk_schoolClasses_id = fk_schoolClasses_id;
        this.schoolClassName = schoolClassName;
        this.fk_teachers_id = fk_teachers_id;
        this.teacherSurName = teacherSurName;
        this.teacherLastName = teacherLastName;
        this.fk_subjects_id = fk_subjects_id;
        this.subjectName = subjectName;
    }

    public static void readTeachers_SchoolClasses() {
        DataAccess dataAccess=ReadSchoolDatabase.init();
        if (dataAccess==null){
            return;
        }
        try {
            List<Teachers_SchoolClasses> teachers_SchoolClasses=getAllRowsTeachers_SchoolClasses(dataAccess);
            if (teachers_SchoolClasses==null){

                ReadSchoolDatabase.stop();
                return;
            }else {
                displayRowsTeachers_SchoolClasses(teachers_SchoolClasses);
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

    public static List<Teachers_SchoolClasses> getAllRowsTeachers_SchoolClasses(DataAccess dataAccess) throws SQLException {
        String sql = "SELECT teachers_schoolclasses.fk_schoolclasses_id," +
                "schoolclasses.name,teachers_schoolclasses.fk_teachers_id,teachers.surname,teachers.lastname," +
                " teachers_schoolclasses.fk_subjects_id,subjects.name FROM teachers_schoolclasses " +
                "inner join schoolclasses on teachers_schoolclasses.fk_schoolclasses_id=schoolclasses.id " +
                "inner join teachers on teachers_schoolclasses.fk_teachers_id=teachers.id " +
                "inner join subjects on teachers_schoolclasses.fk_subjects_id=subjects.id " +
                "order by teachers.lastname,teachers.surname,schoolclasses.name";

        try {
            Connection connection = dataAccess.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Teachers_SchoolClasses> list = new ArrayList<>();
            int schoolClassId;
            String schoolClassName;
            int teacherId;
            String teacherSurName;
            String teacherLastName;
            int subjectId;
            String subjectName;
            while(resultSet.next()) {
                schoolClassId = resultSet.getInt("teachers_schoolclasses.fk_schoolclasses_id");
                schoolClassName  = resultSet.getString("schoolclasses.name");
                teacherId = resultSet.getInt("teachers_schoolclasses.fk_teachers_id");
                teacherSurName  = resultSet.getString("teachers.surname");
                teacherLastName  = resultSet.getString("teachers.lastname");
                subjectId = resultSet.getInt("teachers_schoolclasses.fk_subjects_id");
                subjectName  = resultSet.getString("subjects.name");

                list.add(new Teachers_SchoolClasses(schoolClassId,schoolClassName,teacherId,
                        teacherSurName,teacherLastName,subjectId,subjectName));
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



    static public void displayRowsTeachers_SchoolClasses(List<Teachers_SchoolClasses> list) {
        System.out.println("*****************************************************************************");
        System.out.println("*                List of classes and its teachers                            *");
        System.out.println("*****************************************************************************");
        String surName="";
        String lastName="";
        for(Teachers_SchoolClasses item : list) {
            if (!surName.equals(item.teacherSurName) || !lastName.equals(item.teacherLastName)) {
                System.out.println();
                surName = item.teacherSurName;
                lastName = item.teacherLastName;
                System.out.println(" " +  surName +"  " + lastName);
            }
            System.out.println("                 "+item.schoolClassName);
        }
        System.out.println();
        System.out.println();

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

    public int getFk_teachers_id() {
        return fk_teachers_id;
    }

    public void setFk_teachers_id(int fk_teachers_id) {
        this.fk_teachers_id = fk_teachers_id;
    }

    public String getTeacherSurName() {
        return teacherSurName;
    }

    public void setTeacherSurName(String teacherSurName) {
        this.teacherSurName = teacherSurName;
    }

    public String getTeacherLastName() {
        return teacherLastName;
    }

    public void setTeacherLastName(String teacherLastName) {
        this.teacherLastName = teacherLastName;
    }

    public int getFk_subjects_id() {
        return fk_subjects_id;
    }

    public void setFk_subjects_id(int fk_subjects_id) {
        this.fk_subjects_id = fk_subjects_id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "Teachers_SchoolClasses{" +
                "fk_schoolClasses_id=" + fk_schoolClasses_id +
                ", schoolClassName='" + schoolClassName + '\'' +
                ", fk_teachers_id=" + fk_teachers_id +
                ", teacherSurName='" + teacherSurName + '\'' +
                ", teacherLastName='" + teacherLastName + '\'' +
                ", fk_subjects_id=" + fk_subjects_id +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
