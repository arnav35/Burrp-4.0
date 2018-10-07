package com.example.arnavdesai.burrp;

public class Visited {

    public int Mon,Tue,Wed,Thu,Fri,Sat,Sun;
    public int Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec;

    public Visited() {
        Mon = 0;
        Tue = 0;
        Wed = 0;
        Thu = 0;
        Fri = 0;
        Sat = 0;
        Sun = 0;
        Jan = 0;
        Feb = 0;
        Mar = 0;
        Apr = 0;
        May = 0;
        Jun = 0;
        Jul = 0;
        Aug = 0;
        Sep = 0;
        Oct = 0;
        Nov = 0;
        Dec = 0;
    }


    public void update(String weekday_name ,String monthName)
    {
        if(weekday_name.equals("Monday")) this.Mon++;
        if(weekday_name.equals("Tuesday")) this.Tue++;
        if(weekday_name.equals("Wednesday")) this.Wed++;
        if(weekday_name.equals("Thurs")) this.Thu++;
        if(weekday_name.equals("Friday")) this.Fri++;
        if(weekday_name.equals("Saturday")) this.Sat++;
        if(weekday_name.equals("Sunday")) this.Sun++;

        if(monthName.equals("Jan"))   this.Jan++;
        if(monthName.equals("Feb"))   this.Feb++;
        if(monthName.equals("Mar"))   this.Mar++;
        if(monthName.equals("Apr"))   this.Apr++;
        if(monthName.equals("May"))   this.May++;
        if(monthName.equals("Jun"))   this.Jun++;
        if(monthName.equals("Jul"))   this.Jul++;
        if(monthName.equals("Aug"))   this.Aug++;
        if(monthName.equals("Sep"))   this.Sep++;
        if(monthName.equals("Oct"))   this.Oct++;
        if(monthName.equals("Nov"))   this.Nov++;
        if(monthName.equals("Dec"))   this.Dec++;

    }
}
