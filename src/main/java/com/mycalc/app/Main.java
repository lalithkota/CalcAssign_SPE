package com.mycalc.app;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

class AdvThread extends Thread{
    Socket this_client;
    DataInputStream this_client_dis;
    DataOutputStream this_client_dos;
    ScriptEngine js_engine;
    
    AdvThread(Socket s){
        this_client = s;
    }
    
    @Override
    public void run(){
        try {
            Main.clients.add(this_client);
            this_client_dis = new DataInputStream(this_client.getInputStream());
            this_client_dos = new DataOutputStream(this_client.getOutputStream());
        }
        catch(IOException e){
            //do something
            return;
        }
        
        try{
            js_engine = (new ScriptEngineManager()).getEngineByName("JavaScript");
        }
        catch(NullPointerException e){
            return;
        }
        
        String str;// received
        String sended_value = null;
        while(true){
            
            try{
                sended_value = null;
                if((str = read_one_msg()).equals("exit")) break;
                System.out.println("" + str);
                sended_value = String.valueOf(parse_and_compute(str));
            }
            catch(IOException ioe){
                System.out.println("err1");
            }
            catch(ScriptException se){
                System.out.println("err2");
                sended_value = "Give Proper Expression";
            }
            catch(NullPointerException ne){
                System.out.println("err3");
            }
            catch(Exception e){
                System.out.println("err4" + e);
            }
            
            try{
                if(sended_value!=null) this_client_dos.write((sended_value+"\0").getBytes());
            }
            catch(Exception e){
                System.out.println("err5");
            }
        }
        
    }
    
    double parse_and_compute(String input_str) throws ScriptException {
        return Double.parseDouble(js_engine.eval(input_str).toString());
    }
    
    String read_one_msg() throws IOException{
        int b;
        String str = "";
        while(true){
            if((b = this_client_dis.read())=='\0'){
                break;
            }
            str+=(char)b;
        }
        return str;
    }
}
public class Main{
    static ServerSocket ss;
    static ArrayList<Socket> clients;
    
    static void received_close_server() throws IOException{
        for(Socket s: clients){
            s.close();
        }
        ss.close();
    }
    
    public static void main( String[] args ){
        
        try{
            System.out.println("Server IP: " + args[0]+ " Server Port: " + args[1]);
            ss = new ServerSocket(Integer.valueOf(args[1]),100,InetAddress.getByName(args[0]));
        }
        catch(Exception e){System.out.println(e);return;}
        
        clients = new ArrayList<Socket>();
        while(true){
            try{
                new AdvThread(ss.accept()).start();
                System.out.println("New Client Joined "+ (clients.size()+1));
            }
            catch(IOException e){
                //do something
            }
        }
    }
}
