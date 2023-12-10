public class CommonDataBus {
    private String tag;
    private Object value;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void clearCommonDataBus() {
        this.tag = "";
        this.value = null;
    }

    @Override
    public String toString() {
        return "CommonDataBus{" +
                "tag='" + tag + '\'' +
                ", value=" + value +
                '}';
    }
}
