package vn.edu.ptit.znine.model;

import java.util.Comparator;


public class SortByNew implements Comparator<ProductDetail> {
	@Override
    public int compare(ProductDetail item1, ProductDetail item2) {
    	return Integer.compare(item2.getIdB(), item1.getIdB());
    }
}