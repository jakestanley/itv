# Checkout

## Assumptions
- data is provided to the application via two comma separated, row delimited files with no header row
- skus.csv would contain SKU references and their normal prices (SKU,PRICE)
- offers.csv would provide offers criteria and will be matched against the data provided in the SKUs file (SKU,QUANTITY,OFFERPRICE)
- SKU item names will be represented with strings and their prices with integers, and they'll be stored in a HashMap<String, Integer> 
- purchases will use the "checkout" metaphor and the user // TODO
- only positive, non-zero SKU prices are accepted
- one offer per SKU is accepted

## Nice-to-haves
- JSON parsing
- JUnit tests
- GUI
- API 
- reflection loading using CSV header rows

# Tools and packages used

## Java libraries
- [lombok](http://projectlombok.org)
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
- Eclipse
