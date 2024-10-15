import com.opencsv.CSVWriter;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class CounterTable {
    /**
     * 列名
     */
    protected static String[] initHeader = {"Extension", "File Count", "Total Lines", "Empty Lines", "Single Comment Lines", "Multi Comment Lines", "Code Lines"};

    /**
     * 获取数据，将HashMap转换为String[]的ArrayList
     * @param fileStatsMap
     * @return datas
     */
    protected static ArrayList<String[]> getDatas(Map<String, CodeLineCounter.FileStats> fileStatsMap) {
        ArrayList<String[]> datas = new ArrayList<>();
        for (Map.Entry<String, CodeLineCounter.FileStats> entry : fileStatsMap.entrySet()) {
            String[] temp = new String[7];
            CodeLineCounter.FileStats stats = entry.getValue();

            temp[0] = entry.getKey();
            temp[1] = Integer.toString(stats.fileCnt);
            temp[2] = Integer.toString(stats.totalLines);
            temp[3] = Integer.toString(stats.emptyLines);
            temp[4] = Integer.toString(stats.singleCommentLines);
            temp[5] = Integer.toString(stats.multiCommentLines);
            temp[6] = Integer.toString(stats.codeLines);

            datas.add(temp);
        }

        return datas;
    }

    /**
     * 在系统默认应用中打开CSV
     * @param csvFile
     */
    protected static void openCSV(String csvFile) {
        File file = new File(csvFile);

        try {
            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("文件不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出数据表格
     * @param fileStatsMap
     */
    public static void outPutSummaryTable(Map<String, CodeLineCounter.FileStats> fileStatsMap) {
        String csvFile = "output.csv"; // 输出的 CSV 文件名
        String[] header = initHeader;

        ArrayList<String[]> datas = getDatas(fileStatsMap);

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFile))) {   // 生成CSV
            writer.writeNext(header);
            for(String[] data : datas) {
                writer.writeNext(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
