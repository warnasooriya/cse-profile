package com.stock.soft.socksoft.util;

import pl.allegro.finance.tradukisto.MoneyConverters;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class toWords {
     public static String convert(BigDecimal number){
         return MoneyConverters.ENGLISH_BANKING_MONEY_VALUE.asWords(number.setScale(2, RoundingMode.HALF_UP)).split("£")[0];
     }
}
