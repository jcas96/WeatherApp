import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

public class WeatherAppGUI extends JFrame {
    private JSONObject weatherData;
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

        JButton searchButton = new JButton(loadImage("src/assets/search.png"));

        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchButton.setBounds(375,13,47,45);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get user location
                String userInput = searchTextField.getText();

                if(userInput.replaceAll("\\s","").length()<=0){
                    return;
                }
                weatherData = WeatherApp.getWeatherData(userInput);

                //update GUI//update image
                /*String weatherCon = (String)weatherData.get("weather_condition");
                switch(weatherCon){
                    case "Clear":
                        weatherCondition.setIcon(loadImage("src/assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherCondition.setIcon(loadImage("src/assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherCondition.setIcon(loadImage("src/assets/rain.png"));
                        break;
                    case "Snow":
                        weatherCondition.setIcon(loadImage("src/assets/snow.png"));
                        break;
                }*/


                //updates temp
                double temperature = (double)weatherData.get("temperature");
                temp.setText(temperature+" F");

                //updates weather condition Text
                //weatherDesc.setText(weatherCon);
            }
        });
        add(searchButton);
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