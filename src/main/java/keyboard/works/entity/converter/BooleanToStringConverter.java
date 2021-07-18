package keyboard.works.entity.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean attribute) {
		
		if(attribute != null)
			return attribute ? "Y" : "N";
		
		return null;
	}

	@Override
	public Boolean convertToEntityAttribute(String dbData) {
		
		if(dbData != null)
			switch (dbData) {
			case "Y": return true;
			case "N": return false;
			}
		
		return null;
	}

}
