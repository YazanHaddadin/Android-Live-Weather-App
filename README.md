# Read Me

The Design I went for to create my weather app was a simple one with today’s weather displayed in a large Text View with the today’s 
day and the current location as well as an icon describing the weather conditions (sunny, cloudy, rainy, or snowy). Also I added a 
MORE button which when clicked opens a scroll view popup window displaying a detailed view of today’s weather forecast such as the 
temperature, humidity, pressure, etc. 

At the Bottom of the screen I added a horizontal scroll view which contains the weather forecast of four more days following today, 
as well as an icon describing the weather conditions during that day; when a day is clicked another scroll view popup window appears 
with a detailed forecast of that day showing the weather, humidity, pressure, etc. 

At the bottom right there is a refresh button which when clicked restarts the activity in order for updated data to re-populate the 
weather forecast and keep the users up to date with the current weather. 

At the top right there is a menu button which when clicked gives the option to change the location, once “Change Location” is clicked 
a new activity is started with an Edit text box where the user can input any valid city he desires and once the user clicks the submit 
button the activity is closed and the main activity is started with the new location the user inputted. There is also the option to go 
back to the main activity without changing the location by clicking the X button which closes the activity without any changes to the 
main activity.

This is a simple weather app which gives all the basic details a user needs to know about a 5 day weather forecast with the ability to 
change the location to any valid city, the design was inspired by a mixture between the iPhone and android built-in weather applications
with additional touches of my own style.

To create this I had to create 5 Java classes, one for the Main Activity which is the layout view for the main page of the weather app, 
one for the Location which is the layout view for the editable text box where the user inputs any valid city, and four for the popup 
windows which are the scroll view layouts for each day of the detailed weather forecasts.

The Main Activity class collects all the weather data and stores them, then sends the data to the other activities through intents, 
after starting each activity they will gather the information and use it to display the correct live data to the users, the application 
is based on sending data between activities.



### Main Activity
Creates the main page of the app and uses JSON parsing to fetch data from an open source API and adds that data to the variables instantiated in the Java class file.
The content view is the activity_main XML layout which is the design of the main page of the application.

### Pop
Creates the popup layout of today’s weather forecast which includes details such as the weather, humidity, pressure, and wind speed.
The content view is the popup_window XML layout which is the design of the popup window.

### Pop2
Creates the popup layout of day two’s weather forecast which includes details such as the weather, humidity, pressure, and wind speed.
The content view is the popup_window2 XML layout which is the design of the popup window.

### Pop3
Creates the popup layout of day three’s weather forecast which includes details such as the weather, humidity, pressure, and wind speed.
The content view is the popup_window3 XML layout which is the design of the popup window.

### Pop4
Creates the popup layout of day four’s weather forecast which includes details such as the weather, humidity, pressure, and wind speed.
The content view is the popup_window4 XML layout which is the design of the popup window.

### Pop5
Creates the popup layout of day five’s weather forecast which includes details such as the weather, humidity, pressure, and wind speed.
The content view is the popup_window5 XML layout which is the design of the popup window.

### Location
Creates the location layout where the user can change the location to any city he desires
The content view is location_window XML layout which is the design of the change location activity.
