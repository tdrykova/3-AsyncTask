package ru.andrush.task3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class MainActivity extends Activity {

    private TextView mInfoTextView;
    private CatTask catTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInfoTextView = findViewById(R.id.info_text);
      //  mProgressBar = findViewById(R.id.progressbar);
     //   mProgressBar.setVisibility(View.INVISIBLE);

        catTask = (CatTask) getLastNonConfigurationInstance();
        if (catTask == null) {
            catTask = new CatTask();
            catTask.execute("m1", "m2", "m3", "m4", "m5");
        }
        // передаем в MyTask ссылку на текущее MainActivity
        catTask.link(this);
    }

    public Object onRetainNonConfigurationInstance() {
        // удаляем из MyTask ссылку на старое MainActivity
        catTask.unLink();
        return catTask;
    }

    class CatTask extends AsyncTask<String, String, Integer> {

        MainActivity activity;

        // получаем ссылку на MainActivity
        void link(MainActivity act) {
            activity = act;
        }

        // обнуляем ссылку
        void unLink() {
            activity = null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }


        @Override
        protected Integer doInBackground(String... urls) {
            int counter = 0;
            try {
                counter = 0;

                for (String url : urls) {
                    // загружаем файл или лезем на другой этаж
                    getFloor(counter);
                    counter++;
                    // выводим промежуточные результаты
                    publishProgress(counter + " Сообщение: " + url);
                }

                //TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return counter;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            activity.mInfoTextView.setText("Этаж: " + values[0]);
        }


        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
        }

        private void getFloor(int floor) throws InterruptedException {
            TimeUnit.SECONDS.sleep(2);
        }

    }

}