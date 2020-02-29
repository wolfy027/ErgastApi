
Task 

Create a Spring Boot Service that provides Data for Testing over a existing Rest API. 

  

API: http://ergast.com/mrd/ 

Ergast is a public, open, non-authenticated API that provide historical data over Formula One. 

The API is composed of many Rest Services which can be used freely to achieve the Goal of the task. 

  

Requirements: 

Using Spring boot, we expect two REST services to be created: 

1 - The top 10 most victorious grand-prixs by Nationalities over a range of years 

•    The range of years should be part of the Query String 

•    Response should be done in CSV and JSON (Depending on the param over the Query String) 

•    Result columns are   

o    Rank (1 to 10) 

o    Nationality 

o    Number of Victories 

•    If invalid years, you should return 400 (Bad Request) 

•    Tie-break should be alphabetical order 

  

2 - The average time of pitstops by constructors in a determined year considering a threshold 

•    Threshold value is a maximum accepted average -> Example: threshold = 25s, we will show the averages only of the 25s and less. 

•    We expect a payload for a POST request with the following attributes  

o    year 

o    threshold 

•    Response should be done in CSV and JSON (Depending on the param over the Query String) 

•    Results column are  

o    Rank (1 to X) 

o    Constructor name 

o    Average pit-stop time 

o    Fastest pit-stop time 

o    Slowest pit-stop time 

•    If year is not valid (Before 1950 and after current year), you should return 400 (Bad Request) 

•    If no data for the selected year or the threshold removes all data you should return 204 (No content) 

•    Tie-break should be the constructor with Fastest pit-stop time. 

  

3 - We expect the code to have Tests. 

4 - Project must be a maven/gradle project 

5 - Code should be shared on github. 

6 - (PLUS, not mandatory), Make the application available using a docker image 

 