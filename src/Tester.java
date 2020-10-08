import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Tester {
    private static final ArrayList<String> all= new ArrayList<>();
    private static final ArrayList<String> weight= new ArrayList<>();
    private static final ArrayList<String> number = new ArrayList<>();
    private static final ArrayList<String> rowX = new ArrayList<>();
    private static final ArrayList<String> rowY = new ArrayList<>();
    private static String[] array;

    public static void readFile(String filePath) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath)); //字元輸入流化
            try {
                String data;
                while ((data = br.readLine()) != null) { //迴圈的讀取一行一行讀取檔案，直到最後一行為空停止
                    array = data.split("\\s+");
                    Collections.addAll(all, array); // 遍歷array的值存進all
                }
                br.close(); //關閉輸入流
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void parseData(){
        String row = all.get(0); // 總列數
        String col = all.get(1); // 總行數
        for(int  i = 2; i <= Integer.parseInt(col)+1; i++){
            weight.add(all.get(i)); // 權重
        }
        for(int i = Integer.parseInt(col)+2; i < all.size(); i++){
            number.add(all.get(i));
        }
        for (int i = 0; i < number.size(); i++){
            if(i % 2 == 0){
                rowX.add(number.get(i)); // X項
            }else {
                rowY.add(number.get(i)); // Y項
            }
        }
        System.out.print(row);
        System.out.print(col);
        System.out.print(weight);
        System.out.print(rowX);
        System.out.print(rowY);
    }

    public static void main(String[] args) {
        readFile("C:\\Users\\USER\\Desktop\\bench\\bench1.txt");
        parseData();
    }
}
