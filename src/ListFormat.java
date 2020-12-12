

public class ListFormat {

    /**
     * method formatMyString makes the string for given length for your lists
     * if the string is shorter than the length in the argument, than some empty char will be added
     * if it is shorter, the end of string will be cut away
     * @param length
     * @param myString
     * @return
     */
    public static String formatMyString(int length,String myString){
        String x="";
        for (int n=0;n<length;n++){
            x=x+" ";
        }
        String formattedString=myString+x; //field to help formatting name  20 char long in the list
        formattedString=formattedString.substring(0,length-1);

        return formattedString;
    }



}
