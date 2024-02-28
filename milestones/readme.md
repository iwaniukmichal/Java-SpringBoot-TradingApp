---
export_on_save:
    puppeteer: ["pdf"]

puppeteer:
  font-size: 16px;
  format: "A4"
  margin: 
    top: "2cm"
    bottom: "2cm"
    right: "1.5cm"
    left: "1.5cm"
  scale: 0.8
---

# <p style="text-align: center;">Simulated Stock Trading Application</p>

<p style="text-align: center;">Advanced object-oriented programming<br>Faculty of Mathematics and Information Science<br>Warsaw University of Technology </p>
<p style="text-align: center;"><i>Paweł Pozorski, Michał Iwaniuk</i><br/><i>11.12.2023</i></p>


### Project Idea

The Simulated Stock Trading Application is a comprehensive mobile application designed for Android, featuring a distinct frontend and backend architecture implemented in Java. The primary objective is to offer users an immersive and educational experience in stock trading without exposure to real financial risks.

This application provides a secure environment for users to learn the basics of stock trading through real-time data-based simulations. It eliminates the need to understand the complexities of the advanced GUIs associated with real stocks, making it an ideal platform for beginners. The application incorporates essential features to ensure a realistic and educational trading experience.

1. **Data Retrieval**
   - Utilize Java-based backend services to seamlessly fetch real-time stock data from the [Alpha Vantage API](https://www.alphavantage.co/).

2. **Stock Information Display**
   - Implement a responsive frontend to dynamically present users with a list of available stocks, showcasing their current prices and pertinent details.

3. **Stock Charts Display**
   - Integrate a graphical representation module in the frontend to visualize historical price movements through interactive stock charts over user-selected time intervals.

4. **Buy/Sell Stocks**
   - Enable users to execute simulated buy and sell transactions through backend services handling transactions based on the available stock list.

5. **User Portfolio Display**
   - Develop a frontend module to display the user's portfolio, providing details on the stocks held, their quantities, and current valuations, with data retrieved from the backend.

6. **Transaction History**
   - Implement backend functionality to log and store all user transactions, maintaining a comprehensive history that can be accessed via the frontend.

7. **User Account Balance**
   - Integrate a real-time account balance tracking mechanism in the backend, reflecting funds available for trading, and display it on the frontend.

8. **Error Handling and Validation**
   - Implement robust data validation mechanisms in both frontend and backend to ensure accuracy in user inputs, coupled with a responsive error-handling system for a smooth user experience.

9. **Intuitive User Interface**
   - Design an intuitive and visually appealing frontend interface for easy navigation and seamless user interaction, ensuring a positive user experience.

10. **Educational Resources**
    - Enhance the educational aspect by integrating articles and tutorials within the frontend, providing users with in-depth insights into stock trading concepts.

The entire application relies on data hosted on [Alpha Vantage](https://www.alphavantage.co/) accesable via API and requires user authentication for accessing full functionalities, including stock trading and purchase history.
