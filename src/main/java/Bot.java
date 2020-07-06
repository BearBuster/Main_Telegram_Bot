import com.google.common.io.Files;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Bot extends TelegramLongPollingBot {

    private Book book = new Book();
    private long chat_id;

    @Override
    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
        chat_id = update.getMessage().getChatId();
        sendMessage.setText(input(update.getMessage().getText()));

        try {
            execute(sendMessage);
        }catch (Exception e){
            System.out.println(e);
        }
    }
    public String input(String msg){
        if(msg.equals("Инфо"))
            return getInfoBook();
        return "1";
    }

    private String getInfoBook() {
        SendPhoto sendPhotoRequest = new SendPhoto();

        try{
            InputStream in = new URL(book.getImg()).openStream();
            OutputStream ot = new FileOutputStream("Img");
            int lenght = 0;
            byte[] buffer = new byte[2048];
            while ((lenght = in.read(buffer)) != -1){
                ot.write(buffer,0,lenght);
            }
            sendPhotoRequest.setChatId(chat_id);
            sendPhotoRequest.setPhoto(new File("img"));
            execute(sendPhotoRequest);
            in.close();
            ot.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            System.out.println(e);
        }
        String info = book.getTitle() + "\nАвтор: " + book.getName() + "\nЖанр: " + book.getGeners() + "\nОписание: " + book.getDiscripntion() + "\nКоличество лайков: " + book.getLikes();
        System.out.println(info);
        return info;
    }

    @Override
    public String getBotUsername() {
        return "@Universe_Moldova_bot";
    }

    @Override
    public String getBotToken() {
        return "1293246377:AAG1D__bgusCj61cgjzgt9PWwTuVmRh_Nhk";
    }
}
