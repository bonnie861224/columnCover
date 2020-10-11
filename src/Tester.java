import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Tester {
    private static final ArrayList<String> all= new ArrayList<>();
    private static final ArrayList<String> weight= new ArrayList<>();
    private static final ArrayList<String> number = new ArrayList<>();
    private static final ArrayList<String> rowX = new ArrayList<>();
    private static final ArrayList<String> colY = new ArrayList<>();
    private static final ArrayList<Integer>tmpArr = new ArrayList<>();
    private static int[] arr;
    private static String row;
    private static String col;

    public static void main(String[] args) {
        readFile("C:\\Users\\USER\\Desktop\\bench\\bench1.txt");
        parseData();
        checkCovering();
        for( int i = 0; i < Integer.parseInt(col); i++){
            combine(0 ,i+1); // 列出所有取法
        }
    }

    public static void readFile(String filePath) { // 讀檔案
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath)); //字元輸入流化
            try {
                String data;
                while ((data = br.readLine()) != null) { //迴圈的讀取一行一行讀取檔案，直到最後一行為空停止
                    String[] split_arr = data.split("\\s+");
                    Collections.addAll(all, split_arr); // 遍歷array的值存進all
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

    public static void parseData(){ // 拆解資料
        row = all.get(0); // 總列數
        col = all.get(1); // 總行數

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
                colY.add(number.get(i)); // Y項
            }
        }
    }

    public static void checkCovering(){ // 建立表格
        int[][] check = new int[Integer.parseInt(row)][Integer.parseInt(col)]; // 初值皆為0
        for(int i = 0; i < rowX.size(); i++){
            int x = Integer.parseInt(rowX.get(i));
            int y = Integer.parseInt(colY.get(i));
            check[x-1][y-1] = 1; // 將表格內是1的標示出來
        }
    }

    public static void combine(int index, int k) { // 計算所有取法
        int[] arr = new int[Integer.parseInt(col)];
        for(int i = 0; i < Integer.parseInt(col); i++){
            arr[i] = i+1;
        }

        if(k == 1){
            for (int i = index; i < arr.length; i++) {
                tmpArr.add(arr[i]);
                System.out.println(tmpArr.toString());
                tmpArr.remove((Object) arr[i]);
            }
        }else if(k > 1){
            for (int i = index; i <= arr.length - k; i++) {
                tmpArr.add(arr[i]);
                combine(i + 1,k - 1);
                tmpArr.remove((Object) arr[i]);
            }
        }else{
            return;
        }
    }
}