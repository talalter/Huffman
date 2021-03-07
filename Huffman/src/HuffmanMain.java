import java.io.IOException;
import java.util.Scanner;

public class HuffmanMain {
    public HuffmanMain() {
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the path of the input file");
        String IN_FILE_PATH = sc.nextLine();
        System.out.println("Enter the path of the output file");
        String OUT_FILE_PATH = sc.nextLine();
        HufmannEncoderDecoder test = new HufmannEncoderDecoder();
        String a = "C:/Users/talal/hi";
        String b = "C:/Users/talal/sec";
        test.Compress(a, b);
        //test.Decompress(OUT_FILE_PATH, IN_FILE_PATH);
        sc.close();
    }
}