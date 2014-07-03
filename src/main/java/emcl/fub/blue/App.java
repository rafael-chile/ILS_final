package emcl.fub.blue;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import  emcl.fub.blue.constraints.*;
import emcl.fub.blue.gui.MainForm;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.TimeoutException;


public class App 
{
    public static void main( String[] args ) throws URISyntaxException, TimeoutException, ContradictionException, IOException, ParseFormatException {

        MainForm mf = new MainForm();
        mf.setVisible(true);

        try {
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
