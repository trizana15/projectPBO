import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TaskComponent extends JPanel implements ActionListener {
    private JCheckBox checkBox;
    private JTextPane taskField;
    private JButton deleteButton;

    private JPanel parentPanel;

    public JTextPane getTaskField(){
        return taskField;
    }

    public TaskComponent(JPanel parentPanel){
        this.parentPanel = parentPanel;

        // Set layout to BoxLayout to allow the components to align vertically and horizontally.
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // Horizontal alignment

        // task field
        taskField = new JTextPane();
        taskField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        taskField.setPreferredSize(CommontConstant.TASKFIELD_SIZE);
        taskField.setContentType("text/html");
        taskField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                taskField.setBackground(Color.WHITE);
            }

            @Override
            public void focusLost(FocusEvent e) {
                taskField.setBackground(null);
            }
        });

        // check box
        checkBox = new JCheckBox();
        checkBox.setPreferredSize(CommontConstant.CHECKBOX_SIZE);
        checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        checkBox.addActionListener(this);

        // delete button
        deleteButton = new JButton("X");
        deleteButton.setPreferredSize(CommontConstant.DELETE_BUTTON_SIZE);
        deleteButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        deleteButton.addActionListener(this);

        // Add components to this task component with BoxLayout (Horizontal layout)
        JPanel checkBoxAndFieldPanel = new JPanel();
        checkBoxAndFieldPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // Ensures checkbox and task field are on the left side
        checkBoxAndFieldPanel.add(checkBox);
        checkBoxAndFieldPanel.add(taskField);

        // Add components to the main panel
        add(checkBoxAndFieldPanel); // Add checkbox and task field
        add(deleteButton); // Add the delete button on the right side

        // Ensure the task component is visible
        setPreferredSize(new Dimension(
                CommontConstant.TASKFIELD_SIZE.width + CommontConstant.CHECKBOX_SIZE.width + CommontConstant.DELETE_BUTTON_SIZE.width,
                Math.max(CommontConstant.TASKFIELD_SIZE.height, CommontConstant.CHECKBOX_SIZE.height)
        ));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle checkbox behavior
        if(checkBox.isSelected()){
            String taskText = taskField.getText().replaceAll("<[^>]*>", ""); // Remove HTML tags
            taskField.setText("<html><s>" + taskText + "</s></html>"); // Add strikethrough to the text
        } else {
            String taskText = taskField.getText().replaceAll("<[^>]*>", ""); // Remove HTML tags
            taskField.setText(taskText); // Remove strikethrough
        }

        // Handle delete button behavior
        if(e.getSource() == deleteButton){
            // Remove the task component from the parent panel
            parentPanel.remove(this);
            parentPanel.revalidate();
            parentPanel.repaint();
        }
    }
}
