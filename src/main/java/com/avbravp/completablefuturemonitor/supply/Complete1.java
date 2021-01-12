/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravp.completablefuturemonitor.supply;

import com.avbravo.jmoordbfx.JmoordbFxUtil;

import static com.avbravo.jmoordbfx.JmoordbFxUtil.linuxPackets;
import static com.avbravo.jmoordbfx.JmoordbFxUtil.linuxRtt;
import static com.avbravo.jmoordbfx.JmoordbFxUtil.windowsEnglishPackets;
import static com.avbravo.jmoordbfx.JmoordbFxUtil.windowsEnglishRtt;
import static com.avbravo.jmoordbfx.JmoordbFxUtil.windowsSpanishPackets;
import static com.avbravo.jmoordbfx.JmoordbFxUtil.windowsSpanishRtt;
import com.avbravo.jmoordbfx.configuration.PingSetup;
import com.avbravo.jmoordbfx.ping.Group;
import com.avbravo.jmoordbfx.ping.Host;
import com.avbravo.jmoordbfx.ping.Packets;
import com.avbravo.jmoordbfx.ping.Rtt;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.swing.JOptionPane;

/**
 *
 * @author avbravo
 */
public class Complete1 {

    private Integer count = 0;
    List<CompletableFuture<Host>> futureList = new ArrayList<>();

    void completableFutureSupplyAsync() throws InterruptedException, ExecutionException {
        try {
            PingSetup pingSetup = new PingSetup.Builder()
                    .withPackets(4)
                    .withAttemptsGraph("")
                    .withLossGraph("")
                    .withPacketsGraph("")
                    .withRttLeaderBoardGraph("")
                    .withRttMatrixGraph("")
                    .build();

            Group group = new Group("local", "local");

            Host host = new Host.Builder()
                    .withHost("192.168.0.3")
                    .withDescription("mi movil")
                    .withGroup(group)
                    .withOk(0)
                    .withFailure(0)
                    .withShow("on")
                    .build();
            Host google = new Host.Builder()
                    .withHost("8.8.8.8")
                    .withDescription("www.google.com")
                    .withGroup(group)
                    .withOk(0)
                    .withFailure(0)
                    .withShow("on")
                    .build();
            Host yahoo = new Host.Builder()
                    .withHost("yahoo.com")
                    .withDescription("www.yahoo.com")
                    .withGroup(group)
                    .withOk(0)
                    .withFailure(0)
                    .withShow("on")
                    .build();
            Host movil2 = new Host.Builder()
                    .withHost("192.168.0.2")
                    .withDescription("movil 2")
                    .withGroup(group)
                    .withOk(0)
                    .withFailure(0)
                    .withShow("on")
                    .build();
            Host ebay = new Host.Builder()
                    .withHost("ebay.com")
                    .withDescription("ebay")
                    .withGroup(group)
                    .withOk(0)
                    .withFailure(0)
                    .withShow("on")
                    .build();

            List<Host> hostList = new ArrayList<>();
            hostList.add(host);
            hostList.add(google);
            hostList.add(yahoo);
            hostList.add(movil2);
            hostList.add(ebay);
            
            System.out.println("Falta atrapaar el error del autcomplete hya un link que lo expkica");
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    System.out.println("_______________________________________________");
                    System.out.println("Running: " + new java.util.Date());
                    System.out.println("_______________________________________________");
                    // https://www.baeldung.com/java-completablefuture 
                    IntStream.range(0, hostList.size()).forEach(i -> {
                        try {
                            System.out.println("count "+count);
                            //  TimeUnit.SECONDS.sleep(1);
                            System.out.println("____________IntStream (" + i + ") at " + JmoordbFxUtil.getFechaHoraActual() + "____________________________");
                            futureList = new ArrayList<>();

                            for (Host h : hostList) {
                                CompletableFuture<Host> hostFuture = CompletableFuture.supplyAsync(() -> isReachableAutoincrementable(h.getHost(), h, pingSetup));
                                futureList.add(hostFuture);
                            }
                            CompletableFuture<Void> combinedAll
                                    = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[futureList.size()]));

                            combinedAll.get();

                        } catch (InterruptedException ex) {
                            Logger.getLogger(Complete1.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ExecutionException ex) {
                            Logger.getLogger(Complete1.class.getName()).log(Level.SEVERE, null, ex);
                        }

                        System.out.println(" ||-----Proessando los resultados at " + JmoordbFxUtil.getFechaHoraActual());
                        for (CompletableFuture<Host> cf : futureList) {
                            try {
                                Host h;

                                h = cf.get();

 
                                System.out.println(h.toString());
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Complete1.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ExecutionException ex) {
                                Logger.getLogger(Complete1.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        count++;
                    });

                }
            }, 0, 3000);

        } catch (Exception e) {
            System.out.println("CompletableFuture.supplyAsync(() " + e.getLocalizedMessage());
        }

//	assertEquals(100, someStateVaribale.get());
    }

    public Host isReachableAutoincrementable(String ipAddress, Host host, PingSetup setup) {
        try {

//            System.out.println("=======================================================");
//            System.out.println("Start (*) Host " + host.getHost() + " at " + JmoordbFxUtil.getFechaHoraActual());
//            System.out.println("=======================================================");
            host = isReachablePacketsAndRtt(ipAddress, host, setup);
//            System.out.println("End (*) Host " + host.getHost() + " at" + JmoordbFxUtil.getFechaHoraActual());
//            System.out.println("====================RESULT===================================");
//            System.out.println(" (*) Host " + host.getHost() + " at " + JmoordbFxUtil.getFechaHoraActual());
//            System.out.println(" (*) Packets " + host.getPackets().toString());
//            System.out.println(" (*) Rtt " + host.getRtt().toString());
//            System.out.println("=======================================================");

        } catch (Exception e) {
            System.out.println("isReachableAutoincrementable() " + e.getLocalizedMessage());
        }

        return host;
    }

    public static Host isReachablePacketsAndRtt(String ipAddress, Host host, PingSetup setup) {

        try {
            Packets packets = new Packets.Builder()
                    .withError(0.0)
                    .withLoss(0)
                    .withPercentage(0.0)
                    .withPipe(0.0)
                    .withTransmitted(0)
                    .withTime(0.0)
                    .withLoss(0)
                    .build();

            Rtt rtt = new Rtt.Builder()
                    .withAvg(0.0)
                    .withMax(0.0)
                    .withMin(0.0)
                    .withMdev(0.0)
                    .build();

            List<String> command = buildCommandPing(ipAddress, setup);
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();

            try (BufferedReader standardOutput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                if (standardOutput == null) {

                } else {

                    while ((line = standardOutput.readLine()) != null) {

                        line = line.toLowerCase().trim();

                        //   System.out.println(" searching.... " + line);
                        if (line.contains("packets transmitted")) {
                            packets = linuxPackets(line);
                            //valido
                        } else {
                            if (line.contains("packets: sent")) {
                                packets = windowsEnglishPackets(line);
                            } else {
                                if (line.contains("paquetes:")) {
                                    packets = windowsSpanishPackets(line);
                                } else {
                                    if (line.contains("minimum =")) {
                                        rtt = windowsEnglishRtt(line);
                                    } else {
                                        if (line.contains("mínimo =")) {
                                            rtt = windowsSpanishRtt(line);
                                        } else {
                                            if (line.contains("rtt min/avg/max/mdev")) {
                                                rtt = linuxRtt(line);
                                            } else {

                                            }
                                        }
                                    }
                                }
                                //        System.out.println("(No contiene para Windows o Linux)");
                            }
                        }

                    }

                }

            }
            host.setPackets(packets);
            host.setRtt(rtt);
            if (host.getPackets().getPercentage().equals(100) || (host.getPackets().getTransmitted().equals(host.getPackets().getLoss()))) {
                host.setFailure(host.getFailure() + 1);
            } else {

                host.setOk(host.getOk() + 1);
            }

        } catch (Exception e) {
            System.out.println("---------------------------------------------");
            System.out.println(" isReachable() " + e.getLocalizedMessage());
            System.out.println("---------------------------------------------");
        }
        return host;
    }

    // <editor-fold defaultstate="collapsed" desc="List<String> buildCommandPing(String ipAddress)">
    public static List<String> buildCommandPing(String ipAddress, PingSetup setup) {

        List<String> command = new ArrayList<>();
        command.add("ping");
        try {
            if (JmoordbFxUtil.isWindows()) {
                command.add("-n");
            } else {
                if (JmoordbFxUtil.isMac() || JmoordbFxUtil.isLinux() || JmoordbFxUtil.isSolaris()) {
                    command.add("-c");
                } else {
//                    informationDialog(ipAddress, ipAddress, ipAddress);
//                    throw new UnsupportedOperationException("Unsupported operating system");
                    JOptionPane.showMessageDialog(null, "Unsupported operating system");
                }
            }
//            command.add("1");
            command.add(setup.getPackets().toString().trim());

            command.add(ipAddress);
        } catch (Exception e) {
            System.out.println("buildCommandPing() " + e.getLocalizedMessage());
        }

        return command;
    }    // </editor-fold>
}
