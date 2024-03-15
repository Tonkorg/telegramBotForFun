package ru.tyaga.telegramBotForFun.client;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.tyaga.telegramBotForFun.exception.ServiceException;

import java.io.IOException;

@Component
public class CBRClient {

    @Autowired
    private OkHttpClient client;


    @Value("${cbr.daily.value.url}")
    private String url;

    public String getCurrencyRatesFXML() throws ServiceException
    {
        var request = new Request.Builder()
                .url(url).build();

      try( var responce =  client.newCall(request).execute();)
      {
            var body = responce.body();
            return body ==null ? null : body.string();
      }
      catch (IOException a)
      {
          throw new ServiceException("Ошибка получения курса валют", a);
      }

    }
}
