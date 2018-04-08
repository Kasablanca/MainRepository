package cn.lm.converter;

import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		try {
/*			if(source==null || "".equals(source.trim())){
				return null;
			}
			String[] date=source.split("-");
			Calendar c=new GregorianCalendar(Integer.parseInt(date[0]), 
					Integer.parseInt(date[1])-1, Integer.parseInt(date[2]));
			return c.getTime();*/
			if(source!=null || !"".equals(source)){
				return new Date(Long.parseLong(source));
			} 
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return null;
	}

}
