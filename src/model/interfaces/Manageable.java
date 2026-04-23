package model.interfaces;

public interface Manageable {
    public void create();
    public void update();
    public void delete();
    public Object findById(int id);
}
