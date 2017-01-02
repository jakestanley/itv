# Checkout

## Assumptions
- data is provided to the application via two comma separated, row delimited files with no header row
- skus.csv would contain SKU references and their normal prices (SKU,PRICE)
- offers.csv would provide offers criteria and will be matched against the data provided in the SKUs file (SKU,QUANTITY,OFFERPRICE)
- SKU item names will be represented with strings and their prices with integers, and they'll be stored in a HashMap<String, Integer> 
- only positive, non-zero SKU prices are accepted
- one offer per SKU is accepted

## Nice-to-haves
- JSON parsing
- JUnit tests
- GUI
- API 
- reflection loading using CSV header rows

# Tools and packages used

## Libraries and frameworks
- [Lombok](http://projectlombok.org) for injecting auto-generated getters, setters, constructors, equals and hashcode methods
- [Spring framework](http://spring.io) for creating a RESTful web app
- JUnit for general tests
- Mockito for testing controllers
- json-smart for constructing mock JSON objects

## Software
- macOS Sierra
- Terminal
- brew
- zsh
- Oh My Zsh
- vim
- maven
- git
- Sourcetree
- Sublime Text 3
- Eclipse (and various plugins)

## Usage
// TODO HTTP response codes on exception

| url                       | verb  | description                                   |
|---------------------------|-------|-----------------------------------------------|
| /cart/new                 | GET   | retrieves a new, empty cart                   |
| /cart/{cartId}/item       | POST  | add an item object to a cart                  |
| /cart/{cartId}/checkout   | GET   | gets the formatted total value of the cart    |
| /offer                    | POST  | add a new special offer                       |
| /sku                      | POST  | add a new SKU                                 |