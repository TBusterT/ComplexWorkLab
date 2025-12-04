package command;


import Model.Bouquet;

public class FileCommand  implements Command{


    public FileCommand(Bouquet bouquet) {
    }

    @Override
    public String getDescription() {
        return "Is doing nothing ";
    }

    @Override
    public void execute(String[] args) {

    }

}
