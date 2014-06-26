package williamsilva.dinheironobolso.helpers;

/**
 * Created by William on 26/06/2014.
 */
import java.util.Calendar;

public class ConverterDataHelper {

    private int dia,mes,ano;
    //intancia o calendario da classe nativa Calendar
    private Calendar calendario = Calendar.getInstance();

    public ConverterDataHelper(String data) {

        //ultiliza-se subString para trazer dia,mes e ano separadamente
        dia = Integer.parseInt(data.substring(0,2));
        mes = Integer.parseInt(data.substring(3,5));
        ano = Integer.parseInt(data.substring(6,10));


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

    public String toString()
    {
        return getCalendario().get(Calendar.DAY_OF_MONTH) + "/" +
                (getCalendario().get(Calendar.MONTH)+1) + "/" +
                getCalendario().get(Calendar.YEAR);
    }

}
