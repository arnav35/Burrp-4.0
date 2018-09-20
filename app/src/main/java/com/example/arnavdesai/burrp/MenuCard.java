package com.example.arnavdesai.burrp;

public class MenuCard {
    String bhaji1,bhaji2,bhaji3;
    MenuCard(){

    }
    MenuCard(String bhaji1,String bhaji2,String bhaji3){

        this.bhaji1=bhaji1;
        this.bhaji2=bhaji2;
        this.bhaji3=bhaji3;
    }

    public String getBhaji1() {
        return bhaji1;
    }

    public void setBhaji1(String bhaji1) {
        this.bhaji1 = bhaji1;
    }

    public String getBhaji2() {
        return bhaji2;
    }

    public void setBhaji2(String bhaji2) {
        this.bhaji2 = bhaji2;
    }

    public String getBhaji3() {
        return bhaji3;
    }

    public void setBhaji3(String bhaji3) {
        this.bhaji3 = bhaji3;
    }
}
