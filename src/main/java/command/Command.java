package main.java.command;

public interface Command {
    void execute();
    String getName();
    String getDesc();
}
