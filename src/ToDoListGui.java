import javax.swing.*;
import java.awt.event.*;

public class ToDoListGui {
    private DatabaseHandler dbHandler;

    public ToDoListGui(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;
    }

    public void createAndShowGUI() {
        // Create the frame
        JFrame frame = new JFrame("ToDo List");
        frame.setSize(350, 325);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel to hold all other components
        JPanel panel = new JPanel();
        frame.add(panel); //  EVERY COMPONENT MUST BE ADDED TO THE PANEL

        // Call method to populate the panel
        placeComponents(panel);

        // Make the frame visible
        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        // Create input field and label
        JLabel label = new JLabel("Enter a task");
        label.setBounds(10, 10, 80, 25);
        panel.add(label);

        JTextField textField = new JTextField(20);
        textField.setBounds(100, 10, 160, 25);
        panel.add(textField);

        // Create add task button
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.setBounds(10, 50, 120, 25);
        panel.add(addTaskButton);
        addTaskButton.addActionListener(e -> { // GUI component that can trigger an action, when clicking a button.
            // takes a lambda function (e -> { ... }) as its argument, which defines what should happen when the button is clicked
            dbHandler.addTask(textField.getText());
            textField.setText(""); // clears text after it is added to db.
        });

        // Create tasks display area
        JTextArea tasksArea = new JTextArea();
        tasksArea.setBounds(10, 90, 315, 150);
        tasksArea.setEditable(false);
        panel.add(tasksArea);

        // Create view tasks button
        JButton viewTasksButton = new JButton("View Tasks");
        viewTasksButton.setBounds(150, 50, 120, 25);
        panel.add(viewTasksButton);
        viewTasksButton.addActionListener(e -> {
            tasksArea.setText("");
            tasksArea.append(dbHandler.getTasks());

            /*
            Inside the action listener, this line first clears the tasksArea,
            a JTextArea object. The setText("") method of the JTextArea class
            sets the text of the JTextArea component to an empty string, effectively clearing it.
            Appends the tasks retrieved from the database to the tasksArea.
            The append() method of the JTextArea class appends the given text
            to the end of the document.
             */

        });

        // Create a new text field and label for deleting tasks
        JLabel deleteLabel = new JLabel("Delete a task (ID)");
        deleteLabel.setBounds(10, 250, 120, 25);
        panel.add(deleteLabel);

        JTextField deleteField = new JTextField(20);
        deleteField.setBounds(140, 250, 60, 25);
        panel.add(deleteField);

        // Create delete task button
        JButton deleteTaskButton = new JButton("Delete Task");
        deleteTaskButton.setBounds(210, 250, 120, 25);
        panel.add(deleteTaskButton);
        // Attach action listener to delete task button
        deleteTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Make sure to catch any NumberFormatExceptions in case the input is not a valid integer
                try {
                    int idToDelete = Integer.parseInt(deleteField.getText());
                    dbHandler.deleteTask(idToDelete);
                    deleteField.setText("");
                } catch (NumberFormatException nfe) {
                    System.out.println("Please enter a valid task ID to delete");
                }
            }
        });
    }
}
