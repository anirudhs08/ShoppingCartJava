package com.inventory;

import com.inventory.Category;
import com.inventory.Chain;
import com.inventory.CalculateTotal;
import com.inventory.Items;

import java.util.HashMap;
import java.util.Set;

public class EssentialsCategory implements Chain {
    //for the next handler in the chain of responsibility pattern
    private  Chain nextInChainOfResponsibility;
    public void setNextChainOfResponsibility(Chain nextChain) {

        nextInChainOfResponsibility = nextChain;

    }


    //calculates amount if category matches or else passes it to next handler

    public double calculateAmount(HashMap<String, Items> inventory , String item , int quantity, Set<String> error,
          Set<String> alreadyReadEssentials,Set<String> alreadyReadLuxury,Set<String> alreadyReadMisc) {
        Items items = inventory.get(item);

        if(items.Category == Category.CATEGORY_ESSENTIALS){
            alreadyReadEssentials.add(item);
            if(quantity<=items.getQuantity()){
                CalculateTotal.setTotalEssential(quantity);
                items.setQuantity(items.getQuantity()-quantity);
                return quantity* items.getPrice();
            }
			else{
                error.add(item);
                return 0;
            }
        }
        else {
            return nextInChainOfResponsibility.calculateAmount(inventory,item , quantity,error,alreadyReadEssentials,alreadyReadLuxury,alreadyReadMisc);
        }
    }
}