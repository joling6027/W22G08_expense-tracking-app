# Expense Tracker

An expense tracker mobile application using Android Studio in Java.

## Introduction
As college students who always live on a tight budget, we are thinking about how to optimize every penny we spend. 
Of course, google spread sheet is a nice tool to write down all our expenses and calculate the balance. 
However, with a handy useful app on your smartphone which is always around you, it will be easier and way more convenient to record and track your expenses.

## Feature Specifications
- Main Page

The pie chart we use at the main page of the app shows the percentage of each category under the time period chosen. 
While clicking on the icon of the category, if there are some expenses and incomes under this category, it will show the total amount of cost.


![alt text](https://github.com/joling6027/W22G08_expense-tracking-app/blob/main/app/src/main/res/drawable/splash.png "splash")
![alt text](https://github.com/joling6027/W22G08_expense-tracking-app/blob/main/app/src/main/res/drawable/mainPage_withoutData.png "main page without data")

- Expense Page

This page allows user to insert expense details. 
By selecting date and category, users can input data into the local database and the corresponding expense will be shown on the main page pie chart.

![alt text](https://github.com/joling6027/W22G08_expense-tracking-app/blob/main/app/src/main/res/drawable/expense_page.png "expense page calculator view")
![alt text](https://github.com/joling6027/W22G08_expense-tracking-app/blob/main/app/src/main/res/drawable/expense_page_cat.png "expense page with category")

- Income Page

This page allows user to insert their source of income. User can use it in the same way as using the expense page.

![alt text](https://github.com/joling6027/W22G08_expense-tracking-app/blob/main/app/src/main/res/drawable/income_page.png "income page")

- Search Page

At the action bar of the main page, while clicking on the search button and insert the category name in the search bar, 
it redirects you to the search page which shows all the details under specific category. Data can be deleted simply by click on the item.

![alt text](https://github.com/joling6027/W22G08_expense-tracking-app/blob/main/app/src/main/res/drawable/search_page.png "search page")

## Technical Concepts used
- ListView
- RecyclerView
- GridView
- CircleView
- SqliteOpenHelper
- Menu
- NavigationBbar/ Drawer layout
- Relatice Layout
- Pie Chart (source: https://github.com/PhilJay/MPAndroidChart)
- SearchView

