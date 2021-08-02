package keyboard.works;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import keyboard.works.entity.ProductInOutTransaction;

@SpringBootTest
public class FifoOutTest {
	
	List<ProductInOutTransaction> ins = new ArrayList<>();
	
	@BeforeEach
	public void setUp() {
		
		ins.addAll(
			Stream.iterate(new BigDecimal[] {BigDecimal.ONE, BigDecimal.ZERO}, pair -> {
					return new BigDecimal[] {pair[0].add(pair[1]), pair[0]};
				})
				.limit(8)
				.map(pair -> pair[0])
				.map(fibo -> {
					ProductInOutTransaction in = new ProductInOutTransaction();
					in.setQuantity(fibo);
					in.setQuantityLeft(fibo);
					
					return in;
				}).collect(Collectors.toList()));
		
	}

	@Test
	public void fifoOutCreateTest() {
		
		List<ProductInOutTransaction> outs = new ArrayList<>();
		
		BigDecimal issued = new BigDecimal(10L);
		
		for(ProductInOutTransaction in : ins){
			
			BigDecimal difference = issued.subtract(in.getQuantityLeft());

			ProductInOutTransaction out = new ProductInOutTransaction();
			out.setQuantity(difference.compareTo(BigDecimal.ZERO) >= 0 ? in.getQuantityLeft() : issued);
			
			outs.add(out);
			
			in.setQuantityLeft(difference.compareTo(BigDecimal.ZERO) >= 0 ? BigDecimal.ZERO : in.getQuantityLeft().subtract(issued));
			issued = difference;
			
			if(difference.compareTo(BigDecimal.ZERO) <= 0)
				break;
			
		}
		
		ins.forEach(in -> {
			System.out.println("IN " + in.getQuantity() + " " + in.getQuantityLeft());
		});

		outs.forEach(out -> {
			System.out.println("OUT " + out.getQuantity());
		});
		
	}
	
}
