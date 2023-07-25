public class ToDoList {
    public static void main(String[] args) {
        // Create an instance of DataBaseHandler Class
        DatabaseHandler dbHandler = new DatabaseHandler();
        // Instance of ToDoListGui where te dbhandler object is a specified constructor.
        ToDoListGui gui = new ToDoListGui(dbHandler);
        gui.createAndShowGUI();
    }
}
