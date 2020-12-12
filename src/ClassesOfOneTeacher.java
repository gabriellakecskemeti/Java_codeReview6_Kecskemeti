import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClassesOfOneTeacher {


    public static void ClassesOfTeacher() {
        if (!Teacher.readTeachers(2)){
            return;
        }

        boolean exit=false;
        while(!exit) {
            Teacher teacher = selectTeacher();
            if (teacher == null) {
                return;
            }
            readClassesOfOneTeacher(teacher);
            System.out.println("Choose another teacher, or enter 0 to exit! ");
        }
    }


    public static Teacher selectTeacher() {
        Scanner scanner = new Scanner(System.in);
        int selection = -1;
        boolean exit = false;
        Teacher teacher = null;
        while (!exit) {
            System.out.println("Insert teacher ID: ");
            try {
                selection = Integer.parseInt(scanner.nextLine());
                teacher = checkTeacherId(selection);
                if (teacher != null || (selection == 0)) {
                    if (selection == 0) {
                        teacher = null;
                    }
                    exit = true;
                } else {
                    System.out.println("Not existing teacher Id, try to enter again, or add 0 to exit");
                    exit = false;
                }

            } catch (Exception e) {
                System.out.println("Please Enter one of the user Ids, only numbers are allowed!");
            }
        }
        return teacher;
    }

    public static Teacher checkTeacherId(int selection) {
        boolean result = false;
        DataAccess dataAccess = ReadSchoolDatabase.init();
        Teacher oneTeacher = null;
        try {
            List<Teacher> teachers = Teacher.getAllRowsTeachers(dataAccess);
            for (Teacher item : teachers) {
                if (item.getId() == selection) {
                    oneTeacher = item;
                    ReadSchoolDatabase.stop();
                    return oneTeacher;
                }
            }
            //displayRowsCourses(dataAccess.getAllRowsCourses());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReadSchoolDatabase.stop();
        }
        return oneTeacher;
    }

    public static void readClassesOfOneTeacher(Teacher teacher) {
        DataAccess dataAccess = ReadSchoolDatabase.init();
        if (dataAccess == null) {
            return;
        }
        try {
            List<Teachers_SchoolClasses> classesOfTeacher = getClassesOfTeacher(dataAccess,teacher);
            if (classesOfTeacher == null) {
                ReadSchoolDatabase.stop();
                return;
            } else {
                if (classesOfTeacher.size()<1){
                    System.out.println( teacher.getSurName()+" "+teacher.getLastName()+"  has no classes at the moment.");
                }else {
                    displayClassesOfOneTeacher(classesOfTeacher);
                }
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


    public static List<Teachers_SchoolClasses> getClassesOfTeacher(DataAccess dataAccess,Teacher teacher) throws SQLException {
        String sql = "SELECT teachers_schoolclasses.fk_schoolclasses_id," +
                "schoolclasses.name,teachers_schoolclasses.fk_teachers_id,teachers.surname,teachers.lastname," +
                " teachers_schoolclasses.fk_subjects_id,subjects.name FROM teachers_schoolclasses " +
                "inner join schoolclasses on teachers_schoolclasses.fk_schoolclasses_id=schoolclasses.id " +
                "inner join teachers on teachers_schoolclasses.fk_teachers_id=teachers.id " +
                "inner join subjects on teachers_schoolclasses.fk_subjects_id=subjects.id " +
                "where teachers_schoolclasses.fk_teachers_id=? " +
                "order by teachers.lastname,teachers.surname,schoolclasses.name";

        try {
            Connection connection = dataAccess.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, teacher.getId());
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



    static public void displayClassesOfOneTeacher(List<Teachers_SchoolClasses> list) {

        String surName="";
        String lastName="";
        for(Teachers_SchoolClasses item : list) {
            if (!surName.equals(item.getTeacherSurName()) || !lastName.equals(item.getTeacherLastName())) {
                System.out.println();
                surName = item.getTeacherSurName();
                lastName = item.getTeacherLastName();
                System.out.println(" " +  surName +"  " + lastName);
            }
            System.out.println("                 "+item.getSchoolClassName()+"   "+item.getSubjectName());
        }
        System.out.println();
        System.out.println();

    }


}