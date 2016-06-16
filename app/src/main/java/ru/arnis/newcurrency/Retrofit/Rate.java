package ru.arnis.newcurrency.Retrofit;

public class Rate {
    public String id;
    public double Rate;
    public String Date;
    public String Time;

    public static ru.arnis.newcurrency.Retrofit.Rate assignValuesFromString(String str){
        Rate r = new Rate();
        StringBuilder sb = new StringBuilder();
        int i=0;

        for (char c:str.toCharArray()){
            if (c!='_')
                sb.append(c);
            else {
                i++;
                switch (i){
                    case 1:r.id=sb.toString();sb = new StringBuilder();break;
                    case 2:r.Time=sb.toString();sb = new StringBuilder();break;
                }
            }
        }
        r.Date=sb.toString();
        sb = null;
        return r;
    }
}
