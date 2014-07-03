package emcl.fub.blue.gui;
import emcl.fub.blue.TimetableSolver;
import emcl.fub.blue.constraints.CHR;
import emcl.fub.blue.gui.filltable;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.net.URISyntaxException;
import java.util.*;

import javax.swing.*;
import javax.swing.border.*;
/*
 * Created by JFormDesigner on Mon Jun 23 02:45:54 CEST 2014
 */



/**
 * @author Brainrain
 */
public class MainForm extends JFrame implements ActionListener {
    JFileChooser fc;
    private java.util.List<CHR> results = new ArrayList<CHR>();
    private String[] columnNames= {"Monday","Tuesday","Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private Object data[][];
    private String filePath;

    public MainForm( ) {
        initComponents();
        fc = new JFileChooser();
    }

       //Open File
    private void button1ActionPerformed(ActionEvent e) {

        button1.addActionListener(this);
        //Handle open button action.
        if (e.getSource() == button1) {
            int returnVal = fc.showOpenDialog(MainForm.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                label1.setText("Opening: " + file.getName() + ".");
                filePath = file.getPath();
            } else {
                label1.setText("Open command cancelled by user.");
            }
        }
    }

    private void button2ActionPerformed(ActionEvent e) throws IOException, URISyntaxException, ParseFormatException, TimeoutException, ContradictionException {
        results = TimetableSolver.solveTimetable(filePath);

        data = new Object[24][7];
        for(int i =0; i< results.size(); i++){
            int hour = results.get(i).getTimeSlot().getPeriod();
            int day = results.get(i).getTimeSlot().getDay();
            if (data[hour][day] == null) {
                data[hour][day] = results.get(i).getCourse().getId() + "\n"
                        + " - Room:" + results.get(i).getRoom().getId();
            }else {
                data[hour][day] = data[hour][day] + results.get(i).getCourse().getId() + "\n"
                        + " - Room:" + results.get(i).getRoom().getId();
            }
        }

        table1 = new JTable(data, columnNames);
        scrollPane1.setViewportView(table1);
        scrollPane1.updateUI();
    }

    private void cancelButtonActionPerformed(ActionEvent e) throws Throwable {
        setVisible(false); //you can't see me!
        dispose(); //Destroy the JFrame object
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		contentPanel = new JPanel();
		scrollPane1 = new JScrollPane();
		table1 = new JTable();
        button1 = new JButton("Open a File...", createImageIcon("/Open16.gif"));
		label1 = new JLabel();
		button2 = new JButton();
		label2 = new JLabel();
		buttonBar = new JPanel();
		cancelButton = new JButton();

		//======== this ========
		setTitle("Timetabling");
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setBackground(new Color(0, 153, 204));
			dialogPane.setLayout(new BorderLayout());

			//======== contentPanel ========
			{
				contentPanel.setBackground(new Color(0, 153, 204));

				//======== scrollPane1 ========
				{

					//---- table1 ----
					table1.setMinimumSize(new Dimension(30, 60));
					scrollPane1.setViewportView(table1);
				}

				//---- button1 ----
				button1.setText("Open a File...");
				button1.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						button1ActionPerformed(e);
					}
				});

				//---- label1 ----
				label1.setForeground(Color.white);

				//---- button2 ----
				button2.setText("Execute...");
				button2.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
                        try {
                            button2ActionPerformed(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        } catch (ParseFormatException e1) {
                            e1.printStackTrace();
                        } catch (TimeoutException e1) {
                            e1.printStackTrace();
                        } catch (ContradictionException e1) {
                            e1.printStackTrace();
                        }
                    }
				});

				//---- label2 ----
				label2.setText("TIMETABLING - BLUE TEAM");
				label2.setForeground(Color.white);
				label2.setFont(new Font("Tahoma", Font.BOLD, 14));

				GroupLayout contentPanelLayout = new GroupLayout(contentPanel);
				contentPanel.setLayout(contentPanelLayout);
				contentPanelLayout.setHorizontalGroup(
					contentPanelLayout.createParallelGroup()
						.addGroup(contentPanelLayout.createSequentialGroup()
                                .addGroup(contentPanelLayout.createParallelGroup()
                                        .addGroup(GroupLayout.Alignment.TRAILING, contentPanelLayout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                        .addComponent(scrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 1070, Short.MAX_VALUE)
                                                        .addGroup(GroupLayout.Alignment.LEADING, contentPanelLayout.createSequentialGroup()
                                                                .addComponent(button1, GroupLayout.PREFERRED_SIZE, 148, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(label1, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 518, Short.MAX_VALUE)
                                                                .addComponent(button2, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(contentPanelLayout.createSequentialGroup()
                                                .addGap(446, 446, 446)
                                                .addComponent(label2)))
                                .addContainerGap())
				);
				contentPanelLayout.setVerticalGroup(
					contentPanelLayout.createParallelGroup()
						.addGroup(contentPanelLayout.createSequentialGroup()
                                .addComponent(label2)
                                .addGap(18, 18, 18)
                                .addGroup(contentPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(button1)
                                        .addComponent(label1)
                                        .addComponent(button2))
                                .addGap(18, 18, 18)
                                .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                                .addContainerGap())
				);
			}
			dialogPane.add(contentPanel, BorderLayout.CENTER);

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setBackground(new Color(0, 153, 204));
				buttonBar.setLayout(new GridBagLayout());
				((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 85, 85, 80};
				((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0, 0.0, 0.0};

				//---- cancelButton ----
				cancelButton.setText("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
                        try {
                            cancelButtonActionPerformed(e);
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
				});
				buttonBar.add(cancelButton, new GridBagConstraints(3, 0, 1, 1, 0.0, 0.0,
					GridBagConstraints.CENTER, GridBagConstraints.BOTH,
					new Insets(0, 0, 0, 0), 0, 0));
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel contentPanel;
	private JScrollPane scrollPane1;
	private JTable table1;
	private JButton button1;
	private JLabel label1;
	private JButton button2;
	private JLabel label2;
	private JPanel buttonBar;
	private JButton cancelButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MainForm.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}
