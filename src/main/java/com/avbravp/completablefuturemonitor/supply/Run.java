/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravp.completablefuturemonitor.supply;

import java.util.concurrent.ExecutionException;

/**
 *
 * @author avbravo
 */
public class Run {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // TODO code application logic here
       Complete c = new Complete();
       c.completableFutureSupplyAsync();
        
                
    }
    

    
}
