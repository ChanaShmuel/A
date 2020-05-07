/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mmn14_b;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;






public class Mmn14_b extends JFrame{

    private JComboBox<String> name, phoneNumber;
    private JTextArea tname,tphone;
    private JButton btnadd, btndelete,btnedit,btnsearch,btnimport,btnsave;
    private  PhoneBook phoneBook;
    private JTextField txtSearch;
    private DefaultTableModel tablephonebook;
    private JTable personTable;
    public  Mmn14_b()
    {
        phoneBook=new PhoneBook();
        
        //buttons
        btnadd=new JButton("add");
        btndelete=new JButton("delete");
        btnedit=new JButton("edit");
        btnsearch=new JButton("search");
        btnimport=new JButton("import");
        btnsave=new JButton("save");
        //buttons font,size
        btnadd.setPreferredSize(new Dimension(230, 30));
        btndelete.setPreferredSize(new Dimension(230, 30));
        btnedit.setPreferredSize(new Dimension(230, 30));
        
        btnadd.setPreferredSize(new Dimension(230, 30));
        btnimport.setPreferredSize(new Dimension(230, 30));
        btnsave.setPreferredSize(new Dimension(230, 30));
        btnadd.setFont(new Font("", 1, 25));
        btndelete.setFont(new Font("", 1, 25));
        btnedit.setFont(new Font("", 1, 25));
        btnsave.setFont(new Font("", 1, 25));
        btnimport.setFont(new Font("", 1, 25));
        btnsearch.setFont(new Font("",2, 40));
        txtSearch = new JTextField();
        txtSearch.setPreferredSize(new Dimension(575, 60));
        txtSearch.setFont(new Font("",1, 40));
        
        
        //panel buttons
        JPanel buttonspanel=new JPanel();
        buttonspanel.add(btnadd);
        buttonspanel.add(btndelete);
        buttonspanel.add(btnedit);
        buttonspanel.add(btnimport);
        buttonspanel.add(btnsave);
        buttonspanel.setPreferredSize(new  Dimension(900, 50));
        
        //panel Search
        JPanel searchpanel=new JPanel();
        searchpanel.add(btnsearch);
        searchpanel.add(txtSearch);
        searchpanel.setPreferredSize(new Dimension(900, 50));
        
        //table phone panel
        tablephonebook = new DefaultTableModel();
        tablephonebook.addColumn("Name");
        tablephonebook.addColumn("Phone");
        
        JPanel personPanel = new JPanel();
        personTable = new JTable(tablephonebook);
        personTable.setPreferredScrollableViewportSize(new Dimension(900, 750));
        personTable.setFillsViewportHeight(true);
        personTable.setFont(new Font("",1, 30));
        personTable.getTableHeader().setFont(new Font("",1,30));
        personTable.getTableHeader().setSize(20, 40);
        personTable.setRowHeight(40);
        JScrollPane scrollPane = new JScrollPane(personTable);
        scrollPane.setVisible(true);
        personPanel.add(scrollPane);
        tablephonebook.fireTableDataChanged();
        
        
        this.setLayout(new BorderLayout());

        add(buttonspanel, BorderLayout.NORTH);
        add(searchpanel, BorderLayout.CENTER);
        add(personPanel, BorderLayout.SOUTH);
        
        
        
        ControlsListener controlsListener = new ControlsListener();
        btnadd.addActionListener(controlsListener);
        btnedit.addActionListener(controlsListener);
        btndelete.addActionListener(controlsListener);
        btnsave.addActionListener(controlsListener);
        btnsearch.addActionListener(controlsListener);
        btnimport.addActionListener(controlsListener);
        
       setSize(1200,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
                
    }

    private class ControlsListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnadd) {
                handleAdd();
            } else if (e.getSource() == btndelete) {
                handleDelete();
            } else if(e.getSource() == btnedit) {
                handleEdit();
            } else if(e.getSource() == btnsave) {
                handleSave();
            } else if(e.getSource() == btnimport) {
                handleImport();
            } else if(e.getSource() == btnsearch) {
                handleSearch();
            }
        }
        
        private void reloadPersons()
        {
            ArrayList<Person> persons = phoneBook.getPersons();
            tablephonebook.setRowCount(0);
            for (Person person : persons) {
                tablephonebook.addRow(new String[]{person.getName(), person.getPhone()});
            }
            personTable.setModel(tablephonebook);
        }

        private void handleAdd()
        {
            AddEditDialog dialog = new AddEditDialog(null, true, null, phoneBook);
            dialog.setSize(700, 400);
            dialog.setResizable(false);
            AddEditDialog.AddEditPanelOption option = dialog.run();
            if (option == AddEditDialog.AddEditPanelOption.SaveSuccessfully) {
                reloadPersons();
            }
        }
        
        private void handleDelete()
        {
            int selectedRow = personTable.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please choose a PERSON");
            } else {
                String name = personTable.getValueAt(selectedRow, 0).toString();
                phoneBook.delete(name);
               reloadPersons();
            }
        }
        
        public void  handleEdit()
        {
            int selectedRow = personTable.getSelectedRow();
            if(selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please choose a PERSON");
            } else {
                String name = personTable.getValueAt(selectedRow, 0).toString();
                String phone = personTable.getValueAt(selectedRow, 1).toString();
                Person person = new Person(name, phone);
                AddEditDialog dialog = new AddEditDialog(null, false, person, phoneBook);
                dialog.setSize(700, 400);
                dialog.setResizable(false);
                AddEditDialog.AddEditPanelOption option = dialog.run();
                if (option == AddEditDialog.AddEditPanelOption.SaveSuccessfully) {
                    reloadPersons();
                }
            }
        }
        
        public void  handleSave()
        {
            try {
		 FilesUtils.writeToFile(phoneBook);
		} 
            catch (IOException e) {
		e.printStackTrace();
		}
        }
        
        public void  handleImport()
        {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
            fileChooser.setDialogTitle("Choose a file");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.showOpenDialog(null);
            File selectedFile = fileChooser.getSelectedFile();
            try
            {
                phoneBook.ImportFile(selectedFile);
                reloadPersons();
            }
            catch (Exception ex)
            {
                JOptionPane.showMessageDialog(null, "Error during processing file");
            }
        }
        
        public void handleSearch()
        {
            String name = txtSearch.getText().trim();
            if(name.equals("")) {
                JOptionPane.showMessageDialog(null, "Please enter name of person");
            } else {
                Person person = phoneBook.getPerson(name);
                String message = person != null ? "Name: " + name + "\nPhone: " + person.getPhone() : "Person not exist";
                JOptionPane.showMessageDialog(null, message);
            }
        }
    }    
    public static void main(String[] args) {
       
        new Mmn14_b();
       
    }
}






