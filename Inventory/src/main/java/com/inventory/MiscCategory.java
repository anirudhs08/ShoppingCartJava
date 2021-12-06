package com.inventory;

import java.util.HashMap;
import java.util.Set;

public class MiscCategory implements Chain{

    private  Chain nextInChainOfResponsibility;
    //for the next handler in the chain of responsibility pattern
    @Override
    public void setNextChainOfResponsibility(Chain nextChain) {
        nextInChainOfResponsibility = nextChain;
    }

    //calculates amount if category matches or else passes it to next handler
    @Override
    public double calculateAmount(HashMap<String, Items> inventry , String item , int quantity, Set<String> error,
                            Set<String> alreadyReadEssentials,Set<String> alreadyReadLuxury,Set<String> alreadyReadMisc)  {
        Items items = inventry.get(item);
        if(items.Category == Category.CATEGORY_MISCELLENEOUS){
            alreadyReadMisc.add(item);
            if(quantity<=items.getQuantity()){
                CalculateTotal.setTotalMisc(quantity);
                items.setQuantity(quantity);
                return quantity* items.getPrice();
            }
            else{
                error.add(item);
                return 0;
            }
        }
        else {
            return nextInChainOfResponsibility.calculateAmount(inventry,item , quantity,error,alreadyReadEssentials,alreadyReadLuxury,alreadyReadMisc);
        }
    }
}
