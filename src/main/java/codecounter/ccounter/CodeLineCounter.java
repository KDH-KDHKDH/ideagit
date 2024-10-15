package codecounter.ccounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * 代码统计类。
 */
public class CodeLineCounter {
    static Set<String> sourceCodeExtensions = new HashSet<>(); // 扩展名集合
    /**
     * 总共行数
     */
    static int totalLines = 0;
    /**
     * 空白行数
     */
    static int emptyLines = 0;
    /**
     * 单行注释行数
     */
    static int singleCommentLines = 0;
    /**
     * 多行注释行数
     */
    static int multiCommentLines = 0;
    /**
     * 代码行数
     */
    static int codeLines = 0;
    /**
     * 按后缀名分类的统计数据
     */
    static Map<String, FileStats> fileStatsMap = new HashMap<>();
    /**
     * 开始统计的时间，init()函数中开始计时
     */
    static long startTime;
    /**
     * 结束统计的时间，Count()结束，show()开始前结束计时
     */
    static long endTime;


    /**
     * 初始化函数，将常见的源文件后缀名添加到集合中。每次统计之前需要重新初始化。
     */
    public static void init() {
        // 常见的源文件后缀名数组
        String[] extensions = {
                "c", "h", "cpp", "cc", "cxx", "hpp", "java", "py", "js", "html",
                "htm", "css", "ts", "go", "rb", "swift", "rs", "php", "sh", "pl"
        };
        // 将数组中的后缀名添加到集合中
        sourceCodeExtensions.addAll(Arrays.asList(extensions));
        totalLines = 0;
        emptyLines = 0;
        singleCommentLines = 0;
        codeLines = 0;
        fileStatsMap = new HashMap<>();

        startTime = System.currentTimeMillis();
    }

    /**
     * 核心功能1，递归遍历统计指定文件或目录的代码行数。
     * @param file 文件或目录
     * @throws IOException 如果文件读取失败
     */
    public static void countFilesLines(File file) throws IOException {
        if (file.isDirectory()) { // 如果是目录，递归处理目录中的所有文件
            File[] files = file.listFiles();
            if (files != null) for (File f : files) countFilesLines(f);
        }
        else linesCount(file); // 如果是文件，统计代码行数
    }

    /**
     * 核心功能2，对文件行的读入并分类统计。
     */
    static void linesCount(File file) throws IOException {
        String extension = getFileExtension(file.getName());    // 后缀
        extension = extension.toLowerCase();
        if (sourceCodeExtensions.contains(extension)) {         // 源文件判断
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean inMultiLineComment = false;

            // 当前文件统计数据
            FileStats fileStats = fileStatsMap.getOrDefault(extension, new FileStats()); // new FileStats() 为未找到对应键值的默认关联值
            fileStats.fileCnt++;

            while ((line = reader.readLine()) != null) {

                totalLines++;       //总行数
                fileStats.totalLines++;

                // 去除行首和行尾的空白字符
                line = line.trim();

                // 统计空白、注释、代码行数
                if (line.isEmpty()) {
                    emptyLines++;
                    fileStats.emptyLines++;
                }  else if (line.startsWith("#") || line.startsWith("//")) {
                    singleCommentLines++;
                    fileStats.singleCommentLines++;
                } else if (line.startsWith("/*") || line.startsWith("'''") || inMultiLineComment) {
                    multiCommentLines++;
                    fileStats.multiCommentLines++;
                    if (line.endsWith("*/") || line.endsWith("'''")) {
                        inMultiLineComment = false;
                    } else if (line.startsWith("/*") || line.startsWith("'''")) {
                        inMultiLineComment = true;
                    }
                } else {
                    codeLines++;
                    fileStats.codeLines++;
                }
            }
            // 更新文件统计数据
            fileStatsMap.put(extension, fileStats);
            reader.close();
        }

        endTime = System.currentTimeMillis();
    }

    /**
     * 辅助函数，获取文件的后缀名。
     * @param fileName 文件名
     * @return 文件后缀名
     */
    private static String getFileExtension(String fileName) {
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";
        }
        return fileName.substring(lastDotIndex + 1);
    }

    /**
     * 辅助函数，控制台打印输出。
     */
    public static void showAll() {
        System.out.println("Total Lines: " + totalLines);
        System.out.println("Empty Lines: " + emptyLines);
        System.out.println("Single Comment Lines: " + singleCommentLines);
        System.out.println("Multi Comment Lines: " + multiCommentLines);
        System.out.println("Code Lines: " + codeLines);
        System.out.println();

        // 打印按后缀名分类的统计数据
        for (Map.Entry<String, FileStats> entry : fileStatsMap.entrySet()) {
            String extension = entry.getKey();
            FileStats stats = entry.getValue();
            System.out.println("Extension: " + extension);
            System.out.println("File Count: "+ stats.fileCnt);
            System.out.println("--Total Lines: " + stats.totalLines);
            System.out.println("--Empty Lines: " + stats.emptyLines);
            System.out.println("--Single Comment Lines: " + stats.singleCommentLines);
            System.out.println("--Multi Comment Lines: " + stats.multiCommentLines);
            System.out.println("--Code Lines: " + stats.codeLines);
            System.out.println();
        }

        System.out.println("Runtime: " + (endTime - startTime));
    }

    /**
     * 辅助函数，打印输出表格。
     */
    public static void showTable() {
        System.out.println("Runtime: " + (endTime - startTime));
        CounterTable.outPutSummaryTable(fileStatsMap);
    }

    /**
     * 文件统计数据类，用于存储每个文件的统计信息。
     */
    public static class FileStats {
        int totalLines = 0;
        int emptyLines = 0;
        int singleCommentLines = 0;
        int multiCommentLines = 0;
        int codeLines = 0;
        int fileCnt = 0;
    }
}