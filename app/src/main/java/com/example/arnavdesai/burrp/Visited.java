package com.example.arnavdesai.burrp;

public class Visited {

    public int All,Mon,Tue,Wed,Thu,Fri,Sat,Sun;

    public Visited() {
        Mon = 0;
        Tue = 0;
        Wed = 0;
        Thu = 0;
        Fri = 0;
        Sat = 0;
        Sun = 0;
        All = 0;
    }

    public Visited(int all, int mon, int tue, int wed, int thu, int fri, int sat, int sun) {
        this.All = all;
        this.Mon = mon;
        this.Tue = tue;
        this.Wed = wed;
        this.Thu = thu;
        this.Fri = fri;
        this.Sat = sat;
        this.Sun = sun;
    }

    public void update(int noOfDay)
    {
        this.All++;
        switch(noOfDay){

            case 1:
                this.Mon++;
                break;
            case 2:
                this.Tue++;
                break;
            case 3:
                this.Wed++;
                break;
            case 4:
                this.Thu++;
                break;
            case 5:
                this.Fri++;
                break;
            case 6:
                this.Sat++;
                break;
            case 7:
                this.Sun++;
                break;
        }
    }
}
