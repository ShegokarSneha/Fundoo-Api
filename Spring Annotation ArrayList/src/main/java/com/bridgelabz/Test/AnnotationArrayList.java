package com.bridgelabz.Test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.bridgelabz.Bean.Mobile;
import com.bridgelabz.Bean.Product;

public class AnnotationArrayList {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("com/bridgelabz/Config/BeanAnnotationArrayList.xml");
		Product product = context.getBean("product", Product.class);
		System.out.println("******* Using Autowired Annotation *******\n");
		List<Mobile> list = product.getListofmobile();
		System.out.println("List Of Mobile:");
		for(Mobile l : list) {
			System.out.println(l.getMobilename());
		}
		
		Mobile[] mobile = product.getArrayofmobile();
		System.out.println("\nArray Of Mobile:");
		for(Mobile m : mobile) {
			System.out.println(m.getMobilename());
		}

	}

}
