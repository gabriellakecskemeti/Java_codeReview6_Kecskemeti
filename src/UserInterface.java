
import java.util.Scanner;

public class UserInterface {
    public UserInterface mainMenu(UserInterface userInterface) {
        printHeader();

        int selection; //= -1;

        do {
            printMenu();

            selection=enterSelection();

            switch (selection) {
                case 1:
                    //method to show all students
                    Student.readStudents();
                    return userInterface.mainMenu(userInterface);

                case 2:
                    //method to show all teachers
                    Teacher.readTeachers(1);
                    return userInterface.mainMenu(userInterface);
                case 3:
                    //method to show all classes
                    SchoolClass.readSchoolClasses();
                    return userInterface.mainMenu(userInterface);
                case 4:
                    //Display classes of a teacher
                    ClassesOfOneTeacher.ClassesOfTeacher();
                    return userInterface.mainMenu(userInterface);
                case 5:
                    //Create Data Report to external file
                    ReportToFile.readDataBaseInfo();
                    return userInterface.mainMenu(userInterface);
                case 0:
                    return userInterface;    //it would be also good to  give back a null.
                default:
                    System.out.println("The selection was invalid!");
            }
        } while (selection != 0);
        return userInterface;
    }





    public int enterSelection(){
        Scanner scanner= new Scanner(System.in);
        int selection = -1;
        boolean exit=false;

        while(!exit ) {
            System.out.println("Insert selection: ");
            try {
                selection = Integer.parseInt(scanner.nextLine());
                exit=true;
            } catch (Exception e) {
                System.out.println("Please Enter one of the menu options, only numbers are allowed!");
            }
        }
        return selection;
    }


    public void printHeader() {
        System.out.println(" ---------- ---------------- ");
        System.out.println("|          WELCOME          |");
        System.out.println("|       to the School       |");
        System.out.println(" --------------------------- ");
    }

    public void printMenu() {

        System.out.println("1.Display all students");
        System.out.println("2.Display all teachers");
        System.out.println("3.Display all classes");
        System.out.println("4.Display classes of a teacher");
        System.out.println("5.Create Data Report to external file");
        System.out.println("0.Exit");
    }



}
