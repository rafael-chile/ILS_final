package emcl.fub.blue.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;

import emcl.fub.blue.constraints.CHR;
import emcl.fub.blue.entity.Course;
import emcl.fub.blue.entity.Room;
import emcl.fub.blue.entity.TimeSlot;

public class filltable extends JPanel {
    private boolean DEBUG = false;

    public filltable(List<CHR> results ) {
        super(new GridLayout(1,0));

        String[] columnNames = {"Course",
                "Room",
                "Time Slot"};

		/* Method DEFINED by you */
        //List<CHR> results = getMyCHLst();

        Object data[][] = new Object[results.size()][3]; // 3 because there are 3 columns
        for(int i =0; i< results.size(); i++){
            data[i][0] =  results.get(i).getCourse();
            data[i][1] =  results.get(i).getRoom();
            data[i][2] =  results.get(i).getTimeSlot();
        }

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("filltable");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		List<CHR> lst = new ArrayList<CHR>();
		lst.add(new CHR(new Course(),new Room(),new TimeSlot()));
		lst.add(new CHR(new Course(),new Room(),new TimeSlot()));
		lst.add(new CHR(new Course(),new Room(),new TimeSlot()));
		lst.add(new CHR(new Course(),new Room(),new TimeSlot()));
		
        //Create and set up the content pane.
        filltable newContentPane = new filltable(lst);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
	
}

