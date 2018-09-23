package com.example.arnavdesai.burrp;

import java.util.List;

/**
 * Created by Arnav Desai on 9/20/2018.
 */

public class IndividualMenu {
    public List<String> ItemName,ItemPrice;

    public IndividualMenu() {
    }

    public IndividualMenu(List<String> ItemName, List<String> ItemPrice) {
        this.ItemName = ItemName;
        this.ItemPrice = ItemPrice;
    }

}
