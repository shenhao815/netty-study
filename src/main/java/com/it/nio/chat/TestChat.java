package com.it.nio.chat;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author ch
 * @date 2021-3-12
 */

// 启动聊天程序客户端
public class TestChat {

    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient();

        new Thread(){
            public void run(){
                while (true) {
                    try {
                        chatClient.receiveMsg();
                        Thread.sleep(2000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.nextLine();
            chatClient.sendMsg(msg);
        }
    }
}
