package model;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class CatalogBean {

	private Hashtable<String, List<Product>> catalog;
	private String[] categories = new String[4];
	
	@PostConstruct
	private void init() {
		this.catalog = new Hashtable<String, List<Product>>();
		
		this.categories[0] = "cat 1";
		this.categories[1] = "cat 2";
		this.categories[2] = "cat 3";
		this.categories[3] = "cat 4";
		
		ArrayList<Product> prodCat0 = new ArrayList<Product>();
		ArrayList<Product> prodCat1 = new ArrayList<Product>();
		ArrayList<Product> prodCat2 = new ArrayList<Product>();
		ArrayList<Product> prodCat3 = new ArrayList<Product>();
		
		prodCat0.add(new Product(this.categories[0], "prod 1"));
		prodCat0.add(new Product(this.categories[0], "prod 2"));
		
		prodCat1.add(new Product(this.categories[1], "prod 3"));
		prodCat1.add(new Product(this.categories[1], "prod 4"));
		
		prodCat2.add(new Product(this.categories[2], "prod 5"));
		prodCat2.add(new Product(this.categories[2], "prod 6"));
		
		prodCat3.add(new Product(this.categories[3], "prod 7"));
		prodCat3.add(new Product(this.categories[3], "prod 8"));
		
		this.catalog.put(this.categories[0], prodCat0);
		this.catalog.put(this.categories[1], prodCat1);
		this.catalog.put(this.categories[2], prodCat2);
		this.catalog.put(this.categories[3], prodCat3);
	}

	public List<Product> getCategoryProducts(String category) {
		return this.catalog.get(category);
	}

	public String[] getCategories() {
		return categories;
	}
}
