
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

class HandlerServer extends Thread {
   
    private JDesktopPane desktop = null;
    
    private Socket cSocket = null;
    private JInternalFrame interFrame = new JInternalFrame("Client Screen",
            true, true, true);
    private JPanel cPanel = new JPanel();

    public HandlerServer(Socket cSocket, JDesktopPane desktop) {
        this.cSocket = cSocket;
        this.desktop = desktop;
        start();
    }

    
    public void drawGUI() {
        interFrame.setLayout(new BorderLayout());
        interFrame.getContentPane().add(cPanel, BorderLayout.CENTER);
        interFrame.setSize(100, 100);
        desktop.add(interFrame);
        try {
           
            interFrame.setMaximum(true);
        } catch (PropertyVetoException ex) {
            ex.printStackTrace();
        }      
        
        cPanel.setFocusable(true);
        interFrame.setVisible(true);
    }

    @Override
    public void run() {

       
        Rectangle clientScreenDim = null;
        
        ObjectInputStream ois = null;
        
        drawGUI();

        try {
            
            ois = new ObjectInputStream(cSocket.getInputStream());
            clientScreenDim = (Rectangle) ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
        new ScreenReciever(ois, cPanel);
        
        new CommandsServerSender(cSocket, cPanel, clientScreenDim);
    }

}
class ScreenReciever extends Thread {
     ObjectInputStream ois;
     JPanel p;
     boolean continueLoop = true;

    public ScreenReciever(ObjectInputStream ois, JPanel p) {
        this.ois = ois;
        this.p = p;
        // g???i ?????n run()
        start();
    }

    public void run(){
        
            try {
                
                // ?????c ???nh ch???p m??n h??nh c???a client sau ???? v??? ch??ng
                while(continueLoop){
                    //Nh???n ???nh ch???p m??n h??nh t??? client v?? thay ?????i k??ch th?????c n?? th??nh k??ch th?????c b???ng ??i???u khi???n hi???n t???i
                    ImageIcon imageIcon = (ImageIcon) ois.readObject();
                    System.out.println("New image recieved");
                    Image image = imageIcon.getImage();
                    image = image.getScaledInstance(p.getWidth(),p.getHeight(),Image.SCALE_SMOOTH);
                    // V??? ???nh ch???p m??n h??nh ???? nh???n
                    Graphics graphics = p.getGraphics();
                    graphics.drawImage(image, 0, 0, p.getWidth(),p.getHeight(),p);
                }
            } catch (Exception e) {
               ////
          } 
     }
}
