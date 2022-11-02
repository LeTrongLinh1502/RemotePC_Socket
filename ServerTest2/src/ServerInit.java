

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ServerInit {


    private JFrame frame = new JFrame();
    private JDesktopPane desktop = new JDesktopPane();

    public static void main(String args[]) {
        String port = JOptionPane.showInputDialog("Please enter listening port");
        new ServerInit().initialize(Integer.parseInt(port));
    }

    public void initialize(int port) {

        try {
            ServerSocket sc = new ServerSocket(port);

            drawGUI();
      
            while (true) {
                
                Socket client = sc.accept();
                System.out.println("New client Connected to the server");
                
                new HandlerServer(client, desktop);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

   
    public void drawGUI() {
        frame.add(desktop, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
}
