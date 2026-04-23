package model;

public interface Mangeable {
    public void create();
    public void update();
    public void delete();
    public Object findById(int id);
}
