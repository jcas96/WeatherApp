import javax.swing.*;

public class AppLauncher {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run() {
                //displays the Weather App GUI
                new WeatherAppGUI().setVisible(true);
            }
        });

    }
}
