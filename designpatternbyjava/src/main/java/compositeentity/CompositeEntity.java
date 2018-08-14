package compositeentity;

public class CompositeEntity {
    private CoarseGrainedObject object = new CoarseGrainedObject();

    public void setData(String data1, String data2) {
        object.setData(data1, data2);
    }

    public String[] getData() {
        return object.getData();
    }
}
