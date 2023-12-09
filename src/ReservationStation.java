import java.util.Arrays;

public class ReservationStation {
    private int size;
    private String tagSuffix;
    private ReservationStationEntry[] stations;

    public ReservationStation(int size, String tagSuffix) {
        this.size = size;
        this.tagSuffix = tagSuffix;
        this.stations = new ReservationStationEntry[size];
        for (int i = 0; i < size; i++) {
            this.stations[i] = new ReservationStationEntry(tagSuffix + (i + 1));
        }
    }

    public boolean isEmpty() {
        for (int i = 0; i < size; i++) {
            if (stations[i].isBusy()) {
                return false;
            }
        }
        return true;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            if (!stations[i].isBusy()) {
                return false;
            }
        }
        return true;
    }

    public String issueInstruction (int time, boolean busy, InstructionType op, double vj, double vk, String qj, String qk, int address) {
        for (int i = 0; i < size; i++) {
            if (!stations[i].isBusy()) {
                stations[i].setTime(time);
                stations[i].setBusy(busy);
                stations[i].setOp(op);
                stations[i].setVj(vj);
                stations[i].setVk(vk);
                stations[i].setQj(qj);
                stations[i].setQk(qk);
                stations[i].setAddress(address);
                return stations[i].getTag();
            }
        }

        return "";
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTagSuffix() {
        return tagSuffix;
    }

    public void setTagSuffix(String tagSuffix) {
        this.tagSuffix = tagSuffix;
    }

    public ReservationStationEntry[] getStations() {
        return stations;
    }

    public void setStations(ReservationStationEntry[] stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        return "ReservationStation{" +
                "size=" + size +
                ", tagSuffix='" + tagSuffix + '\'' +
                ", stations=" + Arrays.toString(stations) +
                '}';
    }
}
