/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmn14_b;



import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddEditDialog  extends JDialog {
    enum AddEditPanelOption {
        SaveSuccessfully,
        Cancel
    }

    private boolean addMode; // true for add, false for edit
    private Person person;
    private PhoneBook phoneBook;
    private JButton cmdSave, cmdCancel;
    private JTextField txtName, txtPhone;
    private JLabel lblName, lblPhone, lblError;
    private AddEditPanelOption addEditPanelOption;

    public AddEditDialog(JPanel panel, boolean addMode, Person person, PhoneBook phoneBook) {
        super((Frame)null , "Person Form");
        this.phoneBook = phoneBook;
        this.addMode = addMode;
        if (person != null) {
            this.person = person;
        } else {
            this.person = new Person("", "");
        }

        cmdSave = new JButton("Save");
        cmdCancel = new JButton("Cancel");
        txtName = new JTextField(10);
        txtPhone = new JTextField(10);
        lblName = new JLabel("              Name:");
        lblPhone = new JLabel("             Phone:");
        lblError = new JLabel();

        txtName.setFont(new Font("", 1, 30));
        txtPhone.setFont(new Font("", 1, 30));
        lblName.setFont(new Font("", 1, 30));
        lblPhone.setFont(new Font("", 1, 30));
        //cmdSave.setPreferredSize(new Dimension(300, 60));
        cmdSave.setFont(new Font("", 1, 30));
        cmdCancel.setFont(new Font("", 1, 30));
        lblError.setFont(new Font("", 1, 30));

        JPanel p = new JPanel();
        JPanel controls = new JPanel();
        controls.setLayout(new GridLayout(3, 2, 10, 10));
        controls.add(lblName);
        controls.add(txtName);
        controls.add(lblPhone);
        controls.add(txtPhone);
        controls.add(cmdSave);
        controls.add(cmdCancel);

        JPanel errPanel = new JPanel();
        errPanel.add(lblError);

        p.add(controls);
        p.add(errPanel);

        if (!addMode) { // edit mode
            txtName.setEditable(false);
        }

        txtName.setText(this.person.getName());
        txtPhone.setText(this.person.getPhone());

        this.setLayout(new BorderLayout());
        this.getContentPane().add(p);
        this.setModal(true);

        ControlsListener controlsListener = new ControlsListener();
        cmdSave.addActionListener(controlsListener);
        cmdCancel.addActionListener(controlsListener);

        this.pack();
    }

    public AddEditPanelOption run() {
        setVisible(true);
        return addEditPanelOption;
    }

    private class ControlsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == cmdCancel) {
                addEditPanelOption = AddEditPanelOption.Cancel;
                dispose();
            }
            else if(e.getSource() == cmdSave) {
                String name = txtName.getText().trim();
                String phone = txtPhone.getText().trim();

                if(name.equals("")  || phone.trim().equals("")) {
                    lblError.setText("Name or phone must be fill");
                } else {
                    try {
                        person.setName(name);
                        person.setPhoneNumber(phone);
                        if(addMode) {
                            phoneBook.addNew(person);
                        } else { // edit mode
                            phoneBook.edit(person);
                        }
                        addEditPanelOption = AddEditPanelOption.SaveSuccessfully;
                        dispose();
                    }catch (ContactExistException ex) {
                        lblError.setText(ex.getMessage());
                    }
                }
            }
        }
    }
}
