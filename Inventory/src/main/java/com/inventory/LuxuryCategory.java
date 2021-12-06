package com.inventory;

import java.util.HashMap;
import java.util.Set;

public class LuxuryCategory implements Chain{

    private  Chain nextInChainOfResponsibility;
    //for the next handler in the chain of responsibility pattern
    @Override
    public void setNextChainOfResponsibility(Chain nextChain) {
        nextInChainOfResponsibility = nextChain;
    }

    //calculates amount if category matches or else passes it to next handler
    @Override
    public double calculateAmount(HashMap<String, Items> inventory , String item , int quantity, Set<String> error,
                            Set<String> alreadyReadEssentials,Set<String> alreadyReadLuxury,Set<String> alreadyReadMisc)  {
        Items items = inventory.get(item);
        if(items.Category == Category.CATEGORY_LUXURY){
            alreadyReadLuxury.add(item);
            if( quantity<=items.getQuantity()){
                CalculateTotal.setTotalLuxury(quantity);
                items.setQuantity(quantity);
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
