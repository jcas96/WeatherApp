import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

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
    private void addGuiComponents() {
        // search field for your location

        JTextField searchTextField = new JTextField();

        //set your placement and size of search bar

        searchTextField.setBounds(15, 15, 351, 45);

        //change font

        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);
        add(searchButton);

        //weather img

        JLabel weatherCondition = new JLabel(loadImage("src/assets/cloudy.png"));

        weatherCondition.setBounds(0,125,450,217);
        add(weatherCondition);

        //temp text
        JLabel temp = new JLabel("10 C");
        temp.setBounds(0,350,450,54);
        temp.setFont(new Font("Dialog", Font.BOLD,48));
        temp.setHorizontalAlignment(SwingConstants.CENTER);
        add(temp);

        //weather description
        JLabel weatherDesc = new JLabel("Cloudy");
        weatherDesc.setBounds(0,405,450,36);
        weatherDesc.setFont(new Font("Dialog", Font.PLAIN,32));
        weatherDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherDesc);
    }
    private ImageIcon loadImage(String resourcePath){
            try{
                BufferedImage image = ImageIO.read(new File (resourcePath));
                return new ImageIcon(image);
            }catch (IOException e){
                e.printStackTrace();
            }

            System.out.println("Could not find resource");
            return null;
    }
}