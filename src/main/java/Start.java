import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        // 系统提示
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入需统计的文件夹路径: ");
        String filePath = scanner.nextLine();

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