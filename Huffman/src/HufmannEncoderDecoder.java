import java.io.*;
import java.util.PriorityQueue;
import java.util.Map;
import java.util.BitSet;
import java.util.HashMap;
import java.lang.Byte;

public class HufmannEncoderDecoder implements Compressor
{


    /**
     * Assignment 1
     * Submitted by: Tal Alter
     ID# 206292450
     */

    final static int size = 256;
    PriorityQueue<HufmanNode> queue;
    int [] array;
    HufmanNode root;
    Map<Byte, String> map;
    BitSet bitSet;
    int index=0;







    HufmannEncoderDecoder(){
        this.map=new HashMap<>();

    }

    public void createArrOfZeros() {
        this.array = new int[size];
        for(int i=0;i<size;i++) {
            array[i]=0;
        }
    }


    //Checks the frequency for each byte on the file
    public void freq(FileInputStream IN_FILE_PATH) throws IOException{
        BufferedInputStream input = new BufferedInputStream(IN_FILE_PATH);
        while(true)
        {
            final byte ch =  (byte)input.read();
            if(ch!=-1) {
                if(ch>0) {
                    this.array[ch]++;
                }
                else {
                    this.array[(Byte.toUnsignedInt(ch))]++;
                }
            }
            else break;
        }
    }


    public void buildAQueueOfLeaves () {
        this.queue = new PriorityQueue<HufmanNode>();
        for(int i=0;i<size;i++)
        {
            if(this.array[i]!=0) {
                HufmanNode childHuffmanNode = new HufmanNode(this.array[i], (byte)i);
                queue.add(childHuffmanNode);
            }
        }

    }


    // Build the tree
    public boolean BuildHuffmanTree()
    {
        if(this.queue.size()==0)
            return false; // Unsuccessful to build the tree
        if(this.queue.size()==1) {
            this.root=this.queue.peek();
            return true;
        }
        else {
            int sumOfFreq=this.queue.peek().getFreq();
            HufmanNode father = new HufmanNode();
            father.setLeft(this.queue.poll());
            sumOfFreq += this.queue.peek().getFreq();
            father.setRight(this.queue.poll());
            father.setFreq(sumOfFreq);
            this.queue.add(father);
            return BuildHuffmanTree();
        }
    }

    //	Generating the code by the algorithm
    public void newCodeByHufmann(HufmanNode root, String code) {
        if(!root.isLeaf()) {
            newCodeByHufmann(root.getLeft(), code+"0");
            newCodeByHufmann(root.getRight(), code+"1");
        }
        else {
            this.map.put(root.getData(), code);
        }

    }


    //Generating the output to the file
    public void theOutPutString(FileInputStream fileInput) throws IOException
    {
        this.bitSet = new BitSet();
        BufferedInputStream input = new BufferedInputStream(fileInput);
        String str;
        while(true){
            final byte ch =  (byte)input.read();
            if(ch!=-1){
                str=this.map.get(ch);
                fromStringToBitSet(str);
            }
            else{
                return;
            }

        }
    }

    // replace the String to BitSet
    public void fromStringToBitSet(String code) {
        int lastBitIndex = code.length() - 1;
        int i=0;
        while(i<=lastBitIndex)  {
            if ( code.charAt(i) == '1') {
                this.bitSet.set(this.index, true);
            }
            else  {
                this.bitSet.set(this.index,false);
            }
            this.index++;
            i++;
        }
    }

    public void WriteToTheFile(FileOutputStream OUT_FILE_PATH, byte [] byteCode) throws IOException {
        OUT_FILE_PATH.write(byteCode);
    }

    public void Compress(String input_name, String output_name) throws IOException
    {
        FileInputStream input, input1;
        FileOutputStream output;
        input = new FileInputStream(input_name);
        input1 = new FileInputStream(input_name);
        output = new FileOutputStream(output_name);
        createArrOfZeros();
        freq(input);
        buildAQueueOfLeaves ();
        BuildHuffmanTree();
        newCodeByHufmann(this.root, "");
        theOutPutString(input1);
        WriteToTheFile(output, this.bitSet.toByteArray());
        System.out.println("Compressed");
        input.close();
        input1.close();
        output.close();
    }
    public void Decompress(String input_name, String output_name) throws IOException
    {
        FileInputStream input;
        FileOutputStream output;

        input = new FileInputStream(input_name);
        output = new FileOutputStream(output_name);
        HufmanNode current = this.root;
        int index=0;
        while(index<this.bitSet.length())
        {
            if(this.bitSet.get(index)) // if equals to 1
            {
                current=current.getRight();
            }
            else //if equals to 0
            {
                current=current.getLeft();
            }
            if(current.isLeaf()) {
                output.write(current.getData());
                current = this.root;
            }
            index++;
        }
        System.out.println("Decompressed");
        input.close();
        output.close();
    }

}