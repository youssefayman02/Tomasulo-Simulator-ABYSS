public class FloatingPointRegisterFileEntry {
    private double value;
    private String qi;

    public FloatingPointRegisterFileEntry(double value, String qi) {
        this.value = value;
        this.qi = qi;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getQi() {
        return qi;
    }

    public void setQi(String qi) {
        this.qi = qi;
    }

    @Override
    public String toString() {
        return "FloatingPointRegisterFileEntry{" +
                "value=" + value +
                ", qi='" + qi + '\'' +
                '}';
    }
}
