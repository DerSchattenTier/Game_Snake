package com.nemez.myapplication.sssnake;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen);

        //На весь экран
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Установка начальных координат и направления движения
        snake.add(2217);
        snake.add(2117);
        snake.add(2017);
        way = 2;

        //Запуск змейки
        Thread game = new Thread(new Runnable() {
            @Override
            public void run() {

                while (!fail) {
                    try {
                        Thread.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(!fail)
                    runOnUiThread(showSnake);
                }

            }
        });

        game.start();

        //Конец onCreate
    }

    //Системная кнопка Назад
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Screen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //Массив координат
    ArrayList<Integer> snake = new ArrayList<Integer>();

    //Создание рандомайзера для яблок
    Random random = new Random();

    //Проигрыш
    boolean fail = false;

    //Счет
    int count = 0;

    //Скорость игры
    int speed = 500;

    //Проверка на наличие яблок на поле
    int appleCoord = 1010;
    boolean isApple = false;

    //Направление движения
    int way;
    //Направо
    public void right (View view){
        if(way == 2 || way == 8){
            way = 6;
            Button a = (Button) findViewById(R.id.button_2);
            a.setEnabled(false);
            Button b = (Button) findViewById(R.id.button_4);
            b.setEnabled(false);
            Button c = (Button) findViewById(R.id.button_6);
            c.setEnabled(false);
            Button d = (Button) findViewById(R.id.button_8);
            d.setEnabled(false);
        }
    }
    //Налево
    public void left (View view){
        if(way == 2 || way == 8){
            way = 4;
            Button a = (Button) findViewById(R.id.button_2);
            a.setEnabled(false);
            Button b = (Button) findViewById(R.id.button_4);
            b.setEnabled(false);
            Button c = (Button) findViewById(R.id.button_6);
            c.setEnabled(false);
            Button d = (Button) findViewById(R.id.button_8);
            d.setEnabled(false);
        }
    }
    //Наверх
    public void Up (View view){
        if(way == 4 || way == 6){
            way = 2;
            Button a = (Button) findViewById(R.id.button_2);
            a.setEnabled(false);
            Button b = (Button) findViewById(R.id.button_4);
            b.setEnabled(false);
            Button c = (Button) findViewById(R.id.button_6);
            c.setEnabled(false);
            Button d = (Button) findViewById(R.id.button_8);
            d.setEnabled(false);
        }
    }
    //Вниз
    public void Down (View view){
        if(way == 4 || way == 6){
            way = 8;
            Button a = (Button) findViewById(R.id.button_2);
            a.setEnabled(false);
            Button b = (Button) findViewById(R.id.button_4);
            b.setEnabled(false);
            Button c = (Button) findViewById(R.id.button_6);
            c.setEnabled(false);
            Button d = (Button) findViewById(R.id.button_8);
            d.setEnabled(false);
        }
    }

    //Метод передвижения
    void move(){
        //Передвижение вправо
        if(way == 6){
            snake.add(snake.get(snake.size() - 1) + 1);
        }
        //Передвижение вверх
        if(way == 2){
            snake.add(snake.get(snake.size() - 1) - 100);
        }
        //Передвижение влево
        if(way == 4){
            snake.add(snake.get(snake.size() - 1) - 1);
        }
        //Передвижение вниз
        if(way == 8){
            snake.add(snake.get(snake.size() - 1) + 100);
        }

        //Включаем кнопки
        Button a = (Button) findViewById(R.id.button_2);
        a.setEnabled(true);
        Button b = (Button) findViewById(R.id.button_4);
        b.setEnabled(true);
        Button c = (Button) findViewById(R.id.button_6);
        c.setEnabled(true);
        Button d = (Button) findViewById(R.id.button_8);
        d.setEnabled(true);
    }

    //Метод проверки на проигрыш
    void fail(){
        if(way == 2 && snake.get(snake.size()-1) < 1010) {
            fail = true;
        }
        if(way == 4 && (snake.get(snake.size()-1))% 100 < 10) {
            fail = true;
        }
        if(way == 6 && (snake.get(snake.size()-1))% 100 > 25) {
            fail = true;
        }
        if(way == 8 && snake.get(snake.size()-1) > 2525) {
            fail = true;
        }

        int x = snake.get(snake.size() - 1);
        for(int i = 0; i < (snake.size() - 1); i++){
            if(snake.get(i) == x){
                fail = true;
            }
        }

        if (fail)
            end();
    }

    //Создание яблока
    void apple(){

        //Рандом
        while (!isApple) {
            int r1 = random.nextInt(16);
            r1 = (r1 + 10) * 100;
            int r2 = random.nextInt(16);
            r2 = r2 + 10;
            appleCoord = r1 + r2;

            //Проверка на совпадение со змейкой
            for (int s : snake) {
                 if(appleCoord == s){
                     isApple = false;
                     break;
                 }
                 else{
                     isApple = true;
                 }
            }
        }

        //Окраска клетки яблока
        Resources res = getResources();
        String str = "imageView" + appleCoord;
        int ident = res.getIdentifier(str, "id", getPackageName());
        ImageView test = (ImageView) findViewById(ident);
        test.setImageResource(R.drawable.style_apple);

    }

    //Метод отрисовки
    void draw(){

        //Окраска
        for (int s : snake) {
            Resources res = getResources();
            String str = "imageView" + s;
            int ident = res.getIdentifier(str, "id", getPackageName());
            ImageView test = (ImageView) findViewById(ident);
            test.setImageResource(R.drawable.style_snake);
        }

        //Поедание яблока
        if(snake.get(snake.size() - 1) == appleCoord){
            isApple = false;
            count++;
            //Увеличение скорости игры
            speed = speed - 10;
        }

        //Отрезание хвоста
        if(isApple && way < 10) {
            int s = snake.get(0);
            Resources res = getResources();
            String str = "imageView" + s;
            int ident = res.getIdentifier(str, "id", getPackageName());
            ImageView test = (ImageView) findViewById(ident);
            test.setImageResource(R.drawable.style_field);
            snake.remove(0);
        }
    }

    //Конец игры
    void end(){

        //Звуки
        MediaPlayer mPlayer;
        mPlayer = MediaPlayer.create(this, R.raw.end);
        mPlayer.start();

        //Диалоговое окно со счётом
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.end);
        TextView sc = (TextView) dialog.findViewById(R.id.scoreView);
        sc.setText("" + count);
        dialog.show();
    }

    //Пауза
    public void pause(View view){
        if(way ==2 || way ==4 || way ==6 || way ==8)
        way = way*10;
        else if(way == 20)
            way = 2;
        else if(way == 40)
            way = 4;
        else if(way == 60)
            way = 6;
        else if(way == 80)
            way = 8;
    }

    //Выход в меню
    public void menu (View view) {
        Intent intent = new Intent(Screen.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //Игра
    Runnable showSnake = new Runnable() {
        public void run() {

            //Вызов метода передвижения
            move();

            //Вызов проверки на проигрыш
            fail();

            //Проверка наличия яблок на поле и их создание
            if(!isApple)
            apple();

            //Вызов отрисовки
            if(!fail)
            draw();

        }
    };

}