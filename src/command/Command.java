package command;

public interface Command {
    String getDescription();   // Опис команди для help()
    void execute(String[] args); // args — додаткові параметри команди
}
