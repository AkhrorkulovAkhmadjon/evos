package org.example;

import lombok.SneakyThrows;
import org.example.orsdes.Order;
import org.example.utils.Massage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.example.utils.BotSms.*;

public class MyBot extends TelegramLongPollingBot {
    ArrayList<User> users = new ArrayList<>();
    List<Order> orders = new ArrayList<>();

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null) {
            SendMessage sendMassage = new SendMessage();
            Long id = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            sendMassage.setChatId(id);
            User currentUser = checkingUser(id);
            if (currentUser.getStatus() != null && currentUser.getStatus().equals(XABARYUBORISH)) {
                SendMessage messageAdmin = new SendMessage();
                messageAdmin.setText(text + "\n xabar qoldirgan user Ismi " + currentUser.getUserName() + " \ntel number  " + "+" + currentUser.getTel_number() + "  ID-" + currentUser.getId());
                messageAdmin.setChatId(6847359281l);
                execute(messageAdmin);
                currentUser.setStatus("");
                sendMassage.setText("Xabar yuborildi");
                sendMassage.setReplyMarkup(markup(Massage.MENU));
                execute(sendMassage);
            }
    /*    if (update.getMessage().hasContact()) {
            System.out.println(update.getMessage().getContact());
            sendMassage.setReplyMarkup(markup(Massage.ENTER_LOCATION));
            sendMassage.setText(ENTER_LOCATION);
            currentUser.setStatus(ENTER_LOCATION);
            execute(sendMassage);
            return;
        }
     */
            if (update.getMessage().hasContact()) {
                currentUser.setTel_number(update.getMessage().getContact().getPhoneNumber());
                currentUser.setUserName(update.getMessage().getContact().getFirstName());
                System.out.println(update.getMessage().getContact().getFirstName());
                ReplyKeyboardMarkup res = markup(Massage.ENTER_LOCATION);
                res.getKeyboard().get(0).get(0).setRequestLocation(true);
                sendMassage.setReplyMarkup(res);
                sendMassage.setText("Joylashuvingizni kiriting");
                execute(sendMassage);
                return;
            }
            if (update.getMessage().hasLocation()) {
                text = "menu";
            }
            switch (text) {
                case "/start" -> {
                    sendMassage.setText("Assalomu aleyku avval telefon raqamingizni yuboring ");
                    ReplyKeyboardMarkup resKeyboard = markup(Massage.ENTER_TEL_NUMBER);
                    resKeyboard.getKeyboard().get(0).get(0).setRequestContact(true);
                    sendMassage.setReplyMarkup(resKeyboard);
                    execute(sendMassage);
                }
                case "menu" -> {
                    currentUser.setUserStep(0);
                    currentUser.setLongitude(String.valueOf(update.getMessage().getLocation().getLongitude()));
                    currentUser.setLatitude(String.valueOf(update.getMessage().getLocation().getLatitude()));
                    sendMassage.setText(currentUser.getUserName() + "  quydagilardan birini tanlang");
                    sendMassage.setReplyMarkup(markup(Massage.MENU));
                    execute(sendMassage);
                }
                case MENYU -> {
                    currentUser.setUserStep(1);
                    sendMassage.setText("Tanlang");
                    sendMassage.setReplyMarkup(markup(Massage.MENYU));
                    execute(sendMassage);
                }
                case MENNING_BUYURTMALARIM -> {
                    // kamchiligi bor barcha maxsulotlarni aylanib chiqib Id orqali topib telegramga junatish kk
                    List<Order> myOrder = orders.stream().
                            filter(order -> order.getUserId().equals(id)).toList();
                }
                case SAVAT -> {
                    //   Bunda tanlangan tavarlar ruyxati  turadi
                    sendMassage.setText("Tez orada ishga tushamiz");
                    sendMassage.setChatId(id);
                    sendMassage.setReplyMarkup(markup(Massage.MENU));
                    execute(sendMassage);
                }
                case XABARYUBORISH -> {
                    sendMassage.setText("Adminga xabar qoldiring ");
                    sendMassage.setChatId(id);
                    execute(sendMassage);
                    currentUser.setStatus(XABARYUBORISH);
                }

                case ALOQA -> {
                    SendPhoto sendPhoto = new SendPhoto();
                    sendPhoto.setChatId(id);
                    InputStream inputStream = new FileInputStream("\\C:\\Users\\akhma\\OneDrive\\Pictures\\Screenshots\\photo_2024-04-16_09-27-08.jpg");
                    InputFile fileInputStream = new InputFile(inputStream, "photo");
                    sendPhoto.setPhoto(fileInputStream);
                    sendPhoto.setCaption("""
                            Aloqa uchun telefon raqam:
                            +998 95 888 65 66
                            telegram manzilimiz:
                            @Akhmadjon_1""");
                    sendPhoto.setReplyMarkup(markup(Massage.MENU));
                    execute(sendPhoto);


                }
                case SOZLAMALAR -> {
                    sendMassage.setText("Tez orada ishga tushadi");
                    sendMassage.setChatId(id);
                    sendMassage.setReplyMarkup(markup(Massage.MENU));
                    execute(sendMassage);
                }
                case BIZ_HAQIMIZDA -> {
                    sendMassage.setText("""
                             biz haqimizda buyerdan bilib olasiz 
                            ->>>>>>    https://evos.uz/uz/about/""");
                    sendMassage.setChatId(id);
                    sendMassage.setReplyMarkup(markup(Massage.MENU));
                    execute(sendMassage);

                }
                case SETLAR -> {
                    currentUser.setUserStep(2);
                    sendMassage.setText("Tanlang");
                    sendMassage.setReplyMarkup(markup(Massage.SETLAR));
                    execute(sendMassage);
                }
                case LAVASH -> {
                    currentUser.setUserStep(2);
                    SendPhoto sendPhoto = new SendPhoto();
                    InputStream inputStream = new FileInputStream("C:\\Users\\akhma\\OneDrive\\Pictures\\photo_2024-04-16_09-27-05.jpg");
                    InputFile inputFile = new InputFile(inputStream, "photo");
                    sendPhoto.setPhoto(inputFile);
                    sendPhoto.setChatId(id);
                    sendPhoto.setReplyMarkup(markup(Massage.LAVASH));
                    execute(sendPhoto);
                }
                case SHAURMA -> {
                    SendPhoto sendPhoto = new SendPhoto();
                    InputStream inputStream = new FileInputStream("C:\\Users\\akhma\\OneDrive\\Pictures\\Screenshots\\photo_2023-04-13_11-17-12.jpg");
                    InputFile inputFile = new InputFile(inputStream, "photo");
                    sendPhoto.setPhoto(inputFile);
                    sendPhoto.setReplyMarkup(markup(Massage.LAVASH));
                    sendPhoto.setChatId(id);
                    execute(sendPhoto);
                }
                case ORQAGA -> {
                    int pas = currentUser.getUserStep();
                    if (pas == 1) {
                        currentUser.setUserStep(0);
                        sendMassage.setText(currentUser.getUserName() + "  quydagilardan birini tanlang");
                        sendMassage.setReplyMarkup(markup(Massage.MENU));
                        execute(sendMassage);
                    }
                    if (pas == 2) {
                        currentUser.setUserStep(1);
                        sendMassage.setText("Tanlang");
                        sendMassage.setReplyMarkup(markup(Massage.MENYU));
                        execute(sendMassage);
                    }
                }
            }
        } else if (update.getMessage().getPhoto() != null) {
        } else if (update.getCallbackQuery() != null) {


        }
    }

    private User checkingUser(Long id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        User user = new User();
        user.setId(id);
        users.add(user);
        return user;
    }

    private ReplyKeyboardMarkup markup(String[][] keyboard) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> rows = new ArrayList<>();
        for (String[] strings : keyboard) {
            KeyboardRow row = new KeyboardRow();
            for (String button : strings) {
                row.add(button);
            }
            rows.add(row);
        }
        replyKeyboardMarkup.setKeyboard(rows);
        return replyKeyboardMarkup;
    }

    @Override
    public String getBotUsername() {
        return "t.me/bavariya_bot. ";
    }

    @Override
    public String getBotToken() {
        return "7323711579:AAFAcFSOZMKKpfqBLXHkBNrBrqZCw0n3xhs";
    }

}
