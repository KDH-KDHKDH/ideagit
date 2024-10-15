import codecounter.ccounter.CodeLineCounter;

import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String[] args) {
        // 系统提示
        String filePath = "C:\\Users\\KKK\\Desktop\\SE\\test\\testcase3";
        System.out.println("统计的文件夹路径: " + filePath);


        CodeLineCounter.init();
        // 统计
        try {
            CodeLineCounter.countFilesLines(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 输出
        CodeLineCounter.showTable();
    }
}