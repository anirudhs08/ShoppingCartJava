package com.inventory;
import java.util.HashMap;
import java.util.Set;

public interface Chain {

    public void setNextChainOfResponsibility(Chain nextChain);

    public double calculateAmount(HashMap<String, Items> inventry , String item , int quantity, Set<String> error,
                            Set<String> alreadyReadEssentials,Set<String> alreadyReadLuxury,Set<String> alreadyReadMisc);


}
