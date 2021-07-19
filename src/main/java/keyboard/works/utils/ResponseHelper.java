package keyboard.works.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import keyboard.works.SpringAppContext;

public class ResponseHelper {

	public static <T, S> T createResponse(Class<T> targetClazz, S source) {
		ModelMapper modelMapper = SpringAppContext.getContext().getBean(ModelMapper.class);
		return modelMapper.map(source, targetClazz);
	}
	
	public static <T, S> List<T> createResponses(Class<T> targetClazz, List<S> sources) {
		
		List<T> list = sources.stream()
			.map(source -> {
				return createResponse(targetClazz, source);
			})
			.collect(Collectors.toList());
		
		return list;
	}
	
}
