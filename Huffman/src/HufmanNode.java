public class HufmanNode {
    int freq;
    byte data;
    HufmanNode left;
    HufmanNode right;

    public HufmanNode(int freq, HufmanNode left, HufmanNode right) {
        this.freq = freq;
        this.data = 0;
        this.left = left;
        this.right = right;
    }

    public HufmanNode(int freq, byte data) {
        this.freq = freq;
        this.data = data;
        this.left = null;
        this.right = null;
    }

    public HufmanNode() {
        this.data = 0;
        this.freq = 0;
    }

    public void setFreq(int freq) {
        this.freq = freq;
    }

    public void setLeft(HufmanNode left) {
        this.left = left;
    }

    public void setRight(HufmanNode right) {
        this.right = right;
    }

    public HufmanNode getLeft() {
        return this.left;
    }

    public HufmanNode getRight() {
        return this.right;
    }

    public int getFreq() {
        return this.freq;
    }

    public byte getData() {
        return this.data;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    public int compareTo(HufmanNode other) {
        return this.getFreq() - other.getFreq();
    }
}