package com.mycalc.app;

import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.Scanner;



public class Main{
    
    static String read_one_msg(DataInputStream dis) throws IOException{
        int b;
        String str = "";
        while(true){
            if((b = dis.read())=='\0'){
                break;
            }
            str+=(char)b;
        }
        return str;
    }
    
    static Socket s_main;
    static DataInputStream dis;
    static DataOutputStream dos;
    public static void main( String[] args ){
        try{
            s_main = new Socket(args[0],Integer.valueOf(args[1]));
            dis = new DataInputStream(s_main.getInputStream());
            dos = new DataOutputStream(s_main.getOutputStream());
        }
        catch(IOException ie){
            System.out.println("Cannot connect to server with these values of ip and port");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);
        String str_send,str_recv;
        while(true){
            System.out.print("> ");
            str_send = scanner.next();
            
            try{
                dos.write((str_send+"\0").getBytes());
                //dis.read();
            }
            catch(IOException io){
                System.out.println("Server Quit: 1");
                return;
            }
            if(str_send.equals("exit") || str_send.equals("admin:exit")){
                try{s_main.close();}catch(IOException e){}
                return;
            }
            
            try{
                str_recv = read_one_msg(dis);
            }
            catch(IOException io){
                System.out.println("Server Quit: 2");
                return;
            }
            
            System.out.println("" + str_recv);
        }
        
        
    }
}
