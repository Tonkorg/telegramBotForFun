package ru.tyaga.telegramBotForFun.service;

import ru.tyaga.telegramBotForFun.exception.ServiceException;

public interface ExchangeRatesService {

    String getUSDExchangeRate() throws SecurityException, ServiceException;
    String getEUROxchangeRate() throws SecurityException, ServiceException;

}
