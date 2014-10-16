ViewPagerAndTabsExample
=======================

Things why i thought it was worthwhile adding this project to github:
 1 - Makes use of a ContentProvider.
    If you are looking at this page, chances are you are not a veteran Android Developer and everyday i see new developers query the database by opening a connection and whatnot. Remember: everytime you query your SQLite database whithout using a content resolver a puppy dies.
 2 - Has a neat ActionBar tab + ViewPager integration.
    To be honest it's not that neat. You could probably find this code everywhere on the internet.

This project implements a simple ViewPager connected with the ActionBarTabs with 3 fragments.It makes use of a content provider to query an SQLite database. The fragments have a ListView with dummy student records (name and grade), there is one fragment for All the records, the students that passed and the ones that failed.

The number of records can be increased by clicking the LoadMore button at the end of the ListView, it creates another set of dummy data and adds it to the database. This is meant to emulate the use of an API to fetch data from the internet.
