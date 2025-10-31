package game.weather;

/**
 * Created by:
 * @author Shannon Jian Hong Chia
 * Modified by:
 *
 */

/**
 * The WeatherControl class is responsible for managing the current weather conditions in the game.
 * It allows for changing the weather between sunny and rainy and updating the weather periodically.
 */
public class WeatherControl {

    /**
     * The current weather in the game.
     */
    private static Weather currentWeather;

    /**
     * The frequency (in days) at which the weather changes.
     */
    private final int weatherChangeFrequency = 3; // Days

    /**
     * The counter for the number of days with the current weather.
     */
    private int daysOfCurrentWeather;

    /**
     * Constructor for the WeatherControl class.
     * Initializes the current weather to sunny and the counter to zero.
     */
    public WeatherControl() {
        currentWeather = Weather.SUNNY; // Initial weather
        this.daysOfCurrentWeather = 0; // Counter
    }

    /**
     * Gets the current weather.
     *
     * @return The current weather (sunny or rainy).
     */
    public static Weather getCurrentWeather(){
        return currentWeather;
    }

    /**
     * Changes the weather from sunny to rainy or vice versa.
     */
    public void changeWeather(){
        switch (currentWeather){
            case SUNNY:
                currentWeather = Weather.RAINY;
                break;
            case RAINY:
                currentWeather = Weather.SUNNY;
                break;
        }
    }

    /**
     * Updates the weather by incrementing the counter for days with the current weather.
     * If the counter reaches the weather change frequency, the weather is changed, and the counter is reset.
     */
    public void updateWeather(){
        daysOfCurrentWeather++;
        if (daysOfCurrentWeather >= weatherChangeFrequency){
            changeWeather();
            daysOfCurrentWeather = 0;
        }
    }

}
