# Financial Literacy for everyone

This project is an Investment Simulator App built with Jetpack Compose. It provides users with a platform to learn financial literacy while also offering an opportunity to simulate investments in various assets. Users can visualize potential returns over different periods, manage, and track simulated investments interactively.

## Preview Screenshots
<p align="center">
  <img src="/Screenshots/homePage.png" alt="Home Page" width="200"/>
  <img src="/Screenshots/portfolio.png" alt="Portfolio Page" width="200"/>
  <img src="/Screenshots/markets.png" alt="Markets Page" width="200"/>
  <img src="/Screenshots/trade.png" alt="Trade Page" width="200"/>
  <img src="/Screenshots/signIn.png" alt="SignIn Page" width="200"/>
  <img src="/Screenshots/signUp.png" alt="SignUp Page" width="200"/>
  <img src="/Screenshots/searchPage.png" alt="Search Page" width="200"/>
</p>

## Features
- **User Balances and Wallet System:** Each user gets a starting balance upon registration, and they can simulate buying/selling of assets, which are tracked in a wallet system. The app records details such as purchase price and time for each asset.

- **Educational Contents:** This app aims to enhance users' financial literacy by educating them on topics such as debt management, investment instruments, and credit scores. Through interactive simulations and educational content, users can develop a better understanding of financial principles and make informed decisions in their investment journey.

- **Real-Time Crypto and Stock Data:** Fetches live data from the CoinMarketCap API, including information on cryptocurrencies such as Bitcoin, Ethereum, and many others. Data is displayed with essential metrics like price, market cap, and percentage changes over various timeframes.

- **Interactive Portfolio Management:** Users can manage their simulated investments, view their portfolio’s total balance, and track the performance of each asset in real-time.

## Tech Stack
- Kotlin & Jetpack Compose: Utilized for building the UI with a modern, declarative approach.
- MVVM Architecture: Ensures a separation of concerns, making the app scalable and maintainable.
- Dependency Injection with Hilt: Injects dependencies, enhancing code quality and modularity.
- Coroutines and Flow: For handling asynchronous operations and data streams efficiently, providing reactive programming capabilities.
- Firebase: Used for authentication and managing user data like portfolio details and transaction history.
- Retrofit: Integrates with multiple APIs (e.g., CoinMarketCap) for fetching live data. Utilizes auth interceptors to manage API keys securely.
