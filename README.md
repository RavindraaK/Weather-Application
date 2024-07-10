Here's a sample README file for your weather application:

```markdown
# WeatherApp

A simple Java console application to fetch and display weather information for a specified city using the OpenWeatherMap API.

## Features

- Fetches weather data for a specified city.
- Displays the temperature (in Celsius), humidity, and weather description.

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- OpenWeatherMap API key

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/weatherapp.git
cd weatherapp
```

### 2. Set Up the API Key

Replace `YOUR_API_KEY` in `WeatherApp.java` with your actual OpenWeatherMap API key.

```java
private static final String API_KEY = "YOUR_API_KEY";
```

### 3. Compile and Run the Application

```bash
javac -d bin -sourcepath src src/com/weather/WeatherApp.java
java -cp bin com.weather.WeatherApp
```

### 4. Usage

- Run the application.
- Enter the city name when prompted.
- The application will display the current temperature, humidity, and weather description for the specified city.

## Example

```
Enter city name: London
Temperature: 15.75°C
Humidity: 82%
Description: clear sky
```

## Code Explanation

### WeatherApp.java

- **Imports**: Necessary libraries for HTTP connections, input/output operations, and JSON processing.
- **API Key**: Replace the placeholder with your OpenWeatherMap API key.
- **Main Method**: Reads the city name from the user, fetches weather data, and displays it.
- **getWeatherData**: Sends a GET request to the OpenWeatherMap API and retrieves the weather data in JSON format.
- **displayWeatherInfo**: Extracts and prints the temperature (in Celsius), humidity, and weather description from the JSON response.

## Dependencies

- [JSON.org](https://github.com/stleary/JSON-java)

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any improvements or bug fixes.

## Contact

For any questions or suggestions, please contact me at [4500ravindra@gmail.com].

```

Feel free to adjust the repository URL, contact email, and any other details to match your specific setup and preferences.
