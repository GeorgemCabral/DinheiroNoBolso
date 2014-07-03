package williamsilva.dinheironobolso.helpers;

/**
 * Created by William on 26/06/2014.
 */
import java.util.Calendar;

public class RelogioHelper {

    private int dia,mes,ano;
    //intancia o calendario da classe nativa Calendar
    private Calendar calendario = Calendar.getInstance();

    public RelogioHelper(){}

    public RelogioHelper(String data) {

        //ultiliza-se subString para trazer dia,mes e ano separadamente 03/02/2014
        if(data.length() == 10) {
            dia = Integer.parseInt(data.substring(0, 2));
            mes = Integer.parseInt(data.substring(3, 5));
            ano = Integer.parseInt(data.substring(6, 10));
        }else if(data.length() == 8)
        {
            dia = Integer.parseInt(data.substring(0, 1));
            mes = Integer.parseInt(data.substring(2, 3));
            ano = Integer.parseInt(data.substring(4, 8));
        }


		/* seto o dia, mes e ano, e no caso do mes ultilizo o mes-1 pois a classe
		 * calendar conta os meses a partir do 0. Então segue abaixo um exemplo.
		 *
		 * 0 = Janeiro;
		 * 1 = Fevereiro;
		 * 2 = Março;
		 * 3 = Abril;
		 * 4 = Maio
		 * 5 = Junho;
		 * 6 = Julho;
		 * 7 = Agosto;
		 * 8 = Setembro;
		 * 9 = Outubro;
		 * 10 = Novembro;
		 * 11 = Dezembro;
		 */
        calendario.set(Calendar.YEAR, ano);
        calendario.set(Calendar.MONTH, mes-1);
        calendario.set(Calendar.DAY_OF_MONTH, dia);

    }
    public int getDia() {
        return dia;
    }
    public void setDia(int dia) {
        this.dia = dia;
    }
    public int getMes() {
        return mes;
    }
    public void setMes(int mes) {
        this.mes = mes;
    }
    public int getAno() {
        return ano;
    }
    public void setAno(int ano) {
        this.ano = ano;
    }
    public Calendar getCalendario() {
        return calendario;
    }
    public void setCalendario(Calendar calendario) {
        this.calendario = calendario;
    }

    public static String dataHoje()
    {
        Integer dia,mes,ano;
        Calendar calendario = Calendar.getInstance();
        dia = calendario.get(Calendar.DAY_OF_MONTH);
        mes = calendario.get(Calendar.MONTH);
        ano = calendario.get(Calendar.YEAR);
        return dia + "/" + (mes+1) + "/" + ano;
    }

    public static String horarioDeAgora()
    {
        Integer hora,minuto,segundo;
        Calendar calendario = Calendar.getInstance();

        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minuto = calendario.get(Calendar.MINUTE);
        segundo = calendario.get(Calendar.SECOND);
        return hora + ":" + minuto + ":" + segundo;
    }

    public String toString()
    {
        return getCalendario().get(Calendar.DAY_OF_MONTH) + "/" +
                (getCalendario().get(Calendar.MONTH)+1) + "/" +
                getCalendario().get(Calendar.YEAR);
    }

}
