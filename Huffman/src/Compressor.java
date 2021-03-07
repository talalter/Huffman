import java.io.IOException;

public interface Compressor {
    abstract public void Compress(String input_name, String output_name) throws IOException;
    abstract public void Decompress(String input_name, String output_name) throws IOException;
}
