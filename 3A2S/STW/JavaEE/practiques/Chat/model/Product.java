package model;

public class Product {

	private String category;
	private String model;
	
	public Product(String category, String model) {
		this.category = category;
		this.model = model;
	}

	public String getCategory() {
		return category;
	}

	public String getModel() {
		return model;
	}
	
	@Override
	public String toString() {
		return model;
	}
}
