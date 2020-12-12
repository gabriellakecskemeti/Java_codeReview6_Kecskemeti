import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ReportToFile {

    /**
     * asks about path and file name for the generated report
     * connects the database
     * opens information schema to get table names
     * calls getTableItems to get the items from the table
     * writes all items of all tables to the given path and file name
     */
    public static void readDataBaseInfo() {


        Scanner scanner = new Scanner(System.in);
        String nameOfFile="\\CR7_Gabriella_Report.txt";
        String path="C:\\Test";
        String nameOfFileNew="\\CR7_Gabriella_Report.txt";
        String pathNew= "C:\\Test";
        String pathAndFileName=path+nameOfFile;
        System.out.println("the report will be generated: "+path);

        String choice = askingYesOrNo("Do you want to change this? Y/N");
        boolean exit=false;
        if(choice.toUpperCase().equals("Y")){
            exit=false;
        }else{
            exit=true;
        }
        File file1= new File("");

        while(!exit) {
            file1 = new File(path);

            System.out.println("Old Path: " + path + "   Please enter new Path: ");
            pathNew = scanner.nextLine();

            file1 = new File(pathNew);
            if (file1.exists()) {
                    exit = true;
                    System.out.println("Filepath is accepted!" + pathNew);
            } else {
                if(pathNew.equals("")){
                    System.out.println("Empty file path, process finished!");
                    System.out.println();
                    return;
                }
                    exit = false;
                    System.out.println("NOT existing filepath!" + pathNew);
            }

        }
        System.out.println("the report will be generated to this file: "+nameOfFile.substring(1));
        exit=false;
        choice = askingYesOrNo("Do you want to change this? Y/N");
        exit=false;
        if(choice.toUpperCase().equals("Y")){
            exit=false;
        }else{
            nameOfFileNew=nameOfFile;
            exit=true;
        }

        while(!exit) {
                if (choice.toUpperCase().equals("Y")) {
                    System.out.println("Old File name: " + nameOfFile.substring(1) + "   Please enter new file name, if you let it empty, I keep the old one: ");
                    nameOfFileNew = scanner.nextLine();

                    if (nameOfFileNew.isEmpty()) {
                        exit = true;
                        System.out.println("Empty fileName! I keep the old one!");
                        nameOfFileNew=nameOfFile;
                    } else {
                        exit = true;
                        System.out.println("New file name:  " + nameOfFileNew);
                        nameOfFileNew="\\"+nameOfFileNew;

                    }
                }
        }
        pathAndFileName=pathNew+nameOfFileNew;
        Connection con;
        String tableName = "";
        int numberOfItems;

        try {
            FileWriter file = new FileWriter(pathAndFileName);

            Class.forName("com.mysql.cj.jdbc.Driver");

            String url = "jdbc:mysql://localhost:3306/information_schema?useTimezone=true&serverTimezone=UTC";
            con = DriverManager.getConnection(
                    url,"root", "");
            con.setAutoCommit(false);
            con.setReadOnly(false);
            Statement stmt = con.createStatement();

            String query = "SELECT table_name, table_rows FROM INFORMATION_SCHEMA.TABLES where TABLE_SCHEMA = 'cr7_gabriella'";
            ResultSet rs = stmt.executeQuery(query);
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            file.write("\n" +"Content of Data Base cr7_gabriella"+System.lineSeparator());
            while(rs.next()) {
                file.write("\n" + "-------------------------------------------------------------" + System.lineSeparator());
                file.write( " Table name: " + rs.getString(1) + " Number of items: " + rs.getString(2) + System.lineSeparator());
                file.write( "-------------------------------------------------------------" + System.lineSeparator());
                tableName = rs.getString(1);
                numberOfItems=Integer.valueOf(rs.getString(2));
                ArrayList<String> fileContent=new ArrayList<>();
                fileContent=null;
                //calling getTableItems if we have more than one item
                if(numberOfItems>0) {
                    fileContent = getTableItems(tableName);
                    //write items to the file
                    for (String element:fileContent) {
                        file.write(element+ System.lineSeparator());
                    }
                }
            }
            con.commit();
            con.close();
            file.close();
            System.out.println("\nSuccessfully wrote report to: "+pathAndFileName);
            System.out.println();
        } catch (IOException e) {
            System.out.println("An error occurred during writing into the file.");
            e.printStackTrace();
            return;
        } catch (Exception e){
            System.out.println("The database is not available! Check your environment and start again!");
            //e.printStackTrace();
            return;
        }
    }

    /**
     *
     * @param tableName  the name of the table from where we want to get the items
     * @return an ArrayList containing String values of the items of the table
     *
     * Accepted only String or int type field
     * it could be expanded.
     */
    static public ArrayList<String> getTableItems(String tableName){
        DataAccess dataAccess=ReadSchoolDatabase.init();
        if (dataAccess==null){
            return null;
        }
        ArrayList<String> filecontent=new ArrayList<>();
        try {
            String meta = "SELECT * FROM "+tableName;
            PreparedStatement pstm1 = dataAccess.getConnection().prepareStatement(meta);

            ResultSet rs2 = pstm1.executeQuery(meta);
            ResultSetMetaData resultSetMetaData = rs2.getMetaData();

            String column1 ="";
            String column1Type="";
            int column1length=0;
            int numberOfColumns=resultSetMetaData.getColumnCount();

            while(rs2.next()) {
                String myString="";
                for(int i=1;i<=numberOfColumns;i++){
                    column1 =resultSetMetaData.getColumnName(i);
                    column1Type= resultSetMetaData.getColumnTypeName(i);
                    column1length=resultSetMetaData.getPrecision(i);

                    if(resultSetMetaData.getColumnType(i)==4){
                        myString=myString+column1+": "+String.valueOf(rs2.getInt(i));
                    }else {
                        myString=myString+column1+": "+rs2.getString(i);
                    }
                    if(i<numberOfColumns){
                        myString=myString+",  ";
                    }
                }
                filecontent.add(myString);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                dataAccess.getConnection().commit();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            ReadSchoolDatabase.stop();
            return filecontent;
        }
    }

    /**
     *
     * @param question this is the text,the question to answer with yes or no
     * @return it gives back Y or N in form of a String object
     */
    public static String askingYesOrNo(String question){
        String choice="";
        Scanner scanner = new Scanner(System.in);

        boolean exit=false;
        while (!exit){
            System.out.println(question);
            choice=scanner.nextLine();
            if (choice.toUpperCase().equals("N") || choice.toUpperCase().equals("Y")  ){
                exit=true;
            }else{
                System.out.println("Wrong input, can only be Y/N.");
            }
        }
        return choice.toUpperCase();
    }


}
