import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.stream.IntStream;

public class Tester {
    private static final ArrayList<String> arr_data = new ArrayList<>();
    private static final ArrayList<String> arr_check = new ArrayList<>();
    private static final ArrayList<String> arr_weight = new ArrayList<>();
    private static final ArrayList<String> arr_cost = new ArrayList<>();
    private static final ArrayList<String> arr_number = new ArrayList<>();
    private static final ArrayList<String> arr_rowX = new ArrayList<>();
    private static final ArrayList<String> arr_colY = new ArrayList<>();
    private static final ArrayList<Integer> arr_tmp = new ArrayList<>();
    private static final ArrayList<String> arr_choose = new ArrayList<>();
    private static final ArrayList<ArrayList<String>> tmpList = new ArrayList<>();
    private static final ArrayList<ArrayList<String>> answerList = new ArrayList<>();
    private static int[] arr;
    private static int[][] check;
    private static int[] line;
    private static String srow;
    private static String scol;
    private static int itotalWeight = 0;

    public static void main(String[] args) {
        readFile("C:\\Users\\user\\Desktop\\bench\\bench3.txt");
        parseData();
        buildForm();
        for (int i = 0; i < Integer.parseInt(scol); i++) {
            calculate(0, i + 1); // 列出所有取法
        }
        checkCovering();
    }

    public static void readFile(String filePath) { // 讀檔案
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(filePath)); //字元輸入流化
            try {
                String data;
                while ((data = br.readLine()) != null) { //迴圈的讀取一行一行讀取檔案，直到最後一行為空停止
                    String[] split_arr = data.split("\\s+");
                    Collections.addAll(arr_data, split_arr); // 遍歷array的值存進all
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

    public static void parseData() { // input data的資料拆解
        srow = arr_data.get(0); // 總列數
        scol = arr_data.get(1); // 總行數

        for (int i = 2; i <= Integer.parseInt(scol) + 1; i++) {
            arr_weight.add(arr_data.get(i)); // 權重
        }

        for (int i = Integer.parseInt(scol) + 2; i < arr_data.size(); i++) {
            arr_number.add(arr_data.get(i));
        }

        for (int i = 0; i < arr_number.size(); i++) {
            if (i % 2 == 0) {
                arr_rowX.add(arr_number.get(i)); // X項
            } else {
                arr_colY.add(arr_number.get(i)); // Y項
            }
        }
    }

    public static void buildForm() { // 建立表格
        check = new int[Integer.parseInt(srow)][Integer.parseInt(scol)]; // 初值皆為true
        line = new int[Integer.parseInt(srow)]; // 初值皆為true
        for (int i = 0; i < arr_rowX.size(); i++) {
            int x = Integer.parseInt(arr_rowX.get(i));
            int y = Integer.parseInt(arr_colY.get(i));
            check[x - 1][y - 1] = 1; // 將表格內是1的標示出來
        }
    }

    public static void calculate(int index, int k) { // 計算所有取法
        int[] arr = new int[Integer.parseInt(scol)];
        for (int i = 0; i < Integer.parseInt(scol); i++) {
            arr[i] = i + 1;
        }

        if (k == 1) {
            for (int i = index; i < arr.length; i++) {
                arr_tmp.add(arr[i]);
                arr_choose.addAll(Collections.singleton(arr_tmp.toString()));
                arr_tmp.remove((Object) arr[i]);
            }
        } else if (k > 1) {
            for (int i = index; i <= arr.length - k; i++) {
                arr_tmp.add(arr[i]);
                calculate(i + 1, k - 1);
                arr_tmp.remove((Object) arr[i]);
            }
        } else {
            return;
        }
    }

    public static void checkCovering() {
        for (String s : arr_choose) { // 組合數的資料拆解
            String[] split_arr = s.split("\\[|,+\\s+|]");
            Collections.addAll(arr_check, split_arr);
        }

        for (int i = 0; i < arr_check.size(); i++) { // 去除空白
            if (arr_check.get(i).equals("")) {
                arr_check.remove(i);
            }
        }

        for (int i = Integer.parseInt(scol); i < arr_check.size() - Integer.parseInt(scol); i++) {
            if (Integer.parseInt(arr_check.get(i)) < Integer.parseInt(arr_check.get(i + 1)) ||
                    Integer.parseInt(arr_check.get(i + 1)) > Integer.parseInt(arr_check.get(i + 2))) {
                for (int j = 0; j < Integer.parseInt(srow); j++) {
                    if (check[j][Integer.parseInt(arr_check.get(i)) - 1] +
                            check[j][Integer.parseInt(arr_check.get(i + 1)) - 1] >= 1) {
                        line[j] = 1;
                    }
                    if (!arr_cost.contains(arr_check.get(i)) || !arr_cost.contains(arr_check.get(i + 1))) {
                        arr_cost.add(arr_check.get(i));
                        arr_cost.add(arr_check.get(i + 1));
                    }
                }
                if (IntStream.of(line).allMatch(num -> num == 1)) { // 若全是1代表有column covering
                    LinkedHashSet<String> set = new LinkedHashSet<>(arr_cost); // 利用LinkHashSet去重數
                    ArrayList<String> subList = new ArrayList<>(set); // 將去重的放進新array
                    tmpList.add(subList);
                }
            } else {
                for (int j = 0; j < Integer.parseInt(srow); j++) {
                    line[j] = 0; // 歸零
                }
                arr_cost.clear(); // 清空array
            }
        }

        if (tmpList.size() == 1) {
            answerList.add(tmpList.get(0));
        }else if(tmpList.size() == 0){
            ArrayList<String> arr = new ArrayList<>();
            for(int i = 0; i < Integer.parseInt(scol); i++){
                arr.add(String.valueOf(i+1));
            }
            answerList.add(arr);
        }

        for(int i = 0; i < tmpList.size()-1; i++){ // 取最少column且weight最小者
            if(tmpList.get(i).size() < tmpList.get(i+1).size()){
                answerList.add(tmpList.get(i)); // 將最少column covering的存在answerList
                for(int j = 0; j < tmpList.get(i).size(); j++){
                    int iweight1 = Integer.parseInt(arr_weight.get(Integer.parseInt(tmpList.get(i).get(j))-1));
                    int iweight2 = Integer.parseInt(arr_weight.get(Integer.parseInt(tmpList.get(i+1).get(j))-1));

                    if(answerList.size() == 1 || answerList.size() > 1 && iweight1 < iweight2){
                        answerList.remove(tmpList.get(i+1));
                    }else {
                        answerList.remove(tmpList.get(i));
                    }
                }
            }
        }

        for(int i = 0; i < answerList.get(0).size(); i++){ // 權重加總
            itotalWeight = itotalWeight + Integer.parseInt(arr_weight.get(Integer.parseInt(answerList.get(0).get(i))-1));
        }

        System.out.println("the minimum column cover : " + answerList.get(0));
        System.out.println("cost(C) : (" + answerList.get(0).size() + "," + itotalWeight + ")");
    }
}