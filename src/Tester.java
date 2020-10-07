import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Tester {
    private static final ArrayList<String> size = new ArrayList<>();
    private static final ArrayList<String> weight = new ArrayList<>();
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
                int line = 0; // 計算行數
                while ((data = br.readLine()) != null) { //迴圈的讀取一行一行讀取檔案，直到最後一行為空停止
                    array = data.split("\\s+");
                    ++line; // 計算行數 每印一行 行數就加一
                    System.out.print(Arrays.toString(array));
                }

                size.add(array[0]); // 第一行是表格大小
                weight.add(array[1]); // 第二行是權重
//                for(int i=2; i<line; i++){
//                    number.add(array[i]);
//                }

                System.out.print(size);
                System.out.print(weight);
//                System.out.print(number);
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

    public static void main(String[] args) {
        readFile("C:\\Users\\USER\\Desktop\\bench\\bench1.txt");
    }
}
