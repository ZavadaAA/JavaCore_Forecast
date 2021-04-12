package Lesson_7;
import java.io.IOException;
import java.util.Scanner;

public class UserInterface {
    private final Controller controller=new Controller();

    public void runApplication(){
        Scanner scanner=new Scanner(System.in);
        while (true) {
            System.out.println("Введите название города на английском языке, чтобы проверить прогноз погоды");
            String city= scanner.nextLine();
            //получаем id города
            try { AccuWeatherModel.setGlobalCity(city);
            }
            catch (IOException e) {
                e.printStackTrace();}
            System.out.println("Введите свой ответ: 1 - Вы хотите получить погоду на ближайшие 5 дней, "+"или же 0 - чтобы завершить работу");
            String result=scanner.nextLine();
            Integer result2 = Integer.valueOf(result);
            if(result2==1) {
                try {
                    AccuWeatherModel.getWeather(Periods.IN5DAYS, city);
                } catch (IOException e) {
                    e.printStackTrace();
                }break;
            }
            else if(result2==3) {
                try {
                    AccuWeatherModel.getWeather(Periods.NOW, city);
                } catch (IOException e) {
                    e.printStackTrace();
                }break;
            }
            else {break;}
        }
    }
}