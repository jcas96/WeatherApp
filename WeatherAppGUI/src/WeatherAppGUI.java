import javax.swing.*;
import java.awt.*;

public class WeatherAppGUI extends JFrame {
    public WeatherAppGUI(){
        super("Weather App");


        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(450, 650);


        setLocationRelativeTo(null);

        setLayout(null);

        setResizable(false);


        addGuiComponents();
    }
    private void addGuiComponents(){
        // search field for your location

        JTextField searchTextField = new JTextField();

        //set your placement and size of search bar

        searchTextField.setBounds(15,15,351,45);

        //change font

        searchTextField.setFont(new Font("Dialog", Font.PLAIN,24));
    }
}
