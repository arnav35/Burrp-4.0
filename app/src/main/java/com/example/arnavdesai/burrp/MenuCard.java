package com.example.arnavdesai.burrp;

import java.util.List;

public class MenuCard {
    List<String> bhaji;

    MenuCard(){

    }

    public MenuCard(List<String> bhaji) {
        this.bhaji = bhaji;
    }

    public List<String> getBhaji() {
        return bhaji;
    }

    public void setBhaji(List<String> bhaji) {
        this.bhaji = bhaji;
    }
}
