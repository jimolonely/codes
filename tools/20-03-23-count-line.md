写一个工具类备用

```java
    
    import org.junit.jupiter.api.Test;
    
    import java.io.*;
    
    /**
     * 统计代码行数
     *
     * @author jimo
     * @version 1.0.0
     * @date 2020/3/20 19:07
     */
    public class CountCodeLineTest {
    
        @Test
        void copyAllCodeToFile() throws IOException {
    
            String src = "D:\\workspace\\app\\src\\main";
            String output = "D:\\export\\code.txt";
    
            final CountCode test = new CountCode(src, output);
            System.out.println(test.getTotal());
        }
    
        class CountCode {
    
            /**
             * 源码路径
             */
            private String srcPath;
            /**
             * 输出路径
             */
            private String outPath;
            /**
             * 总行数
             */
            private int total;
            /**
             * 计算哪些文件后缀
             */
            private String[] suffix = {"java", "conf", "xml", "sql"};
    
            public CountCode(String srcPath, String outPath) throws IOException {
                this.srcPath = srcPath;
                this.outPath = outPath;
                countCode();
            }
    
            private void countCode() throws IOException {
                final BufferedWriter bw = new BufferedWriter(new FileWriter(outPath));
                recursiveRead(new File(srcPath), bw);
                bw.close();
            }
    
            private void recursiveRead(File file, BufferedWriter bw) throws IOException {
                // 递归读取文件夹
                if (file.isDirectory()) {
                    final File[] files = file.listFiles();
                    if (files == null || files.length == 0) {
                        return;
                    }
                    for (File f : files) {
                        recursiveRead(f, bw);
                    }
                } else {
                    if (shouldCount(file.getName())) {
                        readFileLine(file, bw);
                    }
                }
            }
    
    
            private boolean shouldCount(String name) {
                for (String s : suffix) {
                    if (name.endsWith(s)) {
                        return true;
                    }
                }
                return false;
            }
    
            private void readFileLine(File file, BufferedWriter bw) throws IOException {
                final BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    bw.write(line);
                    bw.write("\n");
                    total++;
                }
                br.close();
            }
    
            public int getTotal() {
                return total;
            }
        }
    }

```
