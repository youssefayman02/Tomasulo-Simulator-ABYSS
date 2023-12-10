public class IntegerRegisterFileEntry {
    private Long value;
    private String qi;

    public IntegerRegisterFileEntry(Long value, String qi) {
        this.value = value;
        this.qi = qi;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
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
        return "{" +
                "value=" + value +
                ", qi='" + qi + '\'' +
                '}';
    }
}
