package org.example;

public class SubTask extends AbstractTask {
    private Epic epic;

    public SubTask(String name, String description, int id, Status status, Epic epic) {
        super(name, description, id, status);
        this.epic = epic;
        epic.addToList(this);
    }
    public Epic getEpic() {
        return epic;
    }
}
